package init;

import java.net.URL;
import java.util.ResourceBundle;

import conexion.MongoActions;
import interfaz.Interfaz;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import programa.Data;

public class LoginController implements Initializable {
		
	@FXML
	private TextField login_user;
	
	@FXML
	private PasswordField login_password;
	
	@FXML
	private Button login_button;
	
	@FXML
	private Label login_error;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		EventHandler<KeyEvent> enterClick = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				String tecla = event.getCode().getName();
				if(tecla.equals("Enter")) {
					controlarLogin();
				}
			}
		};
		login_user.setOnKeyPressed(enterClick);
		login_password.setOnKeyPressed(enterClick);
	}
	
		
	public void controlarLogin() {
		MongoActions mongo = new MongoActions();
		String user = login_user.getText();
		String pass = login_password.getText();
		
		//COMPRUEBO SI LOS DATOS SON CORRECTOS
		

		if(mongo.checkLogin(user, pass)) { 
			loginDone(mongo);
		} else {
			loginError();
		}


		/*
		//PARA TESTEAR SALTO EL LOGIN
		user = "jcsalinas";
		pass = "1234";
		mongo.checkLogin(user, pass);
		loginDone(mongo);

		 */
	}
	
	//ES INVOCADO SI LOS DATOS DE LOGIN SON CORRECTOS
	private void loginDone(MongoActions mongo) {
		mongo.loadDoctor(); //CARGO LOS DATOS DEL DOCTOR
		Data.interfaz.close(); //CIERRO LA ESCENA ACTUAL
		new Interfaz().loadInterface(); //CARGO LA INTERFAZ
	}
	
	//ES INVOCADO SI LOS DATOS DE LOGIN SON INCORRECTOS
	private void loginError() {
		//LIMPIO LOS TEXTFIELD Y MUESTRO UN LABEL DE ERROR
		login_user.setText("");
		login_password.setText("");
		login_error.setVisible(true);
	}
	
}