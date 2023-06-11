package voll.med.medical.domain.consulta;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoConsulta(
        @NotNull
        @JsonAlias({"idConsulta", "id_consulta"})
        Long idConsulta,

        @NotNull
        MotivoCancelamento motivo
) {
}
