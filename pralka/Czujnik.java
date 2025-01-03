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

    //ustawienie do odpowiedniego poziomu stanu czujnika, np. temperatury
    public void ustawStan(double poziom) {
        double tempo = 0.1;
        while (Math.abs(poziom-stan)>0.01) {
            double roznica = poziom-stan;
            double zmiana = roznica*tempo;
            stan += zmiana;
            System.out.println("Stan: " + stan);
        }
        zuzycie++;
    }
}