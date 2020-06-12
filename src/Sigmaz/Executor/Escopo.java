package Sigmaz.Executor;

import Sigmaz.Compilador.AST;

import java.util.ArrayList;

public class Escopo {

	private ArrayList<ItemCash> mItens;
	private RunTime mRunTime;
	private Escopo mEscopoAnterior;

	private boolean mCancel;
	private boolean mContinue;
	
	public Escopo(RunTime eRunTime, Escopo eEscopoAnterior) {
		mItens = new ArrayList<>();
		mRunTime = eRunTime;
		mEscopoAnterior = eEscopoAnterior;
		mCancel = false;
		mContinue = false;

		mRunTime = eRunTime;

	}
	
	public Escopo getEscopoAnterior() {
		return mEscopoAnterior;
	}



	public boolean run(AST ASTC) {

		boolean ret = true;

		 System.out.println("RUNNING " + ASTC.getNome() + " :: " + ASTC.getValor());

		return ret;
	}
	

	public void Make(String eNome) {
		boolean enc = false;

		for (ItemCash i : mItens) {
			if (i.getNome().contentEquals(eNome)) {
				enc = true;
				break;
			}
		}

		if (!enc) {

			ItemCash IC = BuscarAnterior(eNome);
			if (IC != null) {
				enc = true;
			}

		}

		if (enc) {
			mRunTime.getErros().add("Variavel Duplicada : " + eNome);
		} else {
			mItens.add(new ItemCash(eNome));

		}
	}

	public void Const(String eNome, float eValor) {
		boolean enc = false;

		for (ItemCash i : mItens) {
			if (i.getNome().contentEquals(eNome)) {
				enc = true;
				break;
			}
		}

		if (!enc) {

			ItemCash IC = BuscarAnterior(eNome);
			if (IC != null) {
				enc = true;
			}

		}

		if (enc) {
			mRunTime.getErros().add("Variavel Duplicada : " + eNome);
		} else {
			ItemCash Novo = new ItemCash(eNome);
			Novo.setTipo(1);
			Novo.setValor(eValor);
			mItens.add(Novo);

		}
	}

	public void Apply(String eNome, float eValor) {
		boolean enc = false;

		for (ItemCash i : mItens) {
			if (i.getNome().contentEquals(eNome)) {
				enc = true;
				if (i.getTipo() == 0) {
					i.setValor(eValor);
					//System.out.println("Aplicando Valor em : " + eNome + " -->> " + eValor);
				} else {
					mRunTime.getErros().add("A constante nao pode ser alterada : " + eNome);
				}
				break;
			}
		}

		if (!enc) {

			ItemCash IC = BuscarAnterior(eNome);
			if (IC != null) {
				enc = true;
				if (IC.getTipo() == 0) {
					IC.setValor(eValor);
				} else {
					mRunTime.getErros().add("A constante nao pode ser alterada : " + eNome);
				}

			}

		}

		if (!enc) {
			mRunTime.getErros().add("Variavel nao Encontrada : " + eNome);

		}
	}

	public float Use(String eNome) {
		boolean enc = false;
		float ret = 0;

		//Listar();
		
		for (ItemCash i : mItens) {
			if (i.getNome().contentEquals(eNome)) {
				enc = true;
				ret = i.getValor();
				break;
			}
		}

		if (!enc) {

			ItemCash IC = BuscarAnterior(eNome);
			if (IC != null) {
				enc = true;
				ret = IC.getValor();

			}

		}

		if (!enc) {
			mRunTime.getErros().add("Variavel nao Encontrada : " + eNome);
		}

		return ret;
	}

	public ItemCash BuscarAnterior(String eNome) {
		ItemCash IC = null;
		boolean enc = false;

	//	System.out.println("Buscando anterior : " + eNome);
		
		if (mEscopoAnterior != null) {
			for (ItemCash i : mEscopoAnterior.mItens) {
				if (i.getNome().contentEquals(eNome)) {
					enc = true;
					IC = i;
					break;
				}
			}

		}

		if (!enc) {
			if (mEscopoAnterior != null) {
				return mEscopoAnterior.BuscarAnterior(eNome);
			}

		}

		return IC;
	}

	public void Listar() {
		for (ItemCash i : mItens) {

			System.out.println(" - " + i.getNome() + " = " + i.getValor());

		}

	}





	
	public void setCancel(boolean eCancel) {
		mCancel = eCancel;
	}

	public boolean getCancel() {
		return mCancel;
	}

	public void setContinue(boolean eContinue) {
		mContinue = eContinue;
	}

	public boolean getContinue() {
		return mContinue;
	}

	

	
	
	public class ItemCash {

		private String mNome;
		private float mValor;
		private int mTipo;

		public ItemCash(String eNome) {
			this.mNome = eNome;
			mValor = 0;
			mTipo = 0;
		}

		public void setTipo(int eTipo) {
			mTipo = eTipo;
		}

		public int getTipo() {
			return mTipo;
		}

		public void setValor(float eValor) {
			mValor = eValor;
		}

		public float getValor() {
			return mValor;
		}

		public String getNome() {
			return mNome;
		}

	}

}
