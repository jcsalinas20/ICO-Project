package conexion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import clases.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import programa.Constantes;
import programa.Data;

public class MongoLoadData {

	private Doctor doc;
		
		@SuppressWarnings("unchecked")
		public void loadDoctor(Document doctor, MongoCollection<Document> hospitales) {
			doc = new Doctor();

			//RECOJO LA ID DEL DOCTOR
			int id_doctor = doctor.getInteger(Constantes.MONGO_DOCTORES_ID);
			doc.setId_doctor(id_doctor);

			//RECOJO EL NOMBRE DEL DOCTOR
			String nombreDoctor = doctor.getString(Constantes.MONGO_DOCTORES_NAME);
			doc.setNombre(nombreDoctor);

			//RECOJO LOS APELLIDOS DEL DOCTOR
			String apellidosDoctor = doctor.getString(Constantes.MONGO_DOCTORES_APELLIDOS);
			doc.setApellidos(apellidosDoctor);

			//RECOJO LA PLANTA DEL DOCTOR
			int planta = doctor.getInteger(Constantes.MONGO_DOCTORES_PLANTA);
			doc.setPlanta(planta);

			//RECOJO LA SALA DEL DOCTOR
			int numero_sala = doctor.getInteger(Constantes.MONGO_DOCTORES_SALA);
			doc.setNumero_sala(numero_sala);

			//RECOJO LA ID DEL HOSPITAL
			int id_hospital = doctor.getInteger(Constantes.MONGO_DOCTORES_HOSPITAL);
			doc.setId_hospital(id_hospital);
		}

