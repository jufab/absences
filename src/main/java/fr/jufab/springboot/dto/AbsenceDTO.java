package fr.jufab.springboot.dto;


import java.util.Date;

public class AbsenceDTO   {
    private Long idAbsence;
    private PersonneDTO personne;
    private Date dateAbsence;
    private Boolean matin;
    private Boolean apresMidi;
    private String status;

    public AbsenceDTO() {
    }

    public AbsenceDTO(PersonneDTO personne, Date dateAbsence, Boolean matin, Boolean apresMidi, String status) {
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

    public PersonneDTO getPersonne() {
        return personne;
    }

    public void setPersonne(PersonneDTO personne) {
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