package interfaz;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;


import java.util.Map.Entry;

import clases.Paciente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import programa.Data;
import traducciones.UTF8Control;

public class SearchController implements Initializable {

	public static Paciente pacienteSeleccionado;

	private HashMap<String, Paciente> pacientes;
	
	private ObservableList<AnchorPane> items;

	@FXML
	private TextField search_text;

	@FXML
	private ListView<AnchorPane> pacientes_list;

	@FXML
	private ImageView paciente_image;
	
	@FXML
	private Label nombre_paciente;
	
	@FXML
	private Label apellido_paciente;
	
	@FXML
	private Label dni_paciente;
	
	@FXML
	private Label nacimiento_paciente;
	
	@FXML
	private Label genero_paciente;
	
	@FXML
	private AnchorPane userdata_pane;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		pacientes = Data.doctor.getPacientes();
		items = FXCollections.observableArrayList(new ArrayList<>());
		pacientes_list.setItems(items);
		busqueda(); //CARGO TODOS LOS PACIENTES
	}

	public void busqueda() {
		//BORRO TODO EL LISTADO DE PACIENTES
		items.removeAll();
		pacientes_list.setItems(items);
		
		//BUSCO LOS PACIENTES
		ArrayList<AnchorPane> anchorPacientes = new ArrayList<>(); //RECOGE LOS ANCHOR PANE DE CADA PACIENTE
		String textoBusqueda = search_text.getText().replaceAll(" ", ""); //RECOJO EL TEXTO ESCRITO POR EL USUARIO
		String nombrePos;
		AnchorPane anchorPos;
		for(Entry<String, Paciente> entry : pacientes.entrySet()) {
			nombrePos = entry.getKey(); //RECORRO EL MAPA RECOGIENDO LOS NOMBRES DE PACIENTES
			if(nombrePos.startsWith(textoBusqueda) || nombrePos.contains(textoBusqueda)) {
				anchorPos = castToAnchor(entry.getValue());
				anchorPacientes.add(anchorPos); //AÑADO LOS PACIENTES QUE COINCIDAN CON LA BUSQUEDA AL ARRAYLIST
			}
		}
		
		items = FXCollections.observableArrayList(anchorPacientes);
		pacientes_list.setItems(items); //AÑADO LOS PACIENTES DE LA LISTA AL LISTVIEW
	}
	
	private AnchorPane castToAnchor(Paciente p) {
		AnchorPane anchor = new AnchorPane();
		
		//PONGO LA IMAGEN DEL PACIENTE
		ImageView pacienteImage = new ImageView();
		Image imagenPaciente = new Image(p.getFoto());
		pacienteImage.setImage(imagenPaciente);
		pacienteImage.setX(5);
		pacienteImage.setFitHeight(80);
		pacienteImage.setFitWidth(80);
		
		//PONGO EL NOMBRE DEL PACIENTE
		Label nombrePaciente = new Label(p.getNombre() + " " + p.getApellidos());
		nombrePaciente.setLayoutX(100);
		nombrePaciente.setLayoutY(32);
		nombrePaciente.setFont(Font.font("SansSerif", FontWeight.BOLD, FontPosture.REGULAR, 18));

		//AÑADO LA IMAGE Y EL LABEL AL PANE
		anchor.getChildren().add(pacienteImage);
		anchor.getChildren().add(nombrePaciente);
		
		anchor.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				showDataPaciente(p, imagenPaciente);
			}
		});
				
		return anchor;
	}
	
	private void showDataPaciente(Paciente p, Image pacienteImage) {
		paciente_image.setImage(pacienteImage);
		nombre_paciente.setText(p.getNombre());
		apellido_paciente.setText(p.getApellidos());
		dni_paciente.setText(p.getDni());
		nacimiento_paciente.setText(p.getNacimiento());
		genero_paciente.setText("Hombre");
		if(!userdata_pane.isVisible()) {
			userdata_pane.setVisible(true);
		}
		pacienteSeleccionado = p;
	}

	//MUESTRA LOS DATOS DEL PACIENTE SELECCIONADO
	public void editar() {
		try {
			AnchorPane root;
			AnchorPane scene_panel = Interfaz.scene_panel;

			ResourceBundle resourceBundle;
			if(Data.catalan) {
				resourceBundle = ResourceBundle.getBundle("traducciones/texto_cat", new UTF8Control());
			} else {
				resourceBundle = ResourceBundle.getBundle("traducciones/texto", new UTF8Control());
			}
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("Pacientes.fxml"), resourceBundle);

			ObservableList<Node> panelHijos = scene_panel.getChildren();
			if(panelHijos.size() > 0) {
				panelHijos.remove(0);
			}

			scene_panel.getChildren().add(root);

			String screenTitle = pacienteSeleccionado.getNombre() + " " + pacienteSeleccionado.getApellidos();
			Interfaz.nombre_escena.setText(screenTitle);

		} catch(Exception e) {

		}
	}
}
