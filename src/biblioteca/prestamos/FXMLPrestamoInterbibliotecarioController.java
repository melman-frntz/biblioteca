package biblioteca.prestamos;

import biblioteca.modelo.dao.RecursoDocumentalDAO;
import biblioteca.modelo.pojo.RecursoDocumental;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import utilidades.Utilidades;

public class FXMLPrestamoInterbibliotecarioController implements Initializable {

    @FXML
    private TextField txtDestino;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaEntrega;
    @FXML
    private TextField txtBusquedaRecurso;
    @FXML
    private TextField txtFolio;
    @FXML
    private TextField txtTipoRecurso;
    @FXML
    private TextField txtNombreRecurso;
    @FXML
    private TextField txtAutorRecurso;
    @FXML
    private TextField txtNombreUsuario;
    @FXML
    private TextField txtIdUsuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void clicBttnOpcionCancelar(ActionEvent event) {
        limpiarCampos();
    }

    @FXML
    private void clicBttnOpcionBuscar(ActionEvent event) {
        if(txtBusquedaRecurso.getText() != null){
            buscarRecursoDocumental();
        }else{
            Utilidades.mostrarAlertaSimple("Campo de busqueda vacio", "El campo de busqueda debe estar lleno.", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void clicBttnOpcionGuardarPrestamo(ActionEvent event) {
    }
    
    private void buscarRecursoDocumental(){
        try {
            RecursoDocumental recursoEncontrado = RecursoDocumentalDAO.buscarRecursoPorNombre(txtBusquedaRecurso.getText());
            if(recursoEncontrado != null){
                txtFolio.setText(recursoEncontrado.getFolio());
                txtTipoRecurso.setText(recursoEncontrado.getTipoRecurso());
                txtNombreRecurso.setText(recursoEncontrado.getNombre());
                txtAutorRecurso.setText(recursoEncontrado.getAutor());                
            }else{
                Utilidades.mostrarAlertaSimple("Error de busqueda", "No se proporciono ningun nombre, falta algun caracter o no existe un recurso documental con este nombre.", Alert.AlertType.INFORMATION);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLPrestamoInterbibliotecarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void limpiarCampos(){
        txtNombreUsuario.setText(null);
        txtIdUsuario.setText(null);
        txtDestino.setText(null);
        dpFechaInicio.setValue(null);
        dpFechaEntrega.setValue(null);
        txtBusquedaRecurso.setText(null);
        txtFolio.setText(null);
        txtTipoRecurso.setText(null);
        txtNombreRecurso.setText(null);
        txtAutorRecurso.setText(null);
    }
    
    private void registrarPrestamo(){
        
    }
}
