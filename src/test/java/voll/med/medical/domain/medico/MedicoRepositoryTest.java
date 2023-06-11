package voll.med.medical.domain.medico;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import voll.med.medical.domain.consulta.Consulta;
import voll.med.medical.domain.endereco.DadosEndereco;
import voll.med.medical.domain.paciente.DadosCadastroPacientes;
import voll.med.medical.domain.paciente.Paciente;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository repository;
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deveria retornar null quando não tiver médico disponivel na data")
    void escolherMedicoAleatorioLivreCenario1(){

        //given or arrange
        var proximaSegundaFeiraAs10h = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var medico = cadastrarMedico("Mac", "mac@voll.med", "82823", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Jose", "jose@gmail.com"	, "11199922288");
        cadastarConsulta(medico, paciente, proximaSegundaFeiraAs10h);

        //when or act
        var medicoLivre = repository.escolherMedicoAleatorioLivre(Especialidade.CARDIOLOGIA, proximaSegundaFeiraAs10h);

        //then or assert
        assertThat(medicoLivre).isNull();
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        Medico medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        em.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        Paciente paciente = new Paciente(dadosPacientes(nome, email, cpf));
        em.persist(paciente);
        return paciente;
    }

    private void cadastarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(null, medico, paciente, data, null));
    }

    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade){
        return new DadosCadastroMedico(
                nome,
                email,
                "6198283934",
                crm,
                especialidade,
                dadosEndereco()
        );

    }

    private DadosCadastroPacientes dadosPacientes(String nome, String email, String cpf){
        return new DadosCadastroPacientes(
                nome,
                email,
                "61722873746",
                cpf,
                dadosEndereco()
        );
    }

    private DadosEndereco dadosEndereco(){
        return new DadosEndereco(
                "rua H",
                "Centro",
                "72100000",
                "Jardim Botanico",
                "DF",
                "Casa",
                "2500"
        );
    }

}