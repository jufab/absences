package fr.jufab.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Entity
@Table(name = "absence")
public class Absence {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idAbsence;
    @NotNull
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idPersonne")
    private Personne personne;
    @NotNull
    private Date dateAbsence;
    @NotNull
    private Boolean matin;
    @NotNull
    private Boolean apresMidi;
    @NotNull
    private String status;

    public Absence() { }

    public Absence(long idAbsence) {
        this.idAbsence = idAbsence;
    }

    public Absence(Personne personne, Date dateAbsence, Boolean matin, Boolean apresMidi, String status) {
        this.personne = personne;
        this.dateAbsence = dateAbsence;
        this.matin = matin;
        this.apresMidi = apresMidi;
        this.status = status;
    }

    public Long getIdAbsence() {
        return idAbsence;
    }

    public void setIdAbsence(Long idAbsence) {
        this.idAbsence = idAbsence;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public Date getDateAbsence() {
        return dateAbsence;
    }

    public void setDateAbsence(Date dateAbsence) {
        this.dateAbsence = dateAbsence;
    }

    public Boolean getMatin() {
        return matin;
    }

    public void setMatin(Boolean matin) {
        this.matin = matin;
    }

    public Boolean getApresMidi() {
        return apresMidi;
    }

    public void setApresMidi(Boolean apresMidi) {
        this.apresMidi = apresMidi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
