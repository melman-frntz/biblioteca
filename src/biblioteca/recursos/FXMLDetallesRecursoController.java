package biblioteca.recursos;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Froylan De Jesus Alvarez Rodriguez
 * @fecha 11/12/2022
 */
public class FXMLDetallesRecursoController implements Initializable {

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfAutor;
    @FXML
    private TextField tfSeccion;
    @FXML
    private TextField tfEditorial;
    @FXML
    private TextField tfIsbn;
    @FXML
    private TextField tfPeso;
    @FXML
    private TextField tfDuracion;
    @FXML
    private TextArea taDescripcion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatosRecurso();
    }    

    @FXML
    private void clicBttnRegresar(ActionEvent event) {
        cerrarVentana();
    }
    
    private void cargarDatosRecurso(){
        //TODO pasar el recurso de la tabla en la ventana buscarRecurso a esta ventana y cargar sus datos
    }
    
    private void cerrarVentana(){
        Stage ventana = (Stage) tfNombre.getScene().getWindow();
        ventana.close();
    }   
}
