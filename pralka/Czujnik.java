package pralka;

import java.io.*;
import java.util.Random;

public class Czujnik implements Runnable {
    static String plikCzujnik = "czujnikiStan.csv";

    String nazwa;
    double stan = 0; //stan w sensie temperatury, poziomu wody, itd
    double zuzycie = 0; //zużycie czujnika
    double tempo = 1;
    boolean czyWatek = true;
    Thread czujnikThread;

    public Czujnik(String nazwa, boolean... czyWatek) {
        if (czyWatek.length>0) {
            this.czyWatek = czyWatek[0];
        }
        BufferedReader br = null;
        String line = "";
        String[] wpis;
        File file = new File(plikCzujnik);
        try {
            if (file.exists() && file.length() > 0) {
                br = new BufferedReader(new FileReader(file));

                while((line=br.readLine())!=null) {
                    wpis = line.split(",");
                    if (wpis[0].equals(nazwa)) {
                        stan = Double.parseDouble(wpis[1]);
                        zuzycie = Double.parseDouble(wpis[2]);
                        tempo = Double.parseDouble(wpis[3]);
                    }
                }
            }
        } catch (FileNotFoundException e) {}
        catch (IOException e) {}
        finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {}
            }
        }
        this.nazwa = nazwa;
        if (this.czyWatek) {
            czujnikThread = new Thread(this);
            czujnikThread.start();
        }
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
        System.out.println("Ustawianie "+nazwa+" do stanu "+poziom);
        while (Math.abs(poziom-stan)>(tempo/10)) {
            double roznica = poziom-stan;
            double zmiana = Math.round((roznica/tempo)*100.0)/100.0;
            stan += zmiana;
            try {
            Thread.sleep(50);
            } catch (InterruptedException e) {}
        }
        zuzycie++;
    }

    //dane do raportu o czujnikach
    public String raportuj() {
        String raport = "["+nazwa.toUpperCase()+"] Zużycie: " + zuzycie + "%, zdatność do użytku: "+sprawdzZuzycie();
        return raport;
    }

    public void zapisz() {
        if (czujnikThread !=null) {
            czujnikThread.interrupt();
        }
        String doZapisu = String.join(",", nazwa, String.valueOf(stan), String.valueOf(zuzycie), String.valueOf(tempo));
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(plikCzujnik, true));
            bw.write(doZapisu+"\n");
        } catch (IOException e) {}
        finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {}
            }
        }
    }

    //zmiana stanu podczas działania pralki przez wątki
    private synchronized void zmianaStanu() {
        Random losuj = new Random();
        double nowyStan =   (Math.abs(losuj.nextDouble(21*tempo)+(stan-10*tempo))*100);
        stan = nowyStan/100;
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