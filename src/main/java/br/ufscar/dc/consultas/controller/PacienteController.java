package br.ufscar.dc.consultas.controller;

import br.ufscar.dc.consultas.dao.ConsultaDAO;
import br.ufscar.dc.consultas.dao.PacienteDAO;
import br.ufscar.dc.consultas.domain.Medico;
import br.ufscar.dc.consultas.domain.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
// Define o caminho base para as requisições de mapeamento que este controlador irá tratar
@RequestMapping("/pacientes")
public class PacienteController {

    // Injeção de dependência para o PacienteDAO, que gerencia a persistência dos dados
    @Autowired
    private PacienteDAO pacienteDAO;  
    
    // ConsultaDAO para remover as consultas ligadas a esse paciente se necessário
    @Autowired
    private ConsultaDAO consultaDAO;

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
    	// Seta o role dele fixamente pro login
    	paciente.setRole("PACIENTE");
        // Salva o paciente no banco de dados através do PacienteDAO
        pacienteDAO.save(paciente);
        // Redireciona para a lista de pacientes após a adição
        return "redirect:/pacientes";
    }
    
    
    // Exclusão do paciente
    @GetMapping("/excluir")
    public String deletarPaciente(@RequestParam String cpf) {
    	// Exclui da tabela de pacientes, e as consultas ligadas a ele na tabela de consultas
    	consultaDAO.deleteByPaciente_CPF(cpf);
        pacienteDAO.deleteByCpf(cpf);  
        return "redirect:/pacientes";   
    }
    
    // Função para a edição do paciente, buscando todos seus valores padrões e preenchendo o 
    // formulário para edição
    @GetMapping("/editar") 
    public String editarPaciente(@RequestParam String cpf, Model model) {

    	// Busca o paciente com o cpf do botão clicado
        Paciente paciente = pacienteDAO.findByCPF(cpf); 
        model.addAttribute("paciente", paciente); 
        return "editar_paciente"; 
    }
    
    // Após alterar os dados no html, atualiza no banco
    @PostMapping("/atualizar")
    public String atualizarPaciente(@RequestParam String cpf, @RequestParam String nome, @RequestParam String data_nascimento, @RequestParam String sexo, 
                                  @RequestParam String email, @RequestParam String telefone, @RequestParam String senha) {
    	// Recebe o paciente do formulario, edita com os set e salva no banco
        Paciente paciente = pacienteDAO.findByCPF(cpf);
        paciente.setNome(nome);
        paciente.setEmail(email);
        paciente.setTelefone(telefone);
        paciente.setSenha(senha);
        paciente.setData_nascimento(data_nascimento);
        paciente.setSexo(sexo);
        pacienteDAO.save(paciente); 
        return "redirect:/pacientes";
    }

}
