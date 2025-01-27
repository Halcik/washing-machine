package pralka;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Pralka implements Runnable {
    Scanner sc = new Scanner(System.in);
    boolean sprawdzanie = true;
    List<String> info = new ArrayList<>();

    //Pojemniki
    Pojemnik plynPlukanie = new Pojemnik(10, 50, "płyn do płukania");
    Pojemnik proszekDoPrania = new Pojemnik(5, 10, "proszek do prania");
    Beben beben = new Beben(2, 5);

    Panel panel = new Panel();

    //Czujniki
    Czujnik cisnienieWody = new Czujnik("Ciśnienie");
    Czujnik temperaturaWody = new Czujnik("Temperatura");
    Czujnik poziomWody = new Czujnik("Woda");
    Czujnik zabezpieczenieDrzwi = new Czujnik("Drzwiczki", false); //0-zamknięte, 1-otwarte, 2-zablokowane
    Czujnik przeplywomierz = new Czujnik("Przepływomierz");

    Silnik silnik = new Silnik();

    //zarządzanie wodą - elektrozawory, pompa, grzałka, filtr
    UkladWodny ukladWodny = new UkladWodny();


    //Programy wbudowane
    Program wbudowany1 = new Program("Bawełna", "standardowy program z wyższą temperaturą i prędkością wirowania.", 60.0, false, 15, 60, 1200, false);
    Program wbudowany2 = new Program("Syntetyki", "delikatniejsze pranie z dodatkowym płukaniem.", 40.0, true, 10, 45, 1000, false);
    Program wbudowany3 = new Program("Delikatne", "niska temperatura, krótki czas, mniejsze obroty.", 30.0, false, 0, 30, 800, false);
    Program wbudowany4 = new Program("Wełna", "krótki czas prania i niskie obroty.", 30.0, true, 5, 40, 600, false);
    Program wbudowany5 = new Program("Pranie szybkie", "rótkie, szybkie odświeżenie ubrań w niższej temperaturze.", 20.0, false, 0, 15, 1000, false);

    Program program; //program wybrany do procesu prania

    List<Czujnik> czujniki = Arrays.asList(cisnienieWody, temperaturaWody, poziomWody, zabezpieczenieDrzwi, przeplywomierz, silnik, ukladWodny.grzalka, ukladWodny.pompa, ukladWodny.filtr, ukladWodny.zaworWe, ukladWodny.zaworWy);


    //obsłużenie wybranej akcji przez usera w panelu
    public void akcja() {
        program = null;
        int choice = 0;
        while (choice != 6) {
            choice = panel.widokPanelu(sc);
            switch (choice) {
                case 1:
                    program = panel.buttonProgram(sc);
                    break;
                case 2:
                    if (program != null) {
                        panel.buttonWlacznik();
                        pranie();
                    } else System.out.println("Wybierz najpierw program");
                    break;
                case 3:
                    plynPlukanie.napelnij(sc);
                    proszekDoPrania.napelnij(sc);
                    break;
                case 4:
                    panel.obslugaDrzwiczek(zabezpieczenieDrzwi);
                    break;
                case 5:
                    if (zabezpieczenieDrzwi.stan==1) {
                        beben.napelnij(sc);
                    } else System.out.println("Otwórz najpierw dźwiczki");
                    break;
                case 6:
                    System.out.println("Zakończenie działania symulatora...");
                    break;
                default:
                    System.out.println("Nieznana operacja..");
                    break;
            }
        }
    }

    //obecnie do zamykania Scannera
    public void sprzatanie() {
        sc.close();
        raportPralki();
        File czujnik = new File(Czujnik.plikCzujnik);
        czujnik.delete(); //czyszczenie pliku przed zapisem nowym
        for (Czujnik cz : czujniki) {
            cz.zapisz();
        }
    }

    private void raportPralki() {
        FileWriter writer = null;
        LocalDateTime data = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        try {
            writer = new FileWriter("raport.txt", true);
            writer.write("\n~~~Raport z " + data.format(formatter) + "~~~\n");
            for (Czujnik cz : czujniki) {
                writer.write(cz.raportuj() + "\n");
            }
            if (!info.isEmpty()) {
                writer.write("!!! Zdarzenia dodatkowe !!!\n");
                for (String line : info) {
                    writer.write(line + "\n");
                }
            }
        } catch (IOException e) {
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                }
            }
        }
    }

    //Sprawdzanie stanu pralki - technicznego, więc stan czujników
    private boolean stanPralki() {
        boolean sprawnosc = true;
        for (Czujnik cz : czujniki) {
            if (!cz.sprawdzZuzycie()) {
                sprawnosc = false;
                break;
            }
        }
        return sprawnosc;
    }

    //sprawdzenie warunków prania - czy są potrzebne rzeczy
    private boolean sprawdzWarunkiPrania() throws  IllegalStateException {
        if (zabezpieczenieDrzwi.pomiar() == 1) throw new IllegalStateException("[ERR] Drzwi pralki nie są zamknięte!");
        zabezpieczenieDrzwi.stan = 2;
        System.out.println("Blokowanie drzwiczek..");
        if (!beben.sprawdz()) throw new IllegalStateException("[ERR} Niezgodna waga bębna");
        if (!plynPlukanie.sprawdz() && program.dodatkowePlukanie) throw new IllegalStateException("[ERR] Niedozwolona ilość płynu do płukania");
        if (!proszekDoPrania.sprawdz()) throw new IllegalStateException("[ERR] Niedozwolona ilość proszku do prania");
        return true;
    }

    //proces prania
    private void pranie() {
        try {
            if (sprawdzWarunkiPrania()) {
                beben.wywazenie(silnik);
                ukladWodny.przygotujWode(poziomWody, przeplywomierz, temperaturaWody, beben.ileWody(), program.temperaturaWody);
                proszekDoPrania.uzyj();
                panel.ustawCzas(program.czasPraniaWstepnego + program.czasPraniaZasadniczego);
                System.out.println(panel.czas);
                Thread sprawdz = new Thread(this);
                Thread wyswietlaczCzasu = new Thread(panel);
                wyswietlaczCzasu.start();
                sprawdz.start();
                if (program.czasPraniaWstepnego > 0) pranieWstepne(program);
                pranieZasadnicze(program);
                wyswietlaczCzasu.join();
                sprawdz.interrupt();
                sprawdzanie = false;
                if (program.dodatkowePlukanie)
                    ukladWodny.plukanie(beben, silnik, poziomWody, przeplywomierz, temperaturaWody, plynPlukanie);
                for (int i = 0; i < 2; i++) {
                    beben.wirowanie(silnik, program.predkoscObrotowaWirowania);
                }
                ukladWodny.odprowadzWode(poziomWody, przeplywomierz);
            }
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            info.add(e.getMessage());
        } catch (InterruptedException e) {
        } finally {
            zabezpieczenieDrzwi.stan = 0;
            System.out.println("Odblokowano drzwiczki");
        }
        System.out.println("Pranie zostało zakończone");
    }

    //Wykonanie prania wstępnego
    private void pranieWstepne(Program program) {
        System.out.println("~~~~~~~~~~");
        System.out.println("Rozpoczęcie prania wstępnego");
        int czas = 0;
        double temperatura = program.temperaturaWody / 5 * 3;
        int predkosc = program.predkoscObrotowaWirowania / 2;
        while (czas < program.czasPraniaWstepnego/4) {
            beben.wirowanie(silnik, predkosc);
            czas++;
        }
        System.out.println("Zakończenie prania wstępnego");
    }

    //Wykonanie prania zasadniczego
    private void pranieZasadnicze(Program program) {
        System.out.println("~~~~~~~~~~");
        System.out.println("Rozpoczęcie prania zasadniczego");
        int czas = 0;
        while (czas < program.czasPraniaZasadniczego/5) {
            beben.wirowanie(silnik, program.predkoscObrotowaWirowania);
            czas++;
        }
        System.out.println("Zakończenie prania zasadniczego");
        System.out.println("~~~~~~~~~~");
    }

    public void run() {
        while (sprawdzanie) {
            sprawdzParametry(program.temperaturaWody, program.predkoscObrotowaWirowania);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {}
        }
    }

    //Sprawdzenie utrzymania odpowiednich parametrów prania
    private void sprawdzParametry(double temperatura, int predkosc) {
        System.out.println("Sprawdzanie parametrów");
        double woda = beben.ileWody();
        if (woda != poziomWody.pomiar()) poziomWody.ustawStan(woda);
        if (temperaturaWody.pomiar() != temperatura) temperaturaWody.ustawStan(temperatura);
        if (silnik.pomiar() != predkosc) silnik.ustawPredkosc(predkosc);
        try {
            Thread.sleep(100); //czas zajmuje też ustawianie ewentualne i wirowanie
        } catch (InterruptedException e) {
        }
        poziomWody.zuzycie -=1;
        temperaturaWody.zuzycie -=1;
        silnik.zuzycie -=1;
    }

}
