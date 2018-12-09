package ba.unsa.rpr.tutorijal7;

import java.io.Serializable;

public class Grad implements Serializable {
    private String naziv;
    private int brojStanovnika;
    private double[] temperature = new double[1000];

    public Grad() {
        this.naziv = null;
        this.brojStanovnika = 0;
        for (int i = 0; i < 1000; i++)
            this.temperature[i] = 0;
    }

    public Grad(String naziv, int brojStanovnika, double[] temperature) {
        this.naziv = naziv;
        this.brojStanovnika = brojStanovnika;
        this.temperature = temperature;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getBrojStanovnika() {
        return brojStanovnika;
    }

    public void setBrojStanovnika(int brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }

    public double[] getTemperature() {
        return this.temperature;
    }

    public void setTemperature(double[] temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        String rValue = this.getNaziv() + " čiji je broj stanovnika " + this.getBrojStanovnika() + "\n" + "Temperaturna mjerenja: ";
        for (double tmp : this.getTemperature()) {
            if (tmp != 0.0d)
            rValue += tmp + " ";
        }
        return rValue;
    }
}
