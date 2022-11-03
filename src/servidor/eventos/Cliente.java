/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor.eventos;

import java.io.Serializable;
import java.net.Socket;
import servidor.hilos.HiloMensajesCliente;

/**
 *
 * @author Marcelo
 */
public class Cliente implements Serializable{
    
    private transient Socket socketCliente;
    private String nombrePC;
    private long fechahora;
    private String IP;
    private int id;
    private String user = "";
    private String password ="";
    private transient boolean logueado;
    private transient HiloMensajesCliente hiloMensajesCliente;
    
    public transient boolean conectado;
    public transient boolean play;
    public transient boolean turno;
    public transient int[][] tableroCacho;
    public transient int datoGrande=0;
    public transient int cantidadTurnos=9;
    public transient int puntos=0;
    
   
    public Cliente(Socket socketCliente) {
        this.socketCliente = socketCliente;
        this.nombrePC = socketCliente.getInetAddress().getHostName();
        this.fechahora = System.currentTimeMillis();
        this.IP = socketCliente.getInetAddress().getHostAddress();
        this.id = getCode();
        
        this.user = "";
        this.password = "";
        this.logueado = false;
        
       tableroCacho = new int[3][3];
    
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

    public void setLogueado(boolean logueado) {
        this.logueado = logueado;
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

    public HiloMensajesCliente getHiloMensajesCliente() {
        return hiloMensajesCliente;
    }

    public void setHiloMensajesCliente(HiloMensajesCliente hiloMensajesCliente) {
        this.hiloMensajesCliente = hiloMensajesCliente;
    }
    
    private int getCode() {
        return this.hashCode();
    }
          public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }

    public boolean isConectado() {
        return conectado;
    }

    public boolean isLogueado() {
        return logueado;
    }
    
        public void setPlay(boolean play) {
        this.play = play;
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
    
    public void inicializarTablero(){
         int[][] aux = {{0,0,0},{0,0,0},{0,0,0}};
         tableroCacho = aux;
    }
    
}
