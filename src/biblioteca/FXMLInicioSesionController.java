package biblioteca;

import biblioteca.modelo.dao.UsuarioStaffDAO;
import biblioteca.modelo.pojo.UsuarioStaff;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utilidades.Utilidades;

public class FXMLInicioSesionController implements Initializable {

    @FXML
    private TextField tfNoPersonal;
    @FXML
    private PasswordField pfContraseña;
    @FXML
    private Label lbErrorNoPersonal;
    @FXML
    private Label lbErrorContraseña;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicBtnIniciarSesion(ActionEvent event) {
        String noPersonal = tfNoPersonal.getText();
        String contraseña = pfContraseña.getText();
        boolean valido = true;
        lbErrorNoPersonal.setText("");
        lbErrorContraseña.setText("");
        
        if(noPersonal.isEmpty()){
            valido = false;
            lbErrorNoPersonal.setText("El campo es requerido");
        }
        
        if(contraseña.isEmpty()){
            valido = false;
            lbErrorContraseña.setText("El campo es requerido");
        }
        
        if(valido){
            verificarCredencialesUsuario(noPersonal, contraseña);
        }
    }
    
    private void verificarCredencialesUsuario(String noPersonal, String contraseña) {
        try {
            UsuarioStaff usuarioSesion = UsuarioStaffDAO.verificarUsuario(noPersonal, contraseña);
            if(usuarioSesion.getNumeroDePersonal() != null){
                irPantallaPrincipal();
            }else{
                Utilidades.mostrarAlertaSimple("Credenciales incorrectas", "El número de personal y/o contraseña es incorrecto, favor de "
                        + "verificar", Alert.AlertType.WARNING);
            }
        } catch (SQLException | NullPointerException e) {
            Utilidades.mostrarAlertaSimple("Error de conexión", "Hubo un error en el proceso de "
                    + "comunicación. Intentelo más tarde.", Alert.AlertType.ERROR);
        }
    }
    
    private void irPantallaPrincipal(){
        try {
            Parent vista = FXMLLoader.load(getClass().getResource("FXMLPrincipal.fxml"));
            Scene escenaPrincipal = new Scene(vista);
            Stage escenarioBase = (Stage) tfNoPersonal.getScene().getWindow();
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clicBttnRegistro(ActionEvent event) {
        irPantallaRegistro();
    }
    
    private void irPantallaRegistro(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLRegistro.fxml"));
            Parent ventanaRegistro = fxmlLoader.load();
            Scene escenarioRegistro = new Scene(ventanaRegistro);
            Stage nuevoEscenarioRegistro = new Stage();
            nuevoEscenarioRegistro.setScene(escenarioRegistro);
            nuevoEscenarioRegistro.initModality(Modality.APPLICATION_MODAL);
            nuevoEscenarioRegistro.setResizable(false); 
            nuevoEscenarioRegistro.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
