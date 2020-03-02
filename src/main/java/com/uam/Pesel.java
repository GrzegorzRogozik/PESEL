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
                //TODO validacja imienia i nazwiska - bez znaków specjalnych i cyfer
                String username = m.getName();
                String surname = m.getSurname();
                String PESEL = m.getPESEL();
                boolean save = m.peselValidation(PESEL);
                System.out.println(save + " " + PESEL);
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
                char pesel0 = pesel.charAt(0);
                int controlNumber = Integer.parseInt(String.valueOf(pesel0))*1;
                char pesel1 = pesel.charAt(1);
                controlNumber = controlNumber + ((Integer.parseInt(String.valueOf(pesel1))*3)%10);
                char pesel2 = pesel.charAt(2);
                controlNumber = controlNumber + ((Integer.parseInt(String.valueOf(pesel2))*7)%10);
                char pesel3 = pesel.charAt(3);
                controlNumber = controlNumber + ((Integer.parseInt(String.valueOf(pesel3))*9)%10);
                char pesel4 = pesel.charAt(4);
                controlNumber = controlNumber + ((Integer.parseInt(String.valueOf(pesel4))*1)%10);
                char pesel5 = pesel.charAt(5);
                controlNumber = controlNumber + ((Integer.parseInt(String.valueOf(pesel5))*3)%10);
                char pesel6 = pesel.charAt(6);
                controlNumber = controlNumber + ((Integer.parseInt(String.valueOf(pesel6))*7)%10);
                char pesel7 = pesel.charAt(7);
                controlNumber = controlNumber + ((Integer.parseInt(String.valueOf(pesel7))*9)%10);
                char pesel8 = pesel.charAt(8);
                controlNumber = controlNumber + ((Integer.parseInt(String.valueOf(pesel8))*1)%10);
                char pesel9 = pesel.charAt(9);
                controlNumber = controlNumber + ((Integer.parseInt(String.valueOf(pesel9))*3)%10);
                controlNumber = controlNumber%10;
                char pesel10 = pesel.charAt(10);
                int peselLastChar = Integer.parseInt(String.valueOf(pesel10));
                if (10-controlNumber == peselLastChar){
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

        }else{
            System.out.println("Nieprawidłowy PESEL");
            return false;
        }

    }

}
