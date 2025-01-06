package pralka;

import java.util.Scanner;

public class Pralka {
    Scanner sc = new Scanner(System.in);

    //Pojemniki
    Pojemnik plynPlukanie = new Pojemnik(10, 50);
    Pojemnik proszekDoPrania = new Pojemnik(5, 10);
    Beben beben = new Beben(2, 5);

    Panel panel = new Panel();

    //Czujniki
    Czujnik cisnienieWody = new Czujnik();
    Czujnik temperaturaWody = new Czujnik();
    Czujnik poziomWody = new Czujnik();
    Czujnik zabezpieczenieDrzwi = new Czujnik(); //0-otwarte, 1-zamknięte, 2-zablokowane
    Czujnik przeplywomierz = new Czujnik();

    Silnik silnik = new Silnik();

    //zarządzanie wodą - elektrozawory, pompa, grzałka, filtr
    UkladWodny ukladWodny = new UkladWodny();


    //Programy wbudowane
    Program wbudowany1 = new Program("Bawełna", "standardowy program z wyższą temperaturą i prędkością wirowania.", 60.0, false, 15, 60, 1200, false);
    Program wbudowany2 = new Program("Syntetyki", "delikatniejsze pranie z dodatkowym płukaniem.", 40.0, true, 10, 45, 1000, false);
    Program wbudowany3 = new Program("Delikatne", "niska temperatura, krótki czas, mniejsze obroty.", 30.0, false, 0, 30, 800, false);
    Program wbudowany4 = new Program("Wełna", "krótki czas prania i niskie obroty.", 30.0, true, 5, 40, 600, false);
    Program wbudowany5 = new Program("Pranie szybkie", "rótkie, szybkie odświeżenie ubrań w niższej temperaturze.", 20.0, false, 0, 15, 1000, false);

    //obsłużenie wybranej akcji przez usera w panelu
    public void akcja() {
        Program program = null;
        int choice = 0;
        while (choice!=6) {
            choice = panel.widokPanelu(sc);
            switch (choice) {
                case 1:
                    program = panel.buttonProgram(sc);
                    break;
                case 2: //do zmiany
                    panel.buttonWlacznik();
                    break;
                case 3: //do zmiany
                    plynPlukanie.napelnij(10);
                    proszekDoPrania.napelnij(10);
                    break;
                case 4:
                    panel.obslugaDrzwiczek(zabezpieczenieDrzwi);
                    break;
                case 5: //do zmiany
                    beben.napelnij(5);
                    break;
            }
        }
    }

    //obecnie do zamykania Scannera
    public void sprzatanie() {
        sc.close();
    }


    //Sprawdzanie stanu pralki
    private boolean stanPralki() {
        boolean sprawnosc = true;
        if (cisnienieWody.sprawdzZuzycie()!=true) sprawnosc = false;
        if (temperaturaWody.sprawdzZuzycie()!=true) sprawnosc = false;
        if (zabezpieczenieDrzwi.sprawdzZuzycie()!=true) sprawnosc = false;
        if (przeplywomierz.sprawdzZuzycie()!=true) sprawnosc = false;
        if (silnik.sprawdzZuzycie()!=true) sprawnosc = false;
        if (ukladWodny.sprawdzZuzycie()!=true) sprawnosc = false;
        return sprawnosc;
    }

    //proces prania
    private void pranie(Program program) {
        if (zabezpieczenieDrzwi.pomiar()==1) {
            zabezpieczenieDrzwi.stan=2;
            if (beben.wywazenie(silnik)<=0 && beben.sprawdz()==false) return;
            ukladWodny.przygotujWode(poziomWody, przeplywomierz, temperaturaWody, beben.ileWody(), program.temperaturaWody);
            proszekDoPrania.uzyj();
            panel.ustawCzas(program.czasPraniaWstepnego+program.czasPraniaZasadniczego);
            Thread wyswietlaczCzasu = new Thread(new Panel());
            wyswietlaczCzasu.start();
            if (program.czasPraniaWstepnego>0) pranieWstepne(program);
            pranieZasadnicze(program);
            try {
                wyswietlaczCzasu.join();
            } catch (InterruptedException e) {}
            if (program.dodatkowePlukanie) ukladWodny.plukanie(beben, silnik, poziomWody, przeplywomierz, temperaturaWody, plynPlukanie);
            for (int i = 0; i < 4; i++) {
                beben.wirowanie(silnik, program.predkoscObrotowaWirowania);
            }
            ukladWodny.odprowadzWode(poziomWody, przeplywomierz);
            zabezpieczenieDrzwi.stan=1;
        } else System.out.println("Drzwiczki są otwarte.. Zamknij je przed");
    }

    //Wykonanie prania wstępnego
    private void pranieWstepne(Program program) {
        int czas = 0;
        double temperatura = program.temperaturaWody/5*3;
        int predkosc = program.predkoscObrotowaWirowania/2;
        while (czas < program.czasPraniaWstepnego) {
            sprawdzParametry(temperatura, predkosc);
            beben.wirowanie(silnik, predkosc);
            czas++;
        }
    }

    //Wykonanie prania zasadniczego
    private void pranieZasadnicze(Program program) {
        int czas = 0;
        while (czas < program.czasPraniaWstepnego) {
            sprawdzParametry(program.temperaturaWody, program.predkoscObrotowaWirowania);
            beben.wirowanie(silnik, program.predkoscObrotowaWirowania);
            czas++;
        }

    }

    //Sprawdzenie utrzymania odpowiednich parametrów prania
    private void sprawdzParametry(double temperatura, int predkosc) {
        double woda = beben.ileWody();
        if (woda!=poziomWody.pomiar()) poziomWody.ustawStan(woda);
        if (temperaturaWody.pomiar()!=temperatura) temperaturaWody.ustawStan(temperatura);
        if (silnik.pomiar()!=predkosc) silnik.ustawPredkosc(predkosc);
        try {
            Thread.sleep(600); //czas zajmuje też ustawianie ewentualne i wirowanie
        } catch (InterruptedException e) {}
    }

}
