package pralka;

public class Pojemnik {
    double pojemnoscMin; //pojemność pojemnika minimalna
    double pojemnoscMax; //pojemność maksymalna pojemnika
    double zapelnienie = 0; // ile już jest zapełnione

    public Pojemnik(double pojemnoscMin, double pojemnoscMax) {
        this.pojemnoscMin = pojemnoscMin;
        this.pojemnoscMax = pojemnoscMax;
    }

    //napełnienie pojemnika
    public void napelnij(double waga) {
        zapelnienie += waga;
    }

    //wyciąganie z pojemnika/wylewanie
    public void wyjmij(double waga) {
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
