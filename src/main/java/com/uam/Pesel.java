package com.uam;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
                if (save == true){
                    m.writePeselToFile(PESEL, username, city, surname);
                }
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
    public void writePeselToFile(String PESEL, String username, String city, String surname){
        boolean newPesel = this.checkIfPeselExistInFileAndOverwritte(PESEL, username, city, surname );
        if (newPesel == true){
            this.addNewPeselToFile(PESEL, username, city, surname);
        }
    }
    public boolean checkIfPeselExistInFileAndOverwritte(String PESEL, String username, String city, String surname){
        return true;
    }
    public void addNewPeselToFile(String PESEL, String username, String city, String surname){
        JSONObject peselDetails = new JSONObject();
        peselDetails.put("name", username);
        peselDetails.put("surname", surname);
        peselDetails.put("city", city);

        JSONObject peselObject = new JSONObject();
        peselObject.put(PESEL, peselDetails);

        JSONArray employeeList = new JSONArray();
        employeeList.add(peselObject);

        try (FileWriter file = new FileWriter("PESEL.json")) {

            file.write(employeeList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
