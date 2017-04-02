package fr.jufab.springboot.repository;


import fr.jufab.springboot.constant.StatusAbsence;
import fr.jufab.springboot.domain.Absence;
import fr.jufab.springboot.domain.Personne;
import fr.jufab.springboot.domain.repository.AbsenceRepository;
import fr.jufab.springboot.domain.repository.PersonneRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AbsenceRepositoryTest {

    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private PersonneRepository personneRepository;

    private Personne unePersonne;


    @Before
    public void chargerUnePersonnePourLeTest() {
        Personne unePersonne = new Personne(null,"FABRE", "Julien");
        this.unePersonne = personneRepository.saveAndFlush(unePersonne);
    }

    @Test
    public void testAjoutDUneAbsence() throws Exception {
        Absence uneAbsence = new Absence(this.unePersonne,new Date(),true,false,"S");
        Absence newAbsence = this.absenceRepository.saveAndFlush(uneAbsence);
        assertThat(newAbsence.getIdAbsence()).isNotNull().isNotZero();
    }

    @Test
    public void testSuppressionDUneAbsence() throws Exception {
        Absence uneAbsence = new Absence(this.unePersonne,new Date(),true,false,"S");
        Absence newAbsence = this.absenceRepository.saveAndFlush(uneAbsence);
        this.absenceRepository.delete(newAbsence);
        assertThat(this.absenceRepository.findAll()).hasSize(0);
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "personnes.sql"),
            @Sql(scripts =  "absences.sql")
    })
    public void testListerDesAbsencesEtParStatus() throws Exception {
        List<Absence> absences = this.absenceRepository.findAll();
        assertThat(absences).hasSize(3);
        absences = this.absenceRepository.findByStatus(StatusAbsence.S.toString());
        assertThat(absences).hasSize(2).extracting(Absence::getStatus).contains(StatusAbsence.S.toString());
    }


}
