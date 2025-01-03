package pralka;

import java.util.Scanner;

//interfejs dotykowy do sterowania dla usera
public class Panel {
    boolean stan = false;

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

    //ekranik do wyświetlania czasu - DO NAPISANIA
    public void screenTime(int czas) {

    }

    //zatrzymanie prania - DO NAPISANIA
    public boolean buttonStop() {
        return false;
    }
}
