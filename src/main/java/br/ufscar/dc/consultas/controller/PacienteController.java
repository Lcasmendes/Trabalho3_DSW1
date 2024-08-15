package br.ufscar.dc.consultas.controller;

import br.ufscar.dc.consultas.dao.PacienteDAO;  
import br.ufscar.dc.consultas.domain.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
// Define o caminho base para as requisições de mapeamento que este controlador irá tratar
@RequestMapping("/pacientes")
public class PacienteController {

    // Injeção de dependência para o PacienteDAO, que gerencia a persistência dos dados
    @Autowired
    private PacienteDAO pacienteDAO;  

    // Mapeia requisições HTTP GET para "/pacientes" para listar todos os pacientes
    @GetMapping
    public String listarPacientes(Model model) {
        // Recupera todos os pacientes do banco de dados
        List<Paciente> pacientes = pacienteDAO.findAll();
        // Adiciona a lista de pacientes ao modelo com a chave "pacientes"
        model.addAttribute("pacientes", pacientes);
        // Retorna o nome da view a ser renderizada
        return "pacientes";
    }

    // Mapeia requisições HTTP GET para "/pacientes/novo" para mostrar o formulário de novo paciente
    @GetMapping("/novo")
    public String novaPacienteForm(Model model) {
        // Cria um novo objeto Paciente e adiciona ao modelo
        model.addAttribute("paciente", new Paciente());
        // Retorna o nome da view que exibirá o formulário para criação de um novo paciente
        return "novo-paciente";
    }

    // Mapeia requisições HTTP POST para "/pacientes/novo" para adicionar um novo paciente
    @PostMapping("/novo")
    public String adicionarPaciente(Paciente paciente) {
        // Salva o paciente no banco de dados através do PacienteDAO
        pacienteDAO.save(paciente);
        // Redireciona para a lista de pacientes após a adição
        return "redirect:/pacientes";
    }
}
