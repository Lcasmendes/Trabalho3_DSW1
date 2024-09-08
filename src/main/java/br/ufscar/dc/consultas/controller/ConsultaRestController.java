package br.ufscar.dc.consultas.controller;

import br.ufscar.dc.consultas.domain.Consulta;
import br.ufscar.dc.consultas.service.spec.IConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/consultas")
public class ConsultaRestController {

    @Autowired
    private IConsultaService service;

    // Verificar se o JSON de entrada é válido.
    private boolean isJSONValid(String jsonInString) {
        try {
            return new ObjectMapper().readTree(jsonInString) != null;
        } catch (IOException e) {
            return false;
        }
    }

    private void parse(Consulta consulta, LinkedHashMap<String, Object> json) {
        consulta.setHorario((String) json.get("horario"));
        consulta.setDataConsulta((String) json.get("dataConsulta"));
    }

    // Listar todas as consultas. Retorna uma lista de consultas com os detalhes do médico e paciente.
    @GetMapping
    public ResponseEntity<List<LinkedHashMap<String, Object>>> listar() {
        List<Consulta> consultas = service.buscarTodos();
        if (consultas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Transformação de cada consulta em um LinkedHashMap para ordenação dos campos no JSON.
        List<LinkedHashMap<String, Object>> consultaJsonList = consultas.stream().map(consulta -> {
            LinkedHashMap<String, Object> consultaJson = new LinkedHashMap<>();
            consultaJson.put("id", consulta.getId()); 
            consultaJson.put("horario", consulta.getHorario());
            consultaJson.put("dataConsulta", consulta.getDataConsulta());
            consultaJson.put("medicoNome", consulta.getMedico().getNome());
            consultaJson.put("medicoCRM", consulta.getMedico().getCRM());
            consultaJson.put("pacienteNome", consulta.getPaciente().getNome());
            consultaJson.put("pacienteCPF", consulta.getPaciente().getCPF());
            return consultaJson;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(consultaJsonList);
    }

    // Buscar consulta por ID. Retorna os detalhes de uma consulta específica.
    @GetMapping("/{id}")
    public ResponseEntity<LinkedHashMap<String, Object>> buscarPorId(@PathVariable("id") Long id) {
        Optional<Consulta> consultaOpt = service.buscarPorId(id);
        if (consultaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Consulta consulta = consultaOpt.get();

        // Montagem do JSON com os detalhes da consulta.
        LinkedHashMap<String, Object> consultaJson = new LinkedHashMap<>();
        consultaJson.put("id", consulta.getId()); 
        consultaJson.put("horario", consulta.getHorario());
        consultaJson.put("dataConsulta", consulta.getDataConsulta());
        consultaJson.put("medicoNome", consulta.getMedico().getNome());
        consultaJson.put("medicoCRM", consulta.getMedico().getCRM());
        consultaJson.put("pacienteNome", consulta.getPaciente().getNome());
        consultaJson.put("pacienteCPF", consulta.getPaciente().getCPF());

        return ResponseEntity.ok(consultaJson);
    }

    // Atualizar uma consulta existente. Usa o ID da consulta e os dados fornecidos para atualizar.
//    @PutMapping("/{id}")
//    public ResponseEntity<Consulta> atualizar(@PathVariable("id") Long id, @RequestBody LinkedHashMap<String, Object> json) {
//        Optional<Consulta> consultaExistente = service.buscarPorId(id);
//        if (consultaExistente.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        Consulta consulta = consultaExistente.get();
//        parse(consulta, json); 
//        service.salvar(consulta); 
//        return ResponseEntity.ok(consulta);
//    }

    // Buscar consultas por CPF do paciente. Retorna uma lista de consultas associadas a um paciente específico.
    @GetMapping("/paciente/{cpf}")
    public ResponseEntity<List<LinkedHashMap<String, Object>>> buscarPorPaciente(@PathVariable("cpf") String cpf) {
        List<Consulta> consultas = service.buscarPorPacienteCPF(cpf);
        if (consultas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Montagem do JSON de resposta com os detalhes da consulta e médico.
        List<LinkedHashMap<String, Object>> consultaJsonList = consultas.stream().map(consulta -> {
            LinkedHashMap<String, Object> consultaJson = new LinkedHashMap<>();
            consultaJson.put("id", consulta.getId()); 
            consultaJson.put("horario", consulta.getHorario());
            consultaJson.put("dataConsulta", consulta.getDataConsulta());
            consultaJson.put("medicoNome", consulta.getMedico().getNome());
            return consultaJson;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(consultaJsonList);
    }

    // Buscar consultas por CRM do médico. Retorna uma lista de consultas associadas a um médico específico.
    @GetMapping("/medico/{crm}")
    public ResponseEntity<List<LinkedHashMap<String, Object>>> buscarPorMedico(@PathVariable("crm") String crm) {
        List<Consulta> consultas = service.buscarConsultasPorMedico(crm);
        if (consultas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Montagem do JSON de resposta com os detalhes da consulta e paciente.
        List<LinkedHashMap<String, Object>> consultaJsonList = consultas.stream().map(consulta -> {
            LinkedHashMap<String, Object> consultaJson = new LinkedHashMap<>();
            consultaJson.put("id", consulta.getId()); 
            consultaJson.put("horario", consulta.getHorario());
            consultaJson.put("dataConsulta", consulta.getDataConsulta());
            consultaJson.put("pacienteNome", consulta.getPaciente().getNome());
            consultaJson.put("pacienteCPF", consulta.getPaciente().getCPF());
            return consultaJson;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(consultaJsonList);
    }
}
