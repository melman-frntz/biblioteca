package biblioteca.recursos;

import biblioteca.modelo.dao.RecursoDocumentalDAO;
import biblioteca.modelo.pojo.RecursoDocumental;
import biblioteca.modelo.pojo.ResultadoOperacion;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import utilidades.Utilidades;

/**
 * @autor Froylan De Jesus Alvarez Rodriguez
 * @fecha 11/12/2022
 */
public class FXMLRegistrarRecursoDaniadoController implements Initializable {

    @FXML
    private TextField tfIdentificadorRecurso;
    @FXML
    private TableView<RecursoDocumental> tvRecursos;
    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcAutor;
    @FXML
    private TableColumn tcIdBiblioteca;
    
    ObservableList<RecursoDocumental> listaRecursos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarDatosTabla();
        inicializarBusquedaRecursos();
    }    

    @FXML
    private void clicBttnRegistrar(ActionEvent event) {
        registrarRecursoDaniado();
    }
    
    private void configurarTabla(){
        tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcAutor.setCellValueFactory(new PropertyValueFactory("autor"));
        tcIdBiblioteca.setCellValueFactory(new PropertyValueFactory("idBiblioteca"));
    }
    
    private void cargarDatosTabla(){
        try {
            listaRecursos = FXCollections.observableArrayList();
            ArrayList<RecursoDocumental> recursosBD = RecursoDocumentalDAO.recuperarRecursos();
            
            if(recursosBD != null){
                ArrayList<RecursoDocumental> recursosDisponibles = filtrarRecursosDisponibles(recursosBD);
                
                if(recursosDisponibles != null){
                    listaRecursos.addAll(recursosDisponibles);
                    tvRecursos.setItems(listaRecursos);
                }else{
                    Utilidades.mostrarAlertaSimple("Recursos No disponibles", "Aun no hay recursos documentales que esten disponibles en el sistema", Alert.AlertType.ERROR);
                }
            }else{
                Utilidades.mostrarAlertaSimple("Recursos No Registrados", "Aun no hay recursos documentales registrados en el sistema.", Alert.AlertType.ERROR);
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
    
    private void registrarRecursoDaniado(){
        //TODO actualizar tabla
        RecursoDocumental recursoRegistrar = verificarRecursoSeleccionado();
        
        if(recursoRegistrar != null){
            try {
                ResultadoOperacion resultadoRegistrarDaniado = RecursoDocumentalDAO.registrarRecursoDaniado("dañado", recursoRegistrar.getIdRecurso());
                if(!resultadoRegistrarDaniado.isError()){
                    Utilidades.mostrarAlertaSimple("Registro Exitoso", "Se registró el recurso dañado con exito.", Alert.AlertType.INFORMATION);
                    tfIdentificadorRecurso.setText("");
                    cargarDatosTabla();
                    inicializarBusquedaRecursos();
                }else{
                    Utilidades.mostrarAlertaSimple("Error", resultadoRegistrarDaniado.getMensaje(), Alert.AlertType.ERROR);
                }
            } catch (SQLException sqlExcepcion) {
                sqlExcepcion.printStackTrace();
            }  
        }else{
            Utilidades.mostrarAlertaSimple("Recurso No Seleccionado", "No se ha seleccionado el recurso a mostrar.", Alert.AlertType.ERROR);
        }
    }
    
    private RecursoDocumental verificarRecursoSeleccionado(){
        return tvRecursos.getSelectionModel().getSelectedItem();
    }
    
    private void inicializarBusquedaRecursos(){
        if(listaRecursos.size() > 0){
            FilteredList<RecursoDocumental> filtroRecursos = new FilteredList<>(listaRecursos, p -> true);
            
            tfIdentificadorRecurso.textProperty().addListener(new ChangeListener<String>(){
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filtroRecursos.setPredicate(busqueda -> {
                        if(newValue == null || newValue.isEmpty()){
                            return true;
                        }
                        
                        if(String.valueOf(busqueda.getIdBiblioteca()).contains(newValue)){
                            return true;
                        }
                        
                        return false;
                    });
                }                
            });
            
            SortedList<RecursoDocumental> recursosFiltrados = new SortedList<>(filtroRecursos);
            recursosFiltrados.comparatorProperty().bind(tvRecursos.comparatorProperty());
            tvRecursos.setItems(recursosFiltrados);
        }
    }
}
