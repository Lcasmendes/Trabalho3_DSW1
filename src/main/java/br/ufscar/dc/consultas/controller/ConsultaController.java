package br.ufscar.dc.consultas.controller;

import br.ufscar.dc.consultas.dao.ConsultaDAO;
import br.ufscar.dc.consultas.dao.PacienteDAO;  
import br.ufscar.dc.consultas.dao.MedicoDAO; 
import br.ufscar.dc.consultas.domain.Consulta;
import br.ufscar.dc.consultas.domain.Medico;
import br.ufscar.dc.consultas.domain.Paciente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaDAO consultaDAO;

    @Autowired
    private PacienteDAO pacienteDAO;

    @Autowired
    private MedicoDAO medicoDAO;

    // Listagem de consultas
    @GetMapping("/listarConsultas")
    public String listarConsultas(Model model) {
        List<Consulta> consultas = consultaDAO.findAll();
        model.addAttribute("consultas", consultas);
        return "consultas";
    }

    // Mostra o formulário de consulta
    @GetMapping("/novaConsulta")
    public String novaConsultaForm(Model model) {
        model.addAttribute("consulta", new Consulta());
        model.addAttribute("medicos", medicoDAO.findAll()); 
        return "nova-consulta";
    }

    // Recebe o formulário e salva a nova consulta
    @PostMapping("/novaConsulta")
    public String adicionarConsulta(String pacienteCPF, String medicoCRM, String horario, String dataConsulta, Model model) {
        Paciente paciente = pacienteDAO.findByCPF(pacienteCPF);
        Medico medico = medicoDAO.findByCrm(medicoCRM);

        if (paciente != null && medico != null) {
            Consulta novaConsulta = new Consulta(paciente, medico, horario, dataConsulta);
            consultaDAO.save(novaConsulta);
            return "redirect:/consultas/listarConsultas";
        } else {
            // Caso o paciente ou médico não sejam encontrados
            model.addAttribute("erro", "Paciente ou médico não encontrado. Verifique os dados e tente novamente.");
            return "nova-consulta";
        }
    }
}

