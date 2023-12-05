package rdias.com.agenda.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rdias.com.agenda.domain.entity.Agenda;
import rdias.com.agenda.domain.entity.Paciente;
import rdias.com.agenda.domain.repository.AgendaRepository;
import rdias.com.agenda.exception.BusinessException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AgendaService {

    private final AgendaRepository repository;
    private final PacienteService pacienteService;

    public AgendaService(AgendaRepository repository, PacienteService pacienteService) {
        this.repository = repository;
        this.pacienteService = pacienteService;
    }

    public List<Agenda> listarTodos() {
        return repository.findAll();
    }

    public Optional<Agenda> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Agenda salvar(Agenda agenda) {
        //TODO: para validar as regras de negocio
        // 1. validar se o Paciente existe
        // 2. validar o horario

        // 1. validar se o Paciente existe
        Optional<Paciente> optPaciente = pacienteService.buscarPorId(agenda.getPaciente().getId());
        if (optPaciente.isEmpty()) {
            throw new BusinessException("Paciente não encontrado");
        }

        // 2. validar o horario
        Optional<Agenda> optHorario = repository.findByHorario(agenda.getHorario());
        if (optHorario.isEmpty()) {
            throw new BusinessException("Já existe agendamento para este horário");
        }

        agenda.setPaciente(optPaciente.get());
        agenda.setDataCriacao(LocalDateTime.now());

        return repository.save(agenda);
    }


}
