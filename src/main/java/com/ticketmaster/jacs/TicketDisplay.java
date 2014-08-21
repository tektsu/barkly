package com.ticketmaster.jacs;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TicketDisplay extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panel = new JPanel(new GridBagLayout());
	private JLabel code = new JLabel();
	
	public TicketDisplay(int x, int y, int width, int height) {

		super("Ticket Window");

		code.setIcon(null);
		panel.setBackground(Color.BLACK);
		panel.add(code);
		this.getContentPane().add(panel);
		
		setSize(width, height);
		setLocation(x, y);
		setUndecorated(true);
		
	}
	
	public void setImage(String path) {
		code.setIcon(new ImageIcon(path));
		setVisible(true);
	}
	
	public void clearImage() {
		setVisible(false);
		code.setIcon(null);
	}
}
