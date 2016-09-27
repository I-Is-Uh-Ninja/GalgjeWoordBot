package start;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jibble.pircbot.IrcException;

import ai.WoordBot;
import irc.WoordBotChatter;

public class WoordBotManager {
	
	private static WoordBotChatter woordBotChatter;
	private static WoordBot woordBot;
	private static boolean gameStarted;
	private static String currentPlayer;
	private static ArrayList<String> playersInGame;
	private static StringBuffer huidigWoord;
	private static int fouten;

	public static void main(String[] args) {
		woordBotChatter = new WoordBotChatter();
		gameStarted = false;
		try {
			woordBotChatter.connect("openirc.snt.utwente.nl");
			woordBotChatter.joinChannel("#rsvierGalgje");
		} catch (IOException | IrcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean hasGameStarted(){
		return gameStarted;
	}
	
	public static String getCurrentPlayer(){
		return currentPlayer;
	}
	
	public static void startSingleplayer(String username){
		woordBot = new WoordBot();
		huidigWoord = new StringBuffer();
		gameStarted = true;
		fouten = 0;
		currentPlayer = username;
		playersInGame = new ArrayList<>();
		playersInGame.add(username);
		woordBotChatter.sendMessage("Singleplayer game gestart");
	}
	
	public static void startMultiplayer(List<String> userNames){
		System.out.println("Attempting to start multiplayer game");
		for (String user : userNames){
			System.out.println("found user: " + user);
		}
		woordBot = new WoordBot();
		huidigWoord = new StringBuffer();
		gameStarted = true;
		fouten = 0;
		playersInGame = (ArrayList<String>)userNames;
		currentPlayer = playersInGame.get(0);
		woordBotChatter.sendMessage("Multiplayer game gestart");
	}
	
	public static void generateWord(){
		woordBot.generateWoord();
		initializeHuidigWoord(woordBot.getTeRadenWoord());
	}
	
	public static void saveWord(String woord){
		woordBot.setTeRadenWoord(woord);
		initializeHuidigWoord(woordBot.getTeRadenWoord());
	}
	
	private static void initializeHuidigWoord(String woord){
		for(int i = 0; i < woordBot.getTeRadenWoord().length(); i++){
			huidigWoord.append('-');
		}
	}
	
	public static void printWoordEnFouten(){
		woordBotChatter.sendMessage(huidigWoord.toString());
		woordBotChatter.sendMessage("Het aantal fouten is " + fouten + "/10.");
	}
	
	public static void printHuidigSpeler(){
		woordBotChatter.sendMessage("De beurt is aan " + currentPlayer);
	}
	
	public static void processTurn(String input){
		System.out.println("Huidig speler is " + currentPlayer);
		if(input.length() == 1){
			if(processLetter(input.charAt(0))){
				endTurn();
			}
		}
		else if(input.length() > 1){
			if(processWord(input)){
				endTurn();
			}
		}
		else{
			woordBotChatter.sendMessage("Bot is kaputt");
		}
		if(fouten == 10){
			woordBotChatter.sendMessage("Maximaal aantal fouten bereikt. Je hebt het spel verloren!");
			woordBotChatter.sendMessage("Het goede woord was: " + woordBot.getTeRadenWoord());
			gameStarted = false;
		}
		else if(gameStarted){
			printWoordEnFouten();
			printHuidigSpeler();
		}
	}
	
	private static void endTurn(){
		int currentPlayerIndex = playersInGame.indexOf(currentPlayer);
		if (currentPlayerIndex + 1 >= playersInGame.size()){
			currentPlayer = playersInGame.get(0);
		}
		else {
			currentPlayer = playersInGame.get(currentPlayerIndex + 1);
		}
	}
	
	//letter is al geraden -> huidig speler mag nog een keer
	//letter is zit niet in woord -> aantal fouten++, volgende speler aan de beurt
	//letter zit in het woord -> vul letter in woord, woord compleet ? speler gewonnen : volgende speler
	//woord is al geraden -> huidige speler mag nog een keer
	//is woord het goede woord ? speler gewonnen : fouten++, volgende speler
	
	private static boolean processLetter(char letter){
		System.out.println("Process een letter");
		if(isLetterGeraden(letter)){
			woordBotChatter.sendMessage("Deze letter is al een keer geraden. Vul opnieuw een letter in");
			return false;
		}
		else{
			woordBot.letterIsGeraden(letter);
			if(zitLetterInWoord(letter)){
				woordBotChatter.sendMessage("Deze letter zit in het woord!");
				zetLetterInWoord(letter);
				if(isWoordGeraden(huidigWoord.toString())){
					woordBotChatter.sendMessage("Het woord is geraden! Het woord was: " + woordBot.getTeRadenWoord());
					woordBotChatter.sendMessage(currentPlayer + " heeft gewonnen!");
					gameStarted = false;
				}
				return false;
			}
			else {
				woordBotChatter.sendMessage("Deze letter zit niet in het woord.");
				fouten++;
				return true;
			}
		}
	}
	
	private static boolean processWord(String woord){
		System.out.println("Process een woord");
		if(isWoordAlGeraden(woord)){
			woordBotChatter.sendMessage("Dit woord is al een keer geraden");
			return false;
		}
		else {
			woordBot.woordIsGeraden(woord);
			if(isWoordGeraden(woord)){
				woordBotChatter.sendMessage("Dat is het goede woord!");
				woordBotChatter.sendMessage(currentPlayer + " heeft gewonnen!");
				gameStarted = false;
				return false;
			}
			else {
				woordBotChatter.sendMessage("Dat was niet het goede woord.");
				fouten++;
				return true;
			}
		}
	}
	
	private static boolean isLetterGeraden(char letter){
		return woordBot.isLetterAlGeraden(letter);
	}
	
	private static boolean zitLetterInWoord(char letter){
		return woordBot.zitLetterInWoord(letter);
	}
	
	private static void zetLetterInWoord(char letter){
		huidigWoord = woordBot.plaatsLetterInWoord(letter, huidigWoord);
	}
	
	private static boolean isWoordAlGeraden(String woord){
		return woordBot.isWoordAlGeraden(woord);
	}
	
	private static boolean isWoordGeraden(String geradenWoord){
		return woordBot.matchesTeRadenWoord(geradenWoord);
	}

}
