package kamenpapirmakaze;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Igra {
    Scanner sc;
    Random rnd;
    
    /**
     * konstuktor klase
     * kreiraju se objekti klase Scanner i Random koji se koriste kasnije u kodu
     */
    public Igra() {
        sc = new Scanner(System.in);
        rnd = new Random();
        /*
        Random klasa se, za generisanje celobrojnog podatka, koristi:
        int broj = rnd..nextInt(granica)
        nakon cega ce boj imati celobrojnu vrednost od 0 (ukljucujuci 0) do 
        granica - 1
        objekat rnd koristicete prilikom igranja runde kako bi se za svakog 
        igraca dobio kamen, papir ili makaze
        */
    }
    
    /**
     * Proverava da li postoje prethodno sacuvane igre izmedju dva igraca
     * @param ime1 prvi igrac
     * @param ime2 drugi igrac
     * @return true ako postoje rezultati, false u suprotnom
     */
    boolean postojiSacuvanaIgra(String ime1, String ime2){
        /*
        Proveri da li postoje sacuvani podaci za igrace ime 1 i ime2 
        ako ima vrati true u suprotnom vrati false (npr. nazvati fajl sa 
        sacuvanim podacima ime1-ime2.rez ili eventualno citanjem datoteke u 
        kojoj je upisan skor
        */
        
        File f = new File("../KamenPapirMakaze");
        List<File> list = Arrays.asList(f.listFiles(new FilenameFilter(){
            @Override
            public boolean accept(File f, String name) {
                return name.endsWith(".txt"); // or something else
        }}));
        for(File p : list){
            String ime11 = p.getName().split("\\.")[0].split("-")[0];
            String ime22 = p.getName().split("\\.")[0].split("-")[1];
            
            if((ime1.equals(ime11) && ime2.equals(ime22)) || (ime1.equals(ime22)&& ime2.equals(ime11))){
                if(p.length() == 0)
                    return false;
                else
                    return true;
            }
        }
        
        return true; 
    }
    
    /**
     * Trazi broj osvojenih rundi za igraca u prethodno odigranim rundama
     * @param filename ime fajla u kojem su upisani podaci o prethodno odigranim rundama
     * @param ime igraca za kojeg se traze rezultati
     * @return broj osvojenih rundi u prethodno osvojenim partijama
     */
    int pronadjiSkorZaIgraca(String filename, String ime){
        /*
        Procitaj fajl filename i pronadji koliki je "skor" igraca sa imenom ime
        Skor je broj dosada osvojenih rundi za tog igraca
        */
        
        String data = "";
        try {
            File myObj = new File(filename);
            Scanner sc = new Scanner(myObj);
            while (sc.hasNextLine()) {
                data = sc.nextLine();
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
        if(filename.split("\\.")[0].split("-")[0].equals(ime))
            return Integer.parseInt(data.substring(data.lastIndexOf(" ") + 1).split(":")[0]);
        
        return Integer.parseInt(data.substring(data.lastIndexOf(" ") + 1).split(":")[1]);
    }
    
    /**
     * Odigravanje jedne runde
     * @param igrac1 ime prvog igraca
     * @param igrac2 ime drugog igraca
     * @return Ime igraca koji je osvojio rundu, ako je rezultat runde nereseno,
     * odigrava se ponovo sve dok jedan od igraca ne osvoji rundu
     */
    String odigrajRundu(String igrac1, String igrac2){
        /*Koristi rnd objekat kako bi generisao broj koji odgovara kamen, papir
        ili makaze, za svakog igraca. Ponavljaj dok je rezultat neresen, 
        u suprotnom pobednik runde je:
        1) kamen - papir  -> pobednik igrac koji je dobio papir
        2) kamen - makaze  -> pobednik igrac koji je dobio kamen
        3) makaze - papir  -> pobednik igrac koji je dobio makaze
        */
        
        int op1 = rnd.nextInt(3);
        int op2 = rnd.nextInt(3);

        String rez = "";
        
        //0 - kamen, 1 - papir, 2 - makaze   
        while(op1 == op2){
            //System.out.println("Rezultat je neresen, pa ce se ponoviti runda.");
            op1 = rnd.nextInt(3);
            op2 = rnd.nextInt(3);
        }
        
        if(op1 == 0){
            if(op2 == 1)
                rez = igrac1 + ": kamen, " + igrac2 + ": papir, pobednik je " + igrac2;
            else if(op2 == 2)
                rez = igrac1 + ": kamen, "  + igrac2 + ": makaze, pobednik je " + igrac1;
        }
        else if(op1 == 1){
            if(op2 == 0)
                rez = igrac1 + ": papir, " + igrac2 + ": kamen, pobednik je " + igrac1;
            else if(op2 == 2)
                rez = igrac1 + ": papir, " + igrac2 + ": makaze, pobednik je " + igrac2;
        }
        else if(op1 == 2){
            if(op2 == 0)
                rez = igrac1 + ": makaze, " + igrac2 + ": kamen, pobednik je " + igrac2;
            else if(op2 == 1)
                rez = igrac1 + ": makaze, " + igrac2 + ": papir, pobednik je " + igrac1;
        }
        
        return rez;
    }
    
    void upisiRezultatUFajl(String filename, boolean append, String sadrzaj){
        try {
            FileWriter myWriter = new FileWriter(filename, append);
            myWriter.write(sadrzaj);
            myWriter.write("\n");
            myWriter.close();
            //System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    String citajRezultatIzFajla(String fajl){
        String data = "";
        try {
            File myObj = new File("igor.txt");
            Scanner mr = new Scanner(myObj);
            while (mr.hasNextLine()) {
                data = mr.nextLine();
            }
            mr.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
        return data.substring(data.lastIndexOf(" ") + 1);
    }
    
    String postojiFajl(String file, String ime1, String ime2){
        File f = new File("../KamenPapirMakaze");
        List<File> list = Arrays.asList(f.listFiles(new FilenameFilter(){
            @Override
            public boolean accept(File f, String name) {
                return name.endsWith(".txt"); // or something else
        }}));
        
        if(list.isEmpty()){
            try {
                    File f1 = new File(file);
                    if (f1.createNewFile()) {
                        //System.out.println("File created: " + f1.getName());
                    } else {
                        //System.out.println("File already exists.");
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
        }
        
        String res = "";
        for(File p : list){
            String ime11 = p.getName().split("\\.")[0].split("-")[0];
            String ime22 = p.getName().split("\\.")[0].split("-")[1];
            
            if(ime1.equals(ime11) && ime2.equals(ime22)){
                res = ime1 + "-" + ime2;
                break;
            }
            else if(ime1.equals(ime22) && ime2.equals(ime11)){
                res = ime2 + "-" + ime1;
                break;
            }
            else{
                
            }
        }
        
        return res;
    }
    
    int ucitajBrojRunde(String filename){
        String data = "";
        try {
            File myObj = new File(filename);
            Scanner sc = new Scanner(myObj);
            while (sc.hasNextLine()) {
                data = sc.nextLine();
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
        return Integer.parseInt(data.substring(data.indexOf(" ") + 1, data.indexOf(" ") + 2));
    }
    
    void obrisiRezultate(String filename){     
        try{
            PrintWriter pw = new PrintWriter(filename);
            pw.close();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * Metoda koja sadrzi tok igranja igre
     */
    boolean igraj(){
        /*
        1. Trazi od korisnika da unesu imena igraca koriscenjem objekta sc. Ponavljaj dok se ne unesu dva
        razlicita imena.
        2. Proveri da li postoje rezultati za prethodno unete igrace, ako da, pitaj da li da se nastavi, 
        ili da se igra nova igra. Koristite metodu postojiSacuvanaIgra
        3. Ako se nastavlja prethodna igra, pronadji skor svakog od igraca(metoda pronadjiSkorZaIgraca)
        4. Igraj rundu za rundom (odigrajRundu) dok se ne dodje do 5 osvojenih rundi, kada je kraj igre. 
        Kada se dodje do kraja igre, ponudi novu igru ili izlaz iz programa. Ako se izlazi iz programa, 
        obrisi prethodne rezultate, ako postoje
        5. Na kraju svake runde pitaj da li se nastavlja igra, ili se prekida. U slucaju prekida, sacuvaj trenutni skor u fajlu
        kako bi se kasnije mogla nastaviti prekinuta igra
        */ 
        
        //1
        String ime1 = "";
        String ime2 = "";
        while(ime1.equals(ime2)){
            System.out.print("Unesite ime prvog igraca: ");
            ime1 = sc.next();
            System.out.print("Unesite ime drugog igraca: ");
            ime2 = sc.next();
            if(ime1.equals(ime2))
                System.out.println("Imena trebaju biti razlicita!");
            System.out.println("--------------------------");
            
        }
        
        String file = ime1 + "-" + ime2 + ".txt";
        String s = postojiFajl(file, ime1, ime2);
        if(!s.isEmpty()){
            ime1 = s.split("-")[0];
            ime2 = s.split("-")[1];
            file = ime1 + "-" + ime2 + ".txt";
            
            try {
                File fl = new File(file);
                if (fl.createNewFile()) {
                    //System.out.println("File created: " + fl.getName());
                } else {
                    //System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        else{
            try {
                File fl = new File(file);
                if (fl.createNewFile()) {
                    //System.out.println("File created: " + fl.getName());
                } else {
                    //System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        
        int opcija;
        String rez = "";
        int score1 = 0;
        int score2 = 0;
        int runda = 1;
        boolean ucitaniRezultati = false;
        //2
        if(postojiSacuvanaIgra(ime1, ime2) == true){
            System.out.println("1.Da li zelite da nastavite gde ste stali?");
            System.out.println("2.Da li zelite da pocnete novu igru?");
            opcija = Integer.parseInt(sc.next());
            if(opcija == 1){
                while(true){
                    //3
                    if(ucitaniRezultati == false){
                        score1 = pronadjiSkorZaIgraca(file, ime1);
                        score2 = pronadjiSkorZaIgraca(file, ime2);
                        runda = ucitajBrojRunde(file);
                        ucitaniRezultati = true;
                    }

                    rez = "Runda " + ++runda + " ";
                    //4
                    rez += odigrajRundu(ime1, ime2);
                    if(rez.substring(rez.lastIndexOf(" ") + 1).equals(ime1))
                        score1 += 1;
                    else
                        score2 += 1;
                    
                    rez += " " + score1 + ":" + score2;
                    
                    System.out.println(rez);
                    
                    //6
                    if(score1 == 5 || score2 == 5){
                        System.out.println("Gotova igra!");
                        System.out.println("------------------------------------");
                        System.out.println("1.Da li zelite da pocnete novu igru?");
                        System.out.println("2.Da li zelite da izadjete iz programa?");
                        opcija = Integer.parseInt(sc.next());
                        if(opcija == 1){
                            obrisiRezultate(file);
                            return true;
                        }
                        else{
                            obrisiRezultate(file);
                            return false;
                        }
                    }
                    
                    //5
                    System.out.println("---------------------------------");
                    System.out.println("1.Da li zelite da nastavite igru?");
                    System.out.println("2.Da li zelite da prekinete igru?");
                    opcija = Integer.parseInt(sc.next());
                    if(opcija == 1){
                        //upisiRezultatUFajl(file, false, rez);
                        //continue;
                    }
                    else{
                        upisiRezultatUFajl(file, false, rez);
                        return false;
                    }
                    
                }
            }
            else{
                while(true){
                    rez = "Runda " + runda++ + " ";
                    //4
                    rez += odigrajRundu(ime1, ime2);
                    if(rez.substring(rez.lastIndexOf(" ") + 1).equals(ime1))
                        score1 += 1;
                    else
                        score2 += 1;
                    
                    rez += " " + score1 + ":" + score2;
                    
                    System.out.println(rez);
                    
                    //6
                    if(score1 == 5 || score2 == 5){
                        System.out.println("Gotova igra!");
                        System.out.println("------------------------------------");
                        System.out.println("1.Da li zelite da pocnete novu igru?");
                        System.out.println("2.Da li zelite da izadjete iz programa?");
                        opcija = Integer.parseInt(sc.next());
                        if(opcija == 1){
                            obrisiRezultate(file);
                            return true;
                        }
                        else{
                            obrisiRezultate(file);
                            return false;
                        }
                    }
                    
                    //5
                    System.out.println("---------------------------------");
                    System.out.println("1.Da li zelite da nastavite igru?");
                    System.out.println("2.Da li zelite da prekinete igru");
                    opcija = Integer.parseInt(sc.next());
                    if(opcija == 1){
                        //continue;
                    }
                    else{
                        upisiRezultatUFajl(file, false, rez);
                        return false;
                    }
                }
            }
        }
        else{
            System.out.println("1.Da li zelite da pocnete novu igru?");
            opcija = Integer.parseInt(sc.next());
            if(opcija == 1){
                while(true){
                    
                    rez = "Runda " + runda++ + " ";
                    //4
                    rez += odigrajRundu(ime1, ime2);
                    if(rez.substring(rez.lastIndexOf(" ") + 1).equals(ime1))
                        score1 += 1;
                    else
                        score2 += 1;
                    
                    rez += " " + score1 + ":" + score2;
                    
                    System.out.println(rez);
                    
                    //6
                    if(score1 == 5 || score2 == 5){
                        System.out.println("Gotova igra!");
                        System.out.println("---------------------------------");
                        System.out.println("1.Da li zelite da pocnete novu igru?");
                        System.out.println("2.Da li zelite da izadjete iz programa?");
                        opcija = Integer.parseInt(sc.next());
                        if(opcija == 1){
                            obrisiRezultate(file);
                            return true;
                        }
                        else{
                            obrisiRezultate(file);
                            return false;
                        }
                    }
                    
                    //5
                    System.out.println("---------------------------------");
                    System.out.println("1.Da li zelite da nastavite igru?");
                    System.out.println("2.Da li zelite da prekinete igru");
                    opcija = Integer.parseInt(sc.next());
                    if(opcija == 1){
                        //continue;
                    }
                    else{
                        upisiRezultatUFajl(file, false, rez);
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
}