package com.ruegnerlukas.ld39.utils;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {

	
	public static final int MAX_STRINGS_TYPING = 100;
	public static final int KEY_COUNT = 256;
	
	
	
	
	private enum KeyState {
		RELEASED, PRESSED, ONCE
	}
	
	private boolean[] currKeys;
	private KeyState[] keys;
	
	private boolean[] isIndexFree;
	private boolean[] isTyping;
	private boolean[] isLineBreakEnabled;
	private String[] strTyped;
	
	
	
	
	
	
	public KeyInput() {
		currKeys = new boolean[KEY_COUNT];
		keys = new KeyState[KEY_COUNT];
		for(int i=0; i<KEY_COUNT; i++) {
			keys[i] = KeyState.RELEASED;
		}
		
		isTyping = new boolean[MAX_STRINGS_TYPING];
		strTyped = new String[MAX_STRINGS_TYPING];
		isLineBreakEnabled = new boolean[MAX_STRINGS_TYPING];
		isIndexFree = new boolean[MAX_STRINGS_TYPING];
		for(int i=0; i<MAX_STRINGS_TYPING; i++) {
			isTyping[i] = false;
			strTyped[i] = "";
			isLineBreakEnabled[i] = true;
			isIndexFree[i] = true;
		}
	}
	
	
	
	
	
	
	/**
	 * updates this key input
	 * */
	public synchronized void poll() {
		for(int i=0; i<KEY_COUNT; i++) {
			if(currKeys[i]) {
				if(keys[i] == KeyState.RELEASED) {
					keys[i] = KeyState.ONCE;
				} else {
					keys[i] = KeyState.PRESSED;
				}
			} else {
				keys[i] = KeyState.RELEASED;
			}
		}
		
		//DELETE LAST CHAR
		if(keyDownOnce(Key.VK_BACK_SPACE)) {
			for(int i=0; i<MAX_STRINGS_TYPING; i++) {
				if(isTyping[i] && strTyped[i].length() >= 2) {
					strTyped[i] = strTyped[i].substring(0, strTyped[i].length()-2);
				}
			}
		}
	}
	
	
	
	
	/**
	 * @return true, if the key with the given code is held down
	 * */
	public boolean keyDown(int keyCode) {
		return keys[keyCode] == KeyState.ONCE || keys[keyCode] == KeyState.PRESSED;
	}
	
	
	
	
	/**
	 * @return true, if the key with the given code is pressed down
	 * */
	public boolean keyDownOnce( int keyCode ) {
		return keys[keyCode] == KeyState.ONCE;
	}
	
	
	
	
	/**
	 * resets typedString and begins writing all keyInputs to the string
	 * */
	public void beginTyping(int index, boolean reset) {
		if(reset) { strTyped[index] = ""; }
		isTyping[index] = true;
	}

	
	
	
	/**
	 * ends writing to the string
	 * @return the finished string
	 * */
	public String endTyping(int index) {
		isTyping[index] = false;
		return strTyped[index];
	}
	
	
	
	
	/**
	 * @return the current typed string
	 * */
	public String getTypedString(int index) {
		return strTyped[index];
	}
	
	
	
	
	@Override
	public synchronized void keyTyped(KeyEvent e) {
		for(int i=0; i<MAX_STRINGS_TYPING; i++) {
			if(isTyping[i]) {
				if(((int)e.getKeyChar() == 10)) {	//enter = 10
					if(isLineBreakEnabled[i]) {
						strTyped[i] += e.getKeyChar();
					}
				} else {
					strTyped[i] += e.getKeyChar();
				}
//				strTyped[i] += e.getKeyChar();
			}
		}
	}

	
	
	
	@Override
	public synchronized void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if( keyCode >= 0 && keyCode < KEY_COUNT ) {
			currKeys[ keyCode ] = true;
		}
	}

	
	
	
	@Override
	public synchronized void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if( keyCode >= 0 && keyCode < KEY_COUNT ) {
			currKeys[ keyCode ] = false;
		}
	}
	
	
	
	
	/**
	 * @return the index of the next free slot
	 * */
	public int getNextFreeIndex() {
		for(int i=0; i<MAX_STRINGS_TYPING; i++) {
			if(isIndexFree[i]) {
				return i;
			}
		}
		return -1;
	}
	
	
	
	
	/**
	 * occupies the slot at the given index
	 * */
	public void occupyStringIndex(int index, boolean lineBreakEnabled) {
		if(isIndexFree[index]) {
			isIndexFree[index] = false;
			isLineBreakEnabled[index] = lineBreakEnabled;
		}
	}
	

}
