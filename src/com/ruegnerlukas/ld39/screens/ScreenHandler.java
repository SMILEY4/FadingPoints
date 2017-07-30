package com.ruegnerlukas.ld39.screens;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

public class ScreenHandler {
	
	
	
	private static Map<String, Screen> screens = new HashMap<String, Screen>();
	private static Screen active = null;
	
	
	
	private ScreenHandler() {}
	
	
	
	
	
	
	public static void addScreen(Screen screen) {
		screens.put(screen.getName(), screen);
	}

	
	
	
	public static Screen getScreen(String name) {
		return screens.get(name);
	}

	
	
	
	/*
	 * switch A -> B
	 * A: loose focus
	 * set active: B
	 * B: get focus
	 */
	public static void switchTo(String name) {
		
		Screen next = getScreen(name);
		Screen last = active;
		
		if(active != null) {
			active.onLooseFocus(next);
		}
		
		active = next;
		
		if(active != null) {
			active.onGetFocus(last);
		}
		
	}
	
	
	
	
	public static void update(Graphics2D g) {
		if(active != null) {
			active.update(g);
		}
	}
	
	
	

}









