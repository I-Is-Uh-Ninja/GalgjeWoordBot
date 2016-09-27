package ai;

import java.util.HashSet;

public class WoordBot {
	private WoordlijstProcessor woordlijstProcessor;
	private String teRadenWoord;
	private HashSet<Character> alGeradenLetters;
	private HashSet<String> alGeradenWoorden;
	
	public WoordBot(){
		woordlijstProcessor = new WoordlijstProcessor();
		alGeradenLetters = new HashSet<>();
		alGeradenWoorden = new HashSet<>();
	}
	
	public WoordlijstProcessor getWoordlijstProcessor() {
		return woordlijstProcessor;
	}

	public void setWoordlijstProcessor(WoordlijstProcessor woordlijstProcessor) {
		this.woordlijstProcessor = woordlijstProcessor;
	}

	public String getTeRadenWoord() {
		return teRadenWoord;
	}

	public void setTeRadenWoord(String teRadenWoord) {
		this.teRadenWoord = teRadenWoord;
	}

	public void generateWoord(){
		setTeRadenWoord(getWoordlijstProcessor().kiesRandomWoord());
	}
	
	public boolean matchesTeRadenWoord(String geradenWoord){
		return getTeRadenWoord().equals(geradenWoord);
	}
	
	public boolean zitLetterInWoord(String letter){
		return getTeRadenWoord().contains(letter);
	}
	
	public boolean zitLetterInWoord(char letter){
		return zitLetterInWoord("" + letter);
	}
	
	public boolean isLetterAlGeraden(char letter){
		return alGeradenLetters.contains(letter);
	}
	
	public void letterIsGeraden(char letter){
		alGeradenLetters.add(letter);
	}
	
	public boolean isWoordAlGeraden(String woord){
		return alGeradenWoorden.contains(woord);
	}
	
	public void woordIsGeraden(String woord){
		alGeradenWoorden.add(woord);
	}
	
	//zet de letter op alle plaatsen in het woord waar het voorkomt
    public StringBuffer plaatsLetterInWoord(char letter, StringBuffer woord){
        char[] lettersInWoord = teRadenWoord.toCharArray();
        for(int c = 0; c < lettersInWoord.length; c++){
            if (lettersInWoord[c] == letter){
                String letterString = "" + letter;
                woord.replace(c, c+1, letterString);//vervang de placeholder door de letter
            }
        }
        return woord;
    }
}
