package biblioteca;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
        cargarOpciones("FXMLInicio");
    }

    @FXML
    private void clicBttnOpcionInicio(ActionEvent event) {
        cargarOpciones("FXMLInicio");
    }

    @FXML
    private void clicBttnOpcionControlPrestamos(ActionEvent event) {
        cargarOpciones("prestamos/FXMLControlDePrestamos");
    }

    @FXML
    private void clicBttnOpcionControlRecursos(ActionEvent event) {
        cargarOpciones("recursos/FXMLControlDeRecursos");       
    }

    @FXML
    private void clicBttnOpcionControlUsuarios(ActionEvent event) {
        cargarOpciones("usuarios/FXMLControlDeUsuarios");
    }

    @FXML
    private void clicBttnOpcionPapeleo(ActionEvent event) {
        cargarOpciones("papeleo/FXMLPapeleo");
    }

    @FXML
    private void clicBttnOpcionCerrarSesion(ActionEvent event) {
        cerrarPrincipal();
        mostrarInicioSesion();
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
    
    private void cerrarPrincipal(){
            Stage escenarioPrincipal = (Stage) borderPaneLayOut.getScene().getWindow();
            escenarioPrincipal.close();
    }
    
    private void mostrarInicioSesion(){
        try {
            Parent vista = FXMLLoader.load(getClass().getResource("FXMLInicioSesion.fxml"));
            Scene escenaPrincipal = new Scene(vista);
            Stage escenarioBase = (Stage) borderPaneLayOut.getScene().getWindow();
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}