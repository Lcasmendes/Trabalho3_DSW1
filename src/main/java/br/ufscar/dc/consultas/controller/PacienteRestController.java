package br.ufscar.dc.consultas.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufscar.dc.consultas.domain.Consulta;
import br.ufscar.dc.consultas.domain.Medico;
import br.ufscar.dc.consultas.domain.Paciente;
import br.ufscar.dc.consultas.service.spec.IConsultaService;
import br.ufscar.dc.consultas.service.spec.IProfissionalService;
import br.ufscar.dc.consultas.service.spec.IPacienteService;

@CrossOrigin
@RestController
@RequestMapping("/clientes")
public class PacienteRestController {

    @Autowired
    private IPacienteService pacienteService;

    @Autowired
    private IConsultaService consultaService;

    @Autowired
    private IProfissionalService medicoService;

    private boolean isJSONValid(String jsonInString) {
        try {
            new ObjectMapper().readTree(jsonInString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void parse(Paciente paciente, String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonString);

        paciente.setCPF(rootNode.path("cpf").asText());
        paciente.setNome(rootNode.path("nome").asText());
        paciente.setData_nascimento(rootNode.path("dataNascimento").asText());
        paciente.setEmail(rootNode.path("email").asText());
        paciente.setSenha(rootNode.path("senha").asText());
        paciente.setTelefone(rootNode.path("telefone").asText());
        paciente.setSexo(rootNode.path("sexo").asText());
        paciente.setRole(rootNode.path("role").asText());

        JsonNode consultasNode = rootNode.path("consultas");
        if (consultasNode.isArray()) {
            List<Consulta> consultas = new ArrayList<>();
            for (JsonNode consultaNode : consultasNode) {
                Consulta consulta = new Consulta();
                parseConsulta(consulta, consultaNode);

                // Associa a consulta ao paciente
                consulta.setPaciente(paciente);

                consultas.add(consulta);
            }
            paciente.setConsultas(consultas);
        }
    }

    private void parseConsulta(Consulta consulta, JsonNode jsonNode) {
        consulta.setHorario(jsonNode.path("horario").asText());
        consulta.setDataConsulta(jsonNode.path("dataConsulta").asText());

        String medicoCrm = jsonNode.path("medicoCrm").asText();

        if (medicoCrm != null && !medicoCrm.isEmpty()) {
            Medico medico = medicoService.buscarPorId(medicoCrm);
            if (medico != null) {
                consulta.setMedico(medico);
            } else {
                throw new RuntimeException("Médico com CRM " + medicoCrm + " não encontrado.");
            }
        }
    }

    @GetMapping
    public ResponseEntity<List<LinkedHashMap<String, Object>>> lista() {
        List<Paciente> lista = pacienteService.buscarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<LinkedHashMap<String, Object>> pacientesJson = lista.stream().map(paciente -> {
            LinkedHashMap<String, Object> pacienteJson = new LinkedHashMap<>();
            pacienteJson.put("cpf", paciente.getCPF());
            pacienteJson.put("nome", paciente.getNome());
            pacienteJson.put("dataNascimento", paciente.getData_nascimento());
            pacienteJson.put("email", paciente.getEmail());
            pacienteJson.put("telefone", paciente.getTelefone());
            pacienteJson.put("sexo", paciente.getSexo());
            pacienteJson.put("role", paciente.getRole());

            List<LinkedHashMap<String, Object>> consultasJson = paciente.getConsultas().stream().map(consulta -> {
                LinkedHashMap<String, Object> consultaJson = new LinkedHashMap<>();
                consultaJson.put("id", consulta.getId()); 
                consultaJson.put("horario", consulta.getHorario());
                consultaJson.put("dataConsulta", consulta.getDataConsulta());

                Medico medico = consulta.getMedico();
                if (medico != null) {
                    consultaJson.put("medicoNome", medico.getNome());
                    consultaJson.put("medicoCRM", medico.getCRM());
                }

                return consultaJson;
            }).collect(Collectors.toList());

            pacienteJson.put("consultas", consultasJson);
            return pacienteJson;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(pacientesJson);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LinkedHashMap<String, Object>> lista(@PathVariable("id") String cpf) {
        Paciente paciente = pacienteService.buscarPorId(cpf);
        if (paciente == null) {
            return ResponseEntity.notFound().build();
        }

        LinkedHashMap<String, Object> pacienteJson = new LinkedHashMap<>();
        pacienteJson.put("cpf", paciente.getCPF());
        pacienteJson.put("nome", paciente.getNome());
        pacienteJson.put("dataNascimento", paciente.getData_nascimento());
        pacienteJson.put("email", paciente.getEmail());
        pacienteJson.put("telefone", paciente.getTelefone());
        pacienteJson.put("sexo", paciente.getSexo());
        pacienteJson.put("role", paciente.getRole());

        List<LinkedHashMap<String, Object>> consultasJson = paciente.getConsultas().stream().map(consulta -> {
            LinkedHashMap<String, Object> consultaJson = new LinkedHashMap<>();
            consultaJson.put("id", consulta.getId()); 
            consultaJson.put("horario", consulta.getHorario());
            consultaJson.put("dataConsulta", consulta.getDataConsulta());

            Medico medico = consulta.getMedico();
            if (medico != null) {
                consultaJson.put("medicoNome", medico.getNome());
                consultaJson.put("medicoCRM", medico.getCRM());
            }

            return consultaJson;
        }).collect(Collectors.toList());

        pacienteJson.put("consultas", consultasJson);

        return ResponseEntity.ok(pacienteJson);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Paciente> cria(@RequestBody String json) {
        try {
            if (!isJSONValid(json)) {
                return ResponseEntity.badRequest().body(null);
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(json);
            String cpf = rootNode.path("cpf").asText();
            String email = rootNode.path("email").asText();
            if (pacienteService.buscarPorId(cpf) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // CPF já existe
            }
            if (pacienteService.buscarPorEmail(email) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // Email já existe
            }

            Paciente paciente = new Paciente();
            parse(paciente, json);

            pacienteService.save(paciente);

            return ResponseEntity.ok(paciente);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> atualiza(@PathVariable("id") String cpf, @RequestBody String json) {
        try {
            if (!isJSONValid(json)) {
                return ResponseEntity.badRequest().body(null);
            }

            Paciente pacienteExistente = pacienteService.buscarPorId(cpf);
            if (pacienteExistente == null) {
                return ResponseEntity.notFound().build();
            }

            // Atualize os dados básicos do paciente
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(json);

            pacienteExistente.setNome(rootNode.path("nome").asText());
            pacienteExistente.setData_nascimento(rootNode.path("dataNascimento").asText());
            pacienteExistente.setEmail(rootNode.path("email").asText());
            pacienteExistente.setTelefone(rootNode.path("telefone").asText());
            pacienteExistente.setSexo(rootNode.path("sexo").asText());
            pacienteExistente.setRole(rootNode.path("role").asText());

            // Atualize o paciente no banco
            pacienteService.save(pacienteExistente);

            // Atualize as consultas associadas
            JsonNode consultasNode = rootNode.path("consultas");
            if (consultasNode.isArray()) {
                for (JsonNode consultaNode : consultasNode) {
                    Consulta novaConsulta = new Consulta();
                    parseConsulta(novaConsulta, consultaNode);

                    novaConsulta.setPaciente(pacienteExistente); // Associe o paciente à nova consulta
                    consultaService.salvar(novaConsulta); // Salve a nova consulta
                }
            }

            return ResponseEntity.ok(pacienteExistente);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> remove(@PathVariable("id") String cpf) {
        Paciente paciente = pacienteService.buscarPorId(cpf);
        if (paciente == null) {
            return ResponseEntity.notFound().build();
        } 

        // Exclua todas as consultas associadas ao paciente
        List<Consulta> consultas = consultaService.buscarPorPacienteCPF(cpf);
        for (Consulta consulta : consultas) {
            consultaService.remover(consulta.getId()); 
        }

        // Agora exclua o paciente
        pacienteService.delete(cpf);

        return ResponseEntity.noContent().build();
    }

}
