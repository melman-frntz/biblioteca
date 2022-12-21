package utilidades;

/*
*@author froylan
*Fecha creacion: 09/12/2022
*Fecha de ultima modificacion: 16/12/2022
*Ultimo modificador: froylan
*/


import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Utilidades {
    public static void mostrarAlertaSimple(String titulo, String mensaje, Alert.AlertType tipo){
        Alert alertaSimple = new Alert(tipo);
        alertaSimple.setTitle(titulo);
        alertaSimple.setContentText(mensaje);
        alertaSimple.setHeaderText(null);
        alertaSimple.showAndWait();
        
    }
    
    public static boolean mostrarDialogoConfirmacion(String titulo, String mensaje){
        Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        alertaConfirmacion.setTitle(titulo);
        alertaConfirmacion.setContentText(mensaje);
        alertaConfirmacion.setHeaderText(null);
        Optional<ButtonType> respuesta = alertaConfirmacion.showAndWait();
        
        return respuesta.get() == ButtonType.OK;
    }
}
