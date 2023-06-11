package voll.med.medical.domain.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(Long id, Long idMedico, Long idPaciente, @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime data, MotivoCancelamento motivoCancelamento) {

    public DadosDetalhamentoConsulta(Consulta consulta){
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData(), consulta.getMotivoCancelamento());
    }
}
