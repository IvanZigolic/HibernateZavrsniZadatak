package org.example.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Polaznik {
    @Id
    @GeneratedValue  (strategy = GenerationType.IDENTITY)
    @Column(name = "PolaznikID")
    private Long polaznikID;

    private String Ime;
    private String Prezime;

    @OneToMany(mappedBy = "polaznik")
    private Set<Upis> upisi = new HashSet<>();

    public Polaznik() {
    }

    public Polaznik(String ime, String prezime) {
        Ime = ime;
        Prezime = prezime;
    }

    public Long getPolaznikID() {
        return polaznikID;
    }

    public void setPolaznikID(Long polaznikID) {
        this.polaznikID = polaznikID;
    }

    public String getIme() {
        return Ime;
    }

    public void setIme(String ime) {
        Ime = ime;
    }

    public String getPrezime() {
        return Prezime;
    }

    public void setPrezime(String prezime) {
        Prezime = prezime;
    }

    public Set<Upis> getUpisi() {
        return upisi;
    }

    public void setUpisi(Set<Upis> upisi) {
        this.upisi = upisi;
    }

    @Override
    public String toString() {
        return Ime + " " + Prezime;
    }
}
