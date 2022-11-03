/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import formularios.Login;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.*;
// importar la libreria java.net
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.JOptionPane;
import servidor.eventos.EventoCliente;

// importar la libreria java.io

/**
 *
 * @author bismart
 */
public class Cliente implements IEscuhadorDatosCliente,EscuchadorMensajes{

    Socket client;
    int port;
    String host;
    EscuchadorData escuchadorData;
    ClienteDatos clienteDatos;
    //Interface UI 
    Login fLogin=null;
    JuegoF fplay;
    
    
    public HashMap<Integer,ClienteDatos> listaClientesConectados;
   
    // juego
     public Dado[] listaDados;
     public MarcadorCacho tableroDeCacho;
     ReglasJuego rg;

    public ReglasJuego getRg() {
        return rg;
    }

    public void setRg(ReglasJuego rg) {
        this.rg = rg;
    }
     
    public Cliente(int port, String host) {
        this.port = port;
        this.host = host;
        
    }

    public void setfLogin(Login fLogin) {
        this.fLogin = fLogin;
    }

    public void setFplay(JuegoF fplay) {
        this.fplay = fplay;
    }

    public void inicializar(){
        try {
            client = new Socket(host, port);
            System.out.println("Obtener Socket: "+ client.isConnected());
            clienteDatos=new ClienteDatos(client);
            listaClientesConectados = new HashMap<>();
 
            escuchadorData=new EscuchadorData(client);
            escuchadorData.start(); 
            
           
            DespachadorCliente.listaEscuchadores.add(IEscuhadorDatosCliente.class,this);
            DespachadorCliente.listaEscuchadores.add(EscuchadorMensajes.class,this);
          
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void inicializarJuego(){
        
        this.initDados();
        crearDado(10, (fplay.getHeight() / 2));
        
        this.tableroDeCacho = new MarcadorCacho((fplay.getWidth()/2)-(3*50),(fplay.getHeight()/2));
        this.rg = new ReglasJuego(this.listaDados);
        this.tableroDeCacho.setGrande((fplay.getWidth()/2)-(3*50)+60, (fplay.getHeight()/2)+(3*60) + 10, 60, 60);
       // clienteDatos.setTableroDeCachoCliente(tableroDeCacho);
        
    }
    
    public void actualizarPosDado(int posIniX, int posIniY){
        for (int j = 0; j < 5; j++) {
            Dado d = listaDados[j];  
            d.setPosX(posIniX);
            d.setPosY(posIniY + j * 50);
         
        }
    }
    
    public void actualizarTablero(int posX,int posY){
        tableroDeCacho.actualizarPosTablero(posX, posY);
    }
    
      public void actualizarGrande(int posX,int posY){
        tableroDeCacho.actualizarPosGrande(posX, posY);
    }
    public void initDados(){
        this.listaDados = new Dado[5];
        for (int i = 0; i < this.listaDados.length; i++) {
          this.listaDados[i] = new Dado();
        }
    }
   
     public void crearDado(int posIniX, int posIniY){
        int i = 0;
        int valor = 0;
         for (int j = 0; j < this.listaDados.length; j++) {
             Dado d = this.listaDados[j];
             // {new Integer(posIniX + i * 50), new Integer(posIniY + j * 50), false, j, new Integer(valor)};
             d.setPosX(posIniX);
             d.setPosY(posIniY + j * 50);
             d.setEstado(true);
             d.setIdDado(j);
             d.setValor(valor);
         }
    }
     
      public void lanzarDados(String choiceTest){
         Random random = new Random();
          int value=0;
         if(choiceTest!= null){
             generarDadoTest(choiceTest);
         }else{
             for (int i = 0; i < this.listaDados.length; i++) {
             Dado d =  this.listaDados[i];
            if(d.isEstado()){
               value = random.nextInt(5 + 1) + 1;
              d.setValor(value);
              System.out.println("change dado i: "+i+ "valor: "+ value +"-Estado: "+String.valueOf(d.isEstado()));
            }else{
             System.out.println("change dado false i: "+i+  "valor: "+ d.valor +"-Estado: "+String.valueOf(d.isEstado()));
            }
           } 
         }
          
           fplay.repaint();;
      }
      
     public void llenarTest(int[] v){
          for (int i = 0; i < this.listaDados.length; i++) {
             Dado d =  this.listaDados[i];
            d.setValor(v[i]);
           }
     }
      
      public void generarDadoTest(String s){
          switch (s) {
              case "GRANDE":
                  int[] v={5,5,5,5,5};
                  llenarTest(v);
                  break;
              case "ESCALERA":
                 int[] e={1,2,3,4,5};
                  llenarTest(e);
                  break;
              case "FULL":
                  int[] f={5,5,1,1,1};
                  llenarTest(f);
                  break;
              case "POKET":
                 int[] p={3,3,3,3,1};
                  llenarTest(p);
                  break;

              default:
              // code block
          }
      }
      
       public void inicializarDado(){
  
           for (int i = 0; i < this.listaDados.length; i++) {
               Dado d = this.listaDados[i];
               d.setEstado(true);
               d.setValor(0);
           }
           fplay.repaint();
      }
 

    @Override
     public void insertarClienteLogueado(ClienteDatos evc){
        listaClientesConectados.put(evc.getId(), evc);
        System.out.println("lista de clientes conectados insertarClienteLogueado: "+ listaClientesConectados.size());
         
     }
   @Override 
    public void construirListaClientes(String[] vectorClientes){
          
        
        System.out.println("formularios.Cliente.construirListaClientes()" + "length vector" + vectorClientes.length);
        for (String vectorCliente : vectorClientes) {
              System.out.println("insertando cliente id -> ante: " + vectorCliente.split("/")[0]);
        ClienteDatos cli=new ClienteDatos();
         System.out.println("insertando cliente id -> despues : " + vectorCliente.split("/")[0]);
        cli.setId(Integer.valueOf(vectorCliente.split("/")[0]));
        cli.setUser(vectorCliente.split("/")[1]);
        cli.setLogueado(Boolean.valueOf(vectorCliente.split("/")[2]));
        cli.setPlay(Boolean.valueOf(vectorCliente.split("/")[3]));
           
        construirTableroCacho(vectorCliente.split("/")[4].split("#"), cli);
        
        System.out.println("insertando cliente id ->: " + cli.getUser());
        insertarClienteLogueado(cli);
        
        }
   System.out.println("lista de clientes conectados contruir lista: "+ listaClientesConectados.size());

    }
    
    @Override
    public void onConnect(EventoParaCliente evc) {
    String[] protocolo=evc.getMensaje().split(",");
        System.out.println("Entrando a OnConect: " + evc.getMensaje());
       fLogin.setTitle("ID hash : " + protocolo[0] + " USER: " + clienteDatos.getUser());
  
      System.out.println("onConnect cliente antes" + protocolo[0]);
      clienteDatos.setId(Integer.parseInt(protocolo[0]));
        System.out.println("idClinte"+clienteDatos.getId());
        System.out.println("onConnect cliente despues" + protocolo[0]);
    }

    @Override
    public void onDesconnect(EventoParaCliente evc) {
        //listaClientesConectados.remove(eventoCliente.getCliente().getId());
     //    listaClientesConectados.get(evc.getCliente().getId()).setConectado(false);
        Integer idCli=Integer.valueOf(evc.getMensaje().split("/")[0]);
         listaClientesConectados.get(idCli).setLogueado(false);
         //listaClientesConectados.get(idCli).setPlay(false);
        System.out.println(" Clientes deslogueados METODO DESCONECTADO = " + listaClientesConectados.size());
        
        
            }

    @Override
    public void OnMensajeRecivido(EventoParaCliente evc) {
          String[] protocolo=evc.getMensaje().split(",");
        System.out.println("on read on");
        System.out.println(evc.getMensaje());
   
   //  mensaje=protocolo[0]+","+ "" +","+"2"+","+ username +":" + password;
     ExecutorService service = Executors.newCachedThreadPool(); 
     Future future = service.submit(new tareaLeerComandoCliente(evc.getMensaje()));
     System.out.println("corriendo task!");   
    }

    @Override
    public void OnLogin(EventoParaCliente eventoCliente) {
        String[] protocolo = eventoCliente.getMensaje().split(",");
        if (Integer.parseInt(protocolo[0]) == clienteDatos.getId()) {
            if (eventoCliente.getMensaje() != "") { 
                clienteDatos.setId(Integer.parseInt(protocolo[3].split(":")[2]));
                clienteDatos.setUser(protocolo[3].split(":")[0]);
                clienteDatos.setPassword(protocolo[3].split(":")[1]);
                clienteDatos.setLogueado(true);
                clienteDatos.setPlay(true);
                construirTableroCacho(protocolo[3].split(":")[3].split("#"),clienteDatos);
                
                System.out.println("Id del cliente ACTUALIZADO");
                //JOptionPane.showMessageDialog(fLogin, "Cliente logueado");
                fLogin.setTitle(String.valueOf("ID hash : " + clienteDatos.getId() + " USER: " + clienteDatos.getUser()));
                
                insertarClienteLogueado(clienteDatos);
            } else {
                JOptionPane.showMessageDialog(fLogin, "Contrsaseña INCORRECTA");
            }
        }
    }
    
    public void construirTableroCacho(String[] s,ClienteDatos cli){
        int k=0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                 cli.tableroCacho[i][j]=Integer.valueOf(s[k]);
                 k++;
            } 
        }

       cli.datoGrande=Integer.valueOf(s[s.length-2]);
    }

    
    @Override
    public void OnRegister(EventoParaCliente eventoCliente) {
        
        JOptionPane.showMessageDialog(fLogin, "Cliente Registrado");
    }

