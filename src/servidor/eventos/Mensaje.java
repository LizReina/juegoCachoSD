/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor.eventos;

import java.net.Socket;

/**
 *
 * @author Marcelo
 */
public class Mensaje {
    
    private String mensajeCliente;
    private Socket socketCliente;
    private int idClienteEmisor;

    public Mensaje(Socket socketCliente, String mensajeCliente, int idClienteEmisor) {
        this.mensajeCliente = mensajeCliente;
        this.socketCliente = socketCliente;
        this.idClienteEmisor = idClienteEmisor;
    }

    public String getMensajeCliente() {
        return mensajeCliente;
    }

    public void setMensajeCliente(String mensajeCliente) {
        this.mensajeCliente = mensajeCliente;
    }

    public Socket getSocketCliente() {
        return socketCliente;
    }

    public void setSocketCliente(Socket socketCliente) {
        this.socketCliente = socketCliente;
    }

    public int getIdClienteEmisor() {
        return idClienteEmisor;
    }

    public void setIdClienteEmisor(int idClienteEmisor) {
        this.idClienteEmisor = idClienteEmisor;
    }
    
    
    
}
