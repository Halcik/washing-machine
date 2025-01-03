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


    //Klasy wewnętrzne dotyczące układu wodnego
    private class Pompa {
        public void pompowanie(Czujnik poziomWody, Czujnik przeplywomierz, int ileWody) {
            System.out.println("Pompowanie wody...");
            if (poziomWody.pomiar()!=ileWody) {
                przeplywomierz.ustawStan(1);
                poziomWody.ustawStan(ileWody);
                przeplywomierz.ustawStan(0);
            }
        }
    }

    private class Grzalka {
        public void podgrzej(double temperatura, Czujnik temperaturaWody) {
            System.out.println("Podgrzewanie wody...");
            temperaturaWody.ustawStan(temperatura);
        }
    }

    private class Filtr {
        public void filtruj() {
            System.out.println("Filtrowanie wody...");
        }
    }

    private class Elektrozawor {
        private boolean otwarty = false;

        public void otworz() {
            otwarty = true;
        }

        public void zamknij() {
            otwarty = false;
        }
    }
}
