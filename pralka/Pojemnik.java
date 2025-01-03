package pralka;

public class Pojemnik {
    double pojemnoscMin; //pojemność pojemnika minimalna
    double pojemnoscMax; //pojemność maksymalna pojemnika
    double zapelnienie = 0; // ile już jest zapełnione

    public Pojemnik(double pojemnoscMin, double pojemnoscMax) {
        this.pojemnoscMin = pojemnoscMin;
        this.pojemnoscMax = pojemnoscMax;
    }

    //pobranie info o zapełnieniu pralki, czyli wyważenie, ile jest
    public double wywazenie() {
        return zapelnienie;
    }

    //napełnienie pojemnika
    public void napelnij(double waga) {
        zapelnienie += waga;
    }

    //wyciąganie z pojemnika/wylewanie
    public void wyjmij(double waga) {
        zapelnienie -= waga;
    }

    //sprawdzenie zapełnienia pojemnika - czy git, czy nie
    public boolean sprawdz() {
        if (zapelnienie > pojemnoscMax || zapelnienie < pojemnoscMin) {
            return false;
        }
        return true;
    }


}
