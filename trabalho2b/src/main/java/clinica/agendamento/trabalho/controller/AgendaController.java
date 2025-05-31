package clinica.agendamento.trabalho.controller;

import clinica.agendamento.trabalho.model.Agenda;
import clinica.agendamento.trabalho.model.Medico;
import clinica.agendamento.trabalho.service.AgendaService;
import clinica.agendamento.trabalho.service.MedicoService;
import clinica.agendamento.trabalho.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("agenda")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public String iniciar(Agenda agenda, Model model) {
        model.addAttribute("pacientes", pacienteService.listarTodos());
        model.addAttribute("medicos", medicoService.listarTodos());
        return "agenda/formulario";
    }

    @PostMapping("salvar")
    public String salvar(Agenda agenda, Model model) {

        if (agenda.getPaciente() == null || agenda.getPaciente().toString().trim().isEmpty()) {
            model.addAttribute("erro", "O campo Paciente é obrigatório.");
            return iniciar(agenda, model);
        }

        if (agenda.getMedico() == null || agenda.getMedico().toString().isEmpty()) {
            model.addAttribute("erro", "O campo Medico é obrigatório.");
            return iniciar(agenda, model);
        }

        if (agenda.getData() == null || agenda.getData().toString().isEmpty()) {
            model.addAttribute("erro", "O campo Data é obrigatório.");
            return iniciar(agenda, model);
        }

        if (agenda.getHorario() == null || agenda.getHorario().toString().isEmpty()) {
            model.addAttribute("erro", "O campo Horario é obrigatório.");
            return iniciar(agenda, model);
        }

        try {
            agendaService.salvar(agenda);
            return "redirect:/agenda/listar";
        } catch (Exception e) {
            model.addAttribute("erro", "Ocorreu um erro inesperado: " + e.getMessage());
            return iniciar(agenda, model);
        }
    }

    @GetMapping("listar")
    public String listar(Model model) {
        model.addAttribute("agenda", agendaService.listarTodos());
        return "agenda/lista";
    }

    @GetMapping("editar/{id}")
    public String alterar(@PathVariable Long id, Model model){
        model.addAttribute("agenda", agendaService.buscarPorId(id));
        model.addAttribute("pacientes", pacienteService.listarTodos());
        model.addAttribute("medicos", medicoService.listarTodos());
        return "agenda/formulario";
    }


    @GetMapping("remover/{id}")
    public String remover(@PathVariable Long id, Model model) {
        agendaService.deletarPorId(id);
        return "redirect:/agenda/listar";
    }
}
