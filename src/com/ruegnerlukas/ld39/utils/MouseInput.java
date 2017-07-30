package com.ruegnerlukas.ld39.utils;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

class MouseInput implements MouseListener, MouseMotionListener{
	
	
	public static enum MouseState {
		UP, DOWN, PRESSED, RELEASED
	}
	
	
	
	
	private final int BUTTON_COUNT = 3;
	
	private Point mousePos;
	private Point currentPos;
	private int dx = 0;
	private int dy = 0;
	private boolean[] state;
	private boolean[] released;
	private MouseState[] poll;
	
	
	
	
	
	
	public MouseInput() {
		mousePos = new Point(0, 0);
		currentPos = new Point(0, 0);
		state = new boolean[BUTTON_COUNT];
		released = new boolean[BUTTON_COUNT];
		poll = new MouseState[BUTTON_COUNT];
		for(int i=0; i<BUTTON_COUNT; i++) {
			poll[i] = MouseState.UP;
		}
	}
	
	
	
	
	
	
	/**
	 * updates this mouse input
	 * */
	public synchronized void poll() {
		
		dx = currentPos.x - mousePos.x;
		dy = currentPos.y - mousePos.y;
		mousePos = new Point(currentPos);
		
		for(int i=0; i<BUTTON_COUNT; i++) {
			if(state[i]) {
				if(poll[i] == MouseState.UP) {
					poll[i] = MouseState.PRESSED;
				} else {
					poll[i] = MouseState.DOWN;
				}
			} else {
				poll[i] = MouseState.UP;
			}
			if(released[i]) {
				poll[i] = MouseState.RELEASED;
				released[i] = false;
			}
		}
		
	}
	
	
	
	
	/**
	 * @return the distance of the mouse compared to the last update on the x axis
	 * */
	public int getDX() {
		return this.dx;
	}
	
	
	
	
	/**
	 * @return the distance of the mouse compared to the last update on the y axis
	 * */
	public int getDY() {
		return this.dy;
	}
	
	
	
	
	/**
	 * @return the current position of the mouse as a point
	 * */
	public Point getPosition() {
		return mousePos;
	}
	
	
	
	
	/**
	 * @param button	the id of a button on the mouse
	 * @return true, if the given button was pressed down (only one single frame)
	 * */
	public boolean buttonDownOnce(int button) {
		return poll[button-1] == MouseState.PRESSED; 
	}
	
	
	
	
	/**
	 * @param button	the id of a button on the mouse
	 * @return true, if the given button is currently held down
	 * */
	public boolean buttonDown(int button) {
		return poll[button-1] == MouseState.PRESSED || poll[button-1] == MouseState.DOWN;
	}
	
	
	
	
	/**
	 * @param button	the id of a button on the mouse
	 * @return true, if the given button was released (only one single frame)
	 * */
	public boolean buttonReleased(int button) {
		return poll[button-1] == MouseState.RELEASED;
	}
	
	
	
	
	
	
	@Override
	public synchronized void mouseClicked(MouseEvent e) {
	}

	
	
	
	@Override
	public synchronized void mousePressed(MouseEvent e) {
		state[e.getButton()-1] = true;
	}

	
	
	
	@Override
	public synchronized void mouseReleased(MouseEvent e) {
		state[e.getButton()-1] = false;
		released[e.getButton()-1] = true;
	}

	
	
	
	@Override
	public synchronized void mouseEntered(MouseEvent e) {
	}

	
	
	
	@Override
	public synchronized void mouseExited(MouseEvent e) {
	}
	
	
	

	@Override
	public synchronized void mouseDragged(MouseEvent e) {
		currentPos = e.getPoint();
	}


	
	
	@Override
	public synchronized void mouseMoved(MouseEvent e) {
		currentPos = e.getPoint();
	}
	
	
}
