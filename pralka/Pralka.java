package pralka;

public class Pralka {
    //Pojemniki
    Pojemnik plynPlukanie = new Pojemnik(10, 50);
    Pojemnik proszekDoPrania = new Pojemnik(5, 10);
    Pojemnik beben = new Pojemnik(2, 5);

    Panel panel = new Panel();

    //Czujniki
    Czujnik cisnienieWody = new Czujnik();
    Czujnik temperaturaWody = new Czujnik();
    Czujnik poziomWody = new Czujnik();
    Czujnik zabezpieczenieDrzwi = new Czujnik();
    Czujnik przeplywomierz = new Czujnik();

    /* KOMPONENTY
    * grzałka do wody
    * filtr wody
    * */

    /* ELEMENTY WYKONAWCZE
    * pompa do wody
    * elektrozawory wodne
    * silnik inweterowy - do sterowania prędkością
    * sterownik regulujący prędkość obrotową
    * */

    //Programy wbudowane
    Program wbudowany1 = new Program("Bawełna", "standardowy program z wyższą temperaturą i prędkością wirowania.", 60.0, false, 15, 60, 1200, false);
    Program wbudowany2 = new Program("Syntetyki", "delikatniejsze pranie z dodatkowym płukaniem.", 40.0, true, 10, 45, 1000, false);
    Program wbudowany3 = new Program("Delikatne", "niska temperatura, krótki czas, mniejsze obroty.", 30.0, false, 0, 30, 800, false);
    Program wbudowany4 = new Program("Wełna", "krótki czas prania i niskie obroty.", 30.0, true, 5, 40, 600, false);
    Program wbudowany5 = new Program("Pranie szybkie", "rótkie, szybkie odświeżenie ubrań w niższej temperaturze.", 20.0, false, 0, 15, 1000, false);

    //włączenie pralki - zmodyfikować - przed włączeniem - wybranie programu
    public void wlacz() {
        if (!panel.stan) {
            panel.buttonWlacznik();
            if (!stanPralki()) {
                System.out.println("Pralka uszkodzona, trwa wyłączanie");
                wylacz();
                return;
            }
        }
        else System.out.println("Pralka jest włączona");
    }

    //wyłączenie pralki - DO UZUPEŁNIENIA?
    public void wylacz() {
        if (panel.stan) panel.buttonWlacznik();
        else System.out.println("Pralka jest wyłączona");

    }

    //Sprawdzanie stanu pralki - DO NAPISANIA
    private boolean stanPralki() {
        //sprawdzenie stanu pralki
        //czy woda dostępna
        //czy komponenty są sprawne - filtr, pompa
        return true; //do zmiany w zależności od stanu
    }

    //proces prania - DO NAPISANIA
    private void pranie(Program program) {
        // czy drzwiczki są zamknięte
        // zablokowanie drzwiczek
        // napełnienie bębna wodą
        // dodanie detergentu
        // uruchomienie prania wstępnego, jeśli jest
        // uruchomienie prania zasadniczego
        // płukanie, jeśli jest
        // wirowanie - odwirowuje wodę z ubrań
        // opróżnienie wody
        // odblokowanie drzwiczek
        // powinno sprawdzać w trakcie:
        // - poziom wody
        // - temperaturę wody
        // - prędkość obrotową bębna
    }

    //DO NAPISANIA - widok tego, co jest w pralce i wybór akcji
    public void widokPanelu(Panel panel) {}

    // DO NAPISANIA - niekoniecznie tu
    private void pompowanie_wody() {}
    private void podgrzewanie_wody() {}
    private void wywazanieBebna(){
        //równomierne rozłożenie ubrań w bębnie
        // czujnik do tego jest
        // jak jest nie tak, to obraca bęben powoli, by rozłożyć
    }
    private void droznoscFiltra() {}
}
