package clinica.agendamento.trabalho.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NomeController {

    @GetMapping("/nomes")
    public String listarNomes() {
        return "nomes";
    }
}
