package com.ruegnerlukas.ld39;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import com.ruegnerlukas.ld39.game.Data;
import com.ruegnerlukas.ld39.screens.Screen;
import com.ruegnerlukas.ld39.screens.ScreenHandler;
import com.ruegnerlukas.ld39.screens.game.GameScreen;
import com.ruegnerlukas.ld39.utils.Display;
import com.ruegnerlukas.ld39.utils.DisplayConfig;
import com.ruegnerlukas.ld39.utils.DisplayListener;
import com.ruegnerlukas.ld39.utils.Input;


/**
 * try stopping the evil red dots by placing obstacles in their way
 * while the world crumbles and you slowly but shurely loose your resources
 * */
public class FadingPoints {

	
	public static final int DEFAULT_WIDTH = 800;
	public static final int DEFAULT_HEIGHT = 800;
	
	public static boolean debugMode = false;
	public static Input input;
	
	
	public static void main(String[] args) {
		if(args.length > 0 && "debug".equalsIgnoreCase(args[0])) {
			debugMode = true;
		}
		new FadingPoints();
	}

	
	
	public FadingPoints() {
		
		DisplayConfig cfg = new DisplayConfig();
		cfg.width = DEFAULT_WIDTH;
		cfg.height = DEFAULT_HEIGHT;
		cfg.title = "Fading Points - LD39 (2017)";
		cfg.decorated = true;
		cfg.resizable = true;
		cfg.exitOnClose = true;
		cfg.useDefaultJPanel = true;
		
		Display.createDisplay(cfg);

		DisplayListener listener = new DisplayListener() {
			@Override
			public void onDraw(Graphics2D g) {
				update(g);
			}
		};
		Display.getDisplay().setDisplayListener(listener);
		
		input = new Input();
		Display.getDisplay().addInput(input);
		
		
		boolean running = true;
		
		start();
		
		long ts = 0;
		long te = 0;
		long delta = 0;
		
		while(running) {
			ts = System.currentTimeMillis();
			
			input.poll();
			Display.getDisplay().update();
			
//			Display.getDisplay().debugLines[0] = "delta: " + delta + "ms";
			
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			te = System.currentTimeMillis();
			delta = te - ts;
		}
		
		end();
		
	}
	
	
	
	
	
	

	public void start() {
		Screen screenGame = new GameScreen();
		ScreenHandler.addScreen(screenGame);
		ScreenHandler.switchTo(screenGame.getName());
	}

	
	
	
	
	public void update(Graphics2D g) {
		
		Font font = g.getFont();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		ScreenHandler.update(g);
		
		g.setFont(font);
		g.setColor(Data.COLOR_WALL);
		g.drawString("Created by SMILEY_4_ (Lukas Rügner) - Ludum Dare 39  (July 2017)", 5, Display.getDisplay().getContentHeight()-7);
		
	}





	public void end() {
		Display.getDisplay().close();
	}
	
	
}





