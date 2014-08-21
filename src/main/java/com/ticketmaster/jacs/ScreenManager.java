package com.ticketmaster.jacs;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ticketmaster.jacs.TicketDisplay;

public class ScreenManager {

	private static ScreenManager instance = null;
	private static final int qrCodeSize = 300;
	private String imageCache = System.getProperty("user.home") + "/tmp/imagecache";
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
		// Create the image
		String charset = "UTF-8";
		String filePath = imageCache + "/" + qrCodeData + ".png";
		try {
			Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
 
			BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset),
					BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hintMap);
			MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath.lastIndexOf('.') + 1), new File(filePath));
		}
    	catch (WriterException e) {
    		throw new RuntimeException(e.getMessage());
    	}
    	catch (IOException e) {
    		throw new RuntimeException(e.getMessage());
    	}
		
		getQuadrantLabel(quadrant).setImage(filePath);
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
