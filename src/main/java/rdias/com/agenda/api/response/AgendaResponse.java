package rdias.com.agenda.api.response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rdias.com.agenda.domain.entity.Paciente;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgendaResponse {

    private Long id;
    private String descricao;
    private LocalDateTime horario;
    private PacienteResponse paciente;
}
