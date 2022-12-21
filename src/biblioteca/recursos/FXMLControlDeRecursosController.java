package biblioteca.recursos;

/*
*@author franz
*Fecha creacion: 08/12/2022
*Fecha de ultima modificacion: 20/12/2022
*Ultimo modificador: froylan
*/


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLControlDeRecursosController implements Initializable {

    @FXML
    private Button bttnOpcionRegistrarRecurso;
    @FXML
    private Button bttnOpcionBuscarRecurso;
    private int tipoUsuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicBttnOpcionRegistrarRecursoDañado(ActionEvent event) {
        abrirVentanaRegistrarRecursoDaniado();
    }

    @FXML
    private void clicBttnOpcionBuscarRecurso(ActionEvent event) {
        abrirVentanaBuscarRecurso();
    }
    
    private void abrirVentanaBuscarRecurso(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLBuscarRecurso.fxml"));
            Parent ventanaBuscarRecurso = fxmlLoader.load();
            Scene escenarioBuscarRecurso = new Scene(ventanaBuscarRecurso);
            Stage nuevoEscenarioRegistrarRecursoDaniado = new Stage();
            nuevoEscenarioRegistrarRecursoDaniado.setScene(escenarioBuscarRecurso);
            nuevoEscenarioRegistrarRecursoDaniado.initModality(Modality.APPLICATION_MODAL);
            nuevoEscenarioRegistrarRecursoDaniado.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLControlDeRecursosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void abrirVentanaRegistrarRecursoDaniado(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLRegistrarRecursoDaniado.fxml"));
            Parent ventanaRegistrarRecursoDaniado = fxmlLoader.load();
            Scene escenarioRegistrarRecursoDaniado = new Scene(ventanaRegistrarRecursoDaniado);
            Stage nuevoEscenarioRegistrarRecursoDaniado = new Stage();
            nuevoEscenarioRegistrarRecursoDaniado.setScene(escenarioRegistrarRecursoDaniado);
            nuevoEscenarioRegistrarRecursoDaniado.initModality(Modality.APPLICATION_MODAL);
            nuevoEscenarioRegistrarRecursoDaniado.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLControlDeRecursosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void inicializarTipoUsuario(int tipoUsuario){
        this.tipoUsuario = tipoUsuario;
        
        habilitarOpciones();
    }

        /*
         *Esta seccion del programa desactiva las opciones por usuarioBiblioteca actual
        */
        private void habilitarOpciones(){
        switch(tipoUsuario){
            case 1:
                bttnOpcionBuscarRecurso.setDisable(false);
                bttnOpcionRegistrarRecurso.setDisable(false);
                
                break;
            case 2:
                bttnOpcionBuscarRecurso.setDisable(false);
                bttnOpcionRegistrarRecurso.setDisable(true);
                
                break;
            case 3:
                bttnOpcionBuscarRecurso.setDisable(false);
                bttnOpcionRegistrarRecurso.setDisable(true);
                
                break;
            case 4:
                bttnOpcionBuscarRecurso.setDisable(false);
                bttnOpcionRegistrarRecurso.setDisable(true);
                
                break;
        }
    }
}
