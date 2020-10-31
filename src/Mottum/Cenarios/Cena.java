package Mottum.Cenarios;

import java.awt.Graphics;

public abstract class Cena {

	private String mNome;


	public void setNome(String eNome) {
		this.mNome = eNome;

		

	}

	// Propriedades Importantes

	public String getNome() {
		return mNome;
	}


	// Metodos Importantes

	public abstract void iniciar();

	public abstract void update(double dt);

	public abstract void draw(Graphics g);

}
