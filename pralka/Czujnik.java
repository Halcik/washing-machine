package pralka;

public class Czujnik {
    double stan = 0; //stan w sensie temperatury, poziomu wody, itd
    int zuzycie = 0; //zużycie czujnika
    double tempo = 0.1;

    //pomiar stanu czujnika
    public double pomiar() {
        return stan;
    }

    //sprawdzenie zużycia czujnika - jest git do użycia lub nie
    public boolean sprawdzZuzycie() {
        if (zuzycie>97) return false;
        return true;
    }

    //ustawienie do odpowiedniego poziomu stanu czujnika, np. temperatury
    public void ustawStan(double poziom) {
        while (Math.abs(poziom-stan)>(tempo/10)) {
            double roznica = poziom-stan;
            double zmiana = roznica*tempo;
            stan += zmiana;
            System.out.println("Stan: " + stan);
            try {
            Thread.sleep(100);
            } catch (InterruptedException e) {}
        }
        zuzycie++;
    }
}