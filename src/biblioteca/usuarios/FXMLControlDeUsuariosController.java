package biblioteca.usuarios;

/*
*@author franz
*Fecha creacion: 08/12/2022
*Fecha de ultima modificacion: 20/12/2022
*Ultimo modificador: franz
*/

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class FXMLControlDeUsuariosController implements Initializable {

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
