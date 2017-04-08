package fr.jufab.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "absence")
public class Absence implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idAbsence;
    //@JsonManagedReference
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

    public Absence(Long idAbsence,Personne personne, Date dateAbsence, Boolean matin, Boolean apresMidi, String status) {
        this.idAbsence = idAbsence;
        this.personne = personne;
        this.dateAbsence = dateAbsence;
        this.matin = matin;
        this.apresMidi = apresMidi;
        this.status = status;
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

    public void setIdAbsence(Long idAbsence) { this.idAbsence = idAbsence;}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Absence absence = (Absence) o;

        if (idAbsence != null ? !idAbsence.equals(absence.idAbsence) : absence.idAbsence != null) return false;
        if (dateAbsence != null ? !dateAbsence.equals(absence.dateAbsence) : absence.dateAbsence != null) return false;
        if (!matin.equals(absence.matin)) return false;
        if (!apresMidi.equals(absence.apresMidi)) return false;
        return status.equals(absence.status);
    }

}
