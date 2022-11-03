/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidor.Despachador;
import servidor.eventos.EventoCliente;
import servidor.eventos.EventoMensaje;
import servidor.eventos.Mensaje;
import servidor.hilos.HiloMensajesCliente;

/**
 *
 * @author bismart
 */
public class EscuchadorData extends Thread{
     Socket cliente;
     private DataInputStream flujoentrada;
     private EventoParaCliente eventoCliente;
     private List<IEscuhadorDatosCliente> escuchadorData;
    private boolean sw;
      public Integer id;
  
    
    public EscuchadorData(Socket cliente) {
        try {
            this.cliente=cliente;
            this.eventoCliente = eventoCliente;
            this.flujoentrada = new DataInputStream(cliente.getInputStream());
            this.escuchadorData=new ArrayList<IEscuhadorDatosCliente>();
            this.sw = true;
        } catch (IOException ex) {
            Logger.getLogger(HiloMensajesCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  
    
    
    public void registrarUser(String username, String password) throws IOException {
      cliente.getOutputStream().write((this.id.toString()+","+ "" +","+"2"+","+ username +":" + password + "\n").getBytes("UTF-8")); ;
    }
    

    @Override
    public void run() {
          System.out.println("Hilo Escuchando Cliente");
              try {
                   while(sw){
                    String mensajeServidor = flujoentrada.readUTF();
                    System.out.println("Servidor Mesage: "+mensajeServidor);
                    DespachadorCliente.despacharOnRead(new EventoParaCliente(this,new ClienteDatos(cliente), mensajeServidor));
                   } 
                  // DespachadorCliente.despacharOnConnect(new EventoParaCliente(this,mensajeServidor));
              } catch (IOException ex) {
                  Logger.getLogger(EscuchadorData.class.getName()).log(Level.SEVERE, null, ex);
                  Detener();
              }
      
        
    /*    
        BufferedReader sistemaEntrada = null;
        try {
            DataInputStream flujoentrada = null;
            DataOutputStream flujosalida = null;
            try {
                flujoentrada = new DataInputStream(this.cliente.getInputStream());
                flujosalida = new DataOutputStream(this.cliente.getOutputStream());
                
                System.out.println("Conexion establecida!");
            } catch (IOException ex) {
                Logger.getLogger(formularios.Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            sistemaEntrada = new BufferedReader(new InputStreamReader(this.cliente.getInputStream()));
            System.out.println(sistemaEntrada.readLine());
            String linea;
            try {
                /* while (true) {
                System.out.print("Escribir mensaje al servidor: ");
                linea = sistemaEntrada.readLine();
                flujosalida.writeUTF(linea);
                System.out.println("Mensaje Enviado a servidor " + this.cliente.getRemoteSocketAddress());
                
                
                despacharOnRead(linea);
                
                //                }
                } 
                
                while ((linea = sistemaEntrada.readLine()) != null) {
                    
                    System.out.println(linea);
                    
                    despacharOnRead(linea);
                }
                
                sistemaEntrada.close();
            } catch (IOException ex) {
                Logger.getLogger(formularios.Cliente.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    flujoentrada.close();
                    flujosalida.close();
                    sistemaEntrada.close();
                    this.cliente.close();
                } catch (IOException ex) {
                    Logger.getLogger(formularios.Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(EscuchadorData.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
             try {
                 sistemaEntrada.close();
             } catch (IOException ex) {
                 Logger.getLogger(EscuchadorData.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
        */
    }
    
    public void Detener() {
        try {
            flujoentrada.close();
            sw = false;
            System.out.println("formularios.EscuchadorData.Detener()");
            DespachadorCliente.DespacharEventoDesconexion(eventoCliente);
        } catch (IOException ex) {
            Logger.getLogger(HiloMensajesCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

 
    
}
