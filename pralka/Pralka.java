package pralka;

public class Pralka {
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

    //wyłączenie pralki - DO UZUPEŁNIENIA lub usunięcia?
    public void wylacz() {
        if (panel.stan) panel.buttonWlacznik();
        else System.out.println("Pralka jest wyłączona");

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

    //proces prania - DO NAPISANIA - jakaś f-cja do określania, ile wody jest potrzebne + wyważanie bębna + ciuchy skąd
    private void pranie(Program program) {
        if (zabezpieczenieDrzwi.pomiar()==1) {
            zabezpieczenieDrzwi.ustawStan(2);
            ukladWodny.przygotujWode(poziomWody, przeplywomierz, temperaturaWody, beben.ileWody(), program.temperaturaWody);
            proszekDoPrania.uzyj();
            if (program.czasPraniaWstepnego>0) pranieWstepne();
            pranieZasadnicze();
            if (program.dodatkowePlukanie) ukladWodny.plukanie(poziomWody, przeplywomierz, temperaturaWody, beben.ileWody(), program.temperaturaWody);
            // wirowanie - odwirowuje wodę z ubrań
            // opróżnienie wody
            // odblokowanie drzwiczek
            // powinno sprawdzać w trakcie:
            // - poziom wody
            // - temperaturę wody
            // - prędkość obrotową bębna
        } else System.out.println("Drzwiczki są otwarte");
    }
    //Wykonanie prania wstępnego - DO NAPISANIA
    private void pranieWstepne() {}
    //Wykonanie prania zasadniczego - DO NAPISANIA
    private void pranieZasadnicze() {}

    //DO NAPISANIA - widok tego, co jest w pralce i wybór akcji
    public void widokPanelu(Panel panel) {}

}
