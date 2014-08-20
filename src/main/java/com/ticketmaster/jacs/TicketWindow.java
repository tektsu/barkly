package com.ticketmaster.jacs;

import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.lang.RuntimeException;
import java.util.HashMap;
import java.util.Map;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class TicketWindow extends JFrame {
	
	private static TicketWindow instance = null;
	private static final long serialVersionUID = 1L;
	private static final int qrCodeSize = 300;
	private static final int quadrantCount = 4;
	private String imageCache = System.getProperty("user.home") + "/tmp/imagecache";
	//private JPanel pane = new JPanel(new GridBagLayout());
	private JPanel pane = new JPanel(new GridLayout(2,2));
	private JLabel[] codes = new JLabel[quadrantCount];


	protected TicketWindow() {
		super("Ticket Window");

		// Create a panel with an image
		pane.setBackground(Color.BLACK);
		for (int i=0; i<quadrantCount; ++i) {
            codes[i] = new JLabel();
            codes[i].setIcon(null);
            pane.add(codes[i]);
        }

		this.getContentPane().add(pane); 

		// display this frame
		//setExtendedState(Frame.MAXIMIZED_BOTH);
		setSize(2*qrCodeSize+100, 2*qrCodeSize+100);
		setUndecorated(true);
		setVisible(true);

		// Create the image cache directories (up to 4 supported)
		for (int i=1; i<=4; ++i) {
			File dir = new File(imageCache + "/" + i);
			dir.mkdirs();
		}
	}

	public static TicketWindow getInstance() {
		if (instance == null) {
			instance = new TicketWindow();
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
		
		// Display the image
		getQuadrantLabel(quadrant).setIcon(new ImageIcon(filePath));
	}

	public void clearBarcode(int quadrant) throws RuntimeException {
		getQuadrantLabel(quadrant).setIcon(null);
	}
	
	private JLabel getQuadrantLabel(int quadrant) throws RuntimeException {
		switch (quadrant) {
		case 1:
		case 2:
		case 3: 
		case 4: return codes[quadrant-1];
		default: throw new RuntimeException("Invalid Quadrant");
		}
	}
}

