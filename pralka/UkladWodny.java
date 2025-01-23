package pralka;

public class UkladWodny {
    Pompa pompa = new Pompa();
    Grzalka grzalka = new Grzalka();
    Filtr filtr = new Filtr();
    Elektrozawor zaworWe = new Elektrozawor("wejścia");
    Elektrozawor zaworWy = new Elektrozawor("wyjścia");

    public void przygotujWode(Czujnik poziomWody, Czujnik przeplywomierz, Czujnik temperaturaWody, double ileWody, double temperatura) {
        temperaturaWody.stan = 15;
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

    //proces płukania prania
    public void plukanie(Beben beben, Silnik silnik, Czujnik poziomWody, Czujnik przeplywomierz, Czujnik temperaturaWody, Pojemnik plynPlukanie) {
        odprowadzWode(poziomWody, przeplywomierz);
        przygotujWode(poziomWody, przeplywomierz, temperaturaWody, beben.ileWody(), 15);
        plynPlukanie.uzyj();
        for (int i=0; i<4; i++) {
            System.out.println("Trwa płukanie...");
            beben.wirowanie(silnik, 500);
            filtr.filtruj();
            System.out.println("Płukanie zakończone.");
        }
    }


    //Klasy wewnętrzne dotyczące układu wodnego
    protected class Pompa extends Czujnik {
        public Pompa() {
            super("Pompa");
        }
        public void pompowanie(Czujnik poziomWody, Czujnik przeplywomierz, double ileWody) {
            System.out.println("Pompowanie wody...");
            if (poziomWody.pomiar()!=ileWody) {
                przeplywomierz.ustawStan(1);
                poziomWody.ustawStan(ileWody);
                przeplywomierz.ustawStan(0);
            }
            zuzycie++;
            System.out.println("Woda gotowa");
        }
    }

    protected class Grzalka extends Czujnik {
        public Grzalka() {
            super("Grzałka");
        }
        public void podgrzej(double temperatura, Czujnik temperaturaWody) {
            System.out.println("Podgrzewanie wody...");
            temperaturaWody.ustawStan(temperatura);
            zuzycie++;
            System.out.println("Woda podgrzana.");
        }
    }

    protected class Filtr extends Czujnik {
        public Filtr() {
            super("Filtr");
        }
        public void filtruj() {
            System.out.println("Filtrowanie wody...");
            zuzycie++;
            System.out.println("Zakończono filtrowanie.");
        }
    }

    protected class Elektrozawor extends Czujnik {
        private boolean otwarty = false;
        public Elektrozawor(String name) {
            super("Elektrozawor "+name);
        }

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
