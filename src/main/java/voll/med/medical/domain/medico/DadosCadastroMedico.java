package voll.med.medical.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import voll.med.medical.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(
        @NotNull
        @NotBlank
        String nome,
        @NotNull
        @NotBlank
        @Email
        String email,
        @NotBlank
        String telefone,
        @NotNull
        @Pattern(regexp = "\\d{4,6}")
        String crm,
        Especialidade especialidade,
        @NotNull
        @Valid
        DadosEndereco endereco) {
}
