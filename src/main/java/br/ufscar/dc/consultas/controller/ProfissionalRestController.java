package br.ufscar.dc.consultas.controller;

import java.io.IOException;
import java.util.List;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufscar.dc.consultas.domain.Medico;
import br.ufscar.dc.consultas.service.spec.IProfissionalService;

@CrossOrigin
@RestController
@RequestMapping("/profissionais")
public class ProfissionalRestController {

    @Autowired
    private IProfissionalService service;

    private boolean isJSONValid(String jsonInString) {
        try {
            return new ObjectMapper().readTree(jsonInString) != null;
        } catch (IOException e) {
            return false;
        }
    }

    // Faz o parse dos dados do json quando receve pra enviar
    private void parse(Medico medico, JSONObject json) {
        Object crm = json.get("crm");
        if (crm != null) {
            if (crm instanceof Integer) {
                medico.setCRM(String.valueOf(crm));
            } else {
                medico.setCRM((String) crm);
            }
        }

        medico.setNome((String) json.get("nome"));
        medico.setEmail((String) json.get("email"));
        medico.setSenha((String) json.get("senha"));
        medico.setEspecialidade((String) json.get("especialidade"));
        medico.setRole((String) json.get("role"));
    }

    @GetMapping
    public ResponseEntity<List<Medico>> lista() {
        List<Medico> lista = service.buscarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> lista(@PathVariable("id") String crm) {
        Medico medico = service.buscarPorId(crm);
        if (medico == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(medico);
    }
    
    @GetMapping("/especialidade/{especialidade}")
    public ResponseEntity<List<Medico>> lista_e(@PathVariable("especialidade") String especialidade) {
        List<Medico> lista_e = service.buscarPorEspecialidade(especialidade);
        if (lista_e.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista_e);
    }
    
  
    @PostMapping
    @ResponseBody
    public ResponseEntity<Medico> cria(@RequestBody JSONObject json) {
    	try {
            if (!isJSONValid(json.toString())) {
                return ResponseEntity.badRequest().body(null);
            }

            // Verifica se já existe um medico com o mesmo CPF ou email
            String crm = (String) json.get("crm");
            String email = (String) json.get("email");
            if (service.buscarPorId(crm) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // CPF já existe
            }
            if (service.buscarPorEmail(email) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // Email já existe
            }

            Medico medico = new Medico();
            parse(medico, json);
            service.save(medico);
            return ResponseEntity.ok(medico);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medico> atualiza(@PathVariable("id") String crm, @RequestBody JSONObject json) {
    	 try {
             if (!isJSONValid(json.toString())) {
                 return ResponseEntity.badRequest().body(null);
             }

             Medico medicoExistente = service.buscarPorId(crm);
             if (medicoExistente == null) {
                 return ResponseEntity.notFound().build();
             }

             // Atualiza o medico, mantendo o CPF original
             parse(medicoExistente, json);
             medicoExistente.setCRM(crm); // Garante que o CPF não será alterado
             service.save(medicoExistente);
             return ResponseEntity.ok(medicoExistente);
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
         }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> remove(@PathVariable("id") String id) {
        Medico medico = service.buscarPorId(id);
        if (medico == null) {
            return ResponseEntity.notFound().build();
        } else {
            service.delete(id);
            return ResponseEntity.noContent().build();
        }
    }
}
