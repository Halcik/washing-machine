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
    }

}
