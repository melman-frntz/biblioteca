package biblioteca.modelo.pojo;

public class UsuarioStaff {
    
    private String numeroDePersonal;
    private String contraseña;
    private String tipoUsuario;
    private byte[] fotoPerfil;

    public UsuarioStaff() {
    }

    public UsuarioStaff(String numeroDePersonal, String contraseña, String tipoUsuario, byte[] fotoPerfil) {
        this.numeroDePersonal = numeroDePersonal;
        this.contraseña = contraseña;
        this.tipoUsuario = tipoUsuario;
        this.fotoPerfil = fotoPerfil;
    }

    public String getNumeroDePersonal() {
        return numeroDePersonal;
    }

    public void setNumeroDePersonal(String numeroDePersonal) {
        this.numeroDePersonal = numeroDePersonal;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public byte[] getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(byte[] fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
    
    
    
}
