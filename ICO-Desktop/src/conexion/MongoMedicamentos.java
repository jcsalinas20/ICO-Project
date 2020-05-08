package conexion;

import clases.TablaMedicamentos;
import com.mongodb.client.*;
import javafx.scene.control.CheckBox;
import org.bson.Document;
import org.bson.conversions.Bson;
import programa.Constantes;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class MongoMedicamentos {

    private MongoCollection<Document> pacientes;

    private Bson filter;

    private List<Document> listaMedicinas;

    public MongoMedicamentos(MongoCollection<Document> pacientes) {
        this.pacientes = pacientes;
    }

    public void addMedicamento(TablaMedicamentos nuevoMedicamento) {
        int id_paciente = nuevoMedicamento.getId_paciente();
        searchMedicinas(id_paciente); //BUSCO LAS MEDICINAS DE ESTE PACIENTE
        Document nuevaMedicina = crearNuevoDocumento(nuevoMedicamento); //CREO UN NUEVO DOCUMENTO
        listaMedicinas.add(nuevaMedicina); //AÑADO EL MEDICAMENTO
        updateArrayMedicamentos(); //ACTUALIZO LA BASE DE DATOS
    }

    private int getDayValue(CheckBox check) {
        if(check.isSelected()) {
            return 1;
        } else {
            return 0;
        }
    }

    private Document crearNuevoDocumento(TablaMedicamentos nuevoMedicamento) {
        //CREO EL NUEVO DOCUMENTO DE MEDICAMENTO
        Document dias = new Document();
        dias.append(Constantes.MONGO_DIA_LUNES, getDayValue(nuevoMedicamento.getLunes()));
        dias.append(Constantes.MONGO_DIA_MARTES, getDayValue(nuevoMedicamento.getMartes()));
        dias.append(Constantes.MONGO_DIA_MIERCOLES, getDayValue(nuevoMedicamento.getMiercoles()));
        dias.append(Constantes.MONGO_DIA_JUEVES, getDayValue(nuevoMedicamento.getJueves()));
        dias.append(Constantes.MONGO_DIA_VIERNES, getDayValue(nuevoMedicamento.getViernes()));
        dias.append(Constantes.MONGO_DIA_SABADO, getDayValue(nuevoMedicamento.getSabado()));
        dias.append(Constantes.MONGO_DIA_DOMINGO, getDayValue(nuevoMedicamento.getDomingo()));

        String[] horasArray = nuevoMedicamento.getHoras().replaceAll(" ", "").split(";");
        List<String> arrayHorasDoc = new ArrayList<>();
        List<Boolean> pastillaTomada = new ArrayList<>();
        for(int i = 0; i < horasArray.length; i++) {
            arrayHorasDoc.add(horasArray[i]);
            pastillaTomada.add(false);
        }

        Document nuevaMedicina = new Document();
        nuevaMedicina.append(Constantes.MONGO_MEDICAMENTO_DIAS, dias);
        nuevaMedicina.append(Constantes.MONGO_MEDICAMENTO_HORAS, arrayHorasDoc);
        nuevaMedicina.append(Constantes.MONGO_MEDICAMENTO_ID, nuevoMedicamento.getId_medicamento());
        nuevaMedicina.append(Constantes.MONGO_MEDICAMENTO_PASTILLAS, pastillaTomada);

        return nuevaMedicina;
    }

    public void eliminarMedicamento(int id_paciente, int id_medicamento) {
        searchMedicinas(id_paciente);
        Document medicina = getPositionMedicina(id_medicamento);
        listaMedicinas.remove(medicina); //BORRO EL MEDICAMENTO DE LA LISTA
        updateArrayMedicamentos();
    }

    public void editDayMedicamento(TablaMedicamentos data) {

        int id_paciente = data.getId_paciente();
        int id_medicamento = data.getId_medicamento();

        searchMedicinas(id_paciente); //BUSCO LAS MEDICINAS DE ESTE PACIENTE
        Document medicina = getPositionMedicina(id_medicamento); //BUSCO LA MEDICINA QUE VA A SER MODIFICADA
        Document nuevaMedicina = crearNuevoDocumento(data); //CREO EL DOCUMENTO CON LOS NUEVOS DATOS

        listaMedicinas.remove(medicina); //BORRO EL MEDICAMENTO DESACTUALIZADO
        listaMedicinas.add(nuevaMedicina); //AÑADO EL MEDICAMENTO ACTUALIZADO
        updateArrayMedicamentos(); //ACTUALIZO LA BASE DE DATOS
    }

    public void editarHorasMedicamento(int id_paciente, int id_medicamento, String horas) {
        searchMedicinas(id_paciente);
        Document medicina = getPositionMedicina(id_medicamento);

        //CONVIERTO LAS HORAS EN UN ARRAYLIST DE STRING
        String[] horasArray = horas.replaceAll(" ", "").split(";");
        List<String> arrayHorasDoc = new ArrayList<>();
        for(int i = 0; i < horasArray.length; i++) {
            arrayHorasDoc.add(horasArray[i]);
        }

        //CREO EL DOCUMENTO ACTUALIZADO
        String diasCampo = Constantes.MONGO_MEDICAMENTO_DIAS;

        Document dias = (Document) medicina.get(diasCampo);
        List<Boolean> pastillaTomada = new ArrayList<>();
        for(int i = 0; i < arrayHorasDoc.size(); i++) {
            pastillaTomada.add(false); //RESTAURO LOS BOOLEANOS DE PASTILLAS TOMADAS A FALSE
        }

        Document updateMedicina = new Document();
        updateMedicina.append(diasCampo, dias);
        updateMedicina.append(Constantes.MONGO_MEDICAMENTO_HORAS, arrayHorasDoc);
        updateMedicina.append(Constantes.MONGO_MEDICAMENTO_ID, id_medicamento);
        updateMedicina.append(Constantes.MONGO_MEDICAMENTO_PASTILLAS, pastillaTomada);

        //ACTUALIZO LA LISTA Y LA BASE DE DATOS
        listaMedicinas.remove(medicina);
        listaMedicinas.add(updateMedicina);
        updateArrayMedicamentos();
    }

    private void updateArrayMedicamentos() {
        Bson updateDocument = set(Constantes.MONGO_PACIENTES_MEDICAMENTOS, listaMedicinas);
        pacientes.updateOne(filter, updateDocument); //ACTUALIZO LOS CAMBIOS
    }

    //COJO LAS MEDICINAS DE UN PACIENTE
    private void searchMedicinas(int id_paciente) {
        filter = eq(Constantes.MONGO_PACIENTES_ID, id_paciente); //FILTRO PARA ENCONTRAR EL PACIENTE

        //BUSCO EL PACIENTE CON ESTE ID
        FindIterable<Document> pacienteIterable = pacientes.find(filter);
        Document paciente = pacienteIterable.first();
        listaMedicinas = (List<Document>) paciente.get(Constantes.MONGO_PACIENTES_MEDICAMENTOS);
    }

    //BUSCO LA MEDICINA EN EL ARRAYLIST
    private Document getPositionMedicina(int id_medicamento) {
        int id_medicina;
        Document medicinaPos = null;
        //RECORRO ENTRE SUS MEDICINAS
        for(Document medicina : listaMedicinas) {
            id_medicina = medicina.getInteger(Constantes.MONGO_MEDICAMENTO_ID);
            if (id_medicina == id_medicamento) {
                medicinaPos = medicina;
                break;
            }
        }
        return medicinaPos;
    }

}
