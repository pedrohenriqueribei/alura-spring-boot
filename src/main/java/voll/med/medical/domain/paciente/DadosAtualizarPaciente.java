package voll.med.medical.domain.paciente;

import jakarta.validation.constraints.NotNull;
import voll.med.medical.domain.endereco.DadosEndereco;

public record DadosAtualizarPaciente(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}
