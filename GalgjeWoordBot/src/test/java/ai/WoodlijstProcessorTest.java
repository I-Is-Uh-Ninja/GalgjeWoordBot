package ai;

import java.io.File;

import junit.framework.TestCase;

public class WoodlijstProcessorTest extends TestCase {

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
		int aantalWoorden = processor.telWoorden();
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
