package org.example.model;

import jakarta.persistence.*;

@Entity
public class Upis {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int UpisID;

    @ManyToOne
    @JoinColumn(name = "IDProgramObrazovanja")
    private ProgramObrazovanja programObrazovanja;

    @ManyToOne
    @JoinColumn(name = "IDPolaznik")
    private Polaznik polaznik;

    public Upis() {
    }

    public int getUpisID() {
        return UpisID;
    }

    public void setUpisID(int upisID) {
        UpisID = upisID;
    }

    public ProgramObrazovanja getProgramObrazovanja() {
        return programObrazovanja;
    }

    public void setProgramObrazovanja(ProgramObrazovanja programObrazovanja) {
        this.programObrazovanja = programObrazovanja;
    }

    public Polaznik getPolaznik() {
        return polaznik;
    }

    public void setPolaznik(Polaznik polaznik) {
        this.polaznik = polaznik;
    }
}
