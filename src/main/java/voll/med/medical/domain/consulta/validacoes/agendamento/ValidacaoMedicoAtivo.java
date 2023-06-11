package voll.med.medical.domain.consulta.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import voll.med.medical.domain.ValidacaoException;
import voll.med.medical.domain.consulta.DadosAgendamentoConsulta;
import voll.med.medical.domain.medico.MedicoRepository;

@Component
public class ValidacaoMedicoAtivo implements ValidacaoDeRegradeNegociosDoAgendamente {

    @Autowired
    private MedicoRepository repository;


    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        //escolha do médico é opcional
        if(dados.idMedico() == null){
            return;
        }


        var medicoAtivo = repository.findAtivoById(dados.idMedico());

        if(!medicoAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com um médico inativo");
        }
    }
}
