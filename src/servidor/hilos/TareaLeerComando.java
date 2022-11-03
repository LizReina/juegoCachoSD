/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor.hilos;

import java.io.DataOutputStream;
import java.net.Socket;
import servidor.Despachador;
import servidor.eventos.Cliente;
import servidor.eventos.EventoCliente;
import servidor.eventos.EventoClienteAccion;
import servidor.eventos.EventoMensaje;

/**
 *
 * @author Marcelo
 */
public class TareaLeerComando extends Thread{
  
    public Socket cliente;
    String mensaje;  
    String protocolo[] = new String[4];

    public TareaLeerComando(EventoMensaje eventoMensaje) {
     this.protocolo=eventoMensaje.getMensaje().getMensajeCliente().split(",");
     this.mensaje=eventoMensaje.getMensaje().getMensajeCliente();
    }

    @Override
    public void run() {

        switch(protocolo[2]){ // 1122,,1,user:pass
            case "0":
                System.out.println("Estamos login out");
                Despachador.DespacharEventoLogin(new EventoClienteAccion(this, this.mensaje));
                break;

            case "1":
                System.out.println("Estamos reggistrar");
                Despachador.DespacharEventoRegister(new EventoClienteAccion(this, this.mensaje));
                break;
            case "2":
                System.out.println("Estamos logear");
                Despachador.DespacharEventoLogin(new EventoClienteAccion(this, this.mensaje));
                break;

            case "3":

                System.out.println("se Salio del juego");
                Despachador.DespacharSalirDelJuego(new EventoClienteAccion(this, this.mensaje));
                break;

            case "4":

                System.out.println("modificar tablero" + "mensaje: " + this.mensaje);
                Despachador.DespacharNotificacionParaTablero(new EventoClienteAccion(this, this.mensaje));
                break;

            case "5"://iniciar juego
                System.out.println("servidor.leer iniciar juego ");
                Despachador.DespacharIniciarJuego(new EventoClienteAccion(this, this.mensaje));
                break;

            case "6"://añadir Grande ormida
                System.out.println("Dormida ");
                Despachador.DespacharNotificacionTableroGrande(new EventoClienteAccion(this, this.mensaje));
                break;
             
            case "7"://Ganador
                System.out.println("Ganador es: -> " + this.mensaje);
                Despachador.DespacharNotificarGanador(new EventoClienteAccion(this, this.mensaje));
                break;
            default:
                
                System.out.println("introdusca  1 o 0.");

        }
    }
  
  
}
