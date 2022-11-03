/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor.escuchadores;

import servidor.eventos.EventoMensaje;
import java.util.EventListener;
import servidor.eventos.EventoCliente;
import servidor.eventos.EventoClienteAccion;

/**
 *
 * @author Marcelo
 */
public interface EscuchadorMensajes extends EventListener {
    
    public void OnMensajeRecivido(EventoMensaje eventoMensaje);

    public void OnLogin(EventoClienteAccion eventoCliente);

    public void OnRegister(EventoClienteAccion eventoCliente);

    public void notificarClientesLogueados(EventoCliente eventoCliente);

    public void notificarClientesDesLogueados(EventoCliente eventoCliente);

    public void salirDelJuego(EventoClienteAccion eventoCliente);

    public void notificarClientesSalirJuego(EventoCliente eventoCliente);

    public void notificarClientesParaTablero(EventoClienteAccion eventoCliente);

    public void notificarAll(EventoCliente eventoCliente,String procolo);
    
    public void iniciarJuego(EventoClienteAccion eventoCliente);
    
    public void notificarTableroGrande(EventoClienteAccion eventoCliente);
    
    public void notificarGanador(EventoClienteAccion eventoCliente);
     public void notificarATodos(EventoCliente eventoCliente,String procolo);
}
