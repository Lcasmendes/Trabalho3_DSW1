package br.ufscar.dc.consultas.controller;

import br.ufscar.dc.consultas.dao.ConsultaDAO;
import br.ufscar.dc.consultas.dao.MedicoDAO;
import br.ufscar.dc.consultas.domain.Medico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoDAO medicoDAO;  
    @Autowired
    private ConsultaDAO consultaDAO;

    // Método GET para listar todos os médicos ou filtrar por especialidade
    @GetMapping("/home")
    public String listarMedicosHome(Model model, @RequestParam(value = "especialidade", required = false) String especialidade) {
        List<Medico> medicos;
        if (especialidade == null || especialidade.isEmpty()) {
            // Se nenhuma especialidade for fornecida, lista todos os médicos
            medicos = medicoDAO.findAll();
        } else {
            // Se uma especialidade for fornecida, filtra os médicos por essa especialidade
            medicos = medicoDAO.buscarPorEspecialidade(especialidade);
        }
        // Adiciona a lista de médicos ao modelo
        model.addAttribute("medicos", medicos);
        // Retorna o nome da view a ser renderizada
        return "medicos_home";
    }

    // Método POST para buscar médicos por especialidade
    @PostMapping("/home")
    public String buscaEspecialidade(@RequestParam("especialidade") String especialidade, Model model) {
        // Filtra os médicos pela especialidade fornecida
        List<Medico> medicos = medicoDAO.buscarPorEspecialidade(especialidade);
        // Adiciona a lista de médicos ao modelo
        model.addAttribute("medicos", medicos);
        // Retorna a view com os médicos filtrados
        return "medicos_home";
    }

    // Mapeia requisições HTTP GET para "/medicos" para listar todos os médicos
    @GetMapping("/CRUD")
    public String listarMedicos(Model model) {
        // Recupera todos os médicos do banco de dados
        List<Medico> medicos = medicoDAO.findAll();
        // Adiciona a lista de médicos ao modelo com a chave "medicos"
        model.addAttribute("medicos", medicos);
        // Retorna o nome da view a ser renderizada
        return "medicos_CRUD";
    }

    // Mapeia requisições HTTP GET para "/medicos/novo" para mostrar o formulário de novo médico
    @GetMapping("/novo")
    public String novoMedicoForm(Model model) {
        // Cria um novo objeto médico e adiciona ao modelo
        model.addAttribute("medico", new Medico());
        // Retorna o nome da view que exibirá o formulário para criação de um novo médico
        return "novo-medico";
    }

    // Mapeia requisições HTTP POST para "/medicos/novo" para adicionar um novo médico
    @PostMapping("/novo")
    public String adicionarMedico(Medico medico) {
        // Salva o médico no banco de dados através do MedicoDAO
        medicoDAO.save(medico);
        // Redireciona para a lista de médicos após a adição
        return "redirect:/medicos/CRUD";
    }
    
    // Exclusão do medico
    @GetMapping("/excluir")
    public String deletarMedico(@RequestParam String crm) {
    	consultaDAO.deleteByMedico_Crm(crm);
        medicoDAO.deleteByCrm(crm);
        return "redirect:/medicos/CRUD";
    }
    
    
    // Função para a edição do medico, buscando todos seus valores padrões e preenchendo o 
    // formulário para edição
    @GetMapping("/editar") 
    public String editarMedico(@RequestParam String crm, Model model) {
    	// Busca o medico com o crm do botão clicado
        Medico medico = medicoDAO.findByCrm(crm); 
        model.addAttribute("medico", medico);
        return "editar_medico"; 
    }
    
    // Após alterar os dados no html, atualiza no banco
    @PostMapping("/atualizar")
    public String atualizarMedico(@RequestParam String crm, @RequestParam String nome,
                                  @RequestParam String email, @RequestParam String especialidade, @RequestParam String senha) {
    	// Recebe o medico do formulario, edita com os set e salva no banco
        Medico medico = medicoDAO.findByCrm(crm);
        medico.setNome(nome);
        medico.setEmail(email);
        medico.setEspecialidade(especialidade);
        medico.setSenha(senha);
        medicoDAO.save(medico); 
        return "redirect:/medicos/CRUD";
    }


    

}
