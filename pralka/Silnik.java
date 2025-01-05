package pralka;

public class Silnik extends Czujnik {
    //double stan
    //int zuzycie
    //sprawdzZuzycie
    //pomiar - zwraca stan
    //ustawStan

    //wszelkie zmiany prędkości, funkcje sterownika
    public void ustawPredkosc(double predkosc) {
        tempo = 100;
        ustawStan(predkosc);
        System.out.println("Obracanie bębna z prędkością: " + predkosc+" RPM");
    }

    public void zatrzymaj() {
        tempo = 100;
        ustawStan(0);
        System.out.print("Bęben zatrzymany");
    }



}
