package biblioteca.modelo.pojo;

/*
*@author froylan
*Fecha creacion: 08/12/2022
*Fecha de ultima modificacion: 12/12/2022
*Ultimo modificador: enrique
*/


public class ResultadoOperacion {
    private boolean error;
    private String mensaje;
    private int filasAfectadas;
    
    public ResultadoOperacion(){
        
    }

    public ResultadoOperacion(boolean error, String mensaje, int filasAfectadas) {
        this.error = error;
        this.mensaje = mensaje;
        this.filasAfectadas = filasAfectadas;
    }

    public boolean isError() {
        return error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public int getFilasAfectadas() {
        return filasAfectadas;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setFilasAfectadas(int filasAfectadas) {
        this.filasAfectadas = filasAfectadas;
    }
}
