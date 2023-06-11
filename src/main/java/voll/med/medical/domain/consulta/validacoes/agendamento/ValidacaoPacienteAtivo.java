package voll.med.medical.domain.consulta.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import voll.med.medical.domain.ValidacaoException;
import voll.med.medical.domain.consulta.DadosAgendamentoConsulta;
import voll.med.medical.domain.paciente.PacienteRepository;

@Component
public class ValidacaoPacienteAtivo implements ValidacaoDeRegradeNegociosDoAgendamente {

    @Autowired
    private PacienteRepository repository;


    @Override
    public void validar(DadosAgendamentoConsulta dados) {

        var pacienteAtivo = repository.findAtivoById(dados.idPaciente());

        if(!pacienteAtivo){
            throw new ValidacaoException("Consulta não pode ser agendada com paciente desativado");
        }
    }
}
