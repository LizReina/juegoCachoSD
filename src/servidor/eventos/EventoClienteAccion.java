/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor.eventos;

import java.util.EventObject;

/**
 *
 * @author bismart
 */
public class EventoClienteAccion extends EventObject {
      private String mensaje;
      
    
    public EventoClienteAccion(Object source, String mensaje) {
        super(source);
        this.mensaje = mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }


}
