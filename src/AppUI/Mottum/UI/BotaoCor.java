package AppUI.Mottum.UI;

import java.awt.Color;
import java.awt.Graphics;

import AppUI.Mottum.Estrutural.Rect;

public class BotaoCor {
	
	private Rect mRetangulo;
	private Color mCor = Color.black;

	public BotaoCor(int eX, int eY, int eLargura, int eAltura) {
		
		mRetangulo = new Rect(eX,eY,eLargura,eAltura);
		
	}
	

	public BotaoCor(int eX, int eY, int eLargura, int eAltura,Color eCor) {
		
		mRetangulo = new Rect(eX,eY,eLargura,eAltura);
		mCor=eCor;
		
	}
	
	
	public int getX() {
		return mRetangulo.getX();
	}

	public int getY() {
		return mRetangulo.getY();
	}

	public int getLargura() {
		return mRetangulo.getLargura();
	}

	public int getAltura() {
		return mRetangulo.getAltura();
	}

	public int getX2() {
		return mRetangulo.getX2();
	}

	public int getY2() {
		return mRetangulo.getY2();
	}

	public void setX(int eX) {
		mRetangulo.setX(eX);
	}

	public void setY(int eY) {
		mRetangulo.setY(eY);
	}
	
	public Color getCor() {
		return mCor;
	}
	
	public void setCor(Color eCor) {
		mCor=eCor;
	}
	
	public void draw(Graphics g) {
	
		g.setColor(mCor);
		g.fillRect(mRetangulo.getX(), mRetangulo.getY(), mRetangulo.getLargura(), mRetangulo.getAltura());
		
		
	}

	public boolean getClicado(int px,int py) {
		boolean ret = false;
		
		if (px >= this.getX() && px <= this.getX2()) {
			if (py >= this.getY() && py <= this.getY2()) {
				ret=true;
			}
		}
		
		return ret;
	}
	
	
}
