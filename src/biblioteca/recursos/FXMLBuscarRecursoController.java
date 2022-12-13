package biblioteca.recursos;

import biblioteca.modelo.dao.RecursoDocumentalDAO;
import biblioteca.modelo.pojo.RecursoDocumental;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utilidades.Utilidades;

/**
 * @autor Froylan De Jesus Alvarez Rodriguez
 * @fecha 11/12/2022 
 */
public class FXMLBuscarRecursoController implements Initializable {

    @FXML
    private TextField tfNombreRecurso;
    @FXML
    private TableView<RecursoDocumental> tvRecursos;
    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcAutor;
    @FXML
    private TableColumn tcIdBiblioteca;
    @FXML
    private Label lbErrorBuscarRecurso;
    
    private ObservableList<RecursoDocumental> listaRecursos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }    

    @FXML
    private void clicBttnBuscar(ActionEvent event) {
        String nombre = tfNombreRecurso.getText();
        if(!nombre.isEmpty()){
            lbErrorBuscarRecurso.setText("");
            buscarRecursos(nombre);
        }else{
            lbErrorBuscarRecurso.setText("No se ha ingresado un recurso.");
        }
    }

    @FXML
    private void clicBttnMostrar(ActionEvent event) {
        RecursoDocumental recursoMostrar = verificarRecursoSeleccionado();
        
        if(recursoMostrar != null){
            abrirVentanaDetallesRecurso(recursoMostrar);
        }else{
            Utilidades.mostrarAlertaSimple("Recurso No Seleccionado", "No se ha seleccionado el recurso a mostrar.", Alert.AlertType.ERROR);
        }
    }
    
    private void configurarTabla(){
        tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcAutor.setCellValueFactory(new PropertyValueFactory("autor"));
        tcIdBiblioteca.setCellValueFactory(new PropertyValueFactory("idBiblioteca"));
    }
    
    private void buscarRecursos(String nombre){
        try {
            listaRecursos = FXCollections.observableArrayList();
            ArrayList<RecursoDocumental> recursosBD = RecursoDocumentalDAO.buscarRecursosPorNombre(nombre);
            
            if(recursosBD != null){
                ArrayList<RecursoDocumental> recursosDisponibles = filtrarRecursosDisponibles(recursosBD);
                
                if(recursosDisponibles != null){
                    listaRecursos.addAll(recursosDisponibles);
                    tvRecursos.setItems(listaRecursos);
                }else{
                    Utilidades.mostrarAlertaSimple("Recurso No Disponible", "Este recurso documental no tiene existencias disponibles.", Alert.AlertType.ERROR);
                }
            }else{
                Utilidades.mostrarAlertaSimple("Recurso No Encontrado", "Este recurso documental no esta registrado en el sistema.", Alert.AlertType.ERROR);
            }
        } catch (SQLException sqlExcepcion) {
            sqlExcepcion.printStackTrace();
        }
    }
    
    private ArrayList<RecursoDocumental> filtrarRecursosDisponibles(ArrayList<RecursoDocumental> recursosBD){
        ArrayList<RecursoDocumental> recursosFiltrados = null;
        
        if(recursosBD != null){
            recursosFiltrados = new ArrayList<>();
            
            for(int i = 0; i < recursosBD.size(); i++){
                if(recursosBD.get(i).getEstado().toLowerCase().equals("disponible")){
                    recursosFiltrados.add(recursosBD.get(i));
                }
            }
        }
    
        return recursosFiltrados;
    }   
    
    private void abrirVentanaDetallesRecurso(RecursoDocumental recurso){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLDetallesRecurso.fxml"));
            Parent vista = fxmlLoader.load();
            FXMLDetallesRecursoController detallesRecurso = fxmlLoader.getController();
            detallesRecurso.inicializarValores(recurso);
            Scene escenarioDetallesRecurso = new Scene(vista);
            Stage nuevoEscenarioDetallesRecurso = new Stage();
            nuevoEscenarioDetallesRecurso.setScene(escenarioDetallesRecurso);
            nuevoEscenarioDetallesRecurso.initModality(Modality.APPLICATION_MODAL);
            nuevoEscenarioDetallesRecurso.showAndWait();
        } catch (IOException ex) {
            Utilidades.mostrarAlertaSimple("Error", "No se ha podido mostrar los detalles del recurso.", Alert.AlertType.ERROR);
        }
    } 
    
    private RecursoDocumental verificarRecursoSeleccionado(){ 
        return tvRecursos.getSelectionModel().getSelectedItem();
    }
}
