package com.ruegnerlukas.ld39.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Display extends JFrame {

	
	private static final long serialVersionUID = -3424387990465151972L;

	private static Display display;
	
	private DisplayListener listener;
	public JPanel defaultPanel;
	public String[] debugLines = {"", "", "", ""};
	
	

	/**
	 * creates a display with the given configurations. only one display can be created
	 * @param buffer	the buffer which gets rendered to the display
	 * @param config	the configurations of the display
	 * @return the created display
	 * */
	public static Display createDisplay(DisplayConfig config) {
		Display.display = new Display(config);
		return Display.display;
	}
	
	
	/**
	 * @return the instance of the display or null
	 * */
	public static Display getDisplay() {
		if(Display.display == null) {
			return null;
		}
		return Display.display;
	}
	
	

	
	
	
	
	
	
	
	/**
	 * @param buffer	the buffer which gets drawn to the display
	 * @param config	the configuration of the display
	 * */
	private Display(DisplayConfig config) {
		super(config.title);
		
		this.setSize(config.width, config.height);
		this.setUndecorated(!config.decorated);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLocationRelativeTo(null);
		this.setResizable(config.resizable);

		
		JPanel panel = new JPanel() {
			private static final long serialVersionUID = -160756445766046347L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				draw(g);
				int i = 0;
				for(String dLine : debugLines) {
					g.setColor(Color.BLACK);
					g.drawString(dLine, 11, 21 + (14*i));
					
					g.setColor(Color.WHITE);
					g.drawString(dLine, 10, 20 + (14*i));

					i++;
				}
			}
		};
		if(config.useDefaultJPanel) {
			this.add(panel);
			defaultPanel = panel;
		}
		this.setVisible(true);

	}

	
	
	
	public int getContentWidth() {
		return this.getContentPane().getWidth();
	}
	
	
	
	
	public int getContentHeight() {
		return this.getContentPane().getHeight();
	}
	
	
	
	
	public int getBorderHeight() {
		return this.getHeight() - this.getContentPane().getHeight();
	}
	
	
	public void setDisplayListener(DisplayListener listener) {
		this.listener = listener;
	}
	
	
	
	/**
	 * adds the input (mouse and keyboard) to this dispplay
	 * */
	public void addInput(Input input) {
		this.addMouseListener(input.getMouseListener());
		this.addMouseMotionListener(input.getMouseListener());
		this.addKeyListener(input.getKeyListener());
	}
	
	
	
	/**
	 * updates / replaints this display
	 * */
	public void update() {
		display.repaint();
	}
	
	
	
	
	/**
	 * draws the buffer to the screen
	 * */
	private void draw(Graphics g) {
		if(listener != null) {
			listener.onDraw((Graphics2D)g);
		}
	}
	
	
	
	
	/**
	 * closes the application and this display
	 * */
	public void close() {
		this.setVisible(false);
		this.dispose();
		System.exit(0);
	}
	
}
