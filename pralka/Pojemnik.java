package pralka;

import java.util.Scanner;

public class Pojemnik {
    String nazwa;
    double pojemnoscMin; //pojemność pojemnika minimalna
    double pojemnoscMax; //pojemność maksymalna pojemnika
    double zapelnienie = 0; // ile już jest zapełnione

    public Pojemnik(double pojemnoscMin, double pojemnoscMax, String nazwa) {
        this.pojemnoscMin = pojemnoscMin;
        this.pojemnoscMax = pojemnoscMax;
        this.nazwa = nazwa;
    }

    //napełnienie pojemnika
    public void napelnij(Scanner sc) {
        System.out.println("Napełnij "+nazwa+":");
        double waga = sc.nextDouble();
        zapelnienie += waga;
    }

    //wyciąganie z pojemnika/wylewanie
    public void wyjmij(Scanner sc) {
        System.out.println("Wyjmij z"+nazwa+":");
        double waga = sc.nextDouble();
        zapelnienie -= waga;
    }

    //użycie zawartości pojemnika podczas prania
    public void uzyj() {
        try {
            if (zapelnienie<0) throw new IllegalAccessException("Brak płynu");
            System.out.println("Napełnianie płynem...");
            zapelnienie = 0;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //sprawdzenie zapełnienia pojemnika - czy git, czy nie
    public boolean sprawdz() {
        if (zapelnienie > pojemnoscMax || zapelnienie < pojemnoscMin) {
            return false;
        }
        return true;
    }

}
