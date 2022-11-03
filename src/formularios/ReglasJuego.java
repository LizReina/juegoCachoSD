/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import java.util.Arrays;

/**
 *
 * @author bismart
 */
public class ReglasJuego {
 Dado[] listaDados;
 
 int cantOportunidades = 2;
 
    public ReglasJuego(Dado[] listaDados) {
        this.listaDados=cloneDados(listaDados);
    }
    
        public int getCantOportunidades() {
        return cantOportunidades;
    }
    public void asignarListaDatas(Dado[] listaDados){
          this.listaDados=cloneDados(listaDados);
    }
    public Dado[] cloneDados(Dado[] d){
        Dado[] aux =new Dado[d.length];
        
        for (int i = 0; i < d.length; i++) {
            aux[i] = new Dado();
            aux[i].setEstado(d[i].isEstado());
            aux[i].setIdDado(d[i].getIdDado());
            aux[i].setValor(d[i].getValor());
            aux[i].setPosX(d[i].getPosX());
            aux[i].setPosY(d[i].getPosY());
        }
        return aux;
    }
    public void setCantOportunidades(int cantOportunidades) {
        this.cantOportunidades = cantOportunidades;
    }
    
   public void balas(DatoDeTablero bala) {
       int cantUno=contRepet(1);
         bala.setPuntaje(bala.getPuntaje()+cantUno);   
    }

   public void tontos(DatoDeTablero numero) {
       int cant=contRepet(2);
       numero.setPuntaje(numero.getPuntaje()+(cant*2));   
    }
    

    public void tricas(DatoDeTablero numero) {
       int cant=contRepet(3);
       numero.setPuntaje(numero.getPuntaje()+(cant*3));   
    }

    public void cuadras(DatoDeTablero numero) {
       int cant=contRepet(4);
       numero.setPuntaje(numero.getPuntaje()+(cant*4));   
    }

    public void quinas(DatoDeTablero numero) {
       int cant=contRepet(5);
       numero.setPuntaje(numero.getPuntaje()+(cant*5));   
    }

    public void senas(DatoDeTablero numero) {
       int cant=contRepet(6);
       numero.setPuntaje(numero.getPuntaje()+(cant*6));   
    }

    public void escalera(DatoDeTablero numero,int ptj) {
       
        if (esEscalera()) {
            numero.setPuntaje(ptj);
        }
    }

    public void full(DatoDeTablero numero,int ptj) {
         System.out.println(esFull());
        if (esFull()) {
            numero.setPuntaje(ptj);
        }
    }

    public void poket(DatoDeTablero numero,int ptj) {
        if (isPocket()) {
            numero.setPuntaje(ptj);
        }
    }
    
    public int evaluarJugada(){
        
        if(esGrande()){
          return 1;
        }
         if(esEscalera()){
            return 2;
        }
          if(esFull()){
            return 3;
        }
       
        if(isPocket()){
            return 4;
        }
       
        
      return 0;  
    }
      
      public Dado[] ordenarListaDados(){
          int aux;
          Dado[] vd =listaDados;
          
            for (int i = 0; i < vd.length-1; i++) {
               for (int j = i+1; j < vd.length; j++) {
                   if(vd[i].getValor() > vd[j].getValor()){
                      aux=vd[i].getValor();
                      vd[i].setValor(vd[j].getValor());
                      vd[j].setValor(aux);
                   }
               }
           }
            return vd;
 
      }
      
      public boolean verificarDadosEscalera(int v[],Dado[] vd){
          for (int i = 0; i < vd.length; i++) {
              if (vd[i].getValor() !=  v[i]) {
                  return false;
              }
          }
          return true;
      }
      

      
      public boolean esEscalera(){
          int vEs[] = {1, 2, 3, 4, 5};
          int vEsc[] = {2, 3, 4, 5, 6};
          int vEscal[] = {1,3, 4, 5, 6};
          
          boolean bandera;
           Dado[] vd = ordenarListaDados();
          bandera = verificarDadosEscalera(vEs,vd);
          
          if (bandera == false) {
              bandera = verificarDadosEscalera(vEsc,vd);
          }
          if (bandera == false) {
              bandera = verificarDadosEscalera(vEscal,vd);
          }

          return bandera;
      }
      
       //verificar para poket
 
    public int contRepet(int e){
        int c = 0;
          for (int i = 0; i < listaDados.length; i++) 
            if(listaDados[i].getValor() == e)
               c++;
        return c;
    }
    public boolean isPocket(){
        int e;
        int c;
        for (int i = 0; i < listaDados.length; i++){
           e = listaDados[i].getValor();
           c = contRepet(e);
           if(c == 4)
               return true;
        }
        return false;
    }
         
  
      //verificr para full 
      
       public boolean esFull(){ //22333, 11222   
           System.out.println("formularios.ReglasJuego.esFull()");
           for (int i = 0; i < listaDados.length; i++) {
              int e = listaDados[i].getValor();
              int c = contRepet(e);
              if(c!=2 && c != 3)
                  return false;
           }
           return true;   
         }
     
      public boolean esGrande(){
          System.out.println("formularios.ReglasJuego.esGrande()");
           for (int i = 0; i < listaDados.length; i++) {
                int e = listaDados[i].getValor();
              int c = contRepet(e);
              if(c!=5)
                  return false;
           }
           return true;  

      }
      
   
      
}
