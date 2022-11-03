/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor.hilos;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidor.ServidorUtils;

/**
 *
 * @author Marcelo
 */
public class TaskEnviarUsuarios extends Thread {
    
    private DataOutputStream flujosalida;
    
    public TaskEnviarUsuarios(Socket socket) {
        try {
            flujosalida = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(TaskEnviarUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            flujosalida.writeUTF(ServidorUtils.VarsUtil.getValorSB().toString());
        } catch (IOException ex) {
            Logger.getLogger(TaskEnviarUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
