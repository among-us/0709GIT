package trustnet.auth.zone.controller;

import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

import com.google.zxing.WriterException;

public class QRCodeUtilsTest  {
	@Test
	public void text2QRCodeWithText() throws WriterException, IOException {
		String contents = "테스트";
		int width = 200;
		int height = 200;
		String filename = "text_qrcode.png";

		QRCodeUtils.text2QRCode(contents, width, height, filename);
	}

	@Test
	public void text2QRCodeWithUrl() throws WriterException, IOException {
		String contents = "http://ctbrg.cafe24.com";
		int width = 200;
		int height = 200;
		String filename = "url_qrcode.png";

		QRCodeUtils.text2QRCode(contents, width, height, filename);
	}

	@Test
	public void text2QRCodeAsStream() throws WriterException, IOException {
		String contents = "테스트";
		int width = 200;
		int height = 200;
		String filename = "text_qrcode_as_stream.png";

		FileOutputStream fos = new FileOutputStream(filename);
		QRCodeUtils.text2QRCode(contents, width, height, fos);
	}
}
