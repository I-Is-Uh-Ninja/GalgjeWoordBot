/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import java.io.File;
import junit.framework.TestCase;

/**
 *
 * @author Ian
 */
public class WoordlijstProcessorTest extends TestCase {
    
    public void testDecreaseList() {
		File dictionary = new File("dictionary.txt");
		if(dictionary.exists()){
			WoordlijstProcessor.decreaseList(dictionary, 3, 12);
			File woordenlijst = new File("woordenlijst.txt");
			assertTrue(woordenlijst.exists());
		}
		else{
			fail("Bestand dictionary kon niet gevonden worden");
		}
	}
	
	public void testTelWoorden(){
		WoordlijstProcessor processor = new WoordlijstProcessor();
		int aantalWoorden = processor.getAantalWoorden();
		System.out.println("Aantal woorden: " + aantalWoorden);
		assertTrue(aantalWoorden > 0);
	}

	public void testKiesRandomWoord() {
		WoordlijstProcessor processor = new WoordlijstProcessor();
		String randomWoord = processor.kiesRandomWoord();
		assertFalse(randomWoord.equals(""));
		System.out.println("Random woord is: " + randomWoord);
	}
    
}
