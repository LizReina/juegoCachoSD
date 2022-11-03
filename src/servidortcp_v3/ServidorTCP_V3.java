/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidortcp_v3;

import servidor.ServidorSocket;

/**
 *
 * @author Marcelo
 */
public class ServidorTCP_V3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ServidorSocket servidorSocket =  new ServidorSocket(81);
        servidorSocket.Iniciar();
    }
    
}
