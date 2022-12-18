package biblioteca;

import biblioteca.modelo.dao.UsuarioStaffDAO;
import biblioteca.modelo.pojo.ResultadoOperacion;
import biblioteca.modelo.pojo.UsuarioStaff;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import utilidades.Utilidades;

public class FXMLRegistroController implements Initializable {

    @FXML
    private TextField txtNoPersonal;
    @FXML
    private Button bttnSeleccionarFoto;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private ComboBox<String> cbTipoUsuario;
    @FXML
    private Label lbAdvertencia;
    @FXML
    private ImageView ivFotoUsuarioStaff;
    private File archivoFotoUsuarioStaff;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarComponenteCombo();
    }    

    @FXML
    private void clicBttnOpcionCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clicBttnOpcionCompletarRegistro(ActionEvent event) {
        if(verificarCamposLlenos()){
            registrarUsuarioStaff();
        }
    }

    @FXML
    private void clicBttnSeleccionarFoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar foto");
        FileChooser.ExtensionFilter filtrarDialogo = new FileChooser.ExtensionFilter("Archivos PNG (*.png)","*.PNG");
        fileChooser.getExtensionFilters().add(filtrarDialogo);
        Stage escenario;
        escenario = (Stage) ivFotoUsuarioStaff.getScene().getWindow();
        archivoFotoUsuarioStaff = fileChooser.showOpenDialog(escenario);
        
        if(archivoFotoUsuarioStaff != null){
            try{
                BufferedImage bufferImagen = ImageIO.read(archivoFotoUsuarioStaff);
                Image imgFile = SwingFXUtils.toFXImage(bufferImagen, null);
                ivFotoUsuarioStaff.setImage(imgFile);
            }catch(IOException excepcion){
                Utilidades.mostrarAlertaSimple("Error", "Error al mostrar la foto.", Alert.AlertType.ERROR);
            }
        }else{
            Utilidades.mostrarAlertaSimple("Foto no seleccionada", "Debe seleccionar una foto.", Alert.AlertType.ERROR);
        }
    }
    
    private void inicializarComponenteCombo(){
        ObservableList listaTipoUsuario = FXCollections.observableArrayList();
        listaTipoUsuario.addAll("Encargado de biblioteca","Bibliotecario","Usuario de biblioteca","Secretaria");
        cbTipoUsuario.setItems(listaTipoUsuario);
    }
    
    private void cerrarVentana(){
        Stage escenarioPrincipal = (Stage) txtNoPersonal.getScene().getWindow();
        escenarioPrincipal.close();
    }
    
    private boolean verificarCamposLlenos(){
        boolean valido;
        lbAdvertencia.setText("");
        
        if((txtNoPersonal.getText() != null) && (txtPassword.getText() != null) && (cbTipoUsuario.getValue() != null) && (ivFotoUsuarioStaff.getImage() != null)){
            valido = true;
        }else{
            valido = false;
            lbAdvertencia.setText("¡Verifique que todos los campos esten llenos!");
        }
        return valido;
    }
    
    private void registrarUsuarioStaff(){
        UsuarioStaff usuarioStaff = new UsuarioStaff();
        usuarioStaff.setNumeroDePersonal(txtNoPersonal.getText());
        usuarioStaff.setContraseña(txtPassword.getText());
        usuarioStaff.setTipoUsuario(cbTipoUsuario.getValue());
        
        try{
            ResultadoOperacion resultadoGuardar = UsuarioStaffDAO.registrarUsuarioStaff(usuarioStaff, archivoFotoUsuarioStaff);
            if(!resultadoGuardar.isError()){
                Utilidades.mostrarAlertaSimple("Usuario staff registrado", resultadoGuardar.getMensaje(), Alert.AlertType.INFORMATION);
                cerrarVentana();
            }else{
                Utilidades.mostrarAlertaSimple("Error al guardar", resultadoGuardar.getMensaje(), Alert.AlertType.ERROR);
            }
        }catch(SQLException ex){
            Utilidades.mostrarAlertaSimple("Error de conexion", "No hay conexion con la base de datos.", Alert.AlertType.ERROR);
        }
    }
}