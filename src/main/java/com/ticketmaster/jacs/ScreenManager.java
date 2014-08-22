package com.ticketmaster.jacs;

import java.awt.Dimension;
import java.awt.Toolkit;

public class ScreenManager {

	private static ScreenManager instance = null;
	private static TicketDisplay[] displays = new TicketDisplay[4];
	
	protected ScreenManager() {
		Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		int width = screenSize.width / 2;
		int height = screenSize.height / 2;
		displays[0] = new TicketDisplay(0, 0, width, height);
		displays[1] = new TicketDisplay(width, 0, width, height);
		displays[2] = new TicketDisplay(0, height, width, height);
		displays[3] = new TicketDisplay(width, height, width, height);
}
	
	public static ScreenManager getInstance() {
		if (instance == null) {
			instance = new ScreenManager();
		}
		return instance;
	}
	
	public void displayBarcode(int quadrant, String qrCodeData) throws RuntimeException {
		getQuadrantLabel(quadrant).setImage(BarcodeGenerator.getInstance().generateBarcode(qrCodeData));
	}

	public void clearBarcode(int quadrant) throws RuntimeException {
		getQuadrantLabel(quadrant).clearImage();
	}
	
	private TicketDisplay getQuadrantLabel(int quadrant) throws RuntimeException {
		switch (quadrant) {
		case 1:
		case 2:
		case 3: 
		case 4: return displays[quadrant-1];
		default: throw new RuntimeException("Invalid Quadrant");
		}
	}
}
