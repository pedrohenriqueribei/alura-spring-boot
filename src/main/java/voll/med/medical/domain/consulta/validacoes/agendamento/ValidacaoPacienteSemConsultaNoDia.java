package voll.med.medical.domain.consulta.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import voll.med.medical.domain.ValidacaoException;
import voll.med.medical.domain.consulta.ConsultaRepository;
import voll.med.medical.domain.consulta.DadosAgendamentoConsulta;

@Component
public class ValidacaoPacienteSemConsultaNoDia implements ValidacaoDeRegradeNegociosDoAgendamente {

    @Autowired
    private ConsultaRepository repository;

    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        var primeiroHorario = dados.data().withHour(7);
        var ultimoHorario = dados.data().withHour(18);

        var pacientePossuiOutraConsulta = repository.existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiroHorario, ultimoHorario);

        if(pacientePossuiOutraConsulta) {
            throw new ValidacaoException("Paciente já possui uma consulta neste dia");
        }
    }
}
