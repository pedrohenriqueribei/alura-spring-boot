package voll.med.medical.domain.consulta.validacoes.agendamento;

import org.springframework.stereotype.Component;
import voll.med.medical.domain.ValidacaoException;
import voll.med.medical.domain.consulta.DadosAgendamentoConsulta;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidacaoHorarioAntecedenciaAgendamento")
public class ValidacaoHorarioAntecedencia implements ValidacaoDeRegradeNegociosDoAgendamente {

    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();
        var diferenca = Duration.between(agora, dataConsulta).toMinutes();

        if(diferenca < 30){
            throw new ValidacaoException("Consulta deve ser agendada com antecedencia minima de 30 minutos");
        }
    }
}
