package clinica.agendamento.trabalho.service;

import clinica.agendamento.trabalho.model.Agenda;
import clinica.agendamento.trabalho.repository.AgendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository repository;

    @Transactional
    public void salvar(Agenda agenda) {
        try {
            repository.save(agenda);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Agenda> listarTodos() {
        return repository.findAll();
    }

    public Agenda buscarPorId(Long id) {
        return repository.findById(id).get();
    }

    public void deletarPorId(Long id) {
        repository.deleteById(id);
    }
}
