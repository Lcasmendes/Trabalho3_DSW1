package br.ufscar.dc.consultas.controller;

import br.ufscar.dc.consultas.dao.PacienteDAO;  
import br.ufscar.dc.consultas.domain.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteDAO pacienteDAO;  

    @GetMapping
    public String listarPacientes(Model model) {
        model.addAttribute("pacientes", pacienteDAO.findAll());
        return "pacientes";
    }

    @GetMapping("/novo")
    public String novaPacienteForm(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "novo-paciente";
    }

    @PostMapping("/novo")
    public String adicionarPaciente(Paciente paciente) {
        pacienteDAO.save(paciente);
        return "redirect:/pacientes";
    }
}
