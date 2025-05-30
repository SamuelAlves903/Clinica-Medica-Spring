package clinica.agendamento.trabalho.controller;

import clinica.agendamento.trabalho.model.Medico;
import clinica.agendamento.trabalho.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("medico")
public class MedicoController {

    @Autowired
    private MedicoService service;

    @GetMapping
    public String iniciar(Medico medico, Model model) {
        return "medico/formulario";
    }

    @PostMapping("savar")
    public String salvar(Medico medico, Model model) {

        try {
            service.salvar(medico);
            return "redirect:/medico/listar";
        } catch (Exception e) {
            model.addAttribute("Ocorreu um erro inesperado" + e.getMessage());
            return iniciar(medico, model);
        }
    }

    @GetMapping("listar")
    public String listar(Model model) {
        model.addAttribute("medico", service.listarTodos());
        return "medico/lista";
    }

    @GetMapping("editar/{id}")
    public String alterar(@PathVariable Long id, Model model){
        model.addAttribute("medico", service.buscarPorId(id));
        return "compra/formulario";
    }

    @GetMapping("remover/{id}")
    public String remover(@PathVariable Long id, Model model) {
        service.deletarPorId(id);
        return "redirect:/compra/listar";
    }
}
