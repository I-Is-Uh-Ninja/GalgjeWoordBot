/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Deze klasse zorgt voor het inlezen van een text-file met woorden, en het zoeken naar een woord in de file
 * Er is geen List met woorden, omdat dit te veel bleek voor een arraylist
 * 
 * @author Ian
 */
public class WoordlijstProcessor {
    private static File woordenlijst;
    private int aantalWoorden;
    private String[] woorden;

    public WoordlijstProcessor() {
        woordenlijst = new File("woordenlijst.txt");
        aantalWoorden = telWoorden();
        woorden = zetWoordenInArray(aantalWoorden);
    }

    public int getAantalWoorden() {
        return this.aantalWoorden;
    }

    public void setAantalWoorden(int aantalWoorden) {
        this.aantalWoorden = aantalWoorden;
    }

    public String[] getWoorden() {
        return this.woorden;
    }

    public void setWoorden(String[] woorden) {
        this.woorden = woorden;
    }
    
    //tool om een korte woordenlijst te genereren, die alleen woorden heeft van min tot max aantal letters
    protected static void decreaseList(File file, int min, int max){
        try(Scanner input = new Scanner(file); FileWriter fw = new FileWriter("woordenlijst.txt");){
            while(input.hasNextLine()){
                String woord = input.nextLine();
                woord = woord.trim();
                if(woord.matches("\\w{" + min + "," + max + "}")){//als het woord bestaat uit min tot max aantal letters (exclusief letters met speciale tekens, zoals een trema)
                    System.out.println("Woord gevonden: " + woord);
                    String newline = String.format("%n");//omdat verschillende OS'en een andere definitie hebben van een newline, dient men deze methode te gebruiken
                    fw.write(woord + newline);
                    fw.flush();
                }
            }
        }
        catch (FileNotFoundException ex) {
            System.err.println("Bestand niet gevonden: " + ex.getMessage());
        }
        catch (IOException ex) {
            System.err.println("IOException: " + ex.getMessage());
        }
    }
    //tel het aantal woorden in de woordenlijst
    private int telWoorden(){
        int aantalWoorden = 0;
        try(Scanner input = new Scanner(woordenlijst)){
            while(input.hasNextLine()){
                input.nextLine();
                aantalWoorden++;
            }
            return aantalWoorden;
        } catch (FileNotFoundException ex) {
            System.err.println("Bestand niet gevonden: " + ex.getMessage());
        }
        return 0;
    }
    
    private String[] zetWoordenInArray(int aantalWoorden){
    	System.out.println("Het aantal woorden is: " + aantalWoorden);
    	String[] woorden = new String[aantalWoorden];
    	try(Scanner input = new Scanner(woordenlijst)){
    		int index = 0;
            while(input.hasNextLine()){
                woorden[index] = input.nextLine();
                index++;
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Bestand niet gevonden: " + ex.getMessage());
        }
    	return woorden;
    }
    
    public String kiesRandomWoord(){
    	int randomIndex = (int) (Math.random() * aantalWoorden);//dit bepaald het random woord, de int die gegenereerd is staat voor het # van het woord in de lijst
    	return woorden[randomIndex];
    }
    
    /*
    //zoek een woord op een bepaald lijnnummer (dit is nodig voor een random woord kiezen)
    public String zoekWoord(int lijnNummer){
        try(Scanner input = new Scanner(woordenlijst);){
            for (int line = 0; line < (lijnNummer - 1); line++){ //loop tot aan het lijnNummer
                input.nextLine();//ga naar de volgende lijn (tot dat we bij de lijn zijn van het woord die we willen)
            }
            return input.nextLine(); //return het volgende woord (die dus op de lijn #lijnNummer zit)
        }catch (FileNotFoundException ex) {
            System.err.println("Bestand niet gevonden: " + ex.getMessage());
        }
        return "";
    }
    //kies een random woord uit de woordenlijst
    public String kiesRandomWoord(){
        if(woordenlijst.exists()){
            int randomIndex = (int) (Math.random() * telWoorden());//dit bepaald het random woord, de int die gegenereerd is staat voor het # van het woord in de lijst
            return zoekWoord(randomIndex);
        }
        else{
            System.err.println("Geen bestand gevonden!");
            return null;
        }
    }
    */
}
