package voll.med.medical.domain.consulta.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import voll.med.medical.domain.ValidacaoException;
import voll.med.medical.domain.consulta.ConsultaRepository;
import voll.med.medical.domain.consulta.DadosAgendamentoConsulta;

@Component
public class ValidacaoMedicoConsultaNoMesmoDia implements ValidacaoDeRegradeNegociosDoAgendamente {
    @Autowired
    private ConsultaRepository repository;

    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        var medicoPossuiConsultaNoMesmoDia = repository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dados.idMedico(),dados.data());
        if(medicoPossuiConsultaNoMesmoDia){
            throw new ValidacaoException("Médico já possui consulta marcada no mesmo dia e horário");
        }
    }
}