    @Override
    public void notificacion(EventoParaCliente eventoCliente) {
        String[] protocolo = eventoCliente.getMensaje().split(",");
        JOptionPane.showMessageDialog(fLogin,protocolo[3]);   
    }

    @Override
    public void salirJuego(ClienteDatos clienteDato) {
        String mprotocolo = String.valueOf(clienteDato.getId()) + "," + "" + "," + "3" + "," + "Desconectado";
        listaClientesConectados.get(clienteDato.getId()).setPlay(false);
        listaClientesConectados.get(clienteDato.getId()).setLogueado(false);
    }

  
    public void actualizarTableroEventos(String lista) {
        System.out.println("formularios.Cliente.actualizarTableroEventos"+ lista);
        ExecutorService service = Executors.newCachedThreadPool();
        Future future = service.submit(new TareaMensaje(clienteDatos, lista));   
    }

    @Override
    public void actualizarTableroConMensaje(String lista) {
        System.out.println("formularios.Cliente.actualizarTableroConMensaje()"+lista);
       String[] prt=lista.split(",");
      ClienteDatos cli = listaClientesConectados.get(Integer.valueOf(prt[0]));
      
      String[] men= prt[3].split(":");
      cli.tableroCacho[Integer.valueOf(men[0])][Integer.valueOf(men[1])]=Integer.valueOf(men[2]);
      fplay.repaint();
    }

