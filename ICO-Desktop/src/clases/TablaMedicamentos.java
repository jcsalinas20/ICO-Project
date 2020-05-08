package clases;

import conexion.MongoActions;
import interfaz.PacientesController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import programa.Constantes;
import programa.Data;

public class TablaMedicamentos {

    private TablaMedicamentos data;
    private int row;
    private int id_paciente, id_medicamento;
    private String medicamento, horas;
    private CheckBox lunes, martes, miercoles, jueves, viernes, sabado, domingo;

    public TablaMedicamentos(int id_paciente, int id_medicamento, int row, String medicamento, String horas, CheckBox lunes, CheckBox martes, CheckBox miercoles, CheckBox jueves, CheckBox viernes, CheckBox sabado, CheckBox domingo) {
        this.id_paciente = id_paciente;
        this.id_medicamento = id_medicamento;
        this.row = row;
        this.medicamento = medicamento;
        this.horas = horas;
        this.lunes = lunes;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
        this.sabado = sabado;
        this.domingo = domingo;
        this.data = this;
        listenerCheckbox();
    }

    public TablaMedicamentos getData() {
        return data;
    }

    public int getRow() {
        return row;
    }

    public int getId_paciente() {
        return id_paciente;
    }

    public int getId_medicamento() {
        return id_medicamento;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public String getHoras() {
        return horas;
    }

    public CheckBox getLunes() {
        return lunes;
    }

    public CheckBox getMartes() {
        return martes;
    }

    public CheckBox getMiercoles() {
        return miercoles;
    }

    public CheckBox getJueves() {
        return jueves;
    }

    public CheckBox getViernes() {
        return viernes;
    }

    public CheckBox getSabado() {
        return sabado;
    }

    public CheckBox getDomingo() {
        return domingo;
    }

    private void listenerCheckbox() {
        //CONFIGURO EL EVENTO DE CLICK
        EventHandler<ActionEvent> onClick = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Object eventoClick = event.getSource();
                int nuevoValor = -1;
                boolean selected = false;
                int[] dias = Data.doctor.getPacientes().get(PacientesController.pacienteKey).getMedicinas().get(row).getDias();

                if(eventoClick == lunes) {
                    selected = lunes.isSelected();
                    nuevoValor = getNuevoValor(selected);
                    dias[0] = nuevoValor;
                } else if (eventoClick == martes) {
                    selected = martes.isSelected();
                    nuevoValor = getNuevoValor(selected);
                    dias[1] = nuevoValor;
                } else if (eventoClick == miercoles) {
                    selected = miercoles.isSelected();
                    nuevoValor = getNuevoValor(selected);
                    dias[2] = nuevoValor;
                } else if (eventoClick == jueves) {
                    selected = jueves.isSelected();
                    nuevoValor = getNuevoValor(selected);
                    dias[3] = nuevoValor;
                } else if (eventoClick == viernes) {
                    selected = viernes.isSelected();
                    nuevoValor = getNuevoValor(selected);
                    dias[4] = nuevoValor;
                } else if (eventoClick == sabado) {
                    selected = sabado.isSelected();
                    nuevoValor = getNuevoValor(selected);
                    dias[5] = nuevoValor;
                } else if (eventoClick == domingo) {
                    selected = domingo.isSelected();
                    nuevoValor = getNuevoValor(selected);
                    dias[6] = nuevoValor;
                }

                Data.doctor.getPacientes().get(PacientesController.pacienteKey).getMedicinas().get(row).setDias(dias);
                new MongoActions().editMedicamentoDay(data);
            }
        };

        //AÑADO EL LISTENER A TODOS LOS CHECKBOX
        lunes.setOnAction(onClick);
        martes.setOnAction(onClick);
        miercoles.setOnAction(onClick);
        jueves.setOnAction(onClick);
        viernes.setOnAction(onClick);
        sabado.setOnAction(onClick);
        domingo.setOnAction(onClick);
    }

    private int getNuevoValor(boolean selected) {
        int nuevoValor = -1;
        if(selected == true) {
            nuevoValor = 1;
        } else {
            nuevoValor = 0;
        }
        return nuevoValor;
    }

}
