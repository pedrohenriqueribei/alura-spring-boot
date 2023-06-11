package voll.med.medical.domain.consulta;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import voll.med.medical.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(

        @JsonAlias({"idMedico", "id_medico", "medico_id"})
        Long idMedico,

        @JsonAlias({"idPaciente", "id_paciente", "paciente_id"})
        @NotNull
        Long idPaciente,

        @Future @NotNull
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime data,

        Especialidade especialidade

) {
}
