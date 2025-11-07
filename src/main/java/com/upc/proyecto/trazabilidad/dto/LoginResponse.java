package com.upc.proyecto.trazabilidad.dto;

public class LoginResponse {
    private String token;
    private String correo;

    public LoginResponse(String token, String correo) {
        this.token = token;
        this.correo = correo;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