		public void loadConsultas(MongoCollection<Document> consultasCollection, MongoCollection<Document> pacientesCollection, MongoCollection<Document> historialCollection) {

			HashMap<String, Paciente> pacientesDoctor = new HashMap<>();

			//BUSCO LAS CONSULTAS ASIGNADAS AL DOCTOR
			Document buscarConsultasDoctor = new Document(Constantes.MONGO_CONSULTAS_DOCTORID, doc.getId_doctor());
			FindIterable<Document> consultasDoctorIterable = consultasCollection.find(buscarConsultasDoctor);

			//RECOJO LAS CONSULTAS ASIGNADAS AL DOCTOR Y LOS PACIENTES
				//VARIABLES REQUERIDAS PARA EL BUCLE
			Document buscarPacienteConsulta, diasDocument, buscarHistorial;
			FindIterable<Document> pacienteConsultaIterable, medicamentoDocument;
			Paciente pacientePos;
			MedicamentoPaciente medicinaPos;
			int id_paciente, id_medicamento, id_consulta_paciente, id_consulta;
			int[] diasMedicamento;
			ArrayList<String> horasMedicamento;
			List<String> horaPosition;
			String hora, dia, notasPaciente, notasDoctor, nombre, apellidos, dni, foto, nacimiento, genero, hashKey, diaHistorial, notasDoctorHistorial;
			Consulta consultaPos;
			ArrayList<Consulta> consultas;
			ArrayList<MedicamentoPaciente> medicinas;
			ArrayList<HistorialPaciente> historial;
			HistorialPaciente historialPaciente;

			List<Document> medicinasDocumentList, consultasDocumentList, historialList;
				//ITERO LOS RESULTADOS DE LA BUSQUEDA DE CONSULTAS
			for(Document consulta : consultasDoctorIterable) {

				id_consulta_paciente = consulta.getInteger(Constantes.MONGO_CONSULTAS_ID); //ID DE LA CONSULTA DE ESTE MEDICO CON ESTE PACIENTE

					//BUSCO EL PACIENTE
				id_paciente = consulta.getInteger(Constantes.MONGO_CONSULTAS_PACIENTEID);
				buscarPacienteConsulta = new Document(Constantes.MONGO_PACIENTES_ID, id_paciente);
				pacienteConsultaIterable = pacientesCollection.find(buscarPacienteConsulta);
				buscarPacienteConsulta = pacienteConsultaIterable.first();

					//RECOJO LOS DATOS DEL PACIENTE
				nombre = buscarPacienteConsulta.getString(Constantes.MONGO_PACIENTES_NOMBRE);
				apellidos = buscarPacienteConsulta.getString(Constantes.MONGO_PACIENTES_APELLIDOS);
				dni = buscarPacienteConsulta.getString(Constantes.MONGO_PACIENTES_DNI);
				foto = buscarPacienteConsulta.getString(Constantes.MONGO_PACIENTES_FOTO);
				nacimiento = buscarPacienteConsulta.getString(Constantes.MONGO_PACIENTES_NACIMIENTO);
				genero = buscarPacienteConsulta.getString(Constantes.MONGO_PACIENTES_GENERO);

					//BUSCO SU HISTORIAL DE MEDICINAS
				historial = new ArrayList<>();
				buscarHistorial = new Document(Constantes.MONGO_HISTORIAL_PACIENTEID, id_paciente);
				medicamentoDocument = historialCollection.find(buscarHistorial);
				if(medicamentoDocument.cursor().hasNext()) {
					historialList = (List<Document>) medicamentoDocument.first().get(Constantes.MONGO_HISTORIAL_CONSULTAS);
					for (Document posHistorial : historialList) {
						diaHistorial = posHistorial.getString(Constantes.MONGO_HISTORIAL_FECHA);
						notasDoctorHistorial = posHistorial.getString(Constantes.MONGO_HISTORIAL_NOTA);
						historialPaciente = new HistorialPaciente(diaHistorial, notasDoctorHistorial);
						historial.add(historialPaciente);
					}
				}

						//RECOJO SUS MEDICINAS
				medicinas = new ArrayList<>();
				medicinasDocumentList = (List<Document>) buscarPacienteConsulta.get(Constantes.MONGO_PACIENTES_MEDICAMENTOS);
				for(Document medicinaPosDoc : medicinasDocumentList) {
					id_medicamento = medicinaPosDoc.getInteger(Constantes.MONGO_MEDICAMENTO_ID);
					diasDocument = (Document) medicinaPosDoc.get(Constantes.MONGO_MEDICAMENTO_DIAS);
					diasMedicamento = new int[7];
					diasMedicamento[0] = diasDocument.getInteger(Constantes.MONGO_DIA_LUNES);
					diasMedicamento[1] = diasDocument.getInteger(Constantes.MONGO_DIA_MARTES);
					diasMedicamento[2] = diasDocument.getInteger(Constantes.MONGO_DIA_MIERCOLES);
					diasMedicamento[3] = diasDocument.getInteger(Constantes.MONGO_DIA_JUEVES);
					diasMedicamento[4] = diasDocument.getInteger(Constantes.MONGO_DIA_VIERNES);
					diasMedicamento[5] = diasDocument.getInteger(Constantes.MONGO_DIA_SABADO);
					diasMedicamento[6] = diasDocument.getInteger(Constantes.MONGO_DIA_DOMINGO);

					//CARGO LAS HORAS A LAS QUE EL PACIENTE DEBE TOMAR LOS MEDICAMENTOS
					horasMedicamento = new ArrayList<>();
					horaPosition = (List<String>) medicinaPosDoc.get(Constantes.MONGO_MEDICAMENTO_HORAS);
					for(String horaPos: horaPosition) {
						horasMedicamento.add(horaPos);
					}
					medicinaPos = new MedicamentoPaciente(id_medicamento, diasMedicamento, horasMedicamento);
					medicinas.add(medicinaPos);
				}

				//RECOJO LOS DATOS DE LAS CONSULTAS QUE TIENE ESTE PACIENTE
				consultas = new ArrayList<>();
				consultasDocumentList = (List<Document>) consulta.get(Constantes.MONGO_CONSULTAS_CONSULTASARRAY);
				for(Document consultaDocument : consultasDocumentList) {
					hora = consultaDocument.getString(Constantes.MONGO_CONSULTA_HORA);
					dia = consultaDocument.getString(Constantes.MONGO_CONSULTA_DIA);
					notasDoctor = consultaDocument.getString(Constantes.MONGO_CONSULTA_NOTASDOCTOR);
					notasPaciente = consultaDocument.getString(Constantes.MONGO_CONSULTA_NOTASPACIENTE);
					id_consulta = consultaDocument.getInteger(Constantes.MONGO_CONSULTA_ID);
					consultaPos = new Consulta(id_consulta, hora, dia, notasPaciente, notasDoctor, false);
					consultas.add(consultaPos);
				}

				pacientePos = new Paciente(id_paciente, id_consulta_paciente, historial, nombre, apellidos, dni, foto, nacimiento, consultas, medicinas, genero);
				hashKey = (nombre + apellidos).replaceAll(" ", "");
				pacientesDoctor.put(hashKey, pacientePos);
			}

			doc.setPacientes(pacientesDoctor);
			Data.doctor = doc;
		}

		public void loadMedicamentos(MongoCollection<Document> medicamentos) {

			HashMap<Integer, Medicamentos> medicamentosHash = new HashMap<>();
			Medicamentos medicamentoPos;
			int idMedicamento;
			String nombre, imagen;

			//RECORRO TODA LA COLLECTION RECOGIENDO LOS MEDICAMENTOS
			FindIterable<Document> medicamentoDocument = medicamentos.find();
			for(Document medicamento : medicamentoDocument) {
				idMedicamento = medicamento.getInteger(Constantes.MONGO_MEDICAMENTOS_ID);
				nombre = medicamento.getString(Constantes.MONGO_MEDICAMENTOS_NOMBRE);
				imagen = medicamento.getString(Constantes.MONGO_MEDICAMENTOS_IMAGEN);
				medicamentoPos = new Medicamentos(nombre, imagen);
				medicamentosHash.put(idMedicamento, medicamentoPos);
			}

			Data.medicamentos = medicamentosHash;
		}
}
