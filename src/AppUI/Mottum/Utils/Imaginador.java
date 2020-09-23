package AppUI.Mottum.Utils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Imaginador {

	public static BufferedImage Carregar(String elocal) {

		BufferedImage img = null;

		try {
			img = ImageIO.read(new File(elocal));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return img;
	}

	public static BufferedImage PretoEBranco(BufferedImage image1) {

		int w = image1.getWidth();
		int h = image1.getHeight();
		byte[] comp = { 0, -1 };
		IndexColorModel cm = new IndexColorModel(2, 2, comp, comp, comp);
		BufferedImage image2 = new BufferedImage(w, h,
				BufferedImage.TYPE_BYTE_INDEXED, cm);
		Graphics2D g = image2.createGraphics();
		g.drawRenderedImage(image1, null);
		g.dispose();

		return image2;
	}
	
	public static BufferedImage EscalaDeCinza(BufferedImage image) {
		BufferedImage imageGray = new BufferedImage(image.getWidth(), image.getHeight(),BufferedImage.TYPE_BYTE_GRAY);  
		Graphics g = imageGray.getGraphics();  
		g.drawImage(image, 0, 0, null);  
		g.dispose();
		return imageGray;
	}
	
	
	public static BufferedImage CarregarInterno(File eFile) {

		BufferedImage img = null;

		try {
			img = ImageIO.read(eFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return img;
	}

	public static BufferedImage Cortar(BufferedImage src, int x, int y, int largura, int altura) {

		return src.getSubimage(x, y, largura, altura);

	}

	public static BufferedImage CarregarStream(InputStream eFile) {

		BufferedImage img = null;

		try {
			img = ImageIO.read(eFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return img;
	}

	public static BufferedImage Girar(BufferedImage eImg, double eAngulo) {

		int w = eImg.getWidth();
		int h = eImg.getHeight();

		BufferedImage rotated = new BufferedImage(w, h, eImg.getType());
		Graphics2D graphic = rotated.createGraphics();
		graphic.rotate(Math.toRadians(eAngulo), w / 2, h / 2);
		graphic.drawImage(eImg, null, 0, 0);
		graphic.dispose();
		return rotated;
	}

	public static BufferedImage Cinza(BufferedImage image) {
		BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		Graphics g = result.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return result;
	}

}