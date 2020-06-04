package engine.parser.commands;

import engine.Game;
import engine.chess.Color;
import engine.logger.Logger;
import engine.parser.Command;

public class Protover implements Command {

	@Override
	public void accept() {
		Logger.log("Received protover command.");
		System.out.println("feature sigint=0 sigterm=0 san=0 done=1");
		Game.getInstance().setPlaySide(Color.BLACK);
	}

}
