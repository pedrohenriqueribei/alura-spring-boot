package voll.med.medical.domain.consulta.validacoes.cancelamento;

import voll.med.medical.domain.consulta.DadosCancelamentoConsulta;

public interface ValidacaoDeRegrasDeNegocioDoCancelamento {

    void validar(DadosCancelamentoConsulta dados);
}
