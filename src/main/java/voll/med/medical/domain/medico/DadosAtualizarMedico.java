package voll.med.medical.domain.medico;

import jakarta.validation.constraints.NotNull;
import voll.med.medical.domain.endereco.DadosEndereco;

public record DadosAtualizarMedico(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}
