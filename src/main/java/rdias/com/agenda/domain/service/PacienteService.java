package rdias.com.agenda.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rdias.com.agenda.domain.entity.Paciente;
import rdias.com.agenda.domain.repository.PacienteRepository;
import rdias.com.agenda.exception.BusinessException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PacienteService {

    private final PacienteRepository repository;
    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    public Paciente salvar(Paciente paciente){
        // adicionar regra de negocio para não deixar cadastrar pacientes com mesmo cpf
        Boolean existeCpf = false;
        Optional<Paciente> optPaciente =  repository.findByCpf(paciente.getCpf());

        if(optPaciente.isPresent()){
            if(!optPaciente.get().getId().equals(paciente.getId())){
                existeCpf = true;
            }
        }

        if(existeCpf){
            throw new BusinessException("Cpf já cadastrado");
        }

        return repository.save(paciente);
    }


    public List<Paciente> listarTodos(){
        return repository.findAll();
    }

    public Optional<Paciente> buscarPorId(Long  id){
        return repository.findById(id);
    }

    public void deletar(Long id){
        repository.deleteById(id);
    }



}
