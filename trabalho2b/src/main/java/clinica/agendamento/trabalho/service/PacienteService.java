package clinica.agendamento.trabalho.service;

import clinica.agendamento.trabalho.model.Paciente;
import clinica.agendamento.trabalho.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repository;

    @Transactional
    public Paciente salvar(Paciente paciente) {
        return repository.save(paciente);
    }

    public List<Paciente> listarTodos() {
        return repository.findAll();
    }

    public Paciente buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente com id " + id + " não encontrado"));
    }

    @Transactional
    public void deletarPorId(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Paciente com id " + id + " não encontrado para deletar");
        }
        repository.deleteById(id);
    }
}
