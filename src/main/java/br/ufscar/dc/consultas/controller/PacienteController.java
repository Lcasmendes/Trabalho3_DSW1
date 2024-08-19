package br.ufscar.dc.consultas.controller;

import br.ufscar.dc.consultas.dao.ConsultaDAO;
import br.ufscar.dc.consultas.dao.PacienteDAO;
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
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteDAO pacienteDAO;

    @Autowired
    private ConsultaDAO consultaDAO;

    @GetMapping
    public String listarPacientes(Model model) {
        List<Paciente> pacientes = pacienteDAO.findAll();
        model.addAttribute("pacientes", pacientes);
        return "pacientes";
    }

    @GetMapping("/novo")
    public String novaPacienteForm(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "novo-paciente";
    }

    @PostMapping("/novo")
    public String adicionarPaciente(Paciente paciente, Model model) {
        // Valida se o CPF já está cadastrado
        Paciente existente = pacienteDAO.findByCPF(paciente.getCPF());
        if (existente != null) {
            model.addAttribute("cpfExistente", "Já existe um paciente cadastrado com este CPF.");
            return "novo-paciente";
        }
        
        // Valida se o nome tem pelo menos 3 caracteres
        if (paciente.getNome().length() < 3) {
            model.addAttribute("nomeCurto", "O nome deve ter pelo menos 3 caracteres.");
            return "novo-paciente";
        }

        // Valida se o email já está cadastrado
        Paciente pacienteExistenteEmail = pacienteDAO.findByEmail(paciente.getEmail());
        if (pacienteExistenteEmail != null) {
            model.addAttribute("emailExistente", "Já existe um paciente cadastrado com este email.");
            return "novo-paciente";
        }

        // Valida se o email termina com "@gmail.com"
        if (!paciente.getEmail().endsWith("@gmail.com")) {
            model.addAttribute("emailInvalido", "O email deve terminar com '@gmail.com'.");
            return "novo-paciente";
        }

        // Valida se a senha tem pelo menos 4 dígitos
        if (paciente.getSenha().length() < 4) {
            model.addAttribute("senhaCurta", "A senha deve ter pelo menos 4 dígitos.");
            return "novo-paciente";
        }

        // Valida se o sexo é 'F', 'f', 'M', ou 'm'
        if (!paciente.getSexo().equalsIgnoreCase("f") && !paciente.getSexo().equalsIgnoreCase("m")) {
            model.addAttribute("sexoInvalido", "O campo sexo deve ser 'M' ou 'F'.");
            return "novo-paciente";
        }

        // Valida se o telefone tem pelo menos 8 dígitos e contém apenas números
        if (!paciente.getTelefone().matches("\\d{8,}")) {
            model.addAttribute("telefoneInvalido", "O telefone deve conter apenas números e ter pelo menos 8 dígitos.");
            return "novo-paciente";
        }

        // Define o role do paciente e salva no banco de dados
        paciente.setRole("PACIENTE");
        pacienteDAO.save(paciente);
        return "redirect:/pacientes";
    }

    @GetMapping("/excluir")
    public String deletarPaciente(@RequestParam String cpf) {
        consultaDAO.deleteByPaciente_CPF(cpf);
        pacienteDAO.deleteByCpf(cpf);
        return "redirect:/pacientes";
    }

    @GetMapping("/editar")
    public String editarPaciente(@RequestParam String cpf, Model model) {
        Paciente paciente = pacienteDAO.findByCPF(cpf);
        model.addAttribute("paciente", paciente);
        return "editar_paciente";
    }

    @PostMapping("/atualizar")
    public String atualizarPaciente(@RequestParam String cpf, @RequestParam String nome, @RequestParam String data_nascimento, @RequestParam String sexo,
                                    @RequestParam String email, @RequestParam String telefone, @RequestParam String senha) {
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