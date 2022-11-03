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
public class MarcadorCacho {

 
   public DatoDeTablero[][] tableroDeCacho = new DatoDeTablero[3][3];
 
public DatoDeTablero grande ;
 
    public MarcadorCacho(int posX, int posY) {
        String[] sv = {"BALAS","TONTOS","TRICAS","ESCALERA","FULL","POCKET","CUADRAS","QUINAS","SENAS"};
            int e = 0;
            for (int i = 0; i < this.tableroDeCacho.length; i++) {
                for (int j = 0; j < this.tableroDeCacho.length; j++) {
                    tableroDeCacho[i][j] = new DatoDeTablero(sv[e],posX+i*50 , posY+j*50, 0, 60, 60);  
                    tableroDeCacho[i][j].setFila(i);
                    tableroDeCacho[i][j].setCol(j); 
                    e++;
                }
        }
            
       
    }
    
     public void actualizarPosTablero(int posX, int posY){
      
            for (int i = 0; i < this.tableroDeCacho.length; i++) {
                for (int j = 0; j < this.tableroDeCacho.length; j++) {
                 //   tableroDeCacho[i][j] = new DatoDeTablero(sv[e], , , 0, 50, 50);   
                 DatoDeTablero datoTablero = tableroDeCacho[i][j];
                 datoTablero.setPosX(posX+i*60);
                 datoTablero.setPosY(posY+j*60);
                }
        }
    }
     
       public void actualizarPosGrande(int posX, int posY){
             DatoDeTablero datoTablero = grande;
              datoTablero.setPosX(posX);
              datoTablero.setPosY(posY);
       }
       
     public void setGrande(int posX, int posY,int alto,int ancho){
       grande =new DatoDeTablero("GRANDE", posX,posY,0,alto,ancho);
     }

    public DatoDeTablero getGrande() {
        return grande;
    }
  
     
    
    public DatoDeTablero[][] getTableroDeCacho() {
        return tableroDeCacho;
    }

    public void setTableroDeCacho(DatoDeTablero[][] tableroDeCacho) {
        this.tableroDeCacho = tableroDeCacho;
    }

    public int getValor(int fila,int col){
        return tableroDeCacho[fila][col].getPuntaje();
    }

      public void setValor(int fila,int col,int valor){
        tableroDeCacho[fila][col].puntaje=valor;
    }

    public int cantidadPuntos(){
        int valor=0;
    
        for (int i = 0; i < this.tableroDeCacho.length; i++) {
            for (int j = 0; j < this.tableroDeCacho.length; j++) {
                valor = valor + getValor(i, j) ;
            }
        }
        
       valor = valor + getGrande().getPuntaje();
       return valor;
    }
    
 }
