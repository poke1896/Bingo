/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import modelo.Carton;
import modelo.Jugador;

/**
 *
 * @author luisf
 */
public class Cliente {

    static Socket sfd = null;
    static DataInputStream EntradaSocket;
    static DataOutputStream SalidaSocket;
    static Scanner entradaTexto;
    static JFrame ventana;
    static JTable jTable;
    
    static Jugador jugador;
    
    public static void imprimirBingo(){
        int[][] carton = jugador.getCarton().getCarton();
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                System.out.print(carton[j][i] + "\t");
            }
            System.out.println("");
        }
    }
    public static void iniciarVentana(String[][] cartoncito){
        String[] letras = {"B","I","N","G","O"};
        jTable=new JTable(cartoncito,letras);
        jTable.setSize(800,600);
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(jTable);
        
        ventana = new JFrame("Bingo " + jugador.getNombre());
        ventana.setSize(800, 600);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new BorderLayout());
        ventana.add(jScrollPane,BorderLayout.CENTER);
        ventana.setVisible(true);
        
    }
    public static void actualizarTabla(){
        TableModel modeloTabla = jTable.getModel();
        
        
        for(int i =0; i < 5; i++){
            for(int j = 0; j < 5; j ++){
                modeloTabla.setValueAt(String.valueOf(jugador.getCarton().getCarton()[i][j]), j, i);
            }
        }
        jTable.setModel(modeloTabla);
        
    }
    public static void main(String[] args) {
        try {
            entradaTexto = new Scanner(System.in);
            sfd = new Socket("localhost", 8000);
            EntradaSocket = new DataInputStream(new BufferedInputStream(sfd.getInputStream()));
            SalidaSocket = new DataOutputStream(new BufferedOutputStream(sfd.getOutputStream()));
            String nombre = JOptionPane.showInputDialog("Digite su nombre");
            
            SalidaSocket.writeUTF(nombre);
            SalidaSocket.flush();
            jugador = new Jugador(nombre);
            //Recibe y parsea el carton
            String carton = EntradaSocket.readUTF();
            String[] vectorCarton = carton.split(",");
            int [][] cartonParseado = new int[5][5];
            int contador = 0;
            String actual;
            String[][] cartonString = new String[5][5];
            for(int i = 0; i < 5; i++){
                for(int j = 0; j < 5; j ++){
                    actual = vectorCarton[contador];
                    cartonString[j][i] = actual;
                    cartonParseado[i][j] = Integer.valueOf(actual);
                    contador ++;
                }
            }
            jugador.getCarton().setCarton(cartonParseado);
            iniciarVentana(cartonString);
            imprimirBingo();
            while(true){
                jugador.getCarton().verificarNumero(Integer.valueOf(EntradaSocket.readUTF()));
                System.out.println("");
                System.out.println("");
                System.out.println("");
                imprimirBingo();
                actualizarTabla();
            }
        } catch (UnknownHostException uhe) {
            System.out.println("No se puede acceder al servidor.");
            System.exit(1);
        } catch (IOException ioe) {
            System.out.println("Comunicación rechazada.");
            System.exit(1);
        }
        while(true){
            try {
                String entradaServidor = EntradaSocket.readUTF();
                System.out.println(entradaServidor);
            } catch (IOException ex) {
                System.exit(1);
            }
        }
        
    }
}
