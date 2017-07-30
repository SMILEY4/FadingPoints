package com.ruegnerlukas.ld39.screens.game;

import java.awt.Graphics2D;

import com.ruegnerlukas.ld39.game.Game;
import com.ruegnerlukas.ld39.screens.Screen;
import com.ruegnerlukas.ld39.screens.ScreenNames;

public class GameScreen implements Screen {

	
	private Game game = new Game();
	
	
	
	@Override
	public void onGetFocus(Screen prev) {
		game.start();
	}

	
	
	
	
	@Override
	public void update(Graphics2D g) {
		game.update(g);
	}
	
	
	
	
	@Override
	public void onLooseFocus(Screen next) {
		game.end();
	}

	
	
	

	@Override
	public String getName() {
		return ScreenNames.SCREEN_GAME;
	}

}
