package clases;

import conexion.MongoActions;
import interfaz.PacientesController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogEvent;
import programa.Constantes;
import programa.CrearAlertas;
import programa.Data;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TablaConsultas {

    private PacientesController controller;
    private TablaConsultas tabla;
    private int row, id_consulta, num_consulta;
    private String hora, dia, notasPaciente, notasDoctor;
    private CheckBox asistido;

    public TablaConsultas(PacientesController controller, int id_consulta, int num_consulta, int row, String hora, String dia, String notasPaciente, String notasDoctor, CheckBox asistido) {
        this.controller = controller;
        this.id_consulta = id_consulta;
        this.num_consulta = num_consulta;
        this.row = row;
        this.hora = hora;
        this.dia = dia;
        this.notasPaciente = notasPaciente;
        this.notasDoctor = notasDoctor;
        this.asistido = asistido;
        this.tabla = this;
        checkBoxListener();
    }

    public void setNotasPaciente(String notasPaciente) {
        this.notasPaciente = notasPaciente;
    }

    public void setNotasDoctor(String notasDoctor) {
        this.notasDoctor = notasDoctor;
    }

    public int getId_consulta() {
        return id_consulta;
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

    public CheckBox getAsistido() {
        return asistido;
    }

    //CONFIGURO EL LISTENER DEL CHECKBOX
    private void checkBoxListener() {
        asistido.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(Data.getFecha().equals(dia)) { //COMPRUEBO SI ES UN DIA EN QUE EL DOCTOR TENGA CONSULTA CON ESTE PACIENTE
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, Constantes.ARCHIVAR_CONSULTA_CONFIRMATION);
                    a.show();
                    a.setOnHidden(new EventHandler<DialogEvent>() {
                        @Override
                        public void handle(DialogEvent event) {
                            if (a.getResult() == ButtonType.OK) { //SI EL MEDICO CONFIRMA PROCEDO A BORRAR LA CONSULTA
                                controller.removeRowConsultas(row);
                                Data.doctor.getPacientes().get(PacientesController.pacienteKey).getConsultas().remove(row);
                                new MongoActions().changeAsistido(tabla, controller.paciente.getId_paciente());
                            } else {
                                asistido.setSelected(false);
                            }
                        }
                    });
                } else {
                    CrearAlertas.archivarPacienteNoPermitido();
                }
            }
        });
    }
}
