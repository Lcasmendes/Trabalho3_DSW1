package br.ufscar.dc.consultas.controller;

import br.ufscar.dc.consultas.dao.ConsultaDAO;
import br.ufscar.dc.consultas.dao.PacienteDAO;
import br.ufscar.dc.consultas.dao.MedicoDAO;
import br.ufscar.dc.consultas.domain.Consulta;
import br.ufscar.dc.consultas.domain.Medico;
import br.ufscar.dc.consultas.domain.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
    public String novaConsultaForm(@RequestParam(name = "cpf", required = false) String pacienteCPF, Model model) {
        model.addAttribute("pacienteCPF", pacienteCPF);
        model.addAttribute("consulta", new Consulta());
        model.addAttribute("medicos", medicoDAO.findAll());
        return "nova-consulta";
    }

    // Recebe o formulário e salva a nova consulta
    @PostMapping("/novaConsulta")
    public String adicionarConsulta(String pacienteCPF, String medicoCRM, String horario, String dataConsulta, Model model) {
        Paciente paciente = pacienteDAO.findByCPF(pacienteCPF);
        Medico medico = medicoDAO.findByCrm(medicoCRM);

        // Verificação de horário válido
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime horarioConsulta;
        try {
            horarioConsulta = LocalTime.parse(horario, timeFormatter);
        } catch (DateTimeParseException e) {
            model.addAttribute("erro", "Horário inválido. Por favor, insira um horário no formato HH:mm.");
            model.addAttribute("medicos", medicoDAO.findAll());
            return "nova-consulta";
        }

        if (horarioConsulta.isBefore(LocalTime.of(8, 0)) || horarioConsulta.isAfter(LocalTime.of(18, 0)) || 
            !(horarioConsulta.getMinute() == 0 || horarioConsulta.getMinute() == 30)) {
            model.addAttribute("erro", "Horário inválido. O horário deve estar entre 08:00 e 18:00, em intervalos de 30 minutos.");
            model.addAttribute("medicos", medicoDAO.findAll());
            return "nova-consulta";
        }

        // Verificação de data válida
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataConsultaValida;
        try {
            dataConsultaValida = LocalDate.parse(dataConsulta, dateFormatter);
        } catch (DateTimeParseException e) {
            model.addAttribute("erro", "Data inválida. Por favor, insira uma data no formato dd/MM/yyyy.");
            model.addAttribute("medicos", medicoDAO.findAll());
            return "nova-consulta";
        }

        if (paciente != null && medico != null) {
            // Verifica se já existe uma consulta para o médico no mesmo horário e data
            Optional<Consulta> consultaExistente = consultaDAO.findConsultaByMedicoAndHorarioAndDataConsulta(medicoCRM, horario, dataConsulta);
            if (consultaExistente.isPresent()) {
                model.addAttribute("erro", "O médico já tem uma consulta agendada para este horário.");
                model.addAttribute("medicos", medicoDAO.findAll());
                return "nova-consulta";
            }

            Consulta novaConsulta = new Consulta(paciente, medico, horario, dataConsulta);
            consultaDAO.save(novaConsulta);
            return "redirect:/consultas/consultas-paciente";
        } else {
            model.addAttribute("erro", "Paciente ou médico não encontrado. Verifique os dados e tente novamente.");
            model.addAttribute("medicos", medicoDAO.findAll());
            return "nova-consulta";
        }
    }
    
    
    // Lista todas as consultas de um determinado paciente buscando pelo CPF
    @GetMapping("/consultas-paciente")
    public String listarConsultasPorPaciente(String cpf, Model model) {
    	Paciente paciente = pacienteDAO.findByCPF(cpf);
        List<Consulta> consultas = consultaDAO.findByPaciente(paciente);
        model.addAttribute("consultas", consultas);
        return "consultas-paciente";
    }
    
    // Lista todas as consultas de um determinado médico pelo CRM
    @GetMapping("/consultas-medico")
    public String listarConsultasPorMedico(String crm, Model model) {
    	Medico medico = medicoDAO.findByCrm(crm);
       List<Consulta> consultas = consultaDAO.findByMedico(medico);
       model.addAttribute("consultas", consultas);
       return "consultas-medico";
    }

}
