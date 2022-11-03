/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

/**
 *
 * @author bismart
 */
public class Dado {
 int posX;
 int posY;
 boolean estado;
 int idDado;
 int valor=0;
 
 
  public Dado() {
  
  }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }


    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setIdDado(int idDado) {
        this.idDado = idDado;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
 
   

    public boolean isEstado() {
        return estado;
    }

    public int getIdDado() {
        return idDado;
    }

    public int getValor() {
        return valor;
    }
    
    
}
