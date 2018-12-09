package ba.unsa.rpr.tutorijal7;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.beans.XMLEncoder;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Tutorijal implements Serializable {

    public static ArrayList<Grad> ucitajGradove() {
        ArrayList<Grad> rValue = new ArrayList<Grad>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("mjerenja.txt"));
            String[] gradovi = new String[1001];
            String line;
            while ( (line = br.readLine()) != null ) {
                gradovi = line.split(",");
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
            br.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return rValue;
    }

    public static UN ucitajXml(ArrayList<Grad> gradovi) {
        ArrayList<Drzava> drzave = new ArrayList<Drzava>();
        Document xmldoc = null;
        try {
            DocumentBuilder docReader = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            xmldoc = docReader.parse(new File("drzave.xml"));
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        Element rootElement = xmldoc.getDocumentElement();
        Element mjerenja = xmldoc.createElement("temperature");
        rootElement.normalize();
        NodeList children = rootElement.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {
            Node drzavaNode = children.item(i);
            if (drzavaNode instanceof Element) {
                Drzava drzava = new Drzava();
                Grad grad = new Grad();
                Element drzavaElement = (Element) drzavaNode;

                NodeList glavniGradElementi = drzavaElement.getElementsByTagName("glavniGrad");
                if (glavniGradElementi.getLength() != 0) {
                    Element gradElement = ((Element) glavniGradElementi.item(0));
                    grad.setNaziv(gradElement.getElementsByTagName("naziv").item(0).getTextContent());
                    grad.setBrojStanovnika(Integer.valueOf(gradElement.getElementsByTagName("brojStanovnika").item(0).getTextContent()));

                    String pom = "";
                    for (int j = 0; j < gradovi.size(); j++) {
                        if (gradovi.get(j).getNaziv().equals(gradElement.getElementsByTagName("naziv").item(0).getTextContent())) {
                            for (int k = 0; k < gradovi.get(j).getTemperature().length; k++) {
                                pom += String.valueOf(k) + ", ";
                            }
                            mjerenja.appendChild(xmldoc.createTextNode(pom));
                        }
                    }

                    ((Element) glavniGradElementi.item(0)).setAttribute("temperature", pom);
                    Source s = new DOMSource(xmldoc);
                    StreamResult r = new StreamResult("drzave.xml");
                    TransformerFactory tf = TransformerFactory.newInstance();
                    try {
                        Transformer t = tf.newTransformer();
                        t.transform(s,r);
                    } catch (TransformerConfigurationException e) {
                        e.printStackTrace();
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    }
                }
                drzava.setGlavniGrad(grad);
                drzava.setBrojStanovnika(Integer.valueOf(drzavaElement.getElementsByTagName("brojStanovnika").item(0).getTextContent()));
                drzava.setNaziv(drzavaElement.getElementsByTagName("naziv").item(0).getTextContent());
                drzava.setJedinicaZaPovrsinu(drzavaElement.getElementsByTagName("jedinicaZaPovrsinu").item(0).getTextContent());
                drzava.setPovrsina(Double.valueOf(drzavaElement.getElementsByTagName("povrsina").item(0).getTextContent()));
                drzave.add(drzava);
            }
        }
        return new UN(drzave);
    }

    public static void zapisiXml(UN un) {
        try {
            XMLEncoder izlaz = new XMLEncoder(new FileOutputStream("un.xml"));
            izlaz.writeObject(un);
            izlaz.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
	    /*ArrayList<Grad> proba = new ArrayList<Grad>();
	    proba = ucitajGradove();
	    for (Grad g : proba)
	        System.out.println(g);*/
	    UN un = ucitajXml(ucitajGradove());
	    ArrayList<Drzava> drzave = un.getDrzave();
	    for (Drzava d : drzave)
            System.out.println(d);
    }
}
