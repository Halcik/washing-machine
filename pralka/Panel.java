package pralka;

import java.util.Scanner;

//interfejs dotykowy do sterowania dla usera
public class Panel implements Runnable{
    boolean stan = false; //włącznik pralki
    int czas = 0;

    //Widok tego, co jest w pralce i wybór akcji
    public int widokPanelu(Scanner sc) {
        System.out.println("\nStoisz przed pralką z różnymi guzikami do wyboru");
        System.out.println("[1] Wybór programu");
        System.out.println("[2] Włącznik pralki");
        System.out.println("[3] Szufladka na detergenty");
        System.out.println("[4] Drzwiczki");
        System.out.println("[5] Dodanie ciuchów");
        System.out.println("Co chcesz zrobić?");
        int choice = sc.nextInt();
        System.out.println();
        return choice;
    }


    //włączenie pralki przez interfejs
    public void buttonWlacznik() {
        stan = !stan;
        if (stan) System.out.println("Trwa włączanie pralki...");
        else System.out.println("Trwa wyłączanie pralki...");
    }

    //wybór programu istniejącego LUB tworzenie nowego
    public Program buttonProgram(Scanner sc) {
        System.out.println("Wybierz program: ");
        Program.lista();
        int id = sc.nextInt();
        if (id>Program.programy.size()-1) Program.nowy(sc);
        return Program.programy.get(id);
    }

    public void obslugaDrzwiczek(Czujnik zabezpieczenieDrzwi) {
        switch ((int)zabezpieczenieDrzwi.stan) {
            case 1:
                System.out.println("Drzwi są zamykane..");
                zabezpieczenieDrzwi.stan = 0;
                break;
            case 0:
                System.out.println("Drzwi są otwierane");
                zabezpieczenieDrzwi.stan = 1;
                break;
            case 2:
                System.out.println("Drzwi są zablokowane. Nie można ich otworzyć");
                break;
        }
    }

    public void run() {
        screenTime();
    }

    //ekranik do wyświetlania czasu
    private void screenTime() {
        while (czas>0) {
            System.out.println("Pozostały czas: "+czas);
            czas--;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
    }

    //ustawienie czasu do pracy wątku z wyświetlaczem
    public void ustawCzas(int czas) {
        this.czas = czas;
    }
}
