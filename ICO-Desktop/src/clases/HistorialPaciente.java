package clases;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class HistorialPaciente {

    private AnchorPane data;
    private String fecha, nota;

    public HistorialPaciente(String fecha, String nota) {
        this.fecha = fecha;
        this.nota = nota;

        Font font = new Font("Corbel", 14); //CREO LA FUENTE
        Label fechaLab = new Label(fecha + ":");
        fechaLab.setFont(font);
        fechaLab.setLayoutY(19);
        fechaLab.setLayoutX(20);
        fechaLab.setStyle("-fx-text-fill: white;");

        Label notaLab = new Label(nota);
        notaLab.setFont(font);
        notaLab.setLayoutY(19);
        notaLab.setLayoutX(100);
        notaLab.setWrapText(true);
        notaLab.setMaxWidth(350);
        notaLab.setMaxHeight(-1);
        notaLab.setTextAlignment(TextAlignment.JUSTIFY);
        notaLab.setStyle("-fx-text-fill: white;");

        AnchorPane anchor = new AnchorPane(); //CREO EL ANCHOR PANE

        //APLICO ESTILOS AL ANCHOR
        anchor.setMinHeight(40);
        anchor.setMaxWidth(470);
        anchor.setPrefWidth(470);
        anchor.setMinWidth(470);
        anchor.setPadding(new Insets(10,10,10,0));

        anchor.getChildren().add(fechaLab);
        anchor.getChildren().add(notaLab);

        this.data = anchor;
    }

    public AnchorPane getData(int contador) {
        if(contador % 2 == 0) {
            this.data.setStyle("-fx-background-color: rgba(44, 128, 239, 0.8); -fx-background-radius: 10;");
        } else {
            this.data.setStyle("-fx-background-color: rgba(44, 128, 239, 0.5); -fx-background-radius: 10;");
        }
        return this.data;
    }

    public String getFecha() {
        return fecha;
    }
}
