package com.ruegnerlukas.ld39.game;

import java.awt.Graphics2D;
import java.util.Random;

import com.ruegnerlukas.ld39.FadingPoints;
import com.ruegnerlukas.ld39.utils.Display;
import com.ruegnerlukas.ld39.utils.Key;

public class Game {

	private int currLevel = -1;
	
	private GameBoard board;
	private long currentSeed = 0;
	
	
	
	public void start() {
		newBoard();
	}

	
	
	
	
	public void newBoard() {
		currLevel++;
		
		board = Levels.getLevel(currLevel);
		if(board == null) {
			currentSeed = new Random().nextLong();
			board = BoardGenerator.generate(currentSeed);
		}
		
	}
	
	
	
	
	public void restart() {
		
		board = Levels.getLevel(currLevel);
		if(board == null) {
			board = BoardGenerator.generate(currentSeed);
		}
	}
	
	
	
	
	public void update(Graphics2D g) {
		
		g.setColor(Data.COLOR_BACKGROUND);
		g.fillRect(0, 0, Display.getDisplay().getWidth(), Display.getDisplay().getHeight());
		
		if(board == null) {
			return;
		}
		
		if(FadingPoints.input.keyPressed(Key.VK_ENTER)) {
			newBoard();
		}
		if(FadingPoints.input.keyPressed(Key.VK_R)) {
			restart();
		}
		if(FadingPoints.input.keyPressed(Key.VK_SPACE)) {
			board.calcStep();
		}
		
		board.update(g);

		if(board.hasLost()) {
			board.text = "You Lost! Press \"R\" to restart.";
//			restart();
		}
		
	}





	public void end() {
	}
	
	
}




