package fr.pe.buzz.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ijfa3650 on 22/03/2017.
 */
public class PersonneDTO {

    private Long idPersonne;
    private String nom;
    private String prenom;
    private List<AbsenceDTO> absences=new ArrayList<AbsenceDTO>();

    public PersonneDTO() {
    }

    public PersonneDTO(Long idPersonne, String nom, String prenom) {
        this.idPersonne = idPersonne;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Long getIdPersonne() {
        return idPersonne;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public List<AbsenceDTO> getAbsences() {
        return absences;
    }

    public void setAbsences(List<AbsenceDTO> absences) {
        this.absences = absences;
    }
}
