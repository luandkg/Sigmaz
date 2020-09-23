package Container;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ArquivoImagem {

	private Arquivo mArquivo;
	private boolean mIndexado;
	private BufferedImage mImagem;

	public ArquivoImagem(Arquivo eArquivo) {
		mArquivo = eArquivo;
		mIndexado = false;
		mImagem = null;
	}

	public BufferedImage getImagem() {

		if (!mIndexado) {
			try {
				InputStream in = new ByteArrayInputStream(mArquivo.getBytes());
				mImagem = ImageIO.read(in);
				mIndexado = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return mImagem;
	}

	
	public String getNome() {
		return mArquivo.getNome();
	}

	public long getTipo() {
		return mArquivo.getTipo();
	}

	public long getInicio() {
		return mArquivo.getInicio();
	}

	public long getFim() {
		return mArquivo.getFim();
	}

	public long getTamanho() {return mArquivo.getTamanho();}

	public boolean getIndexado(){return mIndexado;}

	public int getLargura() {
		return getImagem().getWidth();
	}

	public int getAltura() {
		return getImagem().getHeight();
	}
}
