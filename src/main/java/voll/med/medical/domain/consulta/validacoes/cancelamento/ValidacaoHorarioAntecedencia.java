package voll.med.medical.domain.consulta.validacoes.cancelamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import voll.med.medical.domain.ValidacaoException;
import voll.med.medical.domain.consulta.Consulta;
import voll.med.medical.domain.consulta.ConsultaRepository;
import voll.med.medical.domain.consulta.DadosCancelamentoConsulta;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidacaoHorarioAntecendenciaCancelamento")
public class ValidacaoHorarioAntecedencia implements ValidacaoDeRegrasDeNegocioDoCancelamento {

   @Autowired
   private ConsultaRepository repository;

    @Override
    public void validar(DadosCancelamentoConsulta dados) {
        Consulta consulta = repository.getReferenceById(dados.idConsulta());
        var agora = LocalDateTime.now();
        var diferencaHr = Duration.between(agora, consulta.getData()).toHours();

        if(diferencaHr < 24) {
            throw new ValidacaoException("Consulta não pode ser desmarcada com menos de 24h de antecedência");
        }
    }
}
