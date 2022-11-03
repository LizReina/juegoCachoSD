/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import servidor.eventos.Cliente;

/**
 *
 * @author Marcelo
 */
public class ServidorUtils {
    
    //PROTOCOLO DEL SENSOR TEMPERATURA
    public static final String IDUSER = "ID=";
    public static final String USER = "user=";
    public static final String PASS = "pass=";
    public static final char SEPARADOR = ',';
    
    //ETIQUETAS
    public static final String TAG_CONECTAR = "#CONECTAR";
    public static final String TAG_LOGIN = "#LOGIN";
    public static final String TAG_REGISTRAR = "#REGISTRAR";
    
    public static abstract class VarsUtil {
        private static StringBuilder valor_sb = null;
        public static StringBuilder getValorSB(){
            if (valor_sb==null) {
                valor_sb = new StringBuilder();
            }
            return valor_sb;
        }
    }
    
    public static void LeerTAG_TAREA(String mensaje, String tag) {
        if (tieneTAG(mensaje)) {
            try {
                StringBuilder valor = VarsUtil.getValorSB();
                
                //Leer la etiqueta para Definir que accion tomar
            } catch (NumberFormatException numberFormatException) {
                System.err.println("ERROR al parsear los numeros. " + numberFormatException.getMessage());
            }
        } else {
            System.err.println("Mensaje no es compatible con tipo Cliente.");
        }
    }
    
    public static void LeerObjetoCliente(String mensaje, Cliente cliente) {
         System.err.println("Mensaje:"+mensaje); 
        if (EsValido(mensaje)) {
            try {
                StringBuilder valor = VarsUtil.getValorSB();
                
                LeerValor(mensaje, mensaje.lastIndexOf(IDUSER) + IDUSER.length(), valor);
                cliente.setId(Integer.parseInt(valor.toString()));
                
                LeerValor(mensaje, mensaje.lastIndexOf(USER) + USER.length(), valor);
                cliente.setUser(valor.toString());
                
                LeerValor(mensaje, mensaje.lastIndexOf(PASS) + PASS.length(), valor);
                cliente.setPassword(valor.toString());
            } catch (NumberFormatException numberFormatException) {
                System.err.println("ERROR al parsear los numeros. " + numberFormatException.getMessage());
            }
        } else {
            System.err.println("Mensaje no es compatible con tipo Cliente.");
        }
    }
    
    private static boolean EsValido(String mensaje){
        return (mensaje!=null && !mensaje.trim().isEmpty() && 
                mensaje.contains(IDUSER) && 
                mensaje.contains(USER) &&
                mensaje.contains(PASS));
    }
    
    private static boolean tieneTAG(String mensaje) {
        return (mensaje!=null && !mensaje.trim().isEmpty() && 
                mensaje.contains(TAG_CONECTAR) && 
                mensaje.contains(TAG_LOGIN) &&
                mensaje.contains(TAG_REGISTRAR));
    }
    
    private static void LeerValor(String mensaje, int desde, StringBuilder ref_valor){
        int i = desde;
        ref_valor.setLength(0);
        while(i<mensaje.length() && mensaje.charAt(i)!=','){
            i++;
        }
        ref_valor.append(mensaje.substring(desde, i));
    }
    
    private static boolean EsUsuarioRegistrado(Cliente cliente) {
        return (cliente.getUser().length()>3 && cliente.getPassword().length()>7);
    }
    
}
