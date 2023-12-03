package rdias.com.agenda.api.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import rdias.com.agenda.api.request.PacienteRequest;
import rdias.com.agenda.api.response.PacienteResponse;
import rdias.com.agenda.domain.entity.Paciente;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PacienteMapper {

    private final ModelMapper mapper;
    public PacienteMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }


    public Paciente toPaciente(PacienteRequest request){
        return mapper.map(request, Paciente.class);
    }

    public PacienteResponse toPacienteResponse(Paciente paciente){
        return mapper.map(paciente, PacienteResponse.class);
    }

    public List<PacienteResponse> toPacienteResponseList(List<Paciente> pacientes){
        return pacientes.stream()
                .map(this::toPacienteResponse)
                .collect(Collectors.toList());
    }



}
