/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import java.util.EventObject;
import servidor.eventos.Mensaje;

/**
 *
 * @author bismart
 */
public class EventoParaCliente  extends EventObject{

        private String mensaje;
        private ClienteDatos cliente;
    public EventoParaCliente(Object source,ClienteDatos cliente, String mensaje) {
        super(source);
        this.cliente = cliente; 
        this.mensaje = mensaje;
    }

    public ClienteDatos getCliente() {
        return cliente;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
}
