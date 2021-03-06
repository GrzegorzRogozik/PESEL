package com.uam;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Pesel {

    public static void main(String [] args){
        JSONArray emptyArray = new JSONArray();
        try (FileWriter file = new FileWriter("PESEL.json")) {

            file.write(emptyArray.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wanna play a game y/n");
        String gra = scanner.next();
        if (gra.equalsIgnoreCase("y")) {
            String continuePlay = "y";
            while (continuePlay.equalsIgnoreCase("y")) {
                Pesel m = new Pesel();
                String city = m.getCity();
                String username = m.getName(); //TODO walidacja wejscia - brak cyfr i znaków specjalnych
                String surname = m.getSurname();
                String PESEL = m.getPESEL();
                boolean save = m.peselValidation(PESEL);
                if (save == true){
                    m.writePeselToFile(PESEL, username, city, surname);
                }
                System.out.println("Jeszcze raz y/n");
                continuePlay = scanner.next();
            }
        }else if(gra.equalsIgnoreCase("n")){
            System.out.println("cya");
        }

    }
    public String getName(){
        boolean validator = false;
        String username = null;
        while (validator == false) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Podaj imię");
            username = scanner.next();
            validator = nameValidator(username);
        }
        return username;

    }
    public String getSurname(){
        boolean validator = false;
        String surname = null;
        while (validator == false) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Podaj nazwisko");
            surname = scanner.next();
            validator = surnameValidator(surname);
        }
        return surname;
    }
    public String getCity(){
        boolean validator = false;
        String city = null;
        while (validator == false) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Podaj miasto");
            city = scanner.next();
            validator = cityValidator(city);
        }
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
        boolean checkedPeselinFile = true;
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("PESEL.json")) {
            Object obj = jsonParser.parse(reader);
            JSONArray peselListOld = (JSONArray) obj;

            for (int i =0; i< peselListOld.size();i++){
                JSONObject singlePesel = (JSONObject) peselListOld.get(i);
                String checkedPesel = (String) singlePesel.get("PESEL");
                if (checkedPesel.matches(PESEL)){
                    peselListOld.remove(i);
                    JSONObject newPeselValues = new JSONObject();
                    newPeselValues.put("PESEL", PESEL);
                    newPeselValues.put("name", username);
                    newPeselValues.put("surname", surname);
                    newPeselValues.put("city", city);
                    checkedPeselinFile = false;
                    peselListOld.add(newPeselValues);

                    try (FileWriter file = new FileWriter("PESEL.json")) {

                        file.write(peselListOld.toJSONString());
                        file.flush();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            checkedPeselinFile = true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return checkedPeselinFile;
    }
    public void addNewPeselToFile(String PESEL, String username, String city, String surname){
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("PESEL.json")) {
            Object obj = jsonParser.parse(reader);
            JSONArray peselListOld = (JSONArray) obj;

            JSONObject peselDetails = new JSONObject();
            peselDetails.put("PESEL", PESEL);
            peselDetails.put("name", username);
            peselDetails.put("surname", surname);
            peselDetails.put("city", city);

            peselListOld.add(peselDetails);

            try (FileWriter file = new FileWriter("PESEL.json")) {

                file.write(peselListOld.toJSONString());
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }


    }
    public boolean nameValidator(String name){
        Pattern compiledPattern = Pattern.compile("[a-zA-Z]+");
        Matcher matcher = compiledPattern.matcher(name);
        boolean validator;
        if (matcher.matches() == true){
            validator = true;
        }else{
            System.out.println("Imię nie może zawierć cyfr, ani znaków specjalnych");
            validator = false;
        }
        return validator;
    }
    public boolean surnameValidator(String surname){
        Pattern compiledPattern = Pattern.compile("[a-zA-Z]+");
        Matcher matcher = compiledPattern.matcher(surname);
        boolean validator;
        if (matcher.matches() == true){
            validator = true;
        }else{
            System.out.println("Nazwisko nie może zawierć cyfr, ani znaków specjalnych");
            validator = false;
        }
        return validator;
    }
    public boolean cityValidator(String city){
        Pattern compiledPattern = Pattern.compile("[a-zA-Z]+");
        Matcher matcher = compiledPattern.matcher(city);
        boolean validator;
        if (matcher.matches() == true){
            validator = true;
        }else{
            System.out.println("Miasto nie może zawierć cyfr, ani znaków specjalnych");
            validator = false;
        }
        return validator;
    }
}
