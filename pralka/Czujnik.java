package pralka;

import java.util.Random;

public class Czujnik implements Runnable {
    String nazwa;
    double stan = 0; //stan w sensie temperatury, poziomu wody, itd
    int zuzycie = 0; //zużycie czujnika
    double tempo = 0.1;
    Thread czujnikThread;

    public Czujnik(String nazwa) {
        this.nazwa = nazwa;
        czujnikThread = new Thread(this);
        czujnikThread.start();
    }

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
    public synchronized void ustawStan(double poziom) {
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

    //dane do raportu o czujnikach
    public String raportuj() {
        String raport = "";
        raport = "["+nazwa.toUpperCase()+"] Zużycie: " + zuzycie + ", zdatność do użytku: "+sprawdzZuzycie();
        return raport;
    }

    //zmiana stanu podczas działania pralki
    private synchronized void zmianaStanu() {
        Random losuj = new Random();
        double nowyStan = losuj.nextDouble(21*tempo)+(stan-10*tempo);
        stan = nowyStan;
    }

    public void run() {
        while (!Thread.interrupted()) {
            zmianaStanu();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

}