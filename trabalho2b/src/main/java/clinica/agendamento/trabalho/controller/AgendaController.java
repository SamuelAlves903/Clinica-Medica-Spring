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
    public String salvar(Long id, Long pacienteId, Long medicoId,
            java.sql.Date data, String horario, Model model) {
        try {
            if (pacienteId == null || medicoId == null || data == null || horario == null) {
                model.addAttribute("erro", "Todos os campos são obrigatórios.");
                model.addAttribute("pacientes", pacienteService.listarTodos());
                model.addAttribute("medicos", medicoService.listarTodos());
                return "agenda/formulario";
            }

            java.sql.Time horarioSql = null;
            try {
                if (horario.split(":").length == 2) {
                    horario = horario + ":00";
                }
                horarioSql = java.sql.Time.valueOf(horario);
            } catch (Exception e) {
                model.addAttribute("erro", "Formato de horário inválido: " + horario);
                model.addAttribute("pacientes", pacienteService.listarTodos());
                model.addAttribute("medicos", medicoService.listarTodos());
                return "agenda/formulario";
            }

            Agenda agenda = (id != null) ? agendaService.buscarPorId(id) : new Agenda();

            agenda.setPaciente(pacienteService.buscarPorId(pacienteId));
            agenda.setMedico(medicoService.buscarPorId(medicoId));
            agenda.setData(data);
            agenda.setHorario(horarioSql);

            agendaService.salvar(agenda);

            return "redirect:/agenda/listar";
        } catch (Exception e) {
            model.addAttribute("erro", "Ocorreu um erro inesperado: " + e.getMessage());
            model.addAttribute("pacientes", pacienteService.listarTodos());
            model.addAttribute("medicos", medicoService.listarTodos());
            return "agenda/formulario";
        }
    }


    @GetMapping("listar")
    public String listar(Model model) {
        model.addAttribute("agendas", agendaService.listarTodos());
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
