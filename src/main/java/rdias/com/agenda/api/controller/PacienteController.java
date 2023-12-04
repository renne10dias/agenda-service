package rdias.com.agenda.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rdias.com.agenda.api.mapper.PacienteMapper;
import rdias.com.agenda.api.request.PacienteRequest;
import rdias.com.agenda.api.response.PacienteResponse;
import rdias.com.agenda.domain.entity.Paciente;
import rdias.com.agenda.domain.service.PacienteService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    private final PacienteService service;
    private final PacienteMapper mapper;

    public PacienteController(PacienteService service, PacienteMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<PacienteResponse> salvar(@Valid @RequestBody PacienteRequest request){
        Paciente paciente = mapper.toPaciente(request);
        Paciente pacienteSalvo = service.salvar(paciente);
        PacienteResponse pacienteResponse = mapper.toPacienteResponse(pacienteSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteResponse);
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponse>> listarTodos(){
        List<Paciente> pacientes = service.listarTodos();
        List<PacienteResponse> pacienteResponses = mapper.toPacienteResponseList(pacientes);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteResponses);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponse> buscarPorId(@PathVariable Long id){
        Optional<Paciente> optPaciente = service.buscarPorId(id);

        if(optPaciente.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toPacienteResponse(optPaciente.get()));
    }


    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponse> alterar(@PathVariable Long id, @RequestBody PacienteRequest request){
        Paciente paciente = mapper.toPaciente(request);
        Paciente pacienteSalvo = service.alterar(id, paciente);
        PacienteResponse pacienteResponse = mapper.toPacienteResponse(pacienteSalvo);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

























