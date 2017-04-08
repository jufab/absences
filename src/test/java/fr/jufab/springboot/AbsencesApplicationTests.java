package fr.jufab.springboot;

import fr.jufab.springboot.controller.AbsenceController;
import fr.jufab.springboot.controller.PersonneController;
import fr.jufab.springboot.domain.repository.AbsenceRepository;
import fr.jufab.springboot.domain.repository.PersonneRepository;
import fr.jufab.springboot.service.AbsenceService;
import fr.jufab.springboot.service.PersonneService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class AbsencesApplicationTests {

	@Autowired
	private AbsenceController absenceController;
	@Autowired
	private PersonneController personneController;
	@Autowired
	private AbsenceService absenceService;
	@Autowired
	private PersonneService personneService;
	@Autowired
	private PersonneRepository personneRepository;
	@Autowired
	private AbsenceRepository absenceRepository;

	@Test
	public void contextLoads()throws Exception {
		assertThat(absenceController).isNotNull();
		assertThat(personneController).isNotNull();
		assertThat(absenceService).isNotNull();
		assertThat(personneService).isNotNull();
		assertThat(personneRepository).isNotNull();
		assertThat(absenceRepository).isNotNull();
	}

}
