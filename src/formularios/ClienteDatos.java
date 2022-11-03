/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import java.net.Socket;
import servidor.hilos.HiloMensajesCliente;

/**
 *
 * @author bismart
 */
public class ClienteDatos {
    private Socket socketCliente;
    private String nombrePC;
    private long fechahora;
    private String IP;
    private int id=-1;
    private String user;
    private String password;
    private boolean logueado;
    private boolean turno = false;
    private boolean play = false;
    
    public int cantidaturnos=5;
   // public MarcadorCacho tableroDeCachoCliente;

    public int[][] tableroCacho= {{0,0,0},{0,0,0},{0,0,0}};
    public int datoGrande=0;
 
     public ClienteDatos() {
    }


    public ClienteDatos(Socket socketCliente) {
        this.socketCliente = socketCliente;
        this.nombrePC = socketCliente.getInetAddress().getHostName();
        this.fechahora = System.currentTimeMillis();
        this.IP = socketCliente.getInetAddress().getHostAddress();
    
        this.user = "";
        this.password = "";
        this.logueado = false;
 
    }

 

    public Socket getSocketCliente() {
        return socketCliente;
    }

   
    public void setSocketCliente(Socket socketCliente) {
        this.socketCliente = socketCliente;
    }

    public String getNombrePC() {
        return nombrePC;
    }

    public void setNombrePC(String nombrePC) {
        this.nombrePC = nombrePC;
    }

    public long getFechahora() {
        return fechahora;
    }

    public void setFechahora(long fechahora) {
        this.fechahora = fechahora;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogueado(boolean logueado) {
        this.logueado = logueado;
    }
   public void setPlay(boolean play) {
        this.play = play;
    }
    
    public boolean isLogueado() {
        return logueado;
    }

    public boolean isPlay() {
        return play;
    }

    public boolean isTurno() {
        return turno;
    }

    public void setTurno(boolean turno) {
        this.turno = turno;
    }

    public int getCantidaturnos() {
        return cantidaturnos;
    }

    public void setCantidaturnos(int cantidaturnos) {
        this.cantidaturnos = cantidaturnos;
    }


    
}
