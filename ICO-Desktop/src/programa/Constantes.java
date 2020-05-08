package programa;

public class Constantes {
	
	//DATOS DE CONEXION PARA MONGO DB
	public static String MONGO_CONNECTION_STRING = "mongodb+srv://admin:admin@clustersalinas-hvex2.mongodb.net/DB_ICO?retryWrites=true&w=majority";
	public static String MONGO_DATABASE_NAME = "DB_ICO";
	
	//COLECCIONES DE LA BASE DE DATOS
	public static String MONGO_DOCTOR_COLLECTION = "Doctores";
	public static String MONGO_HOSPITAL_COLLECTION = "Hospitales";
	public static String MONGO_CONSULTAS_COLLECTION = "Consultas";
	public static String MONGO_PACIENTES_COLLECTION = "Pacientes";
	public static String MONGO_MEDICAMENTOS_COLLECTION = "Medicamentos";
	public static String MONGO_HISTORIAL_COLLECTION = "Historial_Consultas";
	
	//CAMPOS DE LA COLLECTION DE DOCTORES
	public static String MONGO_DOCTORES_USERNAME = "username";
	public static String MONGO_DOCTORES_PASSWORD = "password";
	public static String MONGO_DOCTORES_ID = "id_doctor";
	public static String MONGO_DOCTORES_NAME = "nombre";
	public static String MONGO_DOCTORES_APELLIDOS = "apellidos";
	public static String MONGO_DOCTORES_PLANTA = "planta";
	public static String MONGO_DOCTORES_SALA = "sala";
	public static String MONGO_DOCTORES_HOSPITAL = "id_hospital";
	public static String MONGO_DOCTORES_HORARIOS = "horarios";
	public static String MONGO_DOCTORES_DIAS = "dias";

	//CAMPOS DE LA COLLECTION HOSPITAL
	public static String MONGO_HOSPITAL_ID = "id_hospital";
	public static String MONGO_HOSPITAL_DIRECCION = "direccion";

	//CAMPOS DE LA COLLECTION DE MEDICAMENTOS
	public static String MONGO_MEDICAMENTOS_ID = "id";
	public static String MONGO_MEDICAMENTOS_NOMBRE = "nombre";
	public static String MONGO_MEDICAMENTOS_IMAGEN = "imagen";

	//CAMPOS DE LA COLLECTION DE CONSULTAS
	public static String MONGO_CONSULTAS_DOCTORID = "id_doctor";
	public static String MONGO_CONSULTAS_PACIENTEID = "id_paciente";
	public static String MONGO_CONSULTAS_CONSULTASARRAY = "consultas";
	public static String MONGO_CONSULTAS_ID = "id_consulta";

	//CAMPOS DEL ARRAY DE CONSULTAS
	public static String MONGO_CONSULTA_ID = "num_consulta";
	public static String MONGO_CONSULTA_HORA = "hora";
	public static String MONGO_CONSULTA_DIA = "dia";
	public static String MONGO_CONSULTA_ASISTENCIA = "asistido";
	public static String MONGO_CONSULTA_NOTASPACIENTE = "notas";
	public static String MONGO_CONSULTA_NOTASDOCTOR = "notas_doc";

	//CAMPOS DEL ARRAY PACIENTES PERTENECIENTE AL ARRAY CONSULTAS EN LA COLLECTION DOCTORES
	public static String MONGO_PACIENTES_ID = "id_paciente";
	public static String MONGO_PACIENTES_NOMBRE = "nombre";
	public static String MONGO_PACIENTES_APELLIDOS = "apellidos";
	public static String MONGO_PACIENTES_DNI = "dni";
	public static String MONGO_PACIENTES_NACIMIENTO = "fecha_nacimiento";
	public static String MONGO_PACIENTES_FOTO = "foto";
	public static String MONGO_PACIENTES_MEDICAMENTOS = "medicamentos";
	public static String MONGO_PACIENTES_GENERO = "genero";

	//CAMPOS DEL ARRAY MEDICAMENTOS PERTENECIENTE A PACIENTES
	public static String MONGO_MEDICAMENTO_ID = "id";
	public static String MONGO_MEDICAMENTO_DIAS = "dias";
	public static String MONGO_MEDICAMENTO_HORAS = "hora";
	public static String MONGO_MEDICAMENTO_PASTILLAS = "pastillaTomada";
	
	//CAMPOS DEL OBJETO DIAS
	public static String MONGO_DIA_LUNES = "lunes";
	public static String MONGO_DIA_MARTES = "martes";
	public static String MONGO_DIA_MIERCOLES = "miercoles";
	public static String MONGO_DIA_JUEVES = "jueves";
	public static String MONGO_DIA_VIERNES = "viernes";
	public static String MONGO_DIA_SABADO = "sabado";
	public static String MONGO_DIA_DOMINGO = "domingo";

	//CAMPOS DE LA COLLECTION DE HISTORIAL
	public static String MONGO_HISTORIAL_ID = "id_consulta";
	public static String MONGO_HISTORIAL_PACIENTEID = "id_paciente";
	public static String MONGO_HISTORIAL_HOSPITAL = "id_direccion";
	public static String MONGO_HISTORIAL_DOCTOR = "doctor";
	public static String MONGO_HISTORIAL_PLANTA = "planta";
	public static String MONGO_HISTORIAL_SALA = "sala";
	public static String MONGO_HISTORIAL_CONSULTAS = "consultas";

	//CAMPOS DEL ARRAY DE CONSULTAS DE HISTORIAL
	public static String MONGO_HISTORIAL_FECHA = "dia";
	public static String MONGO_HISTORIAL_NOTA = "notas_doc";
	
	//TEXTOS DE ERROR EN LA BASE DE DATOS
	public static String MONGO_ERROR_CONNECTION = "No se ha podido establecer conexión con la base de datos.";

	//NOMBRE DE LAS ESCENAS
	public static String DASHBOARD_SCENE = "Dashboard";

	//TEXTOS DE ERROR AL MODIFICAR DATOS DEL PACIENTE
	public static String MEDICAMENTO_EDIT_ERROR = "El formato de horas no era el correcto. Introduce las horas con el siguiente formato 00:00 separadas por punto y coma.";
	public static String MEDICAMENTO_ADD_ERROR = "Los datos introducidos no son correctos.";
	public static String MEDICAMENTO_ADD_DUPLICATED = "Este medicamento ya está asignado al paciente.";

	//TEXTOS DE CONFIRMACION
	public static String ELIMINAR_MEDICAMENTO_CONFIRMATION = "¿Estás seguro de eliminar este medicamento?";
	public static String DASHBOARD_EDIT_CONFIRMATION = "¿Seguro que quieres atender a este paciente?";
	public static String ARCHIVAR_CONSULTA_CONFIRMATION = "¿Seguro que quieres marcar la consulta como realizada? La información se archivará.";

	//TEXTO DE ERROR AL INTENTAR ARCHIVAR LA CONSULTA DE UN PACIENTE UN DIA QUE NO TIENE VISITA
	public static String ARCHIVAR_CONSULTA_NOPERMITIDO = "No puedes archivar una consulta que todavía no se ha realizado.";
}
