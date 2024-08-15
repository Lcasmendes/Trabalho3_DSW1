package br.ufscar.dc.consultas.controller;

import br.ufscar.dc.consultas.dao.ConsultaDAO;
import br.ufscar.dc.consultas.dao.PacienteDAO;  
import br.ufscar.dc.consultas.domain.Consulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/consultas")
public class ConsultaController{
	
	@Autowired
	private ConsultaDAO consultaDAO;
	
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
		return "nova-consulta";
	}
	
	@PostMapping("/novaConsulta")
	public String adicionarConsulta(Consulta consulta) {
		consultaDAO.save(consulta);
		return "redirect:/consultas";
	}
	
}