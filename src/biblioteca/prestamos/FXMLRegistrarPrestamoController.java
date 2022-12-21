package biblioteca.prestamos;

/*
*@author manuel
*Fecha creacion: 09/12/2022
*Fecha de ultima modificacion: 20/12/2022
*Ultimo modificador: manuel
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import utilidades.Constantes;
import utilidades.Utilidades;

public class FXMLRegistrarPrestamoController implements Initializable {

    @FXML
    private Label lbErrorIdUsuario;
    @FXML
    private Label lbErrorIdRecurso;
    @FXML
    private TextField tfIdUsuario;
    @FXML
    private TableView<UsuarioBiblioteca> tvUsuario;
    @FXML
    private TableColumn colNombreUsuario;
    @FXML
    private TableColumn colTelUsuario;
    @FXML
    private TableColumn colDomicilioUsuario;
    @FXML
    private TableColumn colCorreoUsuario;
    @FXML
    private TextField tfIdRecursoDocumental;
    @FXML
    private TableView<RecursoDocumental> tvRecursoDocumental;
    @FXML
    private TableColumn colNombreRecurso;
    @FXML
    private TableColumn colAutorRecurso;
    @FXML
    private TableColumn colTipoRecurso;
    @FXML
    private TableColumn colSecciónRecurso;
    
    private ObservableList<UsuarioBiblioteca> listaUsuario;
    
    private ObservableList<RecursoDocumental> listaRecursoDocumental;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTablaUsuario();
        configurarTablaRecurso();
    }    

    @FXML
    private void clicBtnBuscarUsuario(ActionEvent event) {
        String idUsuario = tfIdUsuario.getText();
        if(!idUsuario.isEmpty()){
            lbErrorIdUsuario.setText("");
            buscarUsuario(idUsuario);
        }else{
            lbErrorIdUsuario.setText("No se ha ingresado un usuario");
        }
    }

    @FXML
    private void clicBtnBuscarRecursoDocumental(ActionEvent event) {
        if(!tfIdRecursoDocumental.getText().isEmpty()){
            int idRecursoDocumental = Integer.valueOf(tfIdRecursoDocumental.getText());
            lbErrorIdRecurso.setText("");
            buscarRecursoDocumental(idRecursoDocumental);
        }else{
            lbErrorIdRecurso.setText("No se ha ingresado un  recurso documental");
        }
    }

    @FXML
    private void clicBtnContinuar(ActionEvent event) {
        ObservableList usuarioTV = tvUsuario.getItems();
        ObservableList recursoTV = tvRecursoDocumental.getItems();
        
        
        if(!(usuarioTV.isEmpty()) &&  !(recursoTV.isEmpty())){
            tvUsuario.getSelectionModel().select(0);
            tvRecursoDocumental.getSelectionModel().select(0);
            UsuarioBiblioteca usuario = tvUsuario.getSelectionModel().getSelectedItem();
            try {
                ArrayList<Prestamo> prestamosBD = PrestamoDAO.obtenerPrestamosPorIdUsuario(usuario.getId());
                if(prestamosBD.size() < Constantes. NUMERO_PRESTAMOS_PERMITIDOS){
                    RecursoDocumental recursoDocumental = tvRecursoDocumental.getSelectionModel().getSelectedItem();
                    
                    if(recursoDocumental.getEstado().toLowerCase().equals("disponible")){
                        LocalDateTime fechaEntrega = LocalDateTime.now().plusDays(7);
                        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        ResultadoOperacion  registrarPrestamo = PrestamoDAO.registrarPrestamoADomicilio(usuario, recursoDocumental.getIdRecurso());
                        if(!registrarPrestamo.isError()){
                            Utilidades.mostrarAlertaSimple("Confirmación de registro", "El préstamo ha sido registrado.\n"
                                    + "Fecha de devolución: " + formatoFecha.format(fechaEntrega), Alert.AlertType.INFORMATION);
                            tfIdUsuario.setText("");
                            tfIdRecursoDocumental.setText("");
                            tvRecursoDocumental.getItems().clear();
                            tvUsuario.getItems().clear();
                        }else{
                            Utilidades.mostrarAlertaSimple("ERROR", registrarPrestamo.getMensaje(), Alert.AlertType.ERROR);
                        }
                    }else{
                        Utilidades.mostrarAlertaSimple("Recurso no disponible", "El recurso que solicitó no se encuentra disponible.", Alert.AlertType.WARNING);
                    } 
                }else{
                    Utilidades.mostrarAlertaSimple("Limite excedido", "El usuario ya cuenta con el límite permitido "
                            + "de préstamos a domicilio.", Alert.AlertType.WARNING);
                }                
                
            } catch (SQLException ex) {
                Utilidades.mostrarAlertaSimple("Algo salió mal", "Hubo un error al comunicarse con la base de datos. "
                        + "Por favor inténtelo más tarde.", Alert.AlertType.ERROR);
            }
        }else{
            Utilidades.mostrarAlertaSimple("Selección", "No ha seleccionado algun usuario o recurso", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage cerrar = (Stage) source.getScene().getWindow();
        cerrar.close();
    }
    
    private void buscarUsuario(String idUsuario){
        try {
            listaUsuario = FXCollections.observableArrayList();
            ArrayList<UsuarioBiblioteca> usuarioBD = UsuarioBibliotecaDAO.buscarUsuarioPorId(idUsuario);
            
            if(usuarioBD.isEmpty()){
                Utilidades.mostrarAlertaSimple("Usuario no encontrado", "El usuario que digitó no se encuentra "
                        + "registrado en el sistema", Alert.AlertType.WARNING);
                return;
            }
            
            for(UsuarioBiblioteca usuarioBilioteca : usuarioBD){
                if(!listaUsuario.contains(usuarioBilioteca)){
                    listaUsuario.add(usuarioBilioteca);
                    tvUsuario.setItems(listaUsuario); 
                }else{
                Utilidades.mostrarAlertaSimple("Usuario no encontrado", "El usuario que digitó no se encuentra "
                        + "registrado en el sistema", Alert.AlertType.WARNING);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void configurarTablaUsuario(){
        colNombreUsuario.setCellValueFactory(new PropertyValueFactory("nombre"));
        colTelUsuario.setCellValueFactory(new PropertyValueFactory("telefono"));
        colDomicilioUsuario.setCellValueFactory(new PropertyValueFactory("domicilio"));
        colCorreoUsuario.setCellValueFactory(new PropertyValueFactory("correo"));
    }

    private void buscarRecursoDocumental(int idRecursoDocumental) {
        try {
             listaRecursoDocumental = FXCollections.observableArrayList();
            RecursoDocumental recursoDocumental = RecursoDocumentalDAO.buscarRecursoDocumentalPorId(idRecursoDocumental);
            if(recursoDocumental.getIdRecurso() != 0){
                if(recursoDocumental.getEstado().toLowerCase().equals("disponible")){
                    listaRecursoDocumental.add(0, recursoDocumental);
                    tvRecursoDocumental.setItems(listaRecursoDocumental);
                }else{
                    Utilidades.mostrarAlertaSimple("Recurso no disponible", "El recurdo documental que solicitó no se encuentra"
                            + " disponible.", Alert.AlertType.WARNING);
                }
            }else{
                Utilidades.mostrarAlertaSimple("Error", "El recurso documental no se encuentra registrado.", Alert.AlertType.WARNING);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    private void configurarTablaRecurso(){
        colNombreRecurso.setCellValueFactory(new PropertyValueFactory("nombre"));
        colAutorRecurso.setCellValueFactory(new PropertyValueFactory("autor"));
        colTipoRecurso.setCellValueFactory(new PropertyValueFactory("tipoRecurso"));
        colSecciónRecurso.setCellValueFactory(new PropertyValueFactory("seccion"));
    }
    
    private ArrayList<RecursoDocumental> filtrarRecursosDisponibles(ArrayList<RecursoDocumental> recursosBD){
        ArrayList<RecursoDocumental> recursosFiltrados = null;
        
        if(recursosBD != null){
            recursosFiltrados = new ArrayList<>();
            
            for(int i =  0; i < recursosBD.size();i++){
                if(recursosBD.get(i).getEstado().toLowerCase().equals("disponible")){
                    recursosFiltrados.add(recursosBD.get(i));
                }
            }
        }
        return recursosFiltrados;
    }

    @FXML
    private void restringirDatos(KeyEvent event) {
        tfIdRecursoDocumental.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String valorPrevio, String valorNuevo) {
            if (!valorNuevo.matches("\\d*")) {
                tfIdRecursoDocumental.setText(valorNuevo.replaceAll("[^\\d]", ""));
                lbErrorIdRecurso.setText("No se pueden escribir valores no numericos");
            }
        }
    });
    }
    
}
