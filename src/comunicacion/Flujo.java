/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import modelo.Jugador;

/**
 *
 * @author luisf
 */
public class Flujo extends Thread{
    Socket nsfd;
    DataInputStream FlujoLectura;
    DataOutputStream FlujoEscritura;
    Jugador jugador;
    public Flujo(Socket sfd, Jugador jugador) {
        this.jugador = jugador;
        nsfd = sfd;
        try {
            FlujoLectura = new DataInputStream(new BufferedInputStream(sfd.getInputStream()));
            FlujoEscritura = new DataOutputStream(new BufferedOutputStream(sfd.getOutputStream()));
        } catch (IOException ioe) {
            System.out.println("IOException(Flujo): " + ioe);
        }
    }
    public void run(){
        
    }
    
}
