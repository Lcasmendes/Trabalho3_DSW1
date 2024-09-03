package br.ufscar.dc.consultas.controller;

import br.ufscar.dc.consultas.dao.ConsultaDAO;
import br.ufscar.dc.consultas.dao.MedicoDAO;
import br.ufscar.dc.consultas.domain.Medico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoDAO medicoDAO;  
    @Autowired
    private ConsultaDAO consultaDAO;

    @GetMapping("/home")
public String listarMedicosHome(Model model, @RequestParam(value = "especialidade", required = false) String especialidade) {
    List<Medico> medicos;
    List<String> especialidades = new ArrayList<>();
    
    if (especialidade == null || especialidade.isEmpty()) {
        // Se nenhuma especialidade for fornecida, lista todos os médicos
        medicos = (List<Medico>) medicoDAO.findAll();
    } else {
        // Se uma especialidade for fornecida, filtra os médicos por essa especialidade
        medicos = medicoDAO.buscarPorEspecialidade(especialidade);
    }
    
    // Obtém a lista de especialidades únicas dos médicos
    especialidades = medicoDAO.findAllEspecialidade();
    
    // Adiciona a lista de médicos e especialidades ao modelo
    model.addAttribute("medicos", medicos);
    model.addAttribute("especialidades", especialidades);
    
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
        List<Medico> medicos = (List<Medico>) medicoDAO.findAll();
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

    @PostMapping("/novo")
    public String adicionarMedico(Medico medico, Model model) {
        medico.setRole("MEDICO");

        Medico medicoConfirmacao = medicoDAO.findByCrm(medico.getCRM());
        

        if (medicoConfirmacao == null) {
        	// Validação de nome: verifica se o nome tem pelo menos 3 caracteres
            if (medico.getNome() == null || medico.getNome().length() < 3) {
                model.addAttribute("erro", "O nome deve ter pelo menos 3 caracteres");
                System.out.println("O nome deve ter pelo menos 3 caracteres");
                return "novo-medico";
            
            }
        	// Validação do email: confirma se o email é de um domínio valido como gmail, yahoo ou outlook
            if (!(medico.getEmail().endsWith("@gmail.com") || 
                  medico.getEmail().endsWith("@yahoo.com") || 
                  medico.getEmail().endsWith("@outlook.com"))) {
                model.addAttribute("erro", "E-mail com domínio inválido");
                System.out.println("E-mail com domínio inválido");
                return "novo-medico";
            }
            
            // Validação da senha: verifica se a senha tem pelo menos 4 caracteres
            if (medico.getSenha() == null || medico.getSenha().length() < 4) {
                model.addAttribute("erro", "A senha deve ter pelo menos 4 caracteres");
                System.out.println("A senha deve ter pelo menos 4 caracteres");
                return "novo-medico";
            }    
            
            try {
                // Tenta salvar o médico no banco de dados
                medicoDAO.save(medico);
                // Redireciona para a lista de médicos após a adição
                return "redirect:/medicos/CRUD";
            } catch (Exception e) {
                // Captura outras exceções genéricas
                model.addAttribute("erro", "Erro: " + e.getMessage());
                System.out.println("Erro ao inserir médico: " + e.getMessage());
                return "novo-medico";
            }
        } else {
            model.addAttribute("erro", "Inserção inválida. Já existe médico com esse CRM");
            System.out.println("Inserção inválida. Já existe médico com esse CRM");
            return "novo-medico";
        }
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
