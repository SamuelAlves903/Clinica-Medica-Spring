package clinica.agendamento.trabalho.service;

import clinica.agendamento.trabalho.model.Medico;
import clinica.agendamento.trabalho.repository.MedicoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository repository;

    @Transactional
    public void salvar(Medico medico) {
        try {
            repository.save(medico);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Medico> listarTodos() {
        return repository.findAll();
    }

    public Medico buscarPorId(Long id) {
        return repository.findById(id).get();
    }

    public void deletarPorId(Long id) {
        repository.deleteById(id);
    }
}
