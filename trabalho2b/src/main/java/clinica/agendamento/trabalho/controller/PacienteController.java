package clinica.agendamento.trabalho.controller;

import clinica.agendamento.trabalho.model.Paciente;
import clinica.agendamento.trabalho.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("paciente")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @GetMapping
    public String iniciar(Paciente paciente, Model model) {
        return "paciente/formulario";
    }

    @PostMapping("salvar")
    public String salvar(Paciente paciente, Model model) {

        paciente.setCpf(paciente.getCpf().replaceAll("\\D", ""));
        paciente.setTelefone(paciente.getTelefone().replaceAll("\\D", ""));

        if (paciente.getNome() == null || paciente.getNome().trim().isEmpty()) {
            model.addAttribute("erro", "O campo Nome é obrigatório.");
            model.addAttribute("paciente", paciente);
            return "paciente/formulario";
        }
        if (!paciente.getNome().matches("[a-zA-ZÀ-ÿ\\s]{1,150}")) {
            model.addAttribute("erro", "O campo nome deve conter apenas letras e no máximo 150 caracteres.");
            model.addAttribute("paciente", paciente);
            return "paciente/formulario";
        }

        if (paciente.getCpf() == null || paciente.getCpf().trim().isEmpty()) {
            model.addAttribute("erro", "O campo CPF é obrigatório.");
            model.addAttribute("paciente", paciente);
            return "paciente/formulario";
        }
        if (!paciente.getCpf().matches("\\d{11}")) {
            model.addAttribute("erro", "O campo CPF deve ser numérico e conter 11 digitos.");
            model.addAttribute("paciente", paciente);
            return "paciente/formulario";
        }

        if (paciente.getDataNascimento() == null) {
            model.addAttribute("erro", "O campo Data de Nascimento é obrigatório.");
            model.addAttribute("paciente", paciente);
            return "paciente/formulario";
        }

        if (paciente.getTelefone() == null || paciente.getTelefone().trim().isEmpty()) {
            model.addAttribute("erro", "O campo Telefone é obrigatório.");
            model.addAttribute("paciente", paciente);
            return "paciente/formulario";
        }
        if (!paciente.getTelefone().matches("\\d{10,11}")) {
            model.addAttribute("erro", "O campo telefone deve ser numérico e conter 10 ou 11 digitos.");
            model.addAttribute("paciente", paciente);
            return "paciente/formulario";
        }

        try {
            service.salvar(paciente);
            return "redirect:/paciente/listar";
        } catch (Exception e) {
            model.addAttribute("erro", "Ocorreu um erro inesperado: " + e.getMessage());
            return iniciar(paciente, model);
        }
    }

    @GetMapping("listar")
    public String listar(Model model) {
        model.addAttribute("pacientes", service.listarTodos());
        return "paciente/lista";
    }

    @GetMapping("editar/{id}")
    public String alterar(@PathVariable Long id, Model model) {
        try {
            Paciente paciente = service.buscarPorId(id);
            model.addAttribute("paciente", paciente);
            return "paciente/formulario";
        } catch (RuntimeException e) {
            model.addAttribute("erro", "Paciente não encontrado: " + e.getMessage());
            return "paciente/lista";
        }
    }

    @GetMapping("remover/{id}")
    public String remover(@PathVariable Long id) {
        try {
            service.deletarPorId(id);
        } catch (RuntimeException e) {
            return "redirect:/paciente/listar?erro=" + e.getMessage();
        }
        return "redirect:/paciente/listar";
    }
}