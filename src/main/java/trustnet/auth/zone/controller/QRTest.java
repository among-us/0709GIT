package trustnet.auth.zone.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.modelmapper.ModelMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.service.SecurityService;
import trustnet.auth.zone.controller.dto.ZoneVerifyIntegrityDTO;
import trustnet.auth.zone.service.ZoneService;
import trustnet.auth.zone.service.vo.ZoneVerifyIntegrityVO;
import trustnet.tas.except.TNAuthException;
import trustnet.tas.valid.Integrity;
import trustnet.tas.valid.TNLicenseInfo;

@RestController
@Slf4j
@RequiredArgsConstructor
public class QRTest {
	

	public void main_sub(String qrCodeText) {

		try {
//			String sFilePath = "D:\\temp\\qrcode.png";
			String sFilePath = "C:\\Users\\dlwng\\Documents\\workspace-sts-3.9.16.CI-B1683\\TACOCLONE\\src\\main\\resources\\static\\img\\test.jpg";
//			String qrCodeText = "http://www.unet.co.kr";
//			String qrCodeText = "zone=red&info=x6eJ7T5WFm2TMPWObdTpNq7ZQib3tCxNWY3qwINrYSGAzwVjV/Y+xxftLX2aDXYZKr/AJt5tZQBZhtBQtX0eRmkgVQndM03J7gu2D+jOfuI=";
//			String qrCodeText = "zone=white&info=UatSZjHdQTPx6sAhAKLzC1swr6RMGFEmshFWjNEARtvstrWE7sCzrjtjxbfHQH9ZbG+7khTqMq15xVk3/W7BUNzmdeOYPLRCx1Rui1cmOUxs767KACXHok/m6IWvszsbLhcmLb+5z9k7/oCt13QySHGloYCkSiKT4ijTqBmuYwc=";
//			String qrCodeText = "zone=blue&info=hirA5AdAVeBQqChqyaXwWL87SYN0CgewDFr/roSFzOUOF9Zi4xuUC6ET7yA0bNfUiT+AymiOiq96L+vgh29hEBW+YSHllgpiAxWj79uW6Ds=";
			QRTest qr = new QRTest();
			qr.textToQRFile(qrCodeText, sFilePath);
			
			System.out.println("DONE");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void textToQRFile(String sQRCodeText, String sFilePath)
	{
		try {
			File qrFile = new File(sFilePath);
			int size = 150;
			String fileType = "png";
			
			QRTest qr = new QRTest();
			BufferedImage bufImage = qr.createQRImage(sQRCodeText, size, fileType);
			ImageIO.write(bufImage, fileType, qrFile);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static byte[] textToQRImage(String sQRCodeText)
	{
		try {
			byte[] imageInByte;
			QRTest qr = new QRTest();
			BufferedImage bufImg = qr.createQRImage(sQRCodeText, 200, "png");
			ByteArrayOutputStream bas = new ByteArrayOutputStream();
			ImageIO.write(bufImg, "png", bas);
			bas.flush();
			imageInByte = bas.toByteArray();
			bas.close();
			return imageInByte;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public BufferedImage createQRImage(String qrCodeText, int size, String fileType)
			throws WriterException, IOException 
	{
		// Create the ByteMatrix for the QR-Code that encodes the given String
		Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
		// Make the BufferedImage that are to hold the QRCode
		int matrixWidth = byteMatrix.getWidth();
		BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();

		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, matrixWidth, matrixWidth);
		// Paint and save the image using the ByteMatrix
		graphics.setColor(Color.BLACK);

		for (int i = 0; i < matrixWidth; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				if (byteMatrix.get(i, j)) {
					graphics.fillRect(i, j, 1, 1);
				}
			}
		}
		return image;
	}

}
