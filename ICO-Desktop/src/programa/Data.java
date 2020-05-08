package programa;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import clases.Medicamentos;
import com.mongodb.client.MongoDatabase;

import clases.Doctor;
import clases.Paciente;
import conexion.MongoConnection;
import javafx.stage.Stage;

public class Data {
	
	public static MongoConnection mongoDB;

	public static Doctor doctor;
	public static boolean catalan;
	public static HashMap<Integer, Medicamentos> medicamentos;

	public static Stage interfaz;

	public static String getFecha() {
		Calendar calendario = new GregorianCalendar();
		int dia = calendario.get(Calendar.DATE);
		int mes = calendario.get(Calendar.MONTH) + 1; //LE SUMO UNO PORQUE EMPIEZA A CONTAR DESDE 0
		int any = calendario.get(Calendar.YEAR);
		String fechaActual;

		if(mes < 10) { //GUARDO EL VALOR DE LA FECHA ACTUAL
			fechaActual = dia + "-0" + mes + "-" + any;
		} else {
			fechaActual = dia + "-" + mes + "-" + any;
		}
		return fechaActual;
	}
}
