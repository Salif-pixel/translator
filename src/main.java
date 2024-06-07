import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ce programme est un traducteur du francais vers l'anglais et vice-versa");
        System.out.println("choisissez votre langue source  en/fr:");
        String langue=sc.nextLine();
        System.out.println("choisissez le langage de traduction  en/fr:");
        String traduction=sc.nextLine();
        System.out.println("ecrivez:");
        String phrase=sc.nextLine();
        Francais salif= new Francais();
        Anglais zal = new Anglais();
        TranslatorAdapter translatorAdapter = new TranslatorAdapter(zal,salif);
        if(langue.equals("fr") && traduction.equals("en")){
            translatorAdapter.SpeakeEnglish(phrase);
        }else if(langue.equals("en") && traduction.equals("fr")){
            translatorAdapter.SpeakFrench(phrase);
        }

    }
}
