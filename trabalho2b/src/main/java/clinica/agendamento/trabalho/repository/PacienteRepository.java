package clinica.agendamento.trabalho.repository;

import clinica.agendamento.trabalho.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}
