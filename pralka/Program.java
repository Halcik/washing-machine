package pralka;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {
    //statyczne pola klasy
    static List<Program> programy = new ArrayList<>();

    //pola dla obiektu
    String nazwa;
    String opis;
    double temperaturaWody;
    boolean dodatkowePlukanie;
    int czasPraniaWstepnego;
    int czasPraniaZasadniczego;
    int predkoscObrotowaWirowania;
    boolean usuwalnosc; // true - usuwalne, false - nieusuwalne

    //konstruktor
    public Program(String nazwa, String opis, double tempWody, boolean dodPlukanie, int pranieWst, int pranieZas, int predkosc, boolean usuwalnosc) {
        this.nazwa = nazwa;
        this.opis = opis;
        this.temperaturaWody = tempWody;
        this.dodatkowePlukanie = dodPlukanie;
        czasPraniaWstepnego = pranieWst;
        czasPraniaZasadniczego = pranieZas;
        predkoscObrotowaWirowania = predkosc;
        this.usuwalnosc = usuwalnosc;
        Program.programy.add(this);
    }

    //dodanie nowego programu
    public static Program nowy() {
        System.out.println("Kreator nowego programu prania");
        Scanner sc = new Scanner(System.in);
        System.out.print("Nazwa: ");
        String nazwa = sc.nextLine();
        System.out.print("Opis: ");
        String opis = sc.nextLine();
        System.out.print("Temperatura wody: ");
        double tempWody = sc.nextDouble();
        System.out.print("Dodatkowe płukanie [1-tak/0-nie]: ");
        int dodPlukanie = sc.nextInt();
        System.out.print("Czas prania wstępnego: ");
        int pranieWst = sc.nextInt();
        System.out.print("Czas prania zasadniczego: ");
        int pranieZas = sc.nextInt();
        System.out.print("Predkosc wirowania: ");
        int predkosc = sc.nextInt();
        sc.close();
        boolean usuwalnosc = true;
        System.out.println("Program "+nazwa+ "został dodany");
        return new Program(nazwa, opis, tempWody, dodPlukanie==1, pranieWst, pranieZas, predkosc, usuwalnosc);
    }

    //usunięcie programu własnego - NIEUŻYWANE
    public void usun() {
        if (this.usuwalnosc) Program.programy.remove(this);
        else System.out.println("Nie można usunąć programu wbudowanego");
    }

    //wyswietlenie listy programów
    public static void lista() {
        for (Program p : Program.programy) {
            System.out.println("["+Program.programy.indexOf(p) + "] " + p.nazwa);
        }
    }

    //edycja programu - NIEUŻYWANE
    public void edycja() {
        if (this.usuwalnosc) {
            System.out.println("Co chcesz zedytować?");
            System.out.println("[1] Nazwa\n[2] Opis\n[3] Temperatura wody\n[4] Dodatkowe płukanie\n[5] Czas prania wstępnego\n[6] Czas prania zasadniczego\n[7] Predkosc wirowania ");
            Scanner sc = new Scanner(System.in);
            int edytuj = sc.nextInt();
            System.out.print("Wprowadź nową wartość: ");
            String zmiana = sc.nextLine();
            sc.close();
            switch (edytuj) {
                case 1:
                    nazwa = zmiana;
                    break;
                case 2:
                    opis = zmiana;
                    break;
                case 3:
                    temperaturaWody = Double.parseDouble(zmiana);
                    break;
                case 4:
                    dodatkowePlukanie = (zmiana.equals("1"));
                    break;
                case 5:
                    czasPraniaWstepnego = Integer.parseInt(zmiana);
                    break;
                case 6:
                    czasPraniaZasadniczego = Integer.parseInt(zmiana);
                    break;
                case 7:
                    predkoscObrotowaWirowania = Integer.parseInt(zmiana);
                    break;
                default:
                    System.out.println("Nieznana opcja");
            }
        }
        else System.out.println("Nie można edytować programu wbudowanego");
    }
}
