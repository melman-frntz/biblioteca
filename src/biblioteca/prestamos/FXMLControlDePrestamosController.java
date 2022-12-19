package biblioteca.prestamos;

import biblioteca.modelo.pojo.UsuarioStaff;
import biblioteca.recursos.FXMLControlDeRecursosController;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utilidades.Utilidades;

public class FXMLControlDePrestamosController implements Initializable {

    @FXML
    private Button bttnOpcionRegistrarPrestamoDomicilio;
    @FXML
    private Button bttnOpcionRegistrarDevolucion;
    @FXML
    private Button bttnSolicitarRenovacion;
    @FXML
    private Button bttnOpcionPrestamoInterbibliotecario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void clicBttnOpcionPrestamoInterbibliotecario(ActionEvent event) {
        cargarVentanaPrestamoInterbibliotecario();
    }

    @FXML
    private void clicBttnOpcionRegistrarDevolucion(ActionEvent event) {
        mostrarVentanaDevolucionRenovacion(true, false);
    }

    @FXML
    private void clicBttnOpcionSolicitarRenovacion(ActionEvent event) {
        mostrarVentanaDevolucionRenovacion(false, true);
    }

    @FXML
    private void clicBttnOpcionPrestamoDomicilio(ActionEvent event) {
        mostrarVentanaPrestamoDomicilio();
    }
    
    private void cargarVentanaPrestamoInterbibliotecario(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLPrestamoInterbibliotecario.fxml"));
            Parent ventanaPrestamoInterbibliotecario = fxmlLoader.load();
            Scene escenarioPrestamoInterbibliotecario = new Scene(ventanaPrestamoInterbibliotecario);
            Stage nuevoEscenarioPrestamoInterbibliotecario = new Stage();
            nuevoEscenarioPrestamoInterbibliotecario.setScene(escenarioPrestamoInterbibliotecario);
            nuevoEscenarioPrestamoInterbibliotecario.initModality(Modality.APPLICATION_MODAL);
            nuevoEscenarioPrestamoInterbibliotecario.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLControlDeRecursosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void mostrarVentanaDevolucionRenovacion(boolean tituloVentana, boolean renovacion){
        try {
            FXMLLoader accesoControlador = new FXMLLoader(getClass().getResource("FXMLDevolucionRenovacion.fxml"));
            Parent vista = accesoControlador.load();
            FXMLDevolucionRenovacionController ventana = accesoControlador.getController();
            ventana.valorTituloVentana(tituloVentana, renovacion);
            Scene escenaVentana = new Scene(vista);
            Stage escenarioNuevo = new Stage();
            escenarioNuevo.setScene(escenaVentana);
            escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
            escenarioNuevo.showAndWait();
        } catch (IOException ex) {
            Utilidades.mostrarAlertaSimple("Error", "Error al cargar el formulario de alumnos.", Alert.AlertType.ERROR);
            ex.printStackTrace();
        }
    }
    
    private void mostrarVentanaPrestamoDomicilio(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLRegistrarPrestamo.fxml"));
            Parent ventanaRegistrarPrestamo = fxmlLoader.load();
            Scene escenarioRegistrarPrestamo = new Scene(ventanaRegistrarPrestamo);
            Stage nuevoEscenarioRegistrarPrestamo = new Stage();
            nuevoEscenarioRegistrarPrestamo.setScene(escenarioRegistrarPrestamo);
            nuevoEscenarioRegistrarPrestamo.initModality(Modality.APPLICATION_MODAL);
            nuevoEscenarioRegistrarPrestamo.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLControlDeRecursosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
