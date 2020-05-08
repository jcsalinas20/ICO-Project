package conexion;

import clases.Doctor;
import clases.TablaConsultas;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import programa.Constantes;
import programa.Data;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class MongoConsultas {

    private MongoCollection<Document> consultas;

    private TablaConsultas data;

    private Bson filter;

    private List<Document> listaConsultas;

    private Document oldConsulta;

    public MongoConsultas(MongoCollection<Document> consultas, TablaConsultas data) {
        this.consultas = consultas;
        this.data = data;
        searchConsultas();
        searchOldConsulta();
    }

    public void changeAsistido(MongoCollection<Document> historial, int id_paciente) {
       searchConsultas();
       searchOldConsulta();

       Doctor doc = Data.doctor;

       //BUSCO SI YA EXISTE UN HISTORIAL PARA ESTE PACIENTE
       Bson filtro = eq(Constantes.MONGO_HISTORIAL_PACIENTEID, id_paciente); //FILTRO PARA ENCONTRAR EL PACIENTE
       FindIterable<Document> historialIterable = consultas.find(filtro);
       int historialPacientes = 0;
       if(!historialIterable.cursor().hasNext()) { //SI NO TIENE HISTORIAL
           anadirConsultasHistorial(doc, id_paciente, historial); //LE CREO UNO NUEVO
       } else { //SI YA TENIA UNO AÑADO ESTA CONSULTA A SU HISTORIAL
           String mongo_historial_consultas = Constantes.MONGO_HISTORIAL_CONSULTAS;
           List<Document> historialConsultas = (List<Document>) historialIterable.first().get(mongo_historial_consultas);
           String doctorName = doc.getNombre() + " " + doc.getApellidos();
           Document consultaHistorial = oldConsulta;
           consultaHistorial.append(Constantes.MONGO_HISTORIAL_DOCTOR, doctorName);
           consultaHistorial.append(Constantes.MONGO_HISTORIAL_PLANTA, doc.getPlanta());
           consultaHistorial.append(Constantes.MONGO_HISTORIAL_SALA, doc.getNumero_sala());
           historialConsultas.add(consultaHistorial); //AÑADO UNA CONSULTA AL HISTORIAL
           Bson updateDocument = set(mongo_historial_consultas, historialConsultas);
           historial.updateOne(filtro, updateDocument); //ACTUALIZO LOS CAMBIOS
       }

       updateArrayConsultas(); //ACTUALIZO EL ARRAY DE CONSULTAS
    }

    private void anadirConsultasHistorial(Doctor doc, int id_paciente, MongoCollection<Document> historial) {
        //BUSCO EL ID DEL NUEVO DOCUMENTO PARA HISTORIAL
        long totalDocumentos = historial.countDocuments();
        int id_historial = (int) (1 + totalDocumentos);

        //CREO EL DOCUMENTO PARA AÑADIR AL HISTORIAL DE CONSULTAS
        Document historial_consultas = new Document();
        historial_consultas.append(Constantes.MONGO_HISTORIAL_ID, id_historial);
        historial_consultas.append(Constantes.MONGO_HISTORIAL_PACIENTEID, id_paciente);
        historial_consultas.append(Constantes.MONGO_HISTORIAL_HOSPITAL, doc.getId_hospital());
        String doctorName = doc.getNombre() + " " + doc.getApellidos();
        List<Document> consultasArchivadas = new ArrayList<>();
        Document consultaHistorial = oldConsulta;
        consultaHistorial.append(Constantes.MONGO_HISTORIAL_DOCTOR, doctorName);
        consultaHistorial.append(Constantes.MONGO_HISTORIAL_PLANTA, doc.getPlanta());
        consultaHistorial.append(Constantes.MONGO_HISTORIAL_SALA, doc.getNumero_sala());
        consultasArchivadas.add(consultaHistorial);
        historial_consultas.append(Constantes.MONGO_HISTORIAL_CONSULTAS, consultasArchivadas);
        historial.insertOne(historial_consultas); //AÑADO EL DOCUMENTO AL HISTORIAL
    }

    public void createDocument() {
        Document nuevaConsulta = new Document();
        nuevaConsulta.append(Constantes.MONGO_CONSULTA_HORA, data.getHora());
        nuevaConsulta.append(Constantes.MONGO_CONSULTA_DIA, data.getDia());
        nuevaConsulta.append(Constantes.MONGO_CONSULTA_ASISTENCIA, data.getAsistido().isSelected());
        nuevaConsulta.append(Constantes.MONGO_CONSULTA_NOTASPACIENTE, data.getNotasPaciente());
        nuevaConsulta.append(Constantes.MONGO_CONSULTA_NOTASDOCTOR, data.getNotasDoctor());
        nuevaConsulta.append(Constantes.MONGO_CONSULTA_ID, data.getNum_consulta());
        listaConsultas.remove(oldConsulta);
        listaConsultas.add(nuevaConsulta);
        updateArrayConsultas();
    }

    private void updateArrayConsultas() {
        listaConsultas.remove(oldConsulta); //BORRO LOS DATOS DESACTUALIZADOS
        Bson updateDocument = set(Constantes.MONGO_CONSULTAS_CONSULTASARRAY, listaConsultas);
        consultas.updateOne(filter, updateDocument); //ACTUALIZO LOS CAMBIOS
    }

    private void searchConsultas() {
        filter = eq(Constantes.MONGO_CONSULTAS_ID, data.getId_consulta()); //FILTRO PARA ENCONTRAR EL PACIENTE

        //BUSCO LA CONSULTA CON ESTE ID
        FindIterable<Document> consultasIterable = consultas.find(filter);
        Document consulta = consultasIterable.first();
        listaConsultas = (List<Document>) consulta.get(Constantes.MONGO_CONSULTAS_CONSULTASARRAY);
    }

    private void searchOldConsulta() {
        int num_consulta = data.getNum_consulta();
        int num_consulta_pos;

        //RECORRO LAS CONSULTAS DEL PACIENTE
        for(Document consulta : listaConsultas) {
            num_consulta_pos = consulta.getInteger(Constantes.MONGO_CONSULTA_ID);
            if (num_consulta == num_consulta_pos) { //BUSCO LA CONSULTA QUE EL MEDICO HA ACTUALIZADO
                oldConsulta = consulta;
                break;
            }
        }
    }

}
