package biblioteca.recursos;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * @autor Froylan De Jesus Alvarez Rodriguez
 * @fecha 11/12/2022 
 */
public class FXMLBuscarRecursoController implements Initializable {

    @FXML
    private TextField tfNombreRecurso;
    @FXML
    private TableView<?> tvRecursos;
    @FXML
    private TableColumn<?, ?> tcNombre;
    @FXML
    private TableColumn<?, ?> tcAutor;
    @FXML
    private TableColumn<?, ?> tcIdBiblioteca;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicBttnBuscar(ActionEvent event) {
    }

    @FXML
    private void clicBttnMostrar(ActionEvent event) {
    }
    
}
