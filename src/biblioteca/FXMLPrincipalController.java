package biblioteca;

/*
*@author franz
*Fecha creacion: 08/12/2022
*Fecha de ultima modificacion: 20/12/2022
*Ultimo modificador: froylan
*/


import biblioteca.papeleo.FXMLPapeleoController;
import biblioteca.prestamos.FXMLControlDePrestamosController;
import biblioteca.recursos.FXMLControlDeRecursosController;
import biblioteca.usuarios.FXMLControlDeUsuariosController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FXMLPrincipalController implements Initializable {
    
    @FXML
    private Label menuOpcion;
    @FXML
    private AnchorPane cintaOpciones;
    @FXML
    private Label menuOpcionCerrar;
    @FXML
    private AnchorPane background;
    @FXML
    private BorderPane borderPaneLayOut;
    private int tipoUsuario;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarOpciones("FXMLInicio");
    }

    @FXML
    private void clicBttnOpcionInicio(ActionEvent event) {
        cargarOpciones("FXMLInicio");
    }

    @FXML
    private void clicBttnOpcionControlPrestamos(ActionEvent event) {
        cargarOpciones("prestamos/FXMLControlDePrestamos");
    }

    @FXML
    private void clicBttnOpcionControlRecursos(ActionEvent event) {
        cargarOpciones("recursos/FXMLControlDeRecursos");       
    }

    @FXML
    private void clicBttnOpcionControlUsuarios(ActionEvent event) {
        cargarOpciones("usuarios/FXMLControlDeUsuarios");
    }

    @FXML
    private void clicBttnOpcionPapeleo(ActionEvent event) {
        cargarOpciones("papeleo/FXMLPapeleo");
    }

    @FXML
    private void clicBttnOpcionCerrarSesion(ActionEvent event) {
        cerrarPrincipal();
        mostrarInicioSesion();
    }

    private void cargarOpciones(String vista){
        try {                  
            borderPaneLayOut.getChildren().remove(borderPaneLayOut.getCenter()); //Remueve fxml existente del centro
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(vista + ".fxml"));  
            Parent vistap = fxmlLoader.load();
            
            if(vista.startsWith("prestamos/")){
                FXMLControlDePrestamosController prestamos = fxmlLoader.getController();
                prestamos.inicializarTipoUsuario(tipoUsuario);
            }
            
            if(vista.startsWith("recursos/")){
                FXMLControlDeRecursosController recursos = fxmlLoader.getController();
                recursos.inicializarTipoUsuario(tipoUsuario);
            }
            
            if(vista.startsWith("usuarios/")){
                FXMLControlDeUsuariosController usuarios = fxmlLoader.getController();
                usuarios.inicializarTipoUsuario(tipoUsuario);
            }
            
            if(vista.startsWith("papeleo/")){
                FXMLPapeleoController papeleo = fxmlLoader.getController();
                papeleo.inicializarTipoUsuario(tipoUsuario);
            }
            
            borderPaneLayOut.setCenter(vistap);
        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void cerrarPrincipal(){
            Stage escenarioPrincipal = (Stage) borderPaneLayOut.getScene().getWindow();
            escenarioPrincipal.close();
    }
    
    private void mostrarInicioSesion(){
        try {
            Parent vista = FXMLLoader.load(getClass().getResource("FXMLInicioSesion.fxml"));
            Scene escenaPrincipal = new Scene(vista);
            Stage escenarioBase = (Stage) borderPaneLayOut.getScene().getWindow();
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
     *Esta seccion del programa inicializa el usuarioBiblioteca de la sesion actual
    */
    public void inicializarTipoUsuario(int tipoUsuario){
        this.tipoUsuario = tipoUsuario;
        
        cargarOpciones("FXMLInicio");
    }
}