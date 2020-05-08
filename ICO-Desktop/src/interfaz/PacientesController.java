package interfaz;

import clases.*;
import conexion.MongoActions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.util.Callback;
import programa.Constantes;
import programa.CrearAlertas;
import programa.Data;

import java.net.URL;
import java.util.*;

public class PacientesController implements Initializable {

    @FXML
    private TableView<TablaConsultas> consultasTable;

    @FXML
    private TableColumn<TablaConsultas, String> columnDia;

    @FXML
    private TableColumn<TablaConsultas, String> columnHora;

    @FXML
    private TableColumn<TablaConsultas, String> columnNotasPaciente;

    @FXML
    private TableColumn<TablaConsultas, String> columnNotasDoctor;

    @FXML
    private TableColumn<TablaConsultas, CheckBox> columnAsistencia;

    @FXML
    private TableView<TablaMedicamentos> medicamentosTable;

    @FXML
    private TableColumn<TablaMedicamentos, String> columnMedicamento;

    @FXML
    private TableColumn<TablaMedicamentos, String> columnHoras;

    @FXML
    private TableColumn<TablaMedicamentos, CheckBox> columnLunes;

    @FXML
    private TableColumn<TablaMedicamentos, CheckBox> columnMartes;

    @FXML
    private TableColumn<TablaMedicamentos, CheckBox> columnMiercoles;

    @FXML
    private TableColumn<TablaMedicamentos, CheckBox> columnJueves;

    @FXML
    private TableColumn<TablaMedicamentos, CheckBox> columnViernes;

    @FXML
    private TableColumn<TablaMedicamentos, CheckBox> columnSabado;

    @FXML
    private TableColumn<TablaMedicamentos, CheckBox> columnDomingo;

    @FXML
    private ListView<AnchorPane> listaMedicamentos;

    @FXML
    private CheckBox lunes;

    @FXML
    private CheckBox martes;

    @FXML
    private CheckBox miercoles;

    @FXML
    private CheckBox jueves;

    @FXML
    private CheckBox viernes;

    @FXML
    private CheckBox sabado;

    @FXML
    private CheckBox domingo;

    @FXML
    private TextField horasMedicamento;

    @FXML
    private ListView listaNotas;

    @FXML
    private TextField buscarNotas;

    private int medicamentoPosList;

    private ArrayList<Integer> horasMedicamentosArray;

    private ArrayList<HistorialPaciente> historialPacienteLista;

    private ObservableList<AnchorPane> lista;

    private ArrayList<Integer> medicamentoOrder;

    public Paciente paciente;

