package biblioteca.modelo.pojo;

public class UsuarioStaff {
    
    private String numeroDePersonal;
    private String contraseña;
    private byte[] fotoPerfil;

    public UsuarioStaff() {
    }

    public UsuarioStaff(String numeroDePersonal, String contraseña, byte[] fotoPerfil) {
        this.numeroDePersonal = numeroDePersonal;
        this.contraseña = contraseña;
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

    public byte[] getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(byte[] fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
    
    
    
}
