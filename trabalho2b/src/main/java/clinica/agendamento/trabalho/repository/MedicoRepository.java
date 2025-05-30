package clinica.agendamento.trabalho.repository;

import clinica.agendamento.trabalho.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository <Medico, Long> {


}
