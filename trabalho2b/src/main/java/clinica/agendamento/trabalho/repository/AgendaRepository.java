package clinica.agendamento.trabalho.repository;

import clinica.agendamento.trabalho.model.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
}
