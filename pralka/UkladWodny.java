package pralka;

public class UkladWodny {
    Pompa pompa = new Pompa();
    Grzalka grzalka = new Grzalka();
    Filtr filtr = new Filtr();
    Elektrozawor zaworWe = new Elektrozawor();
    Elektrozawor zaworWy = new Elektrozawor();

    public void przygotujWode(Czujnik poziomWody, Czujnik przeplywomierz, Czujnik temperaturaWody, int ileWody, double temperatura) {
        System.out.println("Rozpoczęcie przygotowywania wody");
        zaworWe.otworz();
        filtr.filtruj();
        pompa.pompowanie(poziomWody, przeplywomierz, ileWody);
        grzalka.podgrzej(temperatura, temperaturaWody);
        zaworWe.zamknij();
        System.out.println("Woda przygotowana");
    }

    public void odprowadzWode(Czujnik poziomWody, Czujnik przeplywomierz) {
        System.out.println("Rozpoczęcie odprowadzania wody...");
        zaworWy.otworz();
        pompa.pompowanie(poziomWody, przeplywomierz, 0);
        zaworWy.zamknij();
        System.out.println("Woda odprowadzona.");
    }

    public boolean sprawdzZuzycie() {
        boolean sprawnosc = true;
        if (pompa.sprawdzZuzycie()!=true) sprawnosc = false;
        if (grzalka.sprawdzZuzycie()!=true) sprawnosc = false;
        if (filtr.sprawdzZuzycie()!=true) sprawnosc = false;
        if (zaworWe.sprawdzZuzycie()!=true) sprawnosc = false;
        if (zaworWy.sprawdzZuzycie()!=true) sprawnosc = false;
        return sprawnosc;
    }


    //Klasy wewnętrzne dotyczące układu wodnego
    private class Pompa extends Czujnik {
        public void pompowanie(Czujnik poziomWody, Czujnik przeplywomierz, int ileWody) {
            System.out.println("Pompowanie wody...");
            if (poziomWody.pomiar()!=ileWody) {
                przeplywomierz.ustawStan(1);
                poziomWody.ustawStan(ileWody);
                przeplywomierz.ustawStan(0);
            }
            zuzycie++;
        }
    }

    private class Grzalka extends Czujnik {
        public void podgrzej(double temperatura, Czujnik temperaturaWody) {
            System.out.println("Podgrzewanie wody...");
            temperaturaWody.ustawStan(temperatura);
            zuzycie++;
        }
    }

    private class Filtr extends Czujnik {
        public void filtruj() {
            System.out.println("Filtrowanie wody...");
            zuzycie++;
        }
    }

    private class Elektrozawor extends Czujnik {
        private boolean otwarty = false;

        public void otworz() {
            if (otwarty==false) {
                otwarty = true;
                zuzycie++;
            }
        }

        public void zamknij() {
            if (otwarty) {
                otwarty = false;
            }
        }
    }
}
