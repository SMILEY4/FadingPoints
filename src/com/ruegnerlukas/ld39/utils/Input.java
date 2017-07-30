package com.ruegnerlukas.ld39.utils;

import java.awt.Point;


public class Input {

	
	public static final int MOUSE_LEFT = 1;
	public static final int MOUSE_MIDDLE = 2;
	public static final int MOUSE_RIGHT = 3;

	
	
	
	private MouseInput mouseListener;
	private KeyInput keyListener;

	private int mouseOffX = 0;
	private int mouseOffY = 0;
	private float mouseScaleX = 1f;
	private float mouseScaleY = 1f;
	
	
	
	
	
	
	
	public Input() {
		mouseListener = new MouseInput();
		keyListener = new KeyInput();
	}
	
	
	
	
	
	
	/**
	 * updates the input
	 * */
	public void poll() {
		mouseListener.poll();
		keyListener.poll();
		mouseOffX = 0;
		mouseOffY = 0;
		mouseScaleX = 1f;
		mouseScaleY = 1f;
	}
	
	
	
	
	/**
	 * @return the current position of the mouse as a point
	 * */
	public Point mousePosition() {
		Point pos = new Point((int)((mouseListener.getPosition().x-mouseOffX)/mouseScaleX),
								(int)((mouseListener.getPosition().y-mouseOffY)/mouseScaleY));
		return pos;
	}
	
	
	
	
	/**
	 * @return the current x position of the mouse
	 * */
	public int mouseX() {
		return mousePosition().x;
	}
	
	
	
	
	/**
	 * @return the current y position of the mouse
	 * */
	public int mouseY() {
		if(Display.getDisplay() != null) {
			return mousePosition().y - Display.getDisplay().getBorderHeight();
		}
		return mousePosition().y;
	}
	
	
	
	
	/**
	 * @return the distance of the mouse compared to the last update on the x axis
	 * */
	public int mouseDX() {
		return (int)(mouseListener.getDX()/mouseScaleX);
	}
	
	
	
	
	/**
	 * @return the distance of the mouse compared to the last update on the y axis
	 * */
	public int mouseDY() {
		return (int)(mouseListener.getDY()/mouseScaleY);
	}
	
	
	
	
	/**
	 * @param button	the id of a button on the mouse
	 * @return true, if the given button is currently held down
	 * */
	public boolean mouseDown(int button) {
		return mouseListener.buttonDown(button);
	}
	
	
	
	
	/**
	 * @return true, if a button is currently held down
	 * */
	public boolean mouseDown() {
		return mouseListener.buttonDown(1) ||
				mouseListener.buttonDown(2) ||
				mouseListener.buttonDown(3);
	}
	
	
	
	
	/**
	 * @param button	the id of a button on the mouse
	 * @return true, if the given button was pressed down (only one single frame)
	 * */
	public boolean mousePressed(int button) {
		return mouseListener.buttonDownOnce(button);
	}

	
	
	
	/**
	 * @return true, if a button was pressed down (only one single frame)
	 * */
	public boolean mousePressed() {
		return mouseListener.buttonDownOnce(1) ||
				mouseListener.buttonDownOnce(2) ||
				mouseListener.buttonDownOnce(3);
	}
	
	
	
	
	/**
	 * @param button	the id of a button on the mouse
	 * @return true, if the given button was released (only one single frame)
	 * */
	public boolean mouseReleased(int button) {
		return mouseListener.buttonReleased(button);
	}
	
	
	
	
	/**
	 * @return true, if a button was released (only one single frame)
	 * */
	public boolean mouseReleased() {
		return mouseListener.buttonReleased(1) ||
				mouseListener.buttonReleased(2) ||
				mouseListener.buttonReleased(3);
	}
	
	
	
	
	/**
	 * @param button	the id of a button on the keyboard
	 * @return true, if the given key is held down
	 * */
	public boolean keyDown(int keyCode) {
		return keyListener.keyDown(keyCode);
	}
	
	
	
	
	/**
	 * @return true, if a key is held down
	 * */
	public boolean keyPressed(int keyCode) {
		return keyListener.keyDownOnce(keyCode);
	}
	
	
	
	
	/**
	 * resets typedString and begins writing all keyInputs to the string
	 * */
	public void beginTyping(int index, boolean reset) {
		keyListener.beginTyping(index, reset);
	}
	
	
	
	
	/**
	 * ends writing to the string
	 * @return the finished string
	 * */
	public String endTyping(int index) {
		return keyListener.endTyping(index);
	}
	
	
	
	
	/**
	 * @return the current typed string
	 * */
	public String getTypedString(int index) {
		return keyListener.getTypedString(index);
	}
	
	
	
	
	/**
	 * @return the mouse listener
	 * */
	public MouseInput getMouseListener() {
		return mouseListener;
	}
	
	
	
	
	/**
	 * @return the key listener
	 * */
	public KeyInput getKeyListener() {
		return keyListener;
	}
	
	
}
