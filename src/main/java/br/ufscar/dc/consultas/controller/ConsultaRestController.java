package br.ufscar.dc.consultas.controller;

import br.ufscar.dc.consultas.domain.Consulta;
import br.ufscar.dc.consultas.service.spec.IConsultaService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/consultas")
public class ConsultaRestController {

    @Autowired
    private IConsultaService service;

    private boolean isJSONValid(String jsonInString) {
        try {
            return new ObjectMapper().readTree(jsonInString) != null;
        } catch (IOException e) {
            return false;
        }
    }

    private void parse(Consulta consulta, JSONObject json) {
        consulta.setHorario((String) json.get("horario"));
        consulta.setDataConsulta((String) json.get("dataConsulta"));
        // Aqui você pode fazer associações com Paciente e Medico com base no CPF e CRM fornecidos
    }

    @GetMapping
    public ResponseEntity<List<Consulta>> listar() {
        List<Consulta> lista = service.buscarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> buscarPorId(@PathVariable("id") Long id) {
        Optional<Consulta> consulta = service.buscarPorId(id);
        if (consulta.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(consulta.get());
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Consulta> criar(@RequestBody JSONObject json) {
        try {
            if (!isJSONValid(json.toString())) {
                return ResponseEntity.badRequest().body(null);
            }

            Consulta consulta = new Consulta();
            parse(consulta, json);
            service.salvar(consulta);
            return ResponseEntity.ok(consulta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consulta> atualizar(@PathVariable("id") Long id, @RequestBody JSONObject json) {
        Optional<Consulta> consultaExistente = service.buscarPorId(id);
        if (consultaExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Consulta consulta = consultaExistente.get();
        parse(consulta, json);
        service.salvar(consulta);
        return ResponseEntity.ok(consulta);
    }
    @GetMapping("/paciente/{cpf}")
    public ResponseEntity<List<Consulta>> buscarPorPaciente(@PathVariable("cpf") String cpf) {
        List<Consulta> consultas = service.buscarPorPacienteCPF(cpf);
        if (consultas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(consultas);
    }
    
    @GetMapping("/medico/{crm}")
    public ResponseEntity<List<Consulta>> buscarPorMedico(@PathVariable("crm") String crm) {
        List<Consulta> consultas = service.buscarConsultasPorMedico(crm);
        if (consultas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(consultas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable("id") Long id) {
        Optional<Consulta> consulta = service.buscarPorId(id);
        if (consulta.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