    public static String pacienteKey;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        medicamentoPosList = -1;
        paciente = SearchController.pacienteSeleccionado;
        pacienteKey = (paciente.getNombre() + paciente.getApellidos()).replaceAll(" ", "");
        loadConsultas();
        loadListaNotas();
        loadMedicamentos();
        loadMedicamentosList();
    }

    public void removeRowConsultas(int row) {
        consultasTable.getItems().remove(row);
    }

    private void loadConsultas() {

        ArrayList<TablaConsultas> tablaConsultas = new ArrayList<>();
        TablaConsultas tablaConsultaPos;
        String hora, dia, notasPaciente, notasDoctor;
        CheckBox checkBox;
        int id_consulta, num_consulta, row = 0;
        id_consulta = paciente.getId_consultas();
        //RECORRO TODAS LAS CONSULTAS DEL PACIENTE
        for(Consulta consultaPos : paciente.getConsultas()) {
            hora = consultaPos.getHora();
            dia = consultaPos.getDia();
            notasPaciente = consultaPos.getNotasPaciente();
            notasDoctor = consultaPos.getNotasDoctor();
            checkBox = new CheckBox();
            if(consultaPos.isAsistido()) {
                checkBox.setSelected(true);
            } else {
                checkBox.setSelected(false);
            }
            num_consulta = consultaPos.getNum_consulta();
            tablaConsultaPos = new TablaConsultas(this, id_consulta, num_consulta, row, hora, dia, notasPaciente, notasDoctor, checkBox);
            tablaConsultas.add(tablaConsultaPos);
            row++;
        }

        //CONFIGURO LA TABLA
        ObservableList<TablaConsultas> datosTabla = FXCollections.observableList(tablaConsultas);
        consultasTable.setItems(datosTabla);
        columnDia.setCellValueFactory(new PropertyValueFactory<>("dia"));
        columnHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        columnNotasPaciente.setCellValueFactory(new PropertyValueFactory<>("notasPaciente"));
        columnNotasPaciente.setCellFactory(TextFieldTableCell.forTableColumn());
        columnNotasPaciente.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TablaConsultas, String>>() {
           @Override
           public void handle(TableColumn.CellEditEvent<TablaConsultas, String> event) {
               TablaConsultas tabla = event.getRowValue();
               String nuevoValor = event.getNewValue();
               int position = event.getTablePosition().getRow();

               tabla.setNotasPaciente(nuevoValor);
               paciente.getConsultas().get(position).setNotasPaciente(nuevoValor);
               Data.doctor.getPacientes().get(pacienteKey).getConsultas().get(position).setNotasPaciente(nuevoValor);
               new MongoActions().changeNotasPaciente(tabla);
               consultasTable.refresh();
           }
        });
        columnNotasDoctor.setCellValueFactory(new PropertyValueFactory<>("notasDoctor"));
        columnNotasDoctor.setCellFactory(TextFieldTableCell.forTableColumn());
        columnNotasDoctor.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TablaConsultas, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TablaConsultas, String> event) {
                TablaConsultas tabla = event.getRowValue();
                String nuevoValor = event.getNewValue();
                int position = event.getTablePosition().getRow();

                tabla.setNotasDoctor(nuevoValor);
                paciente.getConsultas().get(position).setNotasDoctor(nuevoValor);
                Data.doctor.getPacientes().get(pacienteKey).getConsultas().get(position).setNotasDoctor(nuevoValor);
                new MongoActions().changeNotasDoctor(tabla);
                consultasTable.refresh();
            }
        });
        columnAsistencia.setCellValueFactory(new PropertyValueFactory<>("asistido"));
    }

    private void loadMedicamentos() {

        ArrayList<TablaMedicamentos> medicamentos = new ArrayList<>();
        TablaMedicamentos tablaMedicamentoPos;

        String medicamento, horas;
        CheckBox lunes, martes, miercoles, jueves, viernes, sabado, domingo;
        int[] dias;
        int id_paciente = paciente.getId_paciente();
        int id_medicamento;
        int row = 0;
        for(MedicamentoPaciente medicamentoPos : paciente.getMedicinas()) {
            id_medicamento = medicamentoPos.getIdMedicamento();

            medicamento = Data.medicamentos.get(id_medicamento).getNombre(); //COJO EL NOMBRE DEL MEDICAMENTO

            //RECOJO LAS HORAS A LAS QUE EL PACIENTE DEBE TOMAR EL MEDICAMENTO
            horas = "";
            for(String horaPos : medicamentoPos.getHoras()) {
                horas = horas + horaPos + "; ";
            }
            horas = horas.substring(0, horas.length()-2); //ELIMINO LA ULTIMA COMA

            //RECOJO LOS DIAS PARA TOMAR LA MEDICACION
            dias = medicamentoPos.getDias();
            lunes = checkDiaSeleccionado(dias[0]);
            martes = checkDiaSeleccionado(dias[1]);
            miercoles = checkDiaSeleccionado(dias[2]);
            jueves = checkDiaSeleccionado(dias[3]);
            viernes = checkDiaSeleccionado(dias[4]);
            sabado = checkDiaSeleccionado(dias[5]);
            domingo = checkDiaSeleccionado(dias[6]);

            tablaMedicamentoPos = new TablaMedicamentos(id_paciente, id_medicamento, row, medicamento, horas, lunes, martes, miercoles, jueves, viernes, sabado, domingo);
            medicamentos.add(tablaMedicamentoPos);
            row++;
            }

        //CONFIGURO LA TABLA
        medicamentosTable.setEditable(true);
        medicamentosTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ObservableList<TablaMedicamentos> datosTabla = FXCollections.observableList(medicamentos);
        medicamentosTable.setItems(datosTabla);
        columnMedicamento.setCellValueFactory(new PropertyValueFactory<>("medicamento"));
        columnHoras.setCellValueFactory(new PropertyValueFactory<>("horas"));
        //HAGO EDITABLE LA COLUMNA HORAS
        columnHoras.setCellFactory(TextFieldTableCell.forTableColumn());
        columnHoras.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TablaMedicamentos, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TablaMedicamentos, String> event) {
                String valor = event.getNewValue();
                int position = event.getTablePosition().getRow();
                editMedicamentoHoras(valor, position);
            }
        });
        columnLunes.setCellValueFactory(new PropertyValueFactory<>("lunes"));
        columnMartes.setCellValueFactory(new PropertyValueFactory<>("martes"));
        columnMiercoles.setCellValueFactory(new PropertyValueFactory<>("miercoles"));
        columnJueves.setCellValueFactory(new PropertyValueFactory<>("jueves"));
        columnViernes.setCellValueFactory(new PropertyValueFactory<>("viernes"));
        columnSabado.setCellValueFactory(new PropertyValueFactory<>("sabado"));
        columnDomingo.setCellValueFactory(new PropertyValueFactory<>("domingo"));
        }

    private CheckBox checkDiaSeleccionado(int valor) {
        CheckBox checkBox = new CheckBox();
        if(valor == 1) {
            checkBox.setSelected(true);
        }
        return checkBox;
    }

    public void deleteRowMedicamento() {
        //PIDO AL MEDICO CONFIRMACION
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, Constantes.ELIMINAR_MEDICAMENTO_CONFIRMATION);
        a.show();
        a.setOnHidden(new EventHandler<DialogEvent>() {
            @Override
            public void handle(DialogEvent event) {
                if (a.getResult() == ButtonType.OK) { //SI EL MEDICO CONFIRMA PROCEDO A BORRAR EL MEDICAMENTO
                    ObservableList<Integer> seleccionados = medicamentosTable.getSelectionModel().getSelectedIndices(); //RECOJO LOS MEDICAMENTOS SELECCIONADOS
                    ArrayList<MedicamentoPaciente> medicinas = paciente.getMedicinas();
                    MongoActions borrarMedicamento = new MongoActions();
                    int id_paciente = paciente.getId_paciente();
                    int id_medicamento;
                    for(int pos : seleccionados) { //RECORRO LOS MEDICAMENTOS SELECCIONADOS
                        medicamentosTable.getItems().remove(pos); //ELIMINO LOS ELEMENTOS DE LA TABLA
                        id_medicamento = medicinas.get(pos).getIdMedicamento(); //RECOJO SU ID
                        Data.doctor.getPacientes().get(pacienteKey).getMedicinas().remove(pos);
                        borrarMedicamento.removeMedicamento(id_paciente, id_medicamento); //LLAMO AL METODO PARA BORRAR EL MEDICAMENTO
                    }
                }
            }
        });
    }

    public void editMedicamentoHoras(String valor, int row) {
        if(checkHourFormat(valor)) { //SI LA CORRECION ES CORRECTA MODIFICO LOS DATOS EN LA DB
            ObservableList<TablaMedicamentos> items = medicamentosTable.getItems();
            valor = ordenarHoras(horasMedicamentosArray);
            items.get(row).setHoras(valor);
            int id_medicamento = paciente.getMedicinas().get(row).getIdMedicamento();
            int id_paciente = paciente.getId_paciente();
            Data.doctor.getPacientes().get(pacienteKey).getMedicinas().get(row).setHoras(formatHoras(valor));
            new MongoActions().editHorasMedicamento(id_paciente, id_medicamento, valor);
        } else {
            new CrearAlertas().medicamentoEditHoursError(); // SI NO ES CORRECTA INFORMO AL DOCTOR SI HA EDITADO MAL LAS HORAS
        }
        medicamentosTable.refresh();
    }

    private String ordenarHoras(ArrayList<Integer> horasNum) {
        Collections.sort(horasNum);
        String valor = "";
        StringBuilder builder;
        for(Integer pos : horasNum) {
            builder = new StringBuilder(pos.toString());
            builder.insert(2, ":");
            valor = valor + builder.toString() + "; ";
        }
        valor = valor.substring(0, valor.length()-2);
        return valor;
    }

    private boolean isNumber(String valor) {
        try {
            Integer.parseInt(valor);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void loadMedicamentosList() {
        ArrayList<AnchorPane> anchorList = new ArrayList<>();
        medicamentoOrder = new ArrayList<>();
        AnchorPane anchor;
        ImageView imagen = new ImageView();
        Image imagenMedicamentoPos;
        Label nombre;
        HashMap<Integer, Medicamentos> medicamentos = Data.medicamentos;
        Medicamentos medicamentoPos;
        int contador = 0;
        Font font = new Font("Corbel", 18); //CREO LA FUENTE
        for(Map.Entry<Integer, Medicamentos> entry : medicamentos.entrySet()) {
            medicamentoPos = entry.getValue(); //RECORRO EL MAPA RECOGIENDO LOS MEDICAMENTOS
            nombre = new Label(medicamentoPos.getNombre());
            nombre.setStyle("-fx-text-fill: white;");
            nombre.setFont(font);
            nombre.setLayoutX(120);
            nombre.setLayoutY(32);
            imagenMedicamentoPos = new Image(medicamentoPos.getImagen());
            imagen.setImage(imagenMedicamentoPos);
            imagen.setX(20);
            imagen.setY(12);
            imagen.setFitHeight(70);
            imagen.setFitWidth(70);
            anchor = new AnchorPane(imagen, nombre);
            if(contador%2 == 0) {
                anchor.setStyle("-fx-background-color: rgba(102, 114, 251, 0.75); -fx-background-radius: 10;");
            } else {
                anchor.setStyle("-fx-background-color: rgba(102, 114, 251, 0.60); -fx-background-radius: 10;");
            }
            contador++;
            anchorList.add(anchor);
            medicamentoOrder.add(entry.getKey());
        }
        ObservableList<AnchorPane> items = FXCollections.observableArrayList(anchorList);
        listaMedicamentos.setItems(items);
        listaMedicamentos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); //SOLO PUEDE SELECCIONAR UN ELEMENTO DE LA LISTA
        listaMedicamentos.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                medicamentoPosList = listaMedicamentos.getSelectionModel().getSelectedIndex();
            }
        });
    }

    public void addMedicamento() {
        String horas = horasMedicamento.getText();
        if(medicamentoPosList > -1 && checkHourFormat(horas)) {
            horas = ordenarHoras(horasMedicamentosArray);
            int id_medicamento = medicamentoOrder.get(medicamentoPosList);
            String medicamentoName = Data.medicamentos.get(id_medicamento).getNombre();

            //COMPRUEBO SI EL MEDICAMENTO YA ESTÁ AÑADIDO
            boolean added = false;
            ObservableList<TablaMedicamentos> medicamentosAdded = medicamentosTable.getItems();
            for(TablaMedicamentos med : medicamentosAdded) {
                if(med.getMedicamento().equals(medicamentoName)) {
                    added = true;
                    break;
                }
            }

            if(!added) { //SI ESTA AÑADIDO LO AÑADO A LA TABLA Y A LA BASE DE DATOS
                boolean lunesSelected = lunes.isSelected();
                boolean martesSelected = martes.isSelected();
                boolean miercolesSelected = miercoles.isSelected();
                boolean juevesSelected = jueves.isSelected();
                boolean viernesSelected = viernes.isSelected();
                boolean sabadoSelected = sabado.isSelected();
                boolean domingoSelected = domingo.isSelected();
                int id_paciente = paciente.getId_paciente();
                TablaMedicamentos nuevoMedicamento = new TablaMedicamentos(id_paciente, id_medicamento, medicamentosTable.getItems().size(), medicamentoName, horas, createCheck(lunesSelected), createCheck(martesSelected), createCheck(miercolesSelected), createCheck(juevesSelected), createCheck(viernesSelected), createCheck(sabadoSelected), createCheck(domingoSelected));
                medicamentosTable.getItems().add(nuevoMedicamento);

                int[] diasTomar = new int[7];
                MedicamentoPaciente mp = new MedicamentoPaciente(id_medicamento, diasTomar, formatHoras(horas));
                Data.doctor.getPacientes().get(pacienteKey).getMedicinas().add(mp);

                new MongoActions().addMedicamento(nuevoMedicamento);
                limpiarAddMedicamento();
            } else {
                new CrearAlertas().medicamentoAddDuplicated();
            }
        } else {
            new CrearAlertas().medicamentoAddError();
        }
    }

    //COMPRUEBA QUE EL FORMATO DE LAS HORAS SEA CORRECTO
    private boolean checkHourFormat(String valor) {
        String[] valores = valor.replaceAll(" ", "").split(";");
        boolean correccion = true;
        String[] auxiliar;
        horasMedicamentosArray = new ArrayList<>();
        //REALIZO COMPROBACIONES PARA COMPROBAR QUE LOS DATOS INTRODUCIDOS SON CORRECTOS
        for(String pos : valores) {
            if(!pos.contains(":")) {
                correccion = false;
                break;
            }
            auxiliar = pos.split(":");
            for(String posAux : auxiliar) {
                if(posAux.length() != 2) {
                    correccion = false;
                    break;
                }
                if(!isNumber(posAux)) {
                    correccion = false;
                    break;
                }
            }
            horasMedicamentosArray.add(Integer.parseInt(pos.replaceAll(":", "")));
        }
        return correccion;
    }

    private void limpiarAddMedicamento() {
        lunes.setSelected(false);
        martes.setSelected(false);
        miercoles.setSelected(false);
        jueves.setSelected(false);
        viernes.setSelected(false);
        sabado.setSelected(false);
        domingo.setSelected(false);
        horasMedicamento.setText("");
    }

    private CheckBox createCheck(boolean selected) {
        CheckBox check = new CheckBox();
        check.setSelected(selected);
        return check;
    }

    private ArrayList<String> formatHoras(String horas) {
        ArrayList<String> horasArrayList = new ArrayList<>();
        String[] horasArray = horas.replaceAll(" ", "").split(";");
        for(String pos : horasArray) {
            horasArrayList.add(pos);
        }
        return horasArrayList;
    }

    private void loadListaNotas() {
        historialPacienteLista = paciente.getHistorial();
        ArrayList<AnchorPane> anchorList = new ArrayList<>();
        int contadorLista = 0;
        for(HistorialPaciente pos : historialPacienteLista) {
            anchorList.add(pos.getData(contadorLista));
            contadorLista++;
        }
        lista = FXCollections.observableArrayList(anchorList);
        listaNotas.setItems(lista);

        //FILTRO LAS NOTAS POR DIA
        buscarNotas.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //BORRO LAS NOTAS
                lista.removeAll();
                listaNotas.setItems(lista);

                //BUSCO LAS NOTAS QUE COINCIDAN CON LA BUSQUEDA
                String fecha = buscarNotas.getText().replaceAll("[^0-9]", ""); //REMPLAZO TODO LO QUE NO SEAN NUMEROS
                String fechaComparacion;
                HistorialPaciente pos;
                ArrayList<AnchorPane> historialPacientesFiltro = new ArrayList<>();
                int contador = 0;
                for(int i = 0; i < historialPacienteLista.size(); i++) {
                    pos = historialPacienteLista.get(i);
                    fechaComparacion = pos.getFecha().replaceAll("[^0-9]", ""); //REMPLAZO TODO LO QUE NO SEAN NUMEROS
                    if(fecha.equals(fechaComparacion) || fechaComparacion.contains(fecha)) {
                        historialPacientesFiltro.add(pos.getData(contador));
                        contador++;
                    }
                }

                lista = FXCollections.observableArrayList(historialPacientesFiltro);
                listaNotas.setItems(lista);
            }
        });
    }
}
