package com.example.retea_gui.ui;

import com.example.retea_gui.domain.Friendship;
import com.example.retea_gui.domain.User;
import com.example.retea_gui.domain.validators.ValidationException;
import com.example.retea_gui.service.Service;

import java.util.List;
import java.util.Scanner;

public class Console {
    private static Service<Integer, User, Friendship> service;

    public Console(Service<Integer, User, Friendship> service){
        this.service = service;
    }

    public static void meniu(){
        System.out.println("\n\nComenzile sunt:\n" +
                "1. Afisare utilizatori\n" +
                "2. Adauga utilizatori\n" +
                "3. Actualizare utilizator\n" +
                "4. Sterge utilizator\n" +
                "5. Cauta utilizator dupa id\n" +
                "6. Afisare prieteni\n" +
                "7. Adauga prietenie\n" +
                "8. Sterge prietenie\n" +
                "9. Numarul de comunitati\n" +
                "10. Cea mai sociabila comunitate\n" +
                "11. Afisare toate prieteniile\n" +
                "12. Iesire");
    }

    public static void TotiUtilizatorii(){

        int var=0;
        Iterable<User> iterable = service.findAll();
        for(User s : iterable){
            System.out.println(s);
            var=1;
        }
        if(var==0)
            System.out.println("Nu exista utilizatori\n");
    }

    public static void uiAdauga(){
        String nume, prenume, username, password;
        int varsta;
        Scanner sc = new Scanner(System.in);
        System.out.println("Introdu numele:");
        nume = sc.nextLine();
        System.out.println("Introdu prenumele:");
        prenume = sc.nextLine();
        System.out.println("Introdu varsta:");
        varsta = sc.nextInt();
        System.out.println("Introdu usernameul:");
        username = sc.nextLine();
        System.out.println("Introdu parola:");
        password = sc.nextLine();

        int id = 0;
        boolean succes = false;
        try{
            User user = service.add(nume, prenume, varsta, username, password);
            succes = true;
            id = user.getId();
        }
        catch(IllegalArgumentException e){
            System.out.println(e);
        }
        catch(ValidationException e){
            System.out.println(e.getMessage());

        }
        if(succes == true)
            service.createFriendsList(id);
    }

    public static void uiActualizeaza(){
        String nume, prenume, username, password;
        int id,varsta;
        Scanner sc = new Scanner(System.in);

        System.out.println("Introdu numele nou:");
        nume = sc.nextLine();
        System.out.println("Introdu prenumele nou:");
        prenume = sc.nextLine();
        System.out.println("Introdu varsta noua:");
        varsta = sc.nextInt();
        System.out.println("Introdu usernameul nou:");
        username = sc.nextLine();
        System.out.println("Introdu parola noua:");
        password = sc.nextLine();
        System.out.println("Introdu id-ul userului pe care vrei sa il modifici:");
        id = sc.nextInt();

        try{
            User user = service.update(id, nume, prenume, varsta, username, password);
        }
        catch (IllegalArgumentException e){
            System.out.println(e);
        }
        catch (ValidationException e){
            System.out.println(e);
        }


    }

    public static void uiSterge(){
        Integer id;
        Scanner sc = new Scanner(System.in);
        System.out.println("Introdu id-ul userului de sters:");
        id = sc.nextInt();

        try{
            service.remove(id);
        }
        catch(IllegalArgumentException e){
            System.out.println(e);
        }
    }

    public static void uiCautaId(){
        Integer id;
        Scanner sc = new Scanner(System.in);
        System.out.println("Introdu id-ul userului cautat:");
        id = sc.nextInt();

        try{
            User user = service.findOne(id);
            System.out.println(user);
        }
        catch(IllegalArgumentException e){
            System.out.println(e);
        }
    }

    public static void uiAdaugaPrietenie(){
        Integer id1, id2;
        Scanner sc = new Scanner(System.in);
        System.out.println("Introdu id-ul primului prieten:");
        id1 = sc.nextInt();
        System.out.println("Introdu id-ul celui de-al doilea prieten:");
        id2 = sc.nextInt();

        try{
            service.addFriendship(id1,id2);
        }
        catch(IllegalArgumentException e){
            System.out.println(e);
        }
    }

    public static void uiPrietenii(){
        Integer id;
        Scanner sc = new Scanner(System.in);
        System.out.println("Introdu id-ul userului caruia doresti sa ii vezi prietenii:");
        id = sc.nextInt();
        List<User> l = service.showFriends(id);

        if(l.size() == 0){
            System.out.println("Utilizatorul nu are prieteni");
        }
        else{
            System.out.println("Prietenii sunt:");
            for(int i=0; i<l.size(); i++){
                System.out.println(l.get(i) + "\n");
            }
        }
    }


    public static void uiStergePrietenie(){
        Integer id1, id2;
        Scanner sc = new Scanner(System.in);
        System.out.println("Introdu id-ul primului prieten:");
        id1 = sc.nextInt();
        System.out.println("Introdu id-ul celui de-al doilea prieten:");
        id2 = sc.nextInt();

        try{
            service.removeFriendship(id1,id2);
        }
        catch(IllegalArgumentException e){
            System.out.println(e);
        }
    }

    public static void uiComunitati(){
        System.out.println("Exista " + service.comunitati() + " comunitati");
    }

    public static void uiComunitateSociabila(){
        System.out.println("Comunitatea este:");
        List<User> l = service.comunitateSociabila();
        for(int i=0; i<l.size(); i++){
            System.out.println(l.get(i));
        }
    }


    public static void uiTotiPrietenii(){
        int var=0;
        Iterable<Friendship> iterable = service.findAllFriends();
        for(Friendship s : iterable){
            System.out.println(s);
            var=1;
        }
        if(var==0)
            System.out.println("Nu exista prietenii\n");
    }


    public void run(){
        String cmd = "";
        Scanner sc = new Scanner(System.in);

        while(true){
            meniu();
            System.out.println("Introdu comanda:");
            cmd = sc.nextLine();
            if(cmd.equals("2")){
                uiAdauga();
            }
            else if(cmd.equals("1")){
                TotiUtilizatorii();
            }
            else if(cmd.equals("3")){
                uiActualizeaza();
            }
            else if(cmd.equals("4")){
                uiSterge();
            }
            else if(cmd.equals("5")){
                uiCautaId();
            }
            else if(cmd.equals("6")){
                uiPrietenii();
            }
            else if(cmd.equals("7")){
                uiAdaugaPrietenie();
            }
            else if(cmd.equals("8")){
                uiStergePrietenie();
            }
            else if(cmd.equals("9")){
                uiComunitati();
            }
            else if(cmd.equals("10")){
                uiComunitateSociabila();
            }
            else if(cmd.equals("11")){
                uiTotiPrietenii();
            }
            else {
                System.out.println("Comanda invalida!");
            }
        }
    }
}

