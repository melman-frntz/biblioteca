package biblioteca;

import java.io.IOException;
import java.net.URL;
import javafx.util.Duration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class FXMLPrincipalController implements Initializable {
    
    @FXML
    private Label menuOpcion;
    @FXML
    private AnchorPane cintaOpciones;
    @FXML
    private Label menuOpcionCerrar;
    @FXML
    private AnchorPane background;
    @FXML
    private BorderPane borderPaneLayOut;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cintaOpciones.setTranslateX(-268);
        clicBttnMenuOpcion();
        clicBttnMenuOpcionCerrar();
        cargarOpciones("FXMLInicio");
    }

    @FXML
    private void clicBttnOpcionInicio(ActionEvent event) {
        cargarOpciones("FXMLInicio");
    }

    @FXML
    private void clicBttnOpcionControlPrestamos(ActionEvent event) {
        cargarOpciones("FXMLControlDePrestamos");
    }

    @FXML
    private void clicBttnOpcionControlRecursos(ActionEvent event) {
        cargarOpciones("FXMLControlDeRecursos");
    }

    @FXML
    private void clicBttnOpcionControlUsuarios(ActionEvent event) {
        cargarOpciones("FXMLControlDeUsuarios");
    }

    @FXML
    private void clicBttnOpcionPapeleo(ActionEvent event) {
        cargarOpciones("FXMLPapeleo");
    }
    
    private void clicBttnMenuOpcion(){
        menuOpcion.setOnMouseClicked(event -> {
            TranslateTransition transicion = new TranslateTransition();
            transicion.setDuration(Duration.seconds(0.2));
            transicion.setNode(cintaOpciones);

            transicion.setToX(0);
            transicion.play();

            cintaOpciones.setTranslateX(-268);

            transicion.setOnFinished((ActionEvent e) ->{
                menuOpcion.setVisible(false);
                menuOpcionCerrar.setVisible(true);
            });
        });
    }
    
    private void clicBttnMenuOpcionCerrar(){
        menuOpcionCerrar.setOnMouseClicked(event -> {
            TranslateTransition transicion = new TranslateTransition();
            transicion.setDuration(Duration.seconds(0.2));
            transicion.setNode(cintaOpciones);

            transicion.setToX(-268);
            transicion.play();

            cintaOpciones.setTranslateX(0);

            transicion.setOnFinished((ActionEvent e) ->{
                menuOpcion.setVisible(true);
                menuOpcionCerrar.setVisible(false);
            });
        });
    }
    
    private void cargarOpciones(String vista){
        try {   
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(vista + ".fxml"));
            borderPaneLayOut.getChildren().remove(borderPaneLayOut.getCenter()); //Remueve fxml existente del centro
            borderPaneLayOut.setCenter(fxmlLoader.load());
        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}