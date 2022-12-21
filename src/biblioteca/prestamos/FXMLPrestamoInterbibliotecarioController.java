package biblioteca.prestamos;

import biblioteca.modelo.dao.CampusDAO;
import biblioteca.modelo.dao.FacultadDAO;
import biblioteca.modelo.dao.PrestamoDAO;
import biblioteca.modelo.dao.RecursoDocumentalDAO;
import biblioteca.modelo.dao.UsuarioBibliotecaDAO;
import biblioteca.modelo.pojo.Campus;
import biblioteca.modelo.pojo.Facultad;
import biblioteca.modelo.pojo.Prestamo;
import biblioteca.modelo.pojo.RecursoDocumental;
import biblioteca.modelo.pojo.ResultadoOperacion;
import biblioteca.modelo.pojo.UsuarioBiblioteca;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilidades.Utilidades;

public class FXMLPrestamoInterbibliotecarioController implements Initializable {

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
    @FXML
    private TextField txtCarrera;
    @FXML
    private TextField txtFechaInicio;
    @FXML
    private TextField txtFechaEntrega;
    @FXML
    private TextField txtOrigen;
    @FXML
    private ComboBox<Campus> cbCampus;
    @FXML
    private ComboBox<Facultad> cbFacultad;
    UsuarioBiblioteca usuarioBiblioteca;
    private ObservableList<Facultad> listaFacultades;
    private ObservableList<Campus> listaCampus;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        InicializarFechas();
        cargarListaCampus();
        cbCampus.valueProperty().addListener(new ChangeListener<Campus>(){
            @Override
            public void changed(ObservableValue<? extends Campus> observable, Campus oldValue, Campus newValue) {
                if(newValue != null){
                    cargarListaFacultades(newValue.getIdCampus());
                }
            }
            
        });
    }

    @FXML
    private void clicBttnOpcionCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clicBttnOpcionBuscar(ActionEvent event) {
            buscarRecursoDocumental();
    }

    @FXML
    private void clicBttnOpcionGuardarPrestamo(ActionEvent event) {
        boolean camposLlenos = validarCamposLlenos();
        boolean numeroPrestamosExcedidos = validarNumeroPrestamos();
        if(camposLlenos){
            if(numeroPrestamosExcedidos == false){
                registrarPrestamo();
                actualizarEstadoRecursoDocumental();
                cerrarVentana();
            }else{
                Utilidades.mostrarAlertaSimple("Prestamos excedidos", "El usuario de la biblioteca excedio los prestamos permitidos.", Alert.AlertType.INFORMATION);
                cerrarVentana();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Campos vacios", "Todos los campos deben estar llenos.", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void clicBttnOpcionBuscarUsuarioBiblioteca(ActionEvent event) {
            buscarUsuarioBiblioteca();
    }
    
    private void buscarRecursoDocumental(){
        try {
            RecursoDocumental recursoEncontrado = RecursoDocumentalDAO.buscarRecursoPorFolio(txtBusquedaRecurso.getText());
            if(recursoEncontrado != null){
                txtFolio.setText(recursoEncontrado.getFolio());
                txtTipoRecurso.setText(recursoEncontrado.getTipoRecurso());
                txtNombreRecurso.setText(recursoEncontrado.getNombre());
                txtAutorRecurso.setText(recursoEncontrado.getAutor());                
            }else{
                Utilidades.mostrarAlertaSimple("Error de busqueda", "No se proporciono ningun nombre, falta algun caracter, no existe un recurso documental con este folio o ha sido prestado.", Alert.AlertType.INFORMATION);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
    
    private void buscarUsuarioBiblioteca(){
        try{
            this.usuarioBiblioteca = UsuarioBibliotecaDAO.obtenerUsuarioPorId(txtIdUsuario.getText());
            if(usuarioBiblioteca != null){
                txtNombreUsuario.setText(usuarioBiblioteca.getNombre());
                txtCarrera.setText(usuarioBiblioteca.getCarrera());               
            }else{
                Utilidades.mostrarAlertaSimple("Error de busqueda", "No se proporciono ninguna matricula/numero de personal, falta algun caracter o no existe un usuario con este nombre.", Alert.AlertType.INFORMATION);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    private void cerrarVentana(){
        Stage escenario = (Stage) txtFolio.getScene().getWindow();
        escenario.close();
    }
    
    private boolean validarCamposLlenos(){
        boolean camposLlenos;
        String idUsuarioBiblioteca = txtIdUsuario.getText();
        String nombreUsuario = txtNombreUsuario.getText();
        String carreraUsuario = txtCarrera.getText();
        Facultad destino = cbFacultad.getSelectionModel().getSelectedItem();
        String folio = txtFolio.getText();
        String tipoRecurso = txtTipoRecurso.getText();
        String nombreRecurso = txtNombreRecurso.getText();
        String autor = txtAutorRecurso.getText();
        
        if(idUsuarioBiblioteca.isEmpty()||nombreUsuario.isEmpty()||carreraUsuario.isEmpty()||destino == null||folio.isEmpty()||tipoRecurso.isEmpty()||nombreRecurso.isEmpty()||autor.isEmpty()){
            camposLlenos = false;
        }else{
            camposLlenos = true;
        }
        return camposLlenos;
    }
    
    private void registrarPrestamo(){
        Prestamo prestamo = new Prestamo();
        int idRecurso, idUsuarioBiblioteca;
        
        try{
            prestamo.setFechaInicio(java.sql.Date.valueOf(txtFechaInicio.getText()));
            prestamo.setFechaEntrega(java.sql.Date.valueOf(txtFechaEntrega.getText()));
            prestamo.setDestino(cbFacultad.getSelectionModel().getSelectedItem().toString());
            prestamo.setOrigen(txtOrigen.getText());
            prestamo.setTipoPrestamo("Interbibliotecario");
            idRecurso = RecursoDocumentalDAO.obtenerIdRecursoDocumental(txtFolio.getText());
            prestamo.setIdRecurso(idRecurso);
            prestamo.setIdUsuarioBiblioteca(usuarioBiblioteca.getId());
            
            if(idRecurso>-1){
                ResultadoOperacion resultadoOP = PrestamoDAO.registrarPrestamoInterbibliotecario(prestamo);
                if(!resultadoOP.isError()){
                    Utilidades.mostrarAlertaSimple("Prestamo exitoso", "El prestamo fue registrado exitosamente.", Alert.AlertType.INFORMATION);
                }else{
                    Utilidades.mostrarAlertaSimple("ERROR", resultadoOP.getMensaje(), Alert.AlertType.ERROR);
                }
            }else{
                Utilidades.mostrarAlertaSimple("Recurso no encontrado", "Error al encontrar el recurso documental.", Alert.AlertType.ERROR);
            }
                
            } catch (SQLException ex) {
                Utilidades.mostrarAlertaSimple("Algo salió mal", "Hubo un error al comunicarse con la base de datos. "
                        + "Por favor inténtelo más tarde.", Alert.AlertType.ERROR);
            }
    }
    
    private void InicializarFechas(){
        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaEntrega = fechaInicio.now().plusDays(14);
        txtFechaInicio.setText(fechaInicio.toString());
        txtFechaEntrega.setText(fechaEntrega.toString());
    }
    
    private void actualizarEstadoRecursoDocumental(){
        try{
            ResultadoOperacion resultadoOP = RecursoDocumentalDAO.actualizarEstadoRecursoDocumental(txtFolio.getText());
            if(!resultadoOP.isError()){
                    Utilidades.mostrarAlertaSimple("Reecurso Actualizado", "Estado: Prestado.", Alert.AlertType.INFORMATION);
            }else{
                    Utilidades.mostrarAlertaSimple("ERROR", resultadoOP.getMensaje(), Alert.AlertType.ERROR);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    private boolean validarNumeroPrestamos(){
        boolean numeroPrestamosExcedidos = false;
        try{
            ArrayList<Prestamo> prestamosBD = PrestamoDAO.obtenerPrestamosPorIdUsuario(usuarioBiblioteca.getId());
            if(prestamosBD.size() < 4){
                numeroPrestamosExcedidos = false;
            }else{
                numeroPrestamosExcedidos = true;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return numeroPrestamosExcedidos;
    }
    
    private void cargarListaCampus(){
        listaCampus = FXCollections.observableArrayList();
        try{
            ArrayList<Campus> campusBD = CampusDAO.obtenerCampus();
            listaCampus.addAll(campusBD);
            cbCampus.setItems(listaCampus);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    private void cargarListaFacultades(int idCampus){
        listaFacultades = FXCollections.observableArrayList();
        try{
            ArrayList<Facultad> facultadesBD = FacultadDAO.obtenerFacultadPorCampus(idCampus);
            listaFacultades.addAll(facultadesBD);
            cbFacultad.setItems(listaFacultades);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}