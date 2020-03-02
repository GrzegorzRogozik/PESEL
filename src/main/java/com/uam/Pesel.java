package com.uam;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pesel {

    public static void main(String [] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wanna play a game y/n");
        String gra = scanner.next();
        if (gra.equalsIgnoreCase("y")) {
            String continuePlay = "y";
            while (continuePlay.equalsIgnoreCase("y")) {
                Pesel m = new Pesel();
                String city = m.getCity();
                String username = m.getName();
                String surname = m.getSurname();
                String PESEL = m.getPESEL();
                boolean save = m.peselValidation(PESEL);
                System.out.println("Jeszcze raz y/n");
                continuePlay = scanner.next();
            }
            //TODO if pesel prawidlowy zapisac pesel w innym wypadku olac
            //TODO zapis do pliku, za kazdym razem sprawdzac czy jest juz taki pesel, jak tak to nadpisac, a jak nie to dodac - JSON
        }else if(gra.equalsIgnoreCase("n")){
            System.out.println("cya");
        }

    }
    public String getName(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj imię");
        String username = scanner.next();
        return username;

    }
    public String getSurname(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj nazwisko");
        String surname = scanner.next();
        return surname;
    }
    public String getCity(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj miasto");
        String city = scanner.next();
        return city;
    }
    public String getPESEL(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj PESEL");
        String PESEL = scanner.next();
        return PESEL;
    }
    public boolean peselValidation(String pesel){
        int length = pesel.length();
        if (length == 11){
            Pattern compiledPattern = Pattern.compile("\\d{2}((0[1-9])|(1[0-2]])|(2[1-9])|(3[0-2]))\\d{7}");
            Matcher matcher = compiledPattern.matcher(pesel);
            if (matcher.matches() == true) {
                int intPesel = Integer.parseInt(pesel);


                //TODO suma kontrolna
                System.out.println("PESEL OK");
                return true;
            }else{
                System.out.println("Nieprawidłowy PESEL");
                return false;
            }

        }else{
            System.out.println("Nieprawidłowy PESEL");
            return false;
        }

    }

}
