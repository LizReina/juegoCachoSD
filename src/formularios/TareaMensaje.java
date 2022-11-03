/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import servidor.hilos.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidor.eventos.EventoCliente;

/**
 *
 * @author Marcelo
 */
public class TareaMensaje extends Thread {
    
    private DataOutputStream flujosalida;
    private String mensaje;
    
    public TareaMensaje(ClienteDatos eventoCliente, String mensaje) {
        try {
            //Sugerencia: agregar un atributo DataOutStream en EventoCliente para no crearlo reiteradamente...
            this.flujosalida= new DataOutputStream(eventoCliente.getSocketCliente().getOutputStream());
         // eventoCliente.getCliente().getSocketCliente().getOutputStream().write((mensaje + "\n").getBytes("UTF-8"));
           
            this.mensaje = mensaje;
        } catch (IOException ex) {
            Logger.getLogger(TareaMensaje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
         
            this.flujosalida.writeUTF(mensaje);
           
            System.out.println("tareamensaje: " +mensaje);
            
        } catch (IOException ex) {
            Logger.getLogger(TareaMensaje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
