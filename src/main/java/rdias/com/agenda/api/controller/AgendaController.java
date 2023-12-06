package rdias.com.agenda.api.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rdias.com.agenda.api.mapper.AgendaMapper;
import rdias.com.agenda.api.request.AgendaRequest;
import rdias.com.agenda.api.response.AgendaResponse;
import rdias.com.agenda.domain.entity.Agenda;
import rdias.com.agenda.domain.service.AgendaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/agenda")
public class AgendaController {

    private final AgendaService service;
    private final AgendaMapper mapper;
    public AgendaController(AgendaService service, AgendaMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }


    @GetMapping
    public ResponseEntity<List<AgendaResponse>> buscarTodos(){
        List<Agenda> agendas = service.listarTodos();
        List<AgendaResponse> agendaResponses = mapper.toAgendaResponseList(agendas);
        return ResponseEntity.status(HttpStatus.OK).body(agendaResponses);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AgendaResponse> buscarPorId(@PathVariable Long id){
        Optional<Agenda> optAgenda = service.buscarPorId(id);
        if(optAgenda.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        AgendaResponse agendaResponse = mapper.toAgendaResponse(optAgenda.get());
        return ResponseEntity.status(HttpStatus.OK).body(agendaResponse);

    }

    @PostMapping
    public ResponseEntity<AgendaResponse> salvar(@Valid @RequestBody AgendaRequest request){
        Agenda agenda = mapper.toAgenda(request);
        Agenda AgendaSalva = service.salvar(agenda);
        AgendaResponse agendaResponse = mapper.toAgendaResponse(AgendaSalva);
        return ResponseEntity.status(HttpStatus.CREATED).body(agendaResponse);
    }
}
