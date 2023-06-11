package voll.med.medical.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import voll.med.medical.domain.endereco.DadosEndereco;


public record DadosCadastroPacientes(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank String cpf,
        @NotBlank String telefone,
        @NotNull @Valid DadosEndereco endereco
) {
}
