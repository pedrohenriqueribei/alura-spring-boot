package voll.med.medical.domain.consulta.validacoes.agendamento;


import org.springframework.stereotype.Component;
import voll.med.medical.domain.ValidacaoException;
import voll.med.medical.domain.consulta.DadosAgendamentoConsulta;

import java.time.DayOfWeek;

@Component
public class ValidacaoHorarioFuncionamento implements ValidacaoDeRegradeNegociosDoAgendamente {
    @Override
    public void validar(DadosAgendamentoConsulta dados){
        var dataConsulta = dados.data();

        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAbertura = dataConsulta.getHour() < 7;
        var depoisDoFechamento = dataConsulta.getHour() > 18;

        if(domingo || antesDaAbertura || depoisDoFechamento){
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica");
        }
    }
}
