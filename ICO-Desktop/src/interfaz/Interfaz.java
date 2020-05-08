package interfaz;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import programa.Constantes;
import programa.Data;
import traducciones.UTF8Control;

import java.io.IOException;
import java.util.ResourceBundle;

public class Interfaz {
		
	private Scene escena;

	public static AnchorPane root;

	@FXML
	public static AnchorPane scene_panel;

	@FXML
	public static Label nombre_escena;

	@FXML
	public ImageView dashboard_image;

	@FXML
	public ImageView pacientes_image;

	public void loadInterface() {
		Stage interfaz_load = new Stage();
		try {
			AnchorPane interfaz = loadPanel("Interfaz.fxml"); //CARGO LA INTERFAZ

			//RELACIONO LAS IMAGENES
			ToolBar toolbar_left = (ToolBar) interfaz.getChildren().get(0);
			AnchorPane left_bar = (AnchorPane) toolbar_left.getItems().get(0);
			dashboard_image = (ImageView) left_bar.getChildren().get(0);
			pacientes_image = (ImageView) left_bar.getChildren().get(2);

			scene_panel = (AnchorPane) interfaz.getChildren().get(1); //RELACIONO EL ANCHOR PANE QUE IRA CAMBIANDO
			AnchorPane top_bar = (AnchorPane) interfaz.getChildren().get(2);
			nombre_escena = (Label) top_bar.getChildren().get(0); //RELACIONO CON EL LABEL DE LA ESCENA

			loadDashboard(); //MUESTRO AL MEDICO SU DASHBOARD
			escena = new Scene(interfaz);
			interfaz_load.setScene(escena);
			interfaz_load.setResizable(false); //NO PERMITO QUE SE PUEDA MODIFICAR EL SIZE
			interfaz_load.setMaximized(true); //MAXIMIZO LA VENTANA
			interfaz_load.initStyle(StageStyle.UNDECORATED); //BORRO LOS BORDES
			Data.interfaz = interfaz_load;
			Data.interfaz.show();
		} catch(Exception e) {

		}
	}

	//CARGA EL DASHBOARD
	public void loadDashboard() {
		try {
			root = loadPanel("Dashboard.fxml");
			removeAnchorChildren();
			scene_panel.getChildren().add(root);
			nombre_escena.setText(Constantes.DASHBOARD_SCENE);
			dashboard_image.setOpacity(1);
			pacientes_image.setOpacity(0.44);
		} catch (Exception e) {

		}
	}

	//CARGA LA INTERFAZ DE BUSQUEDA DE PACIENTES
	public void loadSearch() {
		try {
			root = loadPanel("Search.fxml");
			removeAnchorChildren();
			scene_panel.getChildren().add(root);
			String nombreEscena;
			if(Data.catalan) {
				nombreEscena = "Pacients";
			} else {
				nombreEscena = "Pacientes";
			}
			nombre_escena.setText(nombreEscena);
			pacientes_image.setOpacity(1);
			dashboard_image.setOpacity(0.44);
		} catch(Exception e) {

		}
	}

	public void cambiarIdioma() {
		Data.catalan = !Data.catalan;
		Data.interfaz.close(); //CIERRO LA ESCENA ACTUAL
		new Interfaz().loadInterface(); //CARGO LA INTERFAZ
	}

	//CIERRA EL PROGRAMA
	public void logout() {
		Data.interfaz.close();
		System.exit(0);
	}
	
	//BORRA LOS HIJOS DEL ANCHOR PANE PARA CAMBIAR EL CONTENIDO
	private void removeAnchorChildren() {
		ObservableList<Node> panelHijos = scene_panel.getChildren();
		if(panelHijos.size() > 0) {
			panelHijos.remove(0);
		}
	}

	//BUSCA EL ARCHIVO FXL
	private AnchorPane loadPanel(String nombreFXML) throws IOException {
		ResourceBundle resourceBundle;
		if(Data.catalan) {
			resourceBundle = ResourceBundle.getBundle("traducciones/texto_cat", new UTF8Control());
		} else {
			resourceBundle = ResourceBundle.getBundle("traducciones/texto", new UTF8Control());
		}
		return (AnchorPane) FXMLLoader.load(getClass().getResource(nombreFXML), resourceBundle);
	}
}
