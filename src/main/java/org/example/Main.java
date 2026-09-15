package org.example;

import org.example.model.Polaznik;
import org.example.model.ProgramObrazovanja;
import org.example.model.Upis;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean uvjet = true;
        Scanner input = new Scanner(System.in);
        do {
            System.out.println("Meni:\n 1. Unesi novog polaznika\n 2. Unesi novi program obrazovanja\n 3. Upisi polaznika na program obrazovanja\n 4. Prebaci polaznika u drugi program obrazovanja\n 5. Prikazi upisane studente u programu obrazovanja\n " + "6. Izlaz");
            String izbornik = input.nextLine();

            switch (izbornik) {
                case "1":
                    unesiPolaznika(input);
                    break;
                case "2":
                    unesiProgram(input);
                    break;
                case "3":
                    upisiPolaznika(input);
                    break;
                case "4":
                    prebaciPolaznika(input);
                    break;
                case "5":
                    prikaziStudente();
                    break;
                case "6":
                    uvjet = false;
                    System.out.println("Izasli ste iz menia!");
                    break;
                default:
                    System.out.println("Nepoznata opcija!");
            }
        } while (uvjet);

        input.close();
    }
    public static void unesiPolaznika(Scanner input) {
        System.out.println("Unesi ime polaznika");
        String ime = input.nextLine();
        while (ime.isEmpty()) {
            System.out.println("Niste unijeli ime! Unesite!");
            ime = input.nextLine();
        }
        System.out.println("Unesi prezime polaznika");
        String prezime = input.nextLine();
        while (prezime.isEmpty()) {
            System.out.println("Niste unijeli prezime! Unesite!");
            prezime = input.nextLine();
        }
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Polaznik polaznik = new Polaznik(ime, prezime);
            session.persist(polaznik);
            transaction.commit();
            System.out.println("Unesli ste polaznika: " + ime + " " + prezime);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void unesiProgram(Scanner input) {
        System.out.println("Unesi ime programa obrazovanja");
        String ime = input.nextLine();
        while (ime.isEmpty()) {
            System.out.println("Niste unijeli naziv programa obrazovanja! Unesite!");
            ime = input.nextLine();
        }
        System.out.println("Unesi broj potrebnih CSVET bodova");
        int csv = Integer.parseInt(input.nextLine());
        while (csv <= 0 || csv >= 1000) {
            System.out.println("Unesite validne CSVET bodove!");
            csv = Integer.parseInt(input.nextLine());
        }
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            ProgramObrazovanja po = new ProgramObrazovanja(ime, csv);
            session.persist(po);
            transaction.commit();
            System.out.println("Unesli ste program: " + ime + " " + csv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void ispisPolaznika() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Polaznik> polaznici = session.createQuery("FROM Polaznik", Polaznik.class).list();
            System.out.println("Polaznici u databazi:");
            for (Polaznik p : polaznici) {
                System.out.println(p.getPolaznikID() + " | " + p.getIme() + " " + p.getPrezime());
            }
        }
    }
    public static void ispisPrograma() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<ProgramObrazovanja> poi = session.createQuery("FROM ProgramObrazovanja ", ProgramObrazovanja.class).list();
            System.out.println("Programi obrazovanja u databazi:");
            for (ProgramObrazovanja po : poi) {
                System.out.println(po.getProgramObrazovanjaId() + " | " + po.getNaziv() + " " + po.getCSVET());
            }
        }
    }
    public static void prikaziStudente() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Upis> upisi = session.createQuery("FROM Upis", Upis.class).list();
            System.out.println("Polaznici u databazi:");
                for(Upis u : upisi){
                    System.out.println(u.getPolaznik().getIme() + " " +  u.getPolaznik().getPrezime() + " " + u.getProgramObrazovanja());
            }
        }
    }
    public static void upisiPolaznika(Scanner input){
        ispisPolaznika();
        System.out.println("Odaberite ID polaznika kojeg zelite upisati:");
        int pID = Integer.parseInt(input.nextLine());
        ispisPrograma();
        System.out.println("Odaberite ID programa obrazovanja u koji ga zelite upisati:");
        int poID = Integer.parseInt(input.nextLine());
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Polaznik polaznik = session.find(Polaznik.class, pID);
            ProgramObrazovanja program = session.find(ProgramObrazovanja.class, poID);

            Upis u = new Upis();
            u.setPolaznik(polaznik);
            u.setProgramObrazovanja(program);
            session.persist(u);
            transaction.commit();
            System.out.println("Polaznik " + polaznik + " upisan je na program " + program);
        }
    }
    public static void prebaciPolaznika(Scanner input){
        ispisPolaznika();
        System.out.println("Odaberite ID polaznika kojeg zelite prebaciti:");
        int pID = Integer.parseInt(input.nextLine());
        ispisPrograma();
        System.out.println("Odaberite ID programa obrazovanja u koji ga zelite prebaciti:");
        int poID = Integer.parseInt(input.nextLine());
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Polaznik polaznik = session.find(Polaznik.class, pID);
            ProgramObrazovanja program = session.find(ProgramObrazovanja.class, poID);
            String hql = "UPDATE Upis u SET u.programObrazovanja = :poID WHERE u.polaznik.polaznikID = :pID";
            session.createQuery(hql)
                    .setParameter("poID", program)
                    .setParameter("pID", pID)
                    .executeUpdate();
            transaction.commit();
            System.out.println("Polaznik " + polaznik + " prebacen je na program " + program);
        }
        catch (Exception e){
            e.printStackTrace();
            if(transaction.isActive()) {
                transaction.rollback();
            }
        }
        finally{
            session.close();
        }
    }
}