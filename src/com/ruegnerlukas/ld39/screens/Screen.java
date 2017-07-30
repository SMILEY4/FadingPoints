package com.ruegnerlukas.ld39.screens;

import java.awt.Graphics2D;

public interface Screen {

	public void onGetFocus(Screen prev);
	public void onLooseFocus(Screen next);
	public void update(Graphics2D g);
	
	public String getName();
}
