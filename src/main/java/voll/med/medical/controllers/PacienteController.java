package voll.med.medical.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import voll.med.medical.domain.paciente.*;


@RestController
@RequestMapping("pacientes")
@SecurityRequirement(name = "bearer-key") //anotação para springdoc
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPacientes dados, UriComponentsBuilder uriBuilder){
        Paciente paciente = new Paciente(dados);
        repository.save(paciente);

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(paciente.getId()).toUri();
        var dto = new DadosDetalhamentoPaciente(paciente);

        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPacientes>> listar (@PageableDefault(size = 10, sort = {"nome"}, page = 0) Pageable paginacao){
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemPacientes::new);

        return ResponseEntity.ok(page);
    }



    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizarPaciente dados){
        Paciente paciente = repository.getReferenceById(dados.id());
        paciente.atualizarDados(dados);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id){
        Paciente paciente = repository.getReferenceById(id);
        paciente.desativar();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        Paciente paciente = repository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }
}
