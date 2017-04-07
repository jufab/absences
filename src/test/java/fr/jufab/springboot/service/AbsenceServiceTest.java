package fr.jufab.springboot.service;

import fr.jufab.springboot.constant.StatusAbsence;
import fr.jufab.springboot.domain.Absence;
import fr.jufab.springboot.domain.Personne;
import fr.jufab.springboot.domain.repository.AbsenceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.DateUtil.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
@SqlGroup({
        @Sql("classpath:fr/jufab/springboot/repository/personnes.sql"),
        @Sql("classpath:fr/jufab/springboot/repository/absences.sql")
})
public class AbsenceServiceTest {

    @Autowired
    private AbsenceService absenceService;
    @Autowired
    private PersonneService personneService;

    private Personne unePersonne;
    private Date uneDate = new Date();

    @Before
    public void chargementDUnePersonne() {
        List<Personne> desPersonnes = this.personneService.getAllPersonnes(0);
        this.unePersonne = desPersonnes.get(0);
    }

    @Test
    public void testLAjoutDUneAbsence() {
        Absence absence = new Absence(this.unePersonne,uneDate,true,true, StatusAbsence.S.name());
        Absence newAbsence = this.absenceService.createAbsence(absence);
        assertThat(newAbsence).hasFieldOrProperty("idAbsence").isNotNull();
        assertThat(newAbsence).hasFieldOrPropertyWithValue("dateAbsence",uneDate);
        assertThat(newAbsence).hasFieldOrPropertyWithValue("matin",true);
        assertThat(newAbsence).hasFieldOrPropertyWithValue("apresMidi",true);
        assertThat(newAbsence).hasFieldOrPropertyWithValue("status",StatusAbsence.S.name());
    }

    @Test
    public void testListerLesAbsencesAvecEtSansLimite() {
        Calendar calendarTest = Calendar.getInstance();
        calendarTest.clear();
        calendarTest.set(2017,Calendar.JANUARY,1,0,0,0);
        List<Absence> absences = this.absenceService.getAllAbsences(0);
        Absence uneAbsenceDeTest = absences.get(0);
        assertThat(uneAbsenceDeTest.getDateAbsence()).hasSameTimeAs(calendarTest.getTime());
        absences = this.absenceService.getAllAbsences(2);
        assertThat(absences).hasSize(2);
    }

    @Test
    public void testObtenirUneAbsenceParSonId() {
        Absence absence = this.absenceService.getAbsenceById(new Long(1));
        assertThat(absence).isNotNull();
    }

    @Test
    public void testModifierUneAbsence() {
        Absence absence = this.absenceService.getAbsenceById(new Long(1));
        absence.setStatus(StatusAbsence.V.name());
        absence = this.absenceService.updateAbsence(absence);
        assertThat(absence).hasFieldOrPropertyWithValue("status",StatusAbsence.V.name());
    }

    @Test
    public void testSupprimerUneAbsenceParId() {
        List<Absence> absences = this.absenceService.getAllAbsences(0);
        this.absenceService.deleteAbsenceById(new Long(absences.size()));
        List<Absence> newAbsences = this.absenceService.getAllAbsences(0);
        assertThat(newAbsences.size()).isEqualTo(absences.size()-1);
    }

    @Test
    public void testSupprimerUneAbsence() {
        List<Absence> absences = this.absenceService.getAllAbsences(0);
        Absence absence = absences.get(absences.size()-1);
        this.absenceService.deleteAbsence(absence);
        List<Absence> newAbsences = this.absenceService.getAllAbsences(0);
        assertThat(newAbsences.size()).isEqualTo(absences.size()-1);
    }
}
