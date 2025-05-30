package clinica.agendamento.trabalho.controller;

import clinica.agendamento.trabalho.model.Paciente;
import clinica.agendamento.trabalho.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public String listarPacientes(Model model, @RequestParam(value = "erro", required = false) String erro) {
        model.addAttribute("pacientes", pacienteService.listarTodos());
        if (erro != null) {
            model.addAttribute("erro", erro);
        }
        return "pacientes/listar";
    }

    @GetMapping("/novo")
    public String mostrarFormularioNovoPaciente(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "pacientes/form";
    }

    @PostMapping("/salvar")
    public String salvarPaciente(@ModelAttribute Paciente paciente) {
        pacienteService.salvar(paciente);
        return "redirect:/pacientes";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarPaciente(@PathVariable Long id, Model model) {
        try {
            Paciente paciente = pacienteService.buscarPorId(id);
            model.addAttribute("paciente", paciente);
            return "pacientes/form";
        } catch (RuntimeException e) {
            return "redirect:/pacientes?erro=" + e.getMessage();
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletarPaciente(@PathVariable Long id) {
        try {
            pacienteService.deletarPorId(id);
        } catch (RuntimeException e) {
            return "redirect:/pacientes?erro=" + e.getMessage();
        }
        return "redirect:/pacientes";
    }
}
