package conexion;

import clases.TablaConsultas;
import clases.TablaMedicamentos;
import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import cifrado.Md5;
import programa.Constantes;
import programa.Data;

import java.util.ArrayList;
import java.util.Calendar;

public class MongoActions {

	private MongoDatabase db;
	private MongoCollection<Document> doctores;
	private Document doctor;

	public MongoActions() {
		this.db = Data.mongoDB.getDB();
		doctores = db.getCollection(Constantes.MONGO_DOCTOR_COLLECTION);
	}

	// COMPRUEBA SI LOS DATOS DE LOGIN SON CORRECTOS
	public boolean checkLogin(String usuario, String password) {
		
		// BUSCO UN DOCUMENTO QUE CORRESPONDA CON EL NOMBRE DE USUARIO INTRODUCIDO
		Document buscar = new Document(Constantes.MONGO_DOCTORES_USERNAME, usuario);
		FindIterable<Document> fi = doctores.find(buscar);

		try {
			doctor = fi.first();
			String dbPassword = doctor.getString(Constantes.MONGO_DOCTORES_PASSWORD); //COJO LA PASSWORD EXISTENTE EN LA DB
			password = new Md5(password).encrypt(); //ENCRIPTO LA CONTRASE�A CON MD5

			//COMPRUEBO QUE LA PASSWORD COINCIDA
			if(password.equals(dbPassword)) {
				return checkHorarios();
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	private boolean checkHorarios() {
		//RECOJO LOS HORARIOS
		String horario = doctor.getString(Constantes.MONGO_DOCTORES_HORARIOS);
		String[] horarioSplit = horario.split("-");
		int[] horarios = new int[2];
		horarios[0] = Integer.parseInt(horarioSplit[0].replaceAll(":", ""));
		horarios[1] = Integer.parseInt(horarioSplit[1].replaceAll(":",""));

		//RECOJO LOS DIAS EN QUE TRABAJA
		ArrayList<Integer> diasArray = (ArrayList<Integer>) doctor.get(Constantes.MONGO_DOCTORES_DIAS);
		int[] dias = new int[7];
		dias[0] = diasArray.get(0);
		dias[1] = diasArray.get(1);
		dias[2] = diasArray.get(2);
		dias[3] = diasArray.get(3);
		dias[4] = diasArray.get(4);
		dias[5] = diasArray.get(5);
		dias[6] = diasArray.get(6);

		//COMPRUEBO SI LE TOCA TRABAJAR
		Calendar now = Calendar.getInstance();
		int diaSemana = now.get(Calendar.DAY_OF_WEEK) - 1;
		if(dias[diaSemana] == 0) {
			return false;
		}

		//COMPRUEBO SI ESTA EN SUS HORARIOS
		int hora = now.get(Calendar.HOUR_OF_DAY);
		int minutos = now.get(Calendar.MINUTE);
		String horaActual;
		if(minutos < 10) {
			horaActual = hora + "0" + minutos;
		} else {
			horaActual = hora + "" + minutos;
		}
		int horaNumerica = Integer.parseInt(horaActual);
		if(horaNumerica >= horarios[0] && horaNumerica < horarios[1]) {
			return true;
		} else {
			return false;
		}
	}

	public void loadDoctor() {
		//CARGO LAS COLECCIONES NECESARIAS
		MongoCollection<Document> hospital = db.getCollection(Constantes.MONGO_HOSPITAL_COLLECTION);
		MongoCollection<Document> consultas = db.getCollection(Constantes.MONGO_CONSULTAS_COLLECTION);
		MongoCollection<Document> pacientes = db.getCollection(Constantes.MONGO_PACIENTES_COLLECTION);
		MongoCollection<Document> medicamentos = db.getCollection(Constantes.MONGO_MEDICAMENTOS_COLLECTION);
		MongoCollection<Document> historial = db.getCollection(Constantes.MONGO_HISTORIAL_COLLECTION);

		//LLAMO A LAS FUNCIONES PARA CARGAR DATOS
		MongoLoadData loadFunctions = new MongoLoadData();
		loadFunctions.loadDoctor(doctor, hospital);
		loadFunctions.loadConsultas(consultas, pacientes, historial);
		loadFunctions.loadMedicamentos(medicamentos);
	}

	public void removeMedicamento(int id_paciente, int id_medicamento) {
		new MongoMedicamentos(getcollectionPacientes()).eliminarMedicamento(id_paciente, id_medicamento);
	}

	public void editHorasMedicamento(int id_paciente, int id_medicamento, String horas) {
		new MongoMedicamentos(getcollectionPacientes()).editarHorasMedicamento(id_paciente, id_medicamento, horas);
	}

	public void addMedicamento(TablaMedicamentos nuevoMedicamento) {
		new MongoMedicamentos(getcollectionPacientes()).addMedicamento(nuevoMedicamento);
	}

	public void editMedicamentoDay(TablaMedicamentos data) {
		new MongoMedicamentos(getcollectionPacientes()).editDayMedicamento(data);
	}

	private MongoCollection<Document> getcollectionPacientes() {
		MongoCollection<Document> pacientes = db.getCollection(Constantes.MONGO_PACIENTES_COLLECTION);
		return pacientes;
	}

	private MongoCollection<Document> getCollectionConsultas() {
		MongoCollection<Document> consultas = db.getCollection(Constantes.MONGO_CONSULTAS_COLLECTION);
		return consultas;
	}

	public void changeAsistido(TablaConsultas data, int id_paciente) {
		MongoCollection<Document> historial = db.getCollection(Constantes.MONGO_HISTORIAL_COLLECTION);
		new MongoConsultas(getCollectionConsultas(), data).changeAsistido(historial, id_paciente);
	}

	public void changeNotasPaciente(TablaConsultas data) {
		new MongoConsultas(getCollectionConsultas(), data).createDocument();
	}

	public void changeNotasDoctor(TablaConsultas data) {
		new MongoConsultas(getCollectionConsultas(), data).createDocument();
	}
}
