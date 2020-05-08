package init;
	
import conexion.MongoConnection;
import javafx.application.Application;
import javafx.stage.Stage;
import programa.Data;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import traducciones.UTF8Control;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class Main extends Application {
			
	@Override
	public void start(Stage primaryStage) {
		loadData(); //CARGO LOS DATOS DEL PROGRAMA
		try {
			ResourceBundle resourceBundle = ResourceBundle.getBundle("traducciones/texto", new UTF8Control());
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("LoginScreen.fxml"), resourceBundle);
			Scene scene = new Scene(root,1000,800); //LE DOY DIMENSIONES DE 1000x600
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene); 
			primaryStage.setResizable(false); //NO PERMITO QUE SE PUEDA MODIFICAR EL SIZE
			Data.catalan = false;
			Data.interfaz = primaryStage;
			Data.interfaz.show();
		} catch(Exception e) {
			
		}
	}

	private void loadData() {
		new MongoConnection(); //CARGO LA BASE DE DATOS
	}

	public static void main(String[] args) {
		launch(args);
	}

}
