/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.EventListenerList;




/**
 *
 * @author bismart
 */
public class DespachadorCliente {
    
   //  public static List<IEscuhadorDatosCliente> escuchadorData =new ArrayList<>();
     
    public static EventListenerList listaEscuchadores = new EventListenerList();
      
    public static void DespacharEventoConexion(EventoParaCliente eventoCliente) {
        System.out.println("Despachador Conexion");
       IEscuhadorDatosCliente[] escuchadores = listaEscuchadores.getListeners(IEscuhadorDatosCliente.class);
         for (IEscuhadorDatosCliente escuchador : escuchadores) {
            escuchador.onConnect(eventoCliente);
            System.out.println("entre a on connect: "+ eventoCliente.getCliente().getId());
        }
    }
      
       public static void despacharOnRead(EventoParaCliente eventoCliente){
      EscuchadorMensajes[] escuchadores = listaEscuchadores.getListeners(EscuchadorMensajes.class);
        for (EscuchadorMensajes escuchador : escuchadores) {
            escuchador.OnMensajeRecivido(eventoCliente);
        }
       
    }  
  
      public static void DespacharEventoLogin(EventoParaCliente eventClientd){
            EscuchadorMensajes[] escuchadores = listaEscuchadores.getListeners(EscuchadorMensajes.class);
        for (EscuchadorMensajes escuchador : escuchadores) {
            escuchador.OnLogin(eventClientd);
        }
   
    }
    
       public static void DespacharEventoRegister(EventoParaCliente eventClient){
           EscuchadorMensajes[] escuchadores = listaEscuchadores.getListeners(EscuchadorMensajes.class);
        for (EscuchadorMensajes escuchador : escuchadores) {
            escuchador.OnRegister(eventClient);
        }
   
    }
       
         public static void DespacharEventoDesconexion(EventoParaCliente eventoCliente) {
        IEscuhadorDatosCliente[] escuchadores = listaEscuchadores.getListeners(IEscuhadorDatosCliente.class);
        for (IEscuhadorDatosCliente escuchador : escuchadores) {
            escuchador.onDesconnect(eventoCliente);
        }
    }
         
           public static void DespacharNotificacion(EventoParaCliente eventoCliente) {
        EscuchadorMensajes[] escuchadores = listaEscuchadores.getListeners(EscuchadorMensajes.class);
        for (EscuchadorMensajes escuchador : escuchadores) {
            escuchador.notificacion(eventoCliente);
        }
    }
           
     public static void DespacharGenerarClienteLogueado(ClienteDatos eventoCliente) {
         System.out.println("DespachadorCliente.DespacharGenerarClienteLogueado");
        EscuchadorMensajes[] escuchadores = listaEscuchadores.getListeners(EscuchadorMensajes.class);
        for (EscuchadorMensajes escuchador : escuchadores) {
            escuchador.insertarClienteLogueado(eventoCliente);
        }
    }      
      
      public static void DespacharConstruirListaClientes(String[] lista) {
        EscuchadorMensajes[] escuchadores = listaEscuchadores.getListeners(EscuchadorMensajes.class);
        for (EscuchadorMensajes escuchador : escuchadores) {
            escuchador.construirListaClientes(lista);
        }
    }   
         
         public static void DespacharSalirDelJuego(ClienteDatos eventoCliente) {
        EscuchadorMensajes[] escuchadores = listaEscuchadores.getListeners(EscuchadorMensajes.class);
        for (EscuchadorMensajes escuchador : escuchadores) {
            escuchador.salirJuego(eventoCliente);
        }
    } 
         
       public static void DespacharActuaiizarTableroConMensaje(String s) {
        EscuchadorMensajes[] escuchadores = listaEscuchadores.getListeners(EscuchadorMensajes.class);
        for (EscuchadorMensajes escuchador : escuchadores) {
            escuchador.actualizarTableroConMensaje(s);
        }
    }
       
      public static void DespacharCabiarTurnoUno() {
        EscuchadorMensajes[] escuchadores = listaEscuchadores.getListeners(EscuchadorMensajes.class);
        for (EscuchadorMensajes escuchador : escuchadores) {
            escuchador.cambiarTurnoUno();
        }
    }

    public static void DespacharCabiarTurnoTodos(String msj) {
        EscuchadorMensajes[] escuchadores = listaEscuchadores.getListeners(EscuchadorMensajes.class);
        for (EscuchadorMensajes escuchador : escuchadores) {
            escuchador.cambiarTurnoTodos(msj);
        }
    }
    
     public static void DespacharNotificacionGame(String msj) {
        EscuchadorMensajes[] escuchadores = listaEscuchadores.getListeners(EscuchadorMensajes.class);
        for (EscuchadorMensajes escuchador : escuchadores) {
            escuchador.notificarGame(msj);
        }
    }
     
      public static void DespacharProcesarGrande(String msj) {
        EscuchadorMensajes[] escuchadores = listaEscuchadores.getListeners(EscuchadorMensajes.class);
        for (EscuchadorMensajes escuchador : escuchadores) {
            escuchador.procesarGrande(msj);
        }
    }
      
       public static void DespacharProcesarGanador(String msj) {
        EscuchadorMensajes[] escuchadores = listaEscuchadores.getListeners(EscuchadorMensajes.class);
        for (EscuchadorMensajes escuchador : escuchadores) {
            escuchador.procesarGanador(msj);
        }
    }
}
