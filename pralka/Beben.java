package pralka;

public class Beben extends Pojemnik {

    public Beben(double pojemnoscMin, double pojemnoscMax) {
        super(pojemnoscMin, pojemnoscMax, "bęben"); //konstruktor z klasy pojemnik
    }

    //Rozkłada równomiernie ciuchy + info o zapełnieniu pralki
    public double wywazenie(Silnik silnik) {
        if (zapelnienie>0) {
            System.out.println("Bęben powoli się obraca...");
            wirowanie(silnik, 500);
            System.out.println("Bęben wyważony - ciuchy rozłożone równomiernie");
        }
        return zapelnienie;
    }

    //sprawdzenie, ile wody potrzeba do prania
    public double ileWody() {
        double woda = zapelnienie*8;
        return woda;
    }

    public void wirowanie(Silnik silnik, int predkosc) {
        System.out.println("Rozpoczynam wirowanie");
        try {
            silnik.ustawPredkosc(predkosc);
            Thread.sleep(50);
            silnik.zatrzymaj();
            Thread.sleep(50);
        } catch (InterruptedException e) {}

    }

}
