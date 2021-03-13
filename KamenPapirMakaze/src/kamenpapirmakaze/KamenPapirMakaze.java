package kamenpapirmakaze;


public class KamenPapirMakaze {

    /**
     * @param args argumenti prosledjeni prilikom startovanja programa, ako vam treba
     */
    public static void main(String[] args) {
        //Kreiraj novi objekat klase Igra i zapocni igru
        Igra igra = new Igra();
        boolean igrajIgru = true;
        while(igrajIgru == true)
            igrajIgru = igra.igraj();
            
        //String s = "Runda 1 asd assd";
        //System.out.println(s.substring(s.indexOf(" ") + 1, s.indexOf(" ") + 2));
    }
    
}
