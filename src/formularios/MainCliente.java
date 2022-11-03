/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import java.io.IOException;

/**
 *
 * @author bismart
 */
public class MainCliente {
        public static void main(String argv[]) {
        
            Cliente cli = new Cliente(81, "127.0.0.1");
            cli.inicializar();
     
    }
}
