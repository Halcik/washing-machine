package pralka;

public class Czujnik {
    double stan = 0; //stan w sensie temperatury, poziomu wody, itd
    int zuzycie = 0; //zużycie czujnika

    //pomiar stanu czujnika
    public double pomiar() {
        return stan;
    }

    //sprawdzenie zużycia czujnika - jest git do użycia lub nie
    public boolean sprawdzZuzycie() {
        if (zuzycie>99) return false;
        return true;
    }

    //DO NAPISANIA
    public double zwiekszStan(double zmiana) {return stan;}

    //ustawienie do odpowiedniego poziomu stanu czujnika, np. temperatury
    public void ustawStan(double poziom) {
    }
}