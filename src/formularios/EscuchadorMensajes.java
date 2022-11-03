/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;
import java.util.EventListener;

/**
 *
 * @author Marcelo
 */
public interface EscuchadorMensajes extends EventListener {
    
    public void OnMensajeRecivido(EventoParaCliente eventoMensaje);

    public void OnLogin(EventoParaCliente eventoCliente);

    public void OnRegister(EventoParaCliente eventoCliente);

    public void notificacion(EventoParaCliente eventoCliente);

    public void insertarClienteLogueado(ClienteDatos clienteDato);

    public void construirListaClientes(String[] lista);

    public void salirJuego(ClienteDatos clienteDato);

    public void actualizarTableroConMensaje(String lista);
    
    public void cambiarTurnoUno();

    public void cambiarTurnoTodos(String msj);
    
    public void notificarGame(String msj);
    
    public void procesarGrande(String msj);

    public void procesarGanador(String msj);
}
