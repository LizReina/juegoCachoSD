/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.net.Socket;
import javax.swing.event.EventListenerList;
import servidor.escuchadores.EscuchadorConexiones;
import servidor.escuchadores.EscuchadorMensajes;
import servidor.eventos.EventoCliente;
import servidor.eventos.EventoClienteAccion;
import servidor.eventos.EventoMensaje;

/**
 *
 * @author Marcelo
 */
public class Despachador {
    
    public static EventListenerList listaEscuchadores = new EventListenerList();
    
    public static void DespacharEventoConexion(EventoCliente eventoCliente) {
        EscuchadorConexiones[] escuchadores = listaEscuchadores.getListeners(EscuchadorConexiones.class);
        for (EscuchadorConexiones escuchador : escuchadores) {
            escuchador.OnClienteConectado(eventoCliente);
            System.out.println(eventoCliente.getCliente().getId());
        }
    }
    
    public static void DespacharEventoDesconexion(EventoCliente eventoCliente) {
        EscuchadorConexiones[] escuchadores = listaEscuchadores.getListeners(EscuchadorConexiones.class);
        for (EscuchadorConexiones escuchador : escuchadores) {
            escuchador.OnClienteDesconectado(eventoCliente);
        }
    }
    
    
    
  public static void DespacharEventoMensajeRecibido(EventoMensaje eventoMensaje) {
        EscuchadorMensajes[] escuchadores = listaEscuchadores.getListeners(EscuchadorMensajes.class);
        for (EscuchadorMensajes escuchador : escuchadores) {
            escuchador.OnMensajeRecivido(eventoMensaje);
        }
    }
  
        public static void DespacharEventoLogin(EventoClienteAccion eventClientd){
            EscuchadorMensajes[] escuchadores = listaEscuchadores.getListeners(EscuchadorMensajes.class);
        for (EscuchadorMensajes escuchador : escuchadores) {
            escuchador.OnLogin(eventClientd);
        }
   
    }
    
        
       public static void DespacharEventoRegister(EventoClienteAccion eventClient){
           EscuchadorMensajes[] escuchadores = listaEscuchadores.getListeners(EscuchadorMensajes.class);
        for (EscuchadorMensajes escuchador : escuchadores) {
            escuchador.OnRegister(eventClient);
        }
   
    }
       
       public static void DespacharNotificacion(EventoCliente eventClient) {
        EscuchadorMensajes[] escuchadores = listaEscuchadores.getListeners(EscuchadorMensajes.class);
        for (EscuchadorMensajes escuchador : escuchadores) {
            escuchador.notificarClientesLogueados(eventClient);
        }
   
    }
       
         public static void DespacharNotificacionParaTablero(EventoClienteAccion eventClient) {
        EscuchadorMensajes[] escuchadores = listaEscuchadores.getListeners(EscuchadorMensajes.class);
        for (EscuchadorMensajes escuchador : escuchadores) {
            escuchador.notificarClientesParaTablero(eventClient);
        }
   
    }
       
         public static void DespacharEventoDesLogeado(EventoCliente eventClientd){
            EscuchadorMensajes[] escuchadores = listaEscuchadores.getListeners(EscuchadorMensajes.class);
        for (EscuchadorMensajes escuchador : escuchadores) {
            escuchador.notificarClientesDesLogueados(eventClientd);
        }
   
    }
         
           public static void DespacharSalirDelJuego(EventoClienteAccion eventClientd){
            EscuchadorMensajes[] escuchadores = listaEscuchadores.getListeners(EscuchadorMensajes.class);
        for (EscuchadorMensajes escuchador : escuchadores) {
            escuchador.salirDelJuego(eventClientd);
        }
   
    }
           
            public static void DespacharNotificarSalirJuego(EventoCliente eventClientd){
            EscuchadorMensajes[] escuchadores = listaEscuchadores.getListeners(EscuchadorMensajes.class);
        for (EscuchadorMensajes escuchador : escuchadores) {
            escuchador.notificarClientesSalirJuego(eventClientd);
        }
   
    }
            
       public static void DespacharNotificacionAll(EventoCliente eventClient,String protocolo) {
        EscuchadorMensajes[] escuchadores = listaEscuchadores.getListeners(EscuchadorMensajes.class);
        for (EscuchadorMensajes escuchador : escuchadores) {
            escuchador.notificarAll(eventClient,protocolo);
        }
   
    }
        public static void DespacharIniciarJuego(EventoClienteAccion eventClientd){
            System.out.println("ENTRO ini");
              EscuchadorMensajes[] escuchadores = listaEscuchadores.getListeners(EscuchadorMensajes.class);
        for (EscuchadorMensajes escuchador : escuchadores) {
            escuchador.iniciarJuego(eventClientd);
        }
   
    }    
        
     public static void DespacharNotificacionTableroGrande(EventoClienteAccion eventClient) {
        EscuchadorMensajes[] escuchadores = listaEscuchadores.getListeners(EscuchadorMensajes.class);
        for (EscuchadorMensajes escuchador : escuchadores) {
            escuchador.notificarTableroGrande(eventClient);
        }

    }
     
       public static void DespacharNotificarGanador(EventoClienteAccion eventClient) {
        EscuchadorMensajes[] escuchadores = listaEscuchadores.getListeners(EscuchadorMensajes.class);
        for (EscuchadorMensajes escuchador : escuchadores) {
            escuchador.notificarGanador(eventClient);
        }

    }
    
         public static void DespacharNotificarATodos(EventoCliente eventClient,String protocolo) {
        EscuchadorMensajes[] escuchadores = listaEscuchadores.getListeners(EscuchadorMensajes.class);
        for (EscuchadorMensajes escuchador : escuchadores) {
            escuchador.notificarATodos(eventClient,protocolo);
        }
   
    }
     
}
