package irc;

import java.util.ArrayList;
import java.util.List;

import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

import start.WoordBotManager;

public class WoordBotChatter extends PircBot {
	private static String channel;
	
	public WoordBotChatter(){
		this.setName("WoordBot");
		channel = "#rsvierGalgje";
	}
	
	public void sendMessage(String message){
		this.sendMessage(channel, message);
	}
	
	@Override
	public void onMessage(String channel, String sender,
            String login, String hostname, String message) {
		if(message.startsWith("!start")){
			if(!WoordBotManager.hasGameStarted()){
				if(message.contains("single")){
					WoordBotManager.startSingleplayer(sender);
					WoordBotManager.generateWord();
					WoordBotManager.printWoordEnFouten();
					sendMessage("Gebruik !raad [letter] of !raad [woord] om een letter of woord te raden.");
					WoordBotManager.printHuidigSpeler();
				}
				else if (message.contains("multi")){
					if(message.contains("auto")){
						WoordBotManager.startMultiplayer(setPlayers());
						WoordBotManager.generateWord();
						WoordBotManager.printWoordEnFouten();
						sendMessage("Gebruik !raad [letter] of !raad [woord] om een letter of woord te raden.");
						WoordBotManager.printHuidigSpeler();
					}
					else {
						sendMessage("Stuur een private message met !setwoord [woord] naar " + this.getName() + " om het woord te bepalen.");
						sendMessage("Let op: de gebruiker die dit stuurt doet niet mee aan het raden.");
					}
				}
			}
			else {
				sendMessage("Een spel kan niet gestart worden als er nog een gaande is.");
			}
		}
		else if(message.startsWith("!raad") && sender.equals(WoordBotManager.getCurrentPlayer()) && WoordBotManager.hasGameStarted()){
			if(message.length() > 5){
				String gok = message.substring(5).trim();
				System.out.println("Input is: " + gok);
				WoordBotManager.processTurn(gok);
			}
			else {
				sendMessage("Je moet wel een letter of woord meegeven");
			}
		}
		else if(message.startsWith("!help")){
			sendMessage("Gebruik !start [single|multi] [woord|auto] voor het starten van een spel.");
		}
		else if(message.startsWith("!")){
			sendMessage("ongeldig commando");
		}
	}
	
	@Override
	public void onPrivateMessage(String sender, String login, String hostname, String message){
		if(!WoordBotManager.hasGameStarted()){
			if(message.startsWith("!start")){
				if(message.contains("single")){
					WoordBotManager.startSingleplayer(sender);
					WoordBotManager.generateWord();
					WoordBotManager.printWoordEnFouten();
					sendMessage("Gebruik !raad [letter] of !raad [woord] om een letter of woord te raden.");
					WoordBotManager.printHuidigSpeler();
				}
				else if (message.contains("multi")){
					if(message.contains("auto")){
						WoordBotManager.startMultiplayer(setPlayers());
						WoordBotManager.generateWord();
						WoordBotManager.printWoordEnFouten();
						sendMessage("Gebruik !raad [letter] of !raad [woord] om een letter of woord te raden.");
						WoordBotManager.printHuidigSpeler();
					}
					else {
					String woord = message.replaceAll("\\!start", "").replaceAll("multi", "").trim();
					System.out.println("Woord is: " + woord);
						if(woord.length() > 0){
							WoordBotManager.startMultiplayer(setPlayers(sender));
							WoordBotManager.saveWord(woord);
							WoordBotManager.printWoordEnFouten();
							sendMessage("Gebruik !raad [letter] of !raad [woord] om een letter of woord te raden.");
							WoordBotManager.printHuidigSpeler();
						}
						else {
							sendMessage(sender, "Gebruik !setwoord om het woord op te geven.");
						}
					}
				}
				else {
					sendMessage(sender, "Gebruik !start [single|multi] [woord|auto] voor het starten van een spel.");
				}
			}
			else if(message.startsWith("!setwoord")){
				if(message.trim().length() > 9){
					String woord = message.substring(9).trim();
					WoordBotManager.startMultiplayer(setPlayers(sender));
					WoordBotManager.saveWord(woord);
					WoordBotManager.printWoordEnFouten();
					sendMessage("Gebruik !raad [letter] of !raad [woord] om een letter of woord te raden.");
					WoordBotManager.printHuidigSpeler();
				}
				else {
					sendMessage(sender, "Je moet wel een woord meegeven");
				}
			}
		}
	}
	
	private List<String> setPlayers(){
		System.out.println("Spelers in dingen aan het zetten");
		User[] users = this.getUsers(channel);
		List<String> userNames = new ArrayList<>();
		for(int i = 0; i < users.length; i++){
			if(!users[i].getNick().equals(this.getName())){
				userNames.add(users[i].getNick());
			}
		}
		System.out.println("Aantal users: " + userNames.size());
		return userNames;
	}
	
	private List<String> setPlayers(String excludedPlayer){
		User[] users = this.getUsers(channel);
		List<String> userNames = new ArrayList<>();
		for(int i = 0; i < users.length; i++){
			if(!users[i].getNick().equals(excludedPlayer) && !users[i].getNick().equals(this.getName())){
				userNames.add(users[i].getNick());
			}
		}
		return userNames;
	}
	
}
