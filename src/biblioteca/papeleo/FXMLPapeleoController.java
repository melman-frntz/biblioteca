package biblioteca.papeleo;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class FXMLPapeleoController implements Initializable {

    private int tipoUsuario;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void inicializarTipoUsuario(int tipoUsuario){
        this.tipoUsuario = tipoUsuario;
    }
}
