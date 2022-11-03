/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import java.net.Socket;
import servidor.Despachador;
import servidor.eventos.EventoClienteAccion;
import servidor.eventos.EventoMensaje;

/**
 *
 * @author bismart
 */
public class tareaLeerComandoCliente  extends Thread{
  
    public ClienteDatos clienteData;
    String mensaje;  
    String protocolo[] = new String[4];
   

    public tareaLeerComandoCliente(String mensaje) {
     this.protocolo=mensaje.split(",");
     this.mensaje=mensaje;
    }

    @Override
    public void run() {
System.out.println("Mesaje: "+ this.mensaje);
        switch(protocolo[2]){ // 1122,,1,user:pass
            case "0": 
               System.out.println("Coneccion Encendida");
               System.out.println("id" + protocolo[0]);
               
               // Login form = new Login();
              // form.setCliente(cliente);
               // form.ID=protocolo[0];
               // form.setVisible(true);
              //  this.id = Integer.parseInt(protocolo[0]);     
               DespachadorCliente.DespacharEventoConexion(new EventoParaCliente(this,null,this.mensaje)); 
               // DespachadorCliente.despacharOnRead(new EventoParaCliente(this, this.mensaje));
            //  DespachadorCliente.DespacharEventoOnLogin(eventClientd);
               
                break;
                
            case "1":
            System.out.println("Estamos rwgistrar EXITOSO");
              DespachadorCliente.DespacharEventoRegister(new EventoParaCliente(this, null, mensaje));
            // String mprotocolo= ","+ "" +","+"2"+","+ username +":" + password + "\n"
       
        //    DespachadorCliente.DespacharEventoOnRegister(new EventoParaCliente(this,this.mensaje)); 
                break;
           
              case "2":
                System.out.println("Estamos Logueo EXITOSO");
                DespachadorCliente.DespacharEventoLogin(new EventoParaCliente(this, null, mensaje));

                String ss[] = protocolo[3].split(":");
                String ssv[] = ss[4].split(";");
                if(ssv.length > 0){
                 DespachadorCliente.DespacharConstruirListaClientes(ssv);
                }
            break;
                
               case "3":
                System.out.println("Estamos modificando tablero");
                String mc =protocolo[3];
                String mv[] = mc.split(":");
                
                System.out.println("LLEGUE A MOSTRAR UN MENSAJE" + mensaje +"protocolo: ->"+ mc);     
                DespachadorCliente.DespacharActuaiizarTableroConMensaje(this.mensaje);
            break;
            
            case "4"://cambiar turno para uno
                System.out.println("Opcion 4");
                DespachadorCliente.DespacharCabiarTurnoUno();
                break;

            case "5": //cambiar turno a todos
                String msTurnoT = protocolo[3];
                System.out.println("Opcion 5");
                DespachadorCliente.DespacharCabiarTurnoTodos(msTurnoT);
                break;
                
            case "6": //Dormida Ganador
                String gnador = protocolo[3];
                System.out.println("Opcion 6");
                if(gnador.split(":")[1].equals("50")){
                    DespachadorCliente.DespacharProcesarGrande(mensaje);
                }else{
                    DespachadorCliente.DespacharNotificacionGame(gnador); 
                }
               
                break;
            
                 case "7": // Ganador
                     String ganador = protocolo[3];
                     String[] v = ganador.split(":");
                     System.out.println("Opcion 7");
                     DespachadorCliente.DespacharProcesarGanador(ganador);
                     DespachadorCliente.DespacharNotificacionGame("EL GANADOR ES :" + v[1] + "puntaje es: " + v[2]);
                break;
                
               case "-2":
                System.out.println("Contraseña incorrecta o usuario incorrecto ");
                DespachadorCliente.DespacharNotificacion(new EventoParaCliente(this, null, mensaje));
                break;
           
            case "-3":
                
                String s =protocolo[3];
                String sv[] = s.split("/");
                System.out.println("LLEGUE A MOSTRAR UN MENSAJE" + mensaje +"protocolo: ->"+ s);

                ClienteDatos cli = new ClienteDatos();
                cli.setId(Integer.valueOf(sv[0]));
                cli.setUser(sv[1]);
                cli.setLogueado(Boolean.valueOf(sv[2]));
                cli.setPlay(Boolean.valueOf(sv[3]));
                
               
                DespachadorCliente.DespacharGenerarClienteLogueado(cli);
                
            break;
            
                case "-4": //logout
                
                String d =protocolo[3];
                String dl[] = d.split("/");
                System.out.println("LLEGUE A MOSTRAR UN MENSAJE" + mensaje +"protocolo: ->"+ d);

                ClienteDatos cl = new ClienteDatos();
                cl.setId(Integer.valueOf(dl[0]));
                cl.setUser(dl[1]);
                cl.setLogueado(Boolean.valueOf(dl[2]));
                cl.setPlay(Boolean.valueOf(dl[3]));
               
                EventoParaCliente ep=new EventoParaCliente(this, cl,protocolo[3]);
                DespachadorCliente.DespacharEventoDesconexion(ep);
                
               break;
            
                 case "-5":  //salir del juego
                         
                String r =protocolo[3];
            

                ClienteDatos clie = new ClienteDatos();
                clie.setId(Integer.valueOf(r));
                DespachadorCliente.DespacharSalirDelJuego(clie);
               break;
            
            default:
                
                System.out.println("introdusca  1 o 0.");
        
            }
    }
  
    
}
