package clinica.agendamento.trabalho;

import clinica.agendamento.trabalho.model.Medico;
import clinica.agendamento.trabalho.model.Paciente;
import clinica.agendamento.trabalho.service.MedicoService;
import clinica.agendamento.trabalho.service.PacienteService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
public class TrabalhoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrabalhoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(PacienteService pacienteService, MedicoService medicoService) {
		return args -> {
			if (pacienteService.listarTodos().isEmpty()) {
				var paciente = new Paciente(
						null,
						"Wellington",
						"10345673456",
						LocalDate.of(2004, 6, 15),
						"4498765467"
				);
				pacienteService.salvar(paciente);
			}

			if (medicoService.listarTodos().isEmpty()) {
				var medico = new Medico(
						null,
						"Samuel",
						"4567896",
						"Pediatria"
				);
				medicoService.salvar(medico);
			}
		};
	}
}
