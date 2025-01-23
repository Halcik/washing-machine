package pralka;

public class Silnik extends Czujnik {
    //double stan
    //int zuzycie
    //sprawdzZuzycie
    //pomiar - zwraca stan
    //ustawStan
    public Silnik() {
        super("Silnik");
        tempo = 10;
    }

    //wszelkie zmiany prędkości, funkcje sterownika
    public void ustawPredkosc(double predkosc) {
        ustawStan(predkosc);
        System.out.println("Obracanie bębna z prędkością: " + predkosc+" RPM");
        zuzycie-=0.5;
    }

    public void zatrzymaj() {
        ustawStan(0);
        System.out.println("Bęben zatrzymany");
    }



}
