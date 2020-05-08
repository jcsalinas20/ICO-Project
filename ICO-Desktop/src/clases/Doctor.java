package clases;

import java.util.HashMap;

public class Doctor {

	private String nombre, apellidos;
	private int id_doctor, id_hospital, planta, numero_sala;
	private HashMap<String, Paciente> pacientes;
	
	public Doctor() {
		
	}

	public int getId_hospital() {
		return id_hospital;
	}

	public void setId_hospital(int id_hospital) {
		this.id_hospital = id_hospital;
	}

	public int getId_doctor() {
		return id_doctor;
	}

	public void setId_doctor(int id_doctor) {
		this.id_doctor = id_doctor;
	}

	public int getPlanta() {
		return planta;
	}

	public int getNumero_sala() {
		return numero_sala;
	}

	public void setPlanta(int planta) {
		this.planta = planta;
	}

	public void setNumero_sala(int numero_sala) {
		this.numero_sala = numero_sala;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public HashMap<String, Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(HashMap<String, Paciente> pacientes) {
		this.pacientes = pacientes;
	}
}
