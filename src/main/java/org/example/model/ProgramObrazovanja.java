package org.example.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class ProgramObrazovanja {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "ProgramObrazovanjaID")
    private Long programObrazovanjaId;

    @Column(name = "Naziv")
    private String naziv;

    private int CSVET;

    @OneToMany(mappedBy = "programObrazovanja")
    private Set<Upis> upisi = new HashSet<>();

    public ProgramObrazovanja() {
    }

    public ProgramObrazovanja(String naziv, int CSVET) {
        this.naziv = naziv;
        this.CSVET = CSVET;
    }

    public Long getProgramObrazovanjaId() {
        return programObrazovanjaId;
    }

    public void setProgramObrazovanjaId(Long programObrazovanjaId) {
        this.programObrazovanjaId = programObrazovanjaId;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getCSVET() {
        return CSVET;
    }

    public void setCSVET(int CSVET) {
        this.CSVET = CSVET;
    }

    public Set<Upis> getUpisi() {
        return upisi;
    }

    public void setUpisi(Set<Upis> upisi) {
        this.upisi = upisi;
    }

    @Override
    public String toString() {
        return naziv + " " + CSVET;
    }
}
