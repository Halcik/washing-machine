package pralka;

import java.util.Scanner;

//interfejs dotykowy do sterowania dla usera
public class Panel implements Runnable{
    boolean stan = false;
    int czas = 0;


    //włączenie pralki przez interfejs
    public void buttonWlacznik() {
        stan = !stan;
        if (stan) System.out.println("Trwa włączanie pralki...");
        else System.out.println("Trwa wyłączanie pralki...");
    }

    //wybór programu istniejącego LUB tworzenie nowego
    public Program buttonProgram() {
        System.out.println("Wybierz program: ");
        Program.lista();
        Scanner sc = new Scanner(System.in);
        int id = sc.nextInt();
        sc.close();
        if (id>Program.programy.size()-1) Program.nowy();
        return Program.programy.get(id);
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
