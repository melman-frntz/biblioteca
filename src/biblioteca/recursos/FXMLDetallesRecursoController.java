package biblioteca.recursos;

/*
*@author froylan
*Fecha creacion: 11/12/2022
*Fecha de ultima modificacion: 20/12/2022
*Ultimo modificador: froylan
*/

import biblioteca.modelo.pojo.RecursoDocumental;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    
    private RecursoDocumental recursoMostrar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    

    @FXML
    private void clicBttnRegresar(ActionEvent event) {
        cerrarVentana();
    }
    
    private void cargarDatosRecurso(){
        tfNombre.setText(recursoMostrar.getNombre());
        tfAutor.setText(recursoMostrar.getAutor());
        tfSeccion.setText(recursoMostrar.getSeccion());
        taDescripcion.setText(recursoMostrar.getDescripcion());
        
        if(recursoMostrar.getEditorial() != null){
            tfEditorial.setText(recursoMostrar.getEditorial());
        }else{
            tfEditorial.setText("No aplica.");
        }
        
        if(recursoMostrar.getIsbn() != null){
            tfIsbn.setText(recursoMostrar.getIsbn());
        }else{
            tfIsbn.setText("No aplica.");
        }
        
        if(recursoMostrar.getPeso() > 0){
            tfPeso.setText(String.valueOf(recursoMostrar.getPeso()));
        }else{
            tfPeso.setText("No aplica.");
        }
        
        if(recursoMostrar.getDuracion() > 0){
            tfDuracion.setText(String.valueOf(recursoMostrar.getDuracion()));
        }else{
            tfDuracion.setText("No aplica.");
        }
    }
    
    private void cerrarVentana(){
        Stage ventana = (Stage) tfNombre.getScene().getWindow();
        ventana.close();
    }   
    
    public void inicializarValores(RecursoDocumental recursoMostrar){
        this.recursoMostrar = recursoMostrar;
        
        cargarDatosRecurso();
    }
}
