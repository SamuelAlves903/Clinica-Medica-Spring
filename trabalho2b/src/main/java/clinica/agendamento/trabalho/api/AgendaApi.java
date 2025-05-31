package clinica.agendamento.trabalho.api;

import clinica.agendamento.trabalho.model.Agenda;
import clinica.agendamento.trabalho.service.AgendaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/agenda")
public class AgendaApi {

    private AgendaService service;

    @GetMapping
    public ResponseEntity listarTodos(){
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity listarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody Agenda agenda){
        service.salvar(agenda);
        return ResponseEntity.ok().build();    }

    @PutMapping
    public ResponseEntity alterar(@RequestBody Agenda agenda){
        if (agenda.getId() == null) return ResponseEntity.badRequest().build();
        service.salvar(agenda);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable Long id){
        service.deletarPorId(id);
        return ResponseEntity.ok().build();
    }
}
