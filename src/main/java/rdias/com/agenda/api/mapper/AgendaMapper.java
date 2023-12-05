package rdias.com.agenda.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import rdias.com.agenda.api.request.AgendaRequest;
import rdias.com.agenda.api.response.AgendaResponse;
import rdias.com.agenda.domain.entity.Agenda;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AgendaMapper {

    private final ModelMapper mapper;
    public AgendaMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Agenda toAgenda(AgendaRequest request){
        return mapper.map(request, Agenda.class);
    }

    public AgendaResponse toAgendaResponse(Agenda agenda){
        return mapper.map(agenda, AgendaResponse.class);
    }

    public List<AgendaResponse> toAgendaResponseList(List<Agenda> agenda){
        return agenda.stream()
                .map(this::toAgendaResponse)
                .collect(Collectors.toList());
    }
}
