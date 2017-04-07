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
    private Date uneDate;

    @Before
    public void chargerUnePersonnePourLeTest() {
        Personne unePersonne = new Personne(null,"FABRE", "Julien");
        this.unePersonne = personneRepository.saveAndFlush(unePersonne);
        this.uneDate = new Date();

    }

    @Test
    public void testAjoutDUneAbsence() throws Exception {
        Absence uneAbsence = new Absence(this.unePersonne,uneDate,true,false,"S");
        Absence newAbsence = this.absenceRepository.saveAndFlush(uneAbsence);
        assertThat(newAbsence.getIdAbsence()).isNotNull().isNotZero();
        uneAbsence.setIdAbsence(newAbsence.getIdAbsence());
        assertThat(uneAbsence).isEqualTo(newAbsence);
    }


    @Test
    public void testSuppressionDUneAbsence() throws Exception {
        Absence uneAbsence = new Absence(this.unePersonne,uneDate,true,false,"S");
        Absence newAbsence = this.absenceRepository.saveAndFlush(uneAbsence);
        this.absenceRepository.delete(newAbsence);
        assertThat(this.absenceRepository.findAll()).hasSize(0);
    }


    @Test
    public void testModifDUneAbsence() throws Exception {
        Absence uneAbsence = new Absence(this.unePersonne,uneDate,true,false,"S");
        this.absenceRepository.saveAndFlush(uneAbsence);
        uneAbsence = this.absenceRepository.findAll().iterator().next();
        uneAbsence.setStatus(StatusAbsence.valueOf("V").name());
        uneAbsence.setApresMidi(true);
        uneAbsence.setMatin(true);
        uneAbsence = this.absenceRepository.saveAndFlush(uneAbsence);
        assertThat(uneAbsence.getIdAbsence()).isNotNull().isNotZero();
        assertThat(uneAbsence).extracting("apresMidi").contains(true);
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
