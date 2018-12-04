package ba.unsa.rpr.tutorijal7;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Tutorijal implements Serializable {

    public static ArrayList<Grad> ucitajGradove() {
        ArrayList<Grad> rValue = new ArrayList<Grad>();
        try {
            Scanner ulaz = new Scanner(new FileReader("mjerenja.txt"));
            String podaciString = new String();
            int brojac = 0;
            int pom = 0;
            int k = 0;
            String podaci = "";
            String[] gradovi = new String[1001];
            while (ulaz.hasNext()) {
                podaci = ulaz.next();
                gradovi = podaci.split(",");
                String naziv = "";
                double[] temperature = new double[1000];
                for (int i = 0; i < gradovi.length; i++) {
                    if (i == 0)
                        naziv = gradovi[0];
                    else
                        temperature[i-1] = Double.parseDouble(gradovi[i]);
                }
                rValue.add(new Grad(naziv, 0, temperature));
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return rValue;
    }

    public static void main(String[] args) {
	    ArrayList<Grad> proba = new ArrayList<Grad>();
	    proba = ucitajGradove();
	    for (Grad g : proba)
	        System.out.println(g);
    }
}
