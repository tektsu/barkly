package com.ticketmaster.jacs;

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

public class BarcodeGenerator {
	
	private static BarcodeGenerator instance = null;
	private static final int qrCodeSize = 300;
	private String imageCache = System.getProperty("user.home") + "/tmp/imagecache";
	
	protected BarcodeGenerator() {
	
        File dir = new File(imageCache);
        dir.mkdirs();
	}
	
	public static BarcodeGenerator getInstance() {
		if (instance == null) {
			instance = new BarcodeGenerator();
		}
		return instance;
	}

	public String generateBarcode(String qrCodeData) throws RuntimeException {
		
		// Create the image
		// TODO: We need file locking on this directory
		// TODO: We need to avoid creating files that already exist
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
		
		return filePath;
	}
}
