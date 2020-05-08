package programa;

import javafx.scene.control.Alert;

public class CrearAlertas {

	//ERROR AL ESTABLECER LA CONEXION CON MONGO DB
	public static void mongoConnectionError() {
		Alert alert = new Alert(Alert.AlertType.ERROR, Constantes.MONGO_ERROR_CONNECTION);
		alert.show();
	}

	//ERROR AL EDITAR EL MEDICAMENTO
	public static void medicamentoEditHoursError() {
		Alert alert = new Alert(Alert.AlertType.ERROR, Constantes.MEDICAMENTO_EDIT_ERROR);
		alert.show();
	}

	//ERROR AL AÑADIR UN NUEVO MEDICAMENTO
	public static void medicamentoAddError() {
		Alert alert = new Alert(Alert.AlertType.ERROR, Constantes.MEDICAMENTO_ADD_ERROR);
		alert.show();
	}

	//ERROR AL AÑADIR UN NUEVO MEDICAMENTO
	public static void medicamentoAddDuplicated() {
		Alert alert = new Alert(Alert.AlertType.ERROR, Constantes.MEDICAMENTO_ADD_DUPLICATED);
		alert.show();
	}

	//NO PUEDES ARCHIVAR UN PACIENTE UN DIA QUE NO TIENES CONSULTA CON HOY
	public static void archivarPacienteNoPermitido() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION, Constantes.ARCHIVAR_CONSULTA_NOPERMITIDO);
		alert.show();
	}

}
