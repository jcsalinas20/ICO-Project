package clases;

import java.util.ArrayList;

public class Paciente {

	private int id_paciente, id_consultas;
	private String nombre, apellidos, dni, foto, nacimiento, genero;
	private ArrayList<Consulta> consultas;
	private ArrayList<MedicamentoPaciente> medicinas;
	private ArrayList<HistorialPaciente> historial;

	public Paciente(int id_paciente, int id_consultas, ArrayList<HistorialPaciente> historial, String nombre, String apellidos, String dni, String foto, String nacimiento, ArrayList<Consulta> consultas, ArrayList<MedicamentoPaciente> medicinas, String genero) {
		this.id_paciente = id_paciente;
		this.id_consultas = id_consultas;
		this.historial = historial;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.foto = foto;
		this.nacimiento = nacimiento;
		this.consultas = consultas;
		this.medicinas = medicinas;
		this.genero = genero;
	}

	public int getId_consultas() {
		return id_consultas;
	}

	public int getId_paciente() {
		return id_paciente;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public String getDni() {
		return dni;
	}

	public String getFoto() {
		return foto;
	}

	public String getNacimiento() {
		return nacimiento;
	}

	public String getGenero() {
		return genero;
	}

	public ArrayList<HistorialPaciente> getHistorial() {
		return historial;
	}

	public ArrayList<Consulta> getConsultas() {
		return consultas;
	}

	public ArrayList<MedicamentoPaciente> getMedicinas() {
		return medicinas;
	}
}