    @Override
    public void cambiarTurnoUno() {
      clienteDatos.setTurno(true);
      rg.setCantOportunidades(2);
        System.out.println("cambiando turno" +String.valueOf(clienteDatos.isTurno()));
       // fplay.enableButtons();
       
      inicializarDado();
      fplay.repaint();
    }

    @Override
    public void cambiarTurnoTodos(String msj) {
        String[] v = msj.split(":");
        Integer idT = Integer.valueOf(v[0]);
        boolean bolT = Boolean.valueOf(v[1]);
        System.out.println("formularios.Cliente.cambiarTurnoTodos() " + msj);
        for (int i = 0; i < listaClientesConectados.size(); i++) {
            ClienteDatos evp = (ClienteDatos) listaClientesConectados.values().toArray()[i];
            if (evp.getId() == idT) {
                System.out.println("formularios.Cliente.cambiarTurnoTodos() entro if" + idT);
                //  ClienteDatos e = (ClienteDatos) listaClientesConectados.get(evp.getId());
                evp.setTurno(bolT);
                break;
            }
        }

       // fplay.disableButtons();
       fplay.visibleButton();
        fplay.repaint();
    }
    
      public int cantidadLogueados(){
        int c=0;
        for (int i = 0; i < listaClientesConectados.size(); i++) {
            System.out.println("Cliente.cantidadLogueados()"+  listaClientesConectados.values().toArray()[i]);
            ClienteDatos v=(ClienteDatos) listaClientesConectados.values().toArray()[i];
          
            if(v.isLogueado()){
                c++;
            }
        }
        return c;
    }

    @Override
    public void notificarGame(String msj) {
        //String[] protocolo = msj.split(",");
        System.out.println("formularios.Cliente.notificarGame()" + msj);
        JOptionPane.showMessageDialog(fplay,msj); 
    }

    @Override
    public void procesarGrande(String msj) {
        String[] prt=msj.split(",");
        ClienteDatos clie=(ClienteDatos) listaClientesConectados.get(Integer.valueOf(prt[0]));
        clie.datoGrande=50;
    }

    @Override
    public void procesarGanador(String msj) {
         String[] v = msj.split(":");
        JOptionPane.showMessageDialog(fplay,"el ganador es : "+ v[1].toUpperCase()+ " su puntaje es: "+ v[2]); 
    }
}