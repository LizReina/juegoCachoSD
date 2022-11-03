/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.management.Query.value;
import static servidor.Despachador.listaEscuchadores;
import servidor.escuchadores.EscuchadorConexiones;
import servidor.escuchadores.EscuchadorMensajes;
import servidor.eventos.Cliente;
import servidor.eventos.EventoCliente;
import servidor.eventos.EventoClienteAccion;
import servidor.eventos.EventoMensaje;
import servidor.hilos.HiloConexiones;
import servidor.hilos.HiloMensajesCliente;
import servidor.hilos.TareaLeerComando;
import servidor.hilos.TaskEnviarUsuarios;
import servidor.hilos.TaskMensaje;

/**
 *
 * @author Marcelo
 */
public class ServidorSocket implements EscuchadorConexiones, EscuchadorMensajes{
    
    private ServerSocket serverSocket;
    private int puerto;
    private HiloConexiones hiloConexiones;
    private HashMap<Integer, EventoCliente> listaClientesConectados;
    //escuchar AccionCliente
    EventoCliente turnoJuego=null;
    
    
    public ServidorSocket( int puerto ){
        try {
            this.puerto = puerto;
            serverSocket =  new ServerSocket(puerto);
            if(!agregarClientes()){
               listaClientesConectados = new HashMap<>();
           }
  
            hiloConexiones = new HiloConexiones(serverSocket);
            
            System.out.println("Servidor creado!");
            System.out.println("Tamanho lista Clientes = " + listaClientesConectados.size());
        } catch (IOException ex) {
            Logger.getLogger(ServidorSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Iniciar(){
        System.out.println("Servidor Iniciado en puerto: " + puerto);
        Despachador.listaEscuchadores.add(EscuchadorConexiones.class, this);
        Despachador.listaEscuchadores.add(EscuchadorMensajes.class, this);
        hiloConexiones.start();
        
//        System.out.println("Cantidad escuchadorConexion...");
    }
    
    public void Detener(){
        try {
            Despachador.listaEscuchadores.remove(EscuchadorConexiones.class, this);
            Despachador.listaEscuchadores.remove(EscuchadorMensajes.class, this);
            hiloConexiones.Detener();
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServidorSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean agregarClientes(){ //EN EL CONSTRUCTOR serializable
          boolean res=false;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("listaUsuarios.dat"));
            listaClientesConectados= (HashMap<Integer, EventoCliente>) ois.readObject();
            ois.close();
            res=true;
            System.err.println("lista cargada");
        } catch (IOException ex) {
            System.out.println("archivo no encontrado");       
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServidorSocket.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println("servidor.ServidorSocket.agregarClientes()");
        }
        
        return res;
    }
    
    private void guardar(){ //EN EL RECIBIR MENSAJE almacena
       
        try {
           ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("listaUsuarios.dat"));
            oos.writeObject(listaClientesConectados);
            oos.close();
           System.err.println("GUARDADO");
 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServidorSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServidorSocket.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }

    @Override
    public void OnClienteConectado(EventoCliente eventoCliente) {
        eventoCliente.getCliente().setHiloMensajesCliente(new HiloMensajesCliente(eventoCliente));
        eventoCliente.getCliente().setConectado(true);
        eventoCliente.getCliente().getHiloMensajesCliente().start();
        listaClientesConectados.put(eventoCliente.getCliente().getId(), eventoCliente); //ponr hash table
        //Responder con un Mensaje que contenga su Id,User,Pass
        Cliente cli = eventoCliente.getCliente();
        String mensaje = "ID=" + cli.getId() + ",user=" + cli.getUser() + ",pass=" + cli.getPassword();
        ExecutorService service = Executors.newCachedThreadPool();
       
        mensaje=eventoCliente.getCliente().getId()+","+ "" +","+"0"+","+"1"; //0 es login, 1 es registrado
        //   cli.getSocketCliente().getOutputStream().write((mensaje + "\n").getBytes("UTF-8"));
        System.out.println("Service Task: "+mensaje);
        Future future = service.submit(new TaskMensaje(eventoCliente, mensaje));
        
        System.out.println("Nuevo Cliente Conectado! ClienteID=" + eventoCliente.getCliente().getId() + "; HORA=" + new Date(eventoCliente.getCliente().getFechahora()));
        System.out.println("Tamanho lista Clientes Conectados = " + listaClientesConectados.size());
        System.out.println("Total escuchadores de mensajes = " + Despachador.listaEscuchadores.getListenerCount(EscuchadorMensajes.class));
    }

    @Override
    public void OnClienteDesconectado(EventoCliente eventoCliente) {
       //listaClientesConectados.remove(eventoCliente.getCliente().getId());
       // eventoCliente.getCliente().setConectado(false);
       //eventoCliente.getCliente().setLogueado(false);
        eventoCliente = listaClientesConectados.get(eventoCliente.getCliente().getId());
        eventoCliente.getCliente().setConectado(false);
        eventoCliente.getCliente().setLogueado(false);
        eventoCliente.getCliente().setPlay(false);
  
        System.out.println("Tamanho lista Clientes Conectados METODO DESCONECTADO = " + listaClientesConectados.size());
        
        Despachador.DespacharEventoDesLogeado(eventoCliente);
                
    }

    @Override
    public void OnMensajeRecivido(EventoMensaje eventoMensaje) {
        System.out.println("Nuevo Mensaje Recibido = " + eventoMensaje.getMensaje().getMensajeCliente());

        ExecutorService service = Executors.newCachedThreadPool();
        Future future = service.submit(new TareaLeerComando(eventoMensaje));
 
        System.out.println("TODOS los mensajes enviados !!!");
    }

    public EventoCliente buscarUser(String user,String pass){
           for (EventoCliente value : listaClientesConectados.values()){
                  System.out.println("usuarios buscados -> "+value.getCliente().getUser());
                if(value.getCliente().getUser().equals(user) && value.getCliente().getPassword().equals(pass)){
                 
                    return value;
                }
            } 
          return null; 
    }
    
    public String generarClienteLogueado(EventoCliente auxCliente){
        return String.valueOf(auxCliente.getCliente().getId()) + "/" + 
               auxCliente.getCliente().getUser() + "/" + auxCliente.getCliente().isLogueado() +"/" 
               +auxCliente.getCliente().isPlay()+ "/" +generarUnTableroCacho(auxCliente);
    }
    
    public String generarListaClientes(){
        String clienteLogueado="";
         for (EventoCliente value : listaClientesConectados.values()) {
            System.out.println("lista clientes logueados genera lista de clientes -> " + value.getCliente().getUser() + value.getCliente().isLogueado());
            if (value.getCliente().isLogueado()) {             
                clienteLogueado = clienteLogueado + generarClienteLogueado(value)+ ";";
     
            }
        }
          System.out.println("lista clientes: "+ clienteLogueado);
         return clienteLogueado;
    }
    
    public String generarUnTableroCacho(EventoCliente auxCliente){
        
        int[][] m= auxCliente.getCliente().tableroCacho;
        String prot="";
         for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                prot=prot + String.valueOf(m[i][j]) + "#";
            }
        }   
       return prot; 
    }
    
   
    @Override
    public void OnLogin(EventoClienteAccion eventoCliente) {

       String[] protocolo=eventoCliente.getMensaje().split(","); // 12345,,1,juan:1234
          System.out.println("cliente procesando Onlogin = " + eventoCliente.getMensaje());
        EventoCliente evC = (EventoCliente) listaClientesConectados.get(Integer.parseInt(protocolo[0]));
        //sacamos el id del cliente
        System.out.println("Onlogin -> id evC hash = " + evC.getCliente().getId());
        EventoCliente auxCliente = buscarUser(protocolo[3].split(":")[0], protocolo[3].split(":")[1]);
        System.out.println("Onlogin -> id evC auxCliente buscar = " + auxCliente.getCliente().getId());
        String mensaje;

        if (auxCliente != null) {
            if (auxCliente.getCliente().isLogueado() == false ) {

                System.out.println("Socket encontrado en el Hastable :" + String.valueOf(auxCliente.getCliente().getId()) + auxCliente.getCliente().isConectado() );
                auxCliente.getCliente().setSocketCliente(evC.getCliente().getSocketCliente());
                auxCliente.getCliente().setLogueado(true);
                auxCliente.getCliente().setPlay(true);
                auxCliente.getCliente().inicializarTablero();
                
                mensaje = String.valueOf(evC.getCliente().getId()) + "," + "" + "," + "2" + "," + protocolo[3] + ":" + 
                        String.valueOf(auxCliente.getCliente().getId()) +":"+generarUnTableroCacho(evC)+ ":" +generarListaClientes();
                  System.out.println("enviando mensaje al cliente: "+  mensaje);
                  
                    if (auxCliente.getCliente().getId() != evC.getCliente().getId() && evC.getCliente().getUser() == "") { 
                        listaClientesConectados.remove(evC.getCliente().getId());
                        evC.getCliente().setId(auxCliente.getCliente().getId());
                        System.out.println("REMOVIDO");
                    }
                    System.out.println("Tamanho onlogin lista Clientes Conectados = " + listaClientesConectados.size());
                    
                   //   auxCliente.getCliente().setConectado(true);
                
                System.out.println("Socket client server is connect: " + auxCliente.getCliente().getSocketCliente().isConnected());

                ExecutorService service = Executors.newCachedThreadPool();
                Future future = service.submit(new TaskMensaje(auxCliente, mensaje));
                
                
                Despachador.DespacharNotificacion(auxCliente);

            } else {
                mensaje = String.valueOf(evC.getCliente().getId()) + "," + "" + "," + "-2" + "," + "EL CLIENTE YA ESTA CONECTADO ";
                System.out.println("EL CLIENTE YA ESTA CONECTADO" + mensaje);
                ExecutorService service = Executors.newCachedThreadPool();
                Future future = service.submit(new TaskMensaje(evC, mensaje));
            }
        } else {
               
             //  mensaje = String.valueOf(auxCliente.getCliente().getId()) + "," + "" + "," + "-2" + "," + protocolo[3];
             mensaje = String.valueOf(evC.getCliente().getId()) + "," + "" + "," + "-2" + "," + "cONTRASEÑA INCORRECTA";
                 System.out.println("no pille al aux Cliente" + mensaje);
                ExecutorService service = Executors.newCachedThreadPool();
                Future future = service.submit(new TaskMensaje(evC, mensaje));
            }
          
           
    }

    @Override
    public void OnRegister(EventoClienteAccion eventoCliente) {
       
        String[] protocolo = eventoCliente.getMensaje().split(","); // 12345,,1,juan:1234has ass

        System.out.println("cliente intentando registrarse = " + eventoCliente.getMensaje());
        EventoCliente evC = (EventoCliente) listaClientesConectados.get(Integer.parseInt(protocolo[0]));
        //sacamos el id del cliente

        if (evC.getCliente().getUser() == "") {
            evC.getCliente().setUser(protocolo[3].split(":")[0]);
            evC.getCliente().setPassword(protocolo[3].split(":")[1]);

            System.out.println("Buscando en el hashtable id :" + String.valueOf(evC.getCliente().getId()));
            String mensaje = String.valueOf(evC.getCliente().getId()) + "," + "" + "," + "1" + "," + "Regstrado.";
         
            ExecutorService service = Executors.newCachedThreadPool();
            System.out.println("SockeytXliente");

            Future future = service.submit(new TaskMensaje(evC, mensaje));
            
            guardar();
        } else {
            String mensaje = String.valueOf(evC.getCliente().getId()) + "," + "" + "," + "-2" + "," + "NO PUEDE REGISTRAR PORQUE ESTE HASHCODE YA FUE ASIGNADO ";
            ExecutorService service = Executors.newCachedThreadPool();
            Future future = service.submit(new TaskMensaje(evC, mensaje));
        }
          
       
    }

    @Override
    public void notificarClientesLogueados(EventoCliente eventoCliente) {
        
        for (EventoCliente value : listaClientesConectados.values()) {
            System.out.println("notificar usuarios conectados -> " + value.getCliente().getUser());
            if (value.getCliente().isLogueado() && eventoCliente.getCliente().getId() != value.getCliente().getId()) {
                
                String mensaje = String.valueOf(value.getCliente().getId()) + "," + "" + "," + "-3" + "," + generarClienteLogueado(eventoCliente);

                ExecutorService service = Executors.newCachedThreadPool();
                System.out.println("enviando mensaje de cada cliente");
                Future future = service.submit(new TaskMensaje(value, mensaje));
            }
        }

    }
    
    

    @Override
    public void notificarClientesDesLogueados(EventoCliente eventoCliente) {
        
            for (EventoCliente value : listaClientesConectados.values()) {
            System.out.println("notificar usuarios desconectaods -> " + value.getCliente().getUser());
            if (value.getCliente().isLogueado() && eventoCliente.getCliente().getId() != value.getCliente().getId()) {
                
                String mensaje = String.valueOf(value.getCliente().getId()) + "," + "" + "," + "-4" + "," + generarClienteLogueado(eventoCliente);

                ExecutorService service = Executors.newCachedThreadPool();
                System.out.println("enviando mensaje de cada cliente");
                Future future = service.submit(new TaskMensaje(value, mensaje));
            }
        }
    }

    @Override
    public void salirDelJuego(EventoClienteAccion eventoCliente) {
        String[] protocolo = eventoCliente.getMensaje().split(","); // 12345,,1,juan:1234has ass
        EventoCliente evC = (EventoCliente) listaClientesConectados.get(Integer.parseInt(protocolo[0]));
        evC.getCliente().setPlay(false);
        
        notificarClientesSalirJuego(evC);
    }

    @Override
    public void notificarClientesSalirJuego(EventoCliente eventoCliente) {
     for (EventoCliente value : listaClientesConectados.values()) {
            System.out.println("notificar usuarios desconectaods -> " + value.getCliente().getUser());
            if (value.getCliente().isLogueado() && eventoCliente.getCliente().getId() != value.getCliente().getId()) {
                
        String mensaje = String.valueOf(value.getCliente().getId()) + "," + "" + "," + "-5" + "," + String.valueOf(eventoCliente.getCliente().getId()) ;

                ExecutorService service = Executors.newCachedThreadPool();
                System.out.println("enviando mensaje de cada cliente");
                Future future = service.submit(new TaskMensaje(value, mensaje));
            }
        }   
    }

    @Override
    public void notificarClientesParaTablero(EventoClienteAccion eventoCliente) {
        System.out.println("servidor.ServidorSocket.notificarClientesParaTablero(): " + eventoCliente.getMensaje());  
        String[] protocolo = eventoCliente.getMensaje().split(","); // 12345,,1,juan:1234has ass
        EventoCliente evC = (EventoCliente) listaClientesConectados.get(Integer.parseInt(protocolo[0]));
        System.out.println("pos I: "+ protocolo[3].split(":")[0] + "pos j: "+ protocolo[3].split(":")[1] + "valor : "+ protocolo[3].split(":")[2]);
        int i = Integer.valueOf(protocolo[3].split(":")[0]);
        int j = Integer.valueOf(protocolo[3].split(":")[1]);
        int valor = Integer.valueOf(protocolo[3].split(":")[2]);
        evC.getCliente().cantidadTurnos=Integer.valueOf(protocolo[3].split(":")[3]);
        evC.getCliente().puntos=Integer.valueOf(protocolo[3].split(":")[4]);
        
        evC.getCliente().tableroCacho[i][j]=valor;
       // guardar();
       
       String mensaje= String.valueOf(evC.getCliente().getId())+"," + "" + "," + "3" + "," + protocolo[3];
       System.out.println("mensaje TabSer: " + mensaje);
       Despachador.DespacharNotificacionAll(evC,mensaje);
       
        Despachador.DespacharNotificarGanador(eventoCliente);
    }
    
    

    @Override
    public void notificarAll(EventoCliente eventoCliente, String procolo) {
           System.out.println("mensaje TabSerAll: " + procolo);
        for (EventoCliente value : listaClientesConectados.values()) {
            System.out.println("notificar a todos los clientes -> " + value.getCliente().getUser());
            if (value.getCliente().isLogueado() && eventoCliente.getCliente().getId() != value.getCliente().getId()) {

                ExecutorService service = Executors.newCachedThreadPool();
                System.out.println("enviando mensaje de cada cliente");
                Future future = service.submit(new TaskMensaje(value, procolo));
            }
        }
    }
    
    public EventoCliente cambiarTurno(){
       EventoCliente v = buscarTurnoLogin(turnoJuego);
        return v;
    }
    public EventoCliente buscarTurnoLogin(EventoCliente ec){
        ec = obtenerSiguiente(ec);
        while(!ec.getCliente().isLogueado() && cantidadLogueados() > 0){
            ec = obtenerSiguiente(ec);
        }
        return ec;
    }
    
    public int cantidadLogueados(){
        int c=0;
        for (int i = 0; i < listaClientesConectados.size(); i++) {
            EventoCliente v=(EventoCliente) listaClientesConectados.values().toArray()[i];
            if(v.getCliente().isLogueado()){
                c++;
            }
        }
        return c;
    }
    
    public EventoCliente obtenerSiguiente( EventoCliente valorEntrada){
        EventoCliente valor = null;//[1,  ,2    ,3   ,4   ,5]
        if(valorEntrada==null)
            return (EventoCliente) listaClientesConectados.values().toArray()[0];
                                   // i=0
        for (int i = 0; i < listaClientesConectados.size(); i++) {
            valor = (EventoCliente) listaClientesConectados.values().toArray()[i];
            if (valor.getCliente().getId() == valorEntrada.getCliente().getId()) {
                if (i + 1 == listaClientesConectados.size()) {
                    valor = (EventoCliente) listaClientesConectados.values().toArray()[0];
                    return valor;
                } else {
                    valor = (EventoCliente) listaClientesConectados.values().toArray()[i + 1];
                    return valor;
                }

            }
        }
        return valor;
    }
    @Override
    public void iniciarJuego(EventoClienteAccion eventoCliente) {
        System.out.println("servidor.ServidorSocket.iniciarJuego()---"+ eventoCliente.getMensaje());
        String[] evc=eventoCliente.getMensaje().split(",");
        if (cantidadLogueados() > 0) {
            if(turnoJuego!=null){
            String mensaje = "" + "," + "" + "," + "5" + "," +  String.valueOf(turnoJuego.getCliente().getId())+ ":"+ "false";
            notificarAll(turnoJuego, mensaje); 
            }
            
            turnoJuego = cambiarTurno();
            System.out.println("Turno: "+ turnoJuego.getCliente().getUser());
            
            String msj = String.valueOf(turnoJuego.getCliente().getId()) + "," + "" + "," + "4" + "," + "Cambiando turno En un Cliente";
            ExecutorService service = Executors.newCachedThreadPool();
            Future future = service.submit(new TaskMensaje(turnoJuego, msj));

            String mensaje = "" + "," + "" + "," + "5" + "," +  String.valueOf(turnoJuego.getCliente().getId())+ ":"+ "true";
            notificarAll(turnoJuego, mensaje);
        }

    }

    @Override
    public void notificarTableroGrande(EventoClienteAccion eventoCliente) {
        System.out.println("Grande: " + eventoCliente.getMensaje());  
        String[] protocolo = eventoCliente.getMensaje().split(","); // 12345,,1,juan:1234has ass
        EventoCliente evC = (EventoCliente) listaClientesConectados.get(Integer.parseInt(protocolo[0]));
        
        System.out.println(String.valueOf(evC.getCliente().getId()) + evC.getCliente().getUser());
        
        System.out.println( "pos I: "+ protocolo[3].split(":")[0] + "pos j: "+ protocolo[3].split(":")[1]);
        String i = protocolo[3].split(":")[0];
        String j = protocolo[3].split(":")[1];
      
       
       String mensaje= String.valueOf(evC.getCliente().getId())+"," + "" + "," + "6" + "," + protocolo[3];
        System.out.println("mensaje TabSer: " + mensaje);
       Despachador.DespacharNotificacionAll(evC,mensaje);
    }
    
    public boolean terminoTurnos(){
        for (int i = 0; i < listaClientesConectados.size(); i++) {
            EventoCliente v=(EventoCliente) listaClientesConectados.values().toArray()[i];
            if(v.getCliente().cantidadTurnos != 0){
                return false;
            }
        }
        return true;
    }
    
    public EventoCliente BuscarGanador(){
        EventoCliente ev=null;
        if (terminoTurnos()) {
            int mayor = 0;
            for (int i = 0; i < listaClientesConectados.size(); i++) {
                EventoCliente v = (EventoCliente) listaClientesConectados.values().toArray()[i];
                if (v.getCliente().puntos > mayor) {
                    ev=v;
                    mayor = v.getCliente().puntos;
                }
            }

        }
      return ev;
    }

    @Override
    public void notificarGanador(EventoClienteAccion eventoCliente) {
        EventoCliente evC=BuscarGanador();

       String mensaje= String.valueOf(evC.getCliente().getId())+"," + "" + "," + "7" + "," +String.valueOf(evC.getCliente().getId())+ ":"+ evC.getCliente().getUser()
               + ":" +String.valueOf(evC.getCliente().puntos);
       
        System.out.println("mensaje Ganador " + mensaje);
       Despachador.DespacharNotificarATodos(evC,mensaje);
    }

    @Override
    public void notificarATodos(EventoCliente eventoCliente, String procolo) {
        System.out.println("mensaje a todos ganador: " + procolo);
        for (EventoCliente value : listaClientesConectados.values()) {
            System.out.println("notificar a todos los clientes -> " + value.getCliente().getUser());
            if (value.getCliente().isLogueado()) {
                ExecutorService service = Executors.newCachedThreadPool();
                System.out.println("enviando mensaje de cada cliente todosss ");
                Future future = service.submit(new TaskMensaje(value, procolo));
            }

        }
    }

}
