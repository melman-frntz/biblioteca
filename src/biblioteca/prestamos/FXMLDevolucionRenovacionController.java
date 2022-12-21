package biblioteca.prestamos;

/*
    @author Jesús Enrique Fernández González
    Fecha creación: 10/12/2022
    Fecha de última modificación: 16/12/2022
    Último modificador: Jesús Enrique Fernández González
*/

import biblioteca.modelo.dao.PrestamoDAO;
import biblioteca.modelo.dao.RecursoDocumentalDAO;
import biblioteca.modelo.dao.UsuarioBibliotecaDAO;
import biblioteca.modelo.pojo.Prestamo;
import biblioteca.modelo.pojo.RecursoDocumental;
import biblioteca.modelo.pojo.ResultadoOperacion;
import biblioteca.modelo.pojo.UsuarioBiblioteca;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utilidades.Utilidades;

public class FXMLDevolucionRenovacionController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private TextField tfIdentificador;
    @FXML
    private Label lbNombreUsuario;
    @FXML
    private Label lbCorreoUsuario;
    @FXML
    private Label lbDireccionUsuario;
    @FXML
    private Label lbTelefonoUsuario;
    @FXML
    private TableView<RecursoDocumental> tvRecursos;
    @FXML
    private TableColumn tcRecursoDocumental;
    @FXML
    private TableColumn tcIDRecursoDocumental;
    @FXML
    private Button btnDevolucion;
    @FXML
    private Button btnRenovacion;
    
    private ObservableList<RecursoDocumental> listaRecursos;
    private boolean esRenovacion;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }    

    @FXML
    private void buscarUsuario(ActionEvent event) {
        try {
            String matricula = tfIdentificador.getText();
            
            //¿Se introdujo un nombre de usuario de biblioteca?
            if(matricula.isEmpty()){
                Utilidades.mostrarAlertaSimple("", "Debe escribir el nombre de un usuario de la biblioteca", 
                        Alert.AlertType.WARNING);
                return;
            }
            
            ArrayList<UsuarioBiblioteca> prestamosBD = UsuarioBibliotecaDAO.obtenerUsuario(matricula);
            
            if(prestamosBD.isEmpty()){
                Utilidades.mostrarAlertaSimple("Usuario no encontrado", "El usuario ingresado no se encontró", 
                        Alert.AlertType.WARNING);
                return;
            }
            
            for(UsuarioBiblioteca usuario : prestamosBD){
                lbNombreUsuario.setText(usuario.getNombre());
                lbCorreoUsuario.setText(usuario.getCorreo());
                lbDireccionUsuario.setText(usuario.getDomicilio());
                lbTelefonoUsuario.setText(usuario.getTelefono());
                
                //¿El usuario del sistema dio clic en "Solicitar renovación"?
                if(esRenovacion){
                    cargarTablaRenovacion(usuario);
                }else{
                    cargarTabla(usuario);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void registrarDevolucion(ActionEvent event) {
        RecursoDocumental recursoFila = verificarRecursoSeleccionado();
        
        //¿Se seleccionó un recurso documental de la tabla?
        if(recursoFila != null){
            boolean eliminar = Utilidades.mostrarDialogoConfirmacion("Registrar devolución", 
                    "¿Deseas devolver el recurso y eliminar el préstamo");
            
            //¿El usuario del sistema dio clic en "Aceptar"?
            if(eliminar){
                try{
                    ResultadoOperacion devolucion = PrestamoDAO.registrarDevolucion(recursoFila.getIdRecurso());
                    
                    //¿La operación marca error?
                    if(!devolucion.isError()){
                        Utilidades.mostrarAlertaSimple("Aviso", "Recurso documental devuelto", Alert.AlertType.CONFIRMATION);
                        
                        String nombreUsuario = tfIdentificador.getText();
                        ArrayList<UsuarioBiblioteca> prestamosBD = UsuarioBibliotecaDAO.obtenerUsuario(nombreUsuario);
                        
                        for(UsuarioBiblioteca usuario : prestamosBD){
                            cargarTabla(usuario);
                        }
                    }else{
                        Utilidades.mostrarAlertaSimple("Error", "No se pudo registrar la devolución", Alert.AlertType.ERROR);
                    }
                } catch (SQLException ex) {
                    Utilidades.mostrarAlertaSimple("Error", "Falló la conexión con la base de datos.\nInténtelo más tarde", 
                            Alert.AlertType.ERROR);
                }
            }
        }else{
            Utilidades.mostrarAlertaSimple("", "Debe seleccionar primero un recurso documental.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void solicitarRenovacion(ActionEvent event) {
        RecursoDocumental recursoFila = verificarRecursoSeleccionado();
        
        //¿Se seleccionó un recurso documental de la tabla?
        if(recursoFila != null){
            try {                
                ResultadoOperacion renovacion = PrestamoDAO.registrarRenovacion(recursoFila.getIdRecurso());
                
                //¿La operación marca error?
                if(!renovacion.isError()){
                    java.sql.Date nuevaFechaEntrega = PrestamoDAO.obtenerFechaEntrega(recursoFila.getIdRecurso());
                    DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                    String fechaNuevaEntrega = formatoFecha.format(nuevaFechaEntrega);
                    
                    Utilidades.mostrarAlertaSimple("Registro de renovación exitoso", 
                            "Fecha de entrega actualizada.\nLa nueva fecha es: "+fechaNuevaEntrega, 
                            Alert.AlertType.CONFIRMATION);
                }else{
                    Utilidades.mostrarAlertaSimple("Error", "No se pudo registrar la renovación. Inténtelo más tarde", 
                            Alert.AlertType.ERROR);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }else{
            Utilidades.mostrarAlertaSimple("", "Debe seleccionar primero un recurso documental", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void volver(ActionEvent event) {
        Stage escenario = (Stage) tfIdentificador.getScene().getWindow();
        escenario.close();
    }
    
    private void configurarTabla(){
        tcRecursoDocumental.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcIDRecursoDocumental.setCellValueFactory(new PropertyValueFactory("idBiblioteca"));
    }
    
    private void cargarTablaRenovacion(UsuarioBiblioteca usuario){
        try{
            listaRecursos = FXCollections.observableArrayList();
            ArrayList<RecursoDocumental> recursoBD = RecursoDocumentalDAO.obtenerNombreRecursoPrestamosRenovacion(usuario);
            listaRecursos.addAll(recursoBD);
            tvRecursos.setItems(listaRecursos);
        }catch(SQLException | NullPointerException e){
            e.printStackTrace();
        }
    }
    
    private void cargarTabla(UsuarioBiblioteca usuario){
        try{
            listaRecursos = FXCollections.observableArrayList();
            ArrayList<RecursoDocumental> recursoBD = RecursoDocumentalDAO.obtenerNombreRecursoPrestamos(usuario);
            listaRecursos.addAll(recursoBD);
            tvRecursos.setItems(listaRecursos);
        }catch(SQLException | NullPointerException e){
            e.printStackTrace();
        }
    }
    
    private RecursoDocumental verificarRecursoSeleccionado(){ 
        int filaSeleccionada = tvRecursos.getSelectionModel().getSelectedIndex();
        return (filaSeleccionada >= 0) ? listaRecursos.get(filaSeleccionada) : null;
    }
    
    public void valorTituloVentana(boolean tituloVentana, boolean renovacion){
        //¿El usuario del sistema dio clic en "Registrar devolución" o en "Solicitar renovación"?
        if(!tituloVentana){
            lbTitulo.setText("Solicitar renovación");
            esRenovacion = renovacion;
            btnDevolucion.setDisable(true);
        }else{
            btnRenovacion.setDisable(true);
        }
    }
}