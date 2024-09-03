package br.ufscar.dc.consultas.controller;

import java.io.IOException;
import java.util.List;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.ufscar.dc.consultas.domain.Paciente;
import br.ufscar.dc.consultas.service.spec.IPacienteService;

@CrossOrigin
@RestController
@RequestMapping("/clientes")
public class PacienteRestController {

    @Autowired
    private IPacienteService service;

    private boolean isJSONValid(String jsonInString) {
        try {
            return new ObjectMapper().readTree(jsonInString) != null;
        } catch (IOException e) {
            return false;
        }
    }

    // Faz o parse dos dados do json quando receve pra enviar
    private void parse(Paciente paciente, JSONObject json) {
        Object cpf = json.get("cpf");
        if (cpf != null) {
            if (cpf instanceof Integer) {
                paciente.setCPF(String.valueOf(cpf));
            } else {
                paciente.setCPF((String) cpf);
            }
        }

        paciente.setNome((String) json.get("nome"));
        paciente.setData_nascimento((String) json.get("dataNascimento"));
        paciente.setEmail((String) json.get("email"));
        paciente.setSenha((String) json.get("senha"));
        paciente.setTelefone((String) json.get("telefone"));
        paciente.setSexo((String) json.get("sexo"));
        paciente.setRole((String) json.get("role"));
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> lista() {
        List<Paciente> lista = service.buscarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> lista(@PathVariable("id") String cpf) {
        Paciente paciente = service.buscarPorId(cpf);
        if (paciente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(paciente);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Paciente> cria(@RequestBody JSONObject json) {
    	try {
            if (!isJSONValid(json.toString())) {
                return ResponseEntity.badRequest().body(null);
            }

            // Verifica se já existe um paciente com o mesmo CPF ou email
            String cpf = (String) json.get("cpf");
            String email = (String) json.get("email");
            if (service.buscarPorId(cpf) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // CPF já existe
            }
            if (service.buscarPorEmail(email) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // Email já existe
            }

            Paciente paciente = new Paciente();
            parse(paciente, json);
            service.save(paciente);
            return ResponseEntity.ok(paciente);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> atualiza(@PathVariable("id") String cpf, @RequestBody JSONObject json) {
    	 try {
             if (!isJSONValid(json.toString())) {
                 return ResponseEntity.badRequest().body(null);
             }

             Paciente pacienteExistente = service.buscarPorId(cpf);
             if (pacienteExistente == null) {
                 return ResponseEntity.notFound().build();
             }

             // Atualiza o paciente, mantendo o CPF original
             parse(pacienteExistente, json);
             pacienteExistente.setCPF(cpf); // Garante que o CPF não será alterado
             service.save(pacienteExistente);
             return ResponseEntity.ok(pacienteExistente);
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
         }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> remove(@PathVariable("id") String id) {
        Paciente paciente = service.buscarPorId(id);
        if (paciente == null) {
            return ResponseEntity.notFound().build();
        } else {
            service.delete(id);
            return ResponseEntity.noContent().build();
        }
    }
}
