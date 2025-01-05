package pralka;

public class Beben extends Pojemnik {

    public Beben(double pojemnoscMin, double pojemnoscMax) {
        super(pojemnoscMin, pojemnoscMax); //konstruktor z klasy pojemnik
    }

    //pobranie info o zapełnieniu pralki, czyli wyważenie, ile jest
    public double wywazenie() {
        if (zapelnienie>0) {
            System.out.println("Bęben powoli się obraca...");
        }
        return zapelnienie;
    }

    //sprawdzenie, ile wody potrzeba do prania
    public double ileWody() {
        double plyn = zapelnienie*8;
        return plyn;
    }

    public void wirowanie(Silnik silnik, int predkosc) {
        System.out.println("Rozpoczynam wirowanie");
        for (int i = 0; i < 5; i++) {
            silnik.ustawPredkosc(predkosc);
            silnik.zatrzymaj();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {}
        }

    }

}