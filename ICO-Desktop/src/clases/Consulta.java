package clases;

public class Consulta {

	private int num_consulta;
	private String hora, dia, notasPaciente, notasDoctor;
	private boolean asistido;

	public Consulta(int num_consulta, String hora, String dia, String notasPaciente, String notasDoctor, boolean asistido) {
		this.num_consulta = num_consulta;
		this.hora = hora;
		this.dia = dia;
		this.notasPaciente = notasPaciente;
		this.notasDoctor = notasDoctor;
		this.asistido = asistido;
	}

	public void setNotasPaciente(String notasPaciente) {
		this.notasPaciente = notasPaciente;
	}

	public void setNotasDoctor(String notasDoctor) {
		this.notasDoctor = notasDoctor;
	}

	public void setAsistido(boolean asistido) {
		this.asistido = asistido;
	}

	public int getNum_consulta() {
		return num_consulta;
	}

	public String getHora() {
		return hora;
	}

	public String getDia() {
		return dia;
	}

	public String getNotasPaciente() {
		return notasPaciente;
	}

	public String getNotasDoctor() {
		return notasDoctor;
	}

	public boolean isAsistido() {
		return asistido;
	}
}