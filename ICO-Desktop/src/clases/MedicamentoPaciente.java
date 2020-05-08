package clases;

import java.util.ArrayList;

public class MedicamentoPaciente {
	
	int idMedicamento;
	int[] dias;
	ArrayList<String> horas;

	public MedicamentoPaciente(int idMedicamento, int[] dias, ArrayList<String> horas) {
		this.idMedicamento = idMedicamento;
		this.dias = dias;
		this.horas = horas;
	}

	public int getIdMedicamento() {
		return idMedicamento;
	}

	public void setIdMedicamento(int idMedicamento) {
		this.idMedicamento = idMedicamento;
	}

	public int[] getDias() {
		return dias;
	}

	public void setDias(int[] dias) {
		this.dias = dias;
	}

	public ArrayList<String> getHoras() {
		return horas;
	}

	public void setHoras(ArrayList<String> horas) {
		this.horas = horas;
	}
}
