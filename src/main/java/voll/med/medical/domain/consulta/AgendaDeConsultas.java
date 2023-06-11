package voll.med.medical.domain.consulta;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import voll.med.medical.domain.ValidacaoException;
import voll.med.medical.domain.consulta.validacoes.agendamento.ValidacaoDeRegradeNegociosDoAgendamente;
import voll.med.medical.domain.consulta.validacoes.cancelamento.ValidacaoDeRegrasDeNegocioDoCancelamento;
import voll.med.medical.domain.medico.Medico;
import voll.med.medical.domain.medico.MedicoRepository;
import voll.med.medical.domain.paciente.Paciente;
import voll.med.medical.domain.paciente.PacienteRepository;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidacaoDeRegradeNegociosDoAgendamente> validadores;

    @Autowired
    private List<ValidacaoDeRegrasDeNegocioDoCancelamento> cancelamentos;

    public DadosDetalhamentoConsulta agendar (DadosAgendamentoConsulta dados) {

        if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("Id do médico não existe!!");
        }
        if(!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("Id do paciente não existe!!");
        }

        validadores.forEach(v -> v.validar(dados));

        Medico medico = escolherMedico(dados);

        if(medico == null){
            throw new ValidacaoException("Não existe médico disponível nesta data");
        }

        Paciente paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        Consulta consulta = new Consulta(null, medico, paciente, dados.data(), null);

        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }


    public void cancelar(@Valid DadosCancelamentoConsulta dados) {
        if(!consultaRepository.existsById(dados.idConsulta())){
            throw new ValidacaoException("Id da consulta não existe");
        }

        cancelamentos.forEach(valid_can -> valid_can.validar(dados));

        Consulta consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }


    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if(dados.idMedico() != null){
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if(dados.especialidade() == null){
            throw new ValidacaoException("Especialidade é obrigatória quando o médico não é escolhido");
        }

        return medicoRepository.escolherMedicoAleatorioLivre(dados.especialidade(), dados.data());
    }


    public Consulta detalhar(Long id) {
        if(!consultaRepository.existsById(id)){
            throw new ValidacaoException("Consulta não existe!!!");
        }

        return consultaRepository.getReferenceById(id);
    }
}
