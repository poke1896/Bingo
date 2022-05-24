/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Jugador;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Esta clase crea un servidor para el bingo
 * @author luisf
 */
public class Servidor extends JFrame {

    private static ArrayList<Jugador> jugadores;
    private static ArrayList<Socket> sockets;
    private JLabel[] callLabels;
    private JButton resetButton;
    private static boolean banderaInicio;
    private JButton simulationButton;
    private static JTextArea numeros;
    private static JTextArea nombresJugadores;
    //Crea un servidor e instancia una ventana
    public Servidor() {
        JFrame frame = new JFrame("Bingo Juego");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(createCalledBoardPanel(), BorderLayout.BEFORE_FIRST_LINE);
        frame.add(createCenterPanel(), BorderLayout.CENTER);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

        System.out.println(frame.getSize());

    }
    /**
     * Crea un panel para agregar un label donde diga los números jugados 
     * @return panel el panel con los números
     */
    private JPanel createCalledBoardPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        Font font = titlePanel.getFont().deriveFont(Font.BOLD, 24f);

        JLabel label = new JLabel("Numeros Jugados");
        label.setFont(font);
        titlePanel.add(label);

        panel.add(titlePanel, BorderLayout.BEFORE_FIRST_LINE);
        panel.add(createCalledPanel(), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCalledPanel() {
        //Creacion de celdas para los numeros 
        JPanel panel = new JPanel(new GridLayout(0, 16, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        Font font = panel.getFont().deriveFont(Font.PLAIN, 16f);
        Font boldFont = panel.getFont().deriveFont(Font.BOLD, 16f);

        callLabels = new JLabel[80];
        int labelCount = 0;
        String[] letters = {"B", "I", "N", "G", "O"};
        int count = 0;
        for (int i = 0; i < 75; i++) {
            if (i % 15 == 0) {
                JPanel cellPanel = new JPanel();
                cellPanel.setPreferredSize(new Dimension(35, 35));

                callLabels[labelCount] = new JLabel(letters[count++]);
                callLabels[labelCount].setForeground(Color.RED);
                callLabels[labelCount].setFont(boldFont);
                cellPanel.add(callLabels[labelCount++]);

                panel.add(cellPanel);
            }
            JPanel cellPanel = new JPanel();
            cellPanel.setPreferredSize(new Dimension(35, 35));

            callLabels[labelCount] = new JLabel(String.format("%2d", i + 1));
            callLabels[labelCount].setFont(font);
            cellPanel.add(callLabels[labelCount++]);

            panel.add(cellPanel);
        }

        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createCardBoardPanel(), BorderLayout.BEFORE_LINE_BEGINS);

        JPanel innerPanel = new JPanel(new FlowLayout());
        innerPanel.add(createCallingBoardPanel());
        panel.add(innerPanel, BorderLayout.AFTER_LINE_ENDS);

        return panel;
    }

    private JPanel createCardBoardPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        Font font = titlePanel.getFont().deriveFont(Font.BOLD, 24f);

        JLabel label = new JLabel("Jugadores");
        label.setFont(font);
        titlePanel.add(label);
       
        

        panel.add(titlePanel, BorderLayout.BEFORE_FIRST_LINE);
        panel.add(createNamePanel(), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createNamePanel() {
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 50;
        gbc.gridy = 50;
        gbc.gridy++;
        panel.add(createCallingNamePanel(), gbc);

        return panel;
    }

    private JPanel createCallingBoardPanel() {
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JPanel titlePanel = new JPanel(new FlowLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        Font font = titlePanel.getFont().deriveFont(Font.BOLD, 24f);

        JLabel label = new JLabel("Calling Board");
        label.setFont(font);
        titlePanel.add(label);

        panel.add(titlePanel, gbc);

        gbc.gridy++;
        panel.add(createCallingPanel(), gbc);

        gbc.gridy++;
        simulationButton = new JButton("Start Round");
        simulationButton.addActionListener(new ActionListener(){
          

            @Override
            public void actionPerformed(ActionEvent e) {
               banderaInicio = true;
            }
        });
        panel.add(simulationButton, gbc);

        return panel;
    }
    private void iniciar(){
        
    }

    private JPanel createCallingPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        numeros = new JTextArea(11, 10);
        numeros.setEditable(false);
        numeros.setFont(new Font("Courier New", Font.BOLD, 16));
        numeros.setMargin(new Insets(5, 5, 5, 5));
        JScrollPane scrollPane = new JScrollPane(numeros);
        panel.add(scrollPane, BorderLayout.CENTER);

        Dimension d = numeros.getPreferredSize();
        panel.setPreferredSize(new Dimension(d.width + 50, d.height));

        return panel;
    }

    private JPanel createCallingNamePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        nombresJugadores = new JTextArea(11, 10);
        nombresJugadores.setEditable(false);
        nombresJugadores.setFont(new Font("Courier New", Font.BOLD, 16));
        nombresJugadores.setMargin(new Insets(5, 5, 5, 5));
        JScrollPane scrollPane = new JScrollPane(nombresJugadores);
        panel.add(scrollPane, BorderLayout.CENTER);

        Dimension d = nombresJugadores.getPreferredSize();
        panel.setPreferredSize(new Dimension(d.width + 50, d.height));

        return panel;
    }
    /**
     * Inicio del servidor
     * @param args 
     */
    public static void main(String[] args) {
        banderaInicio = false;

        Servidor guiv = new Servidor();
        guiv.setVisible(true);

        jugadores = new ArrayList();
        sockets = new ArrayList();
        ServerSocket sfd = null;
        try {
            //Crea el servidor usando el puerto 8000
            sfd = new ServerSocket(8000);
            System.out.println("Servidor iniciado");
        } catch (IOException ioe) {
            System.out.println("Comunicación rechazada." + ioe);
            System.exit(1);
        }

        Socket nsfd = new Socket();
   
        while (!banderaInicio) {
            try {

                nsfd = sfd.accept();

                DataInputStream FlujoLectura = new DataInputStream(new BufferedInputStream(nsfd.getInputStream()));
                String nombre = FlujoLectura.readUTF();
                System.out.println("Conexion aceptada de: " + nombre);
                DataOutputStream FlujoEscritura;
                //Envia cada carton a su respectivo cliente
                Jugador actual = new Jugador(nombre);
                String cartonSalida = "";
                FlujoEscritura = new DataOutputStream(new BufferedOutputStream(nsfd.getOutputStream()));
                for (int fila = 0; fila < 5; fila++) {
                    for (int columna = 0; columna < 5; columna++) {
                        cartonSalida += actual.getCarton().getCarton()[fila][columna] + ",";
                    }
                }
                FlujoEscritura.writeUTF(cartonSalida);
                FlujoEscritura.flush();
                
                
                //Añade el jugador y sus datos a la memoria
                jugadores.add(actual);
                nombresJugadores.append(nombre + "\n");
                sockets.add(nsfd);
             

            } catch (IOException ioe) {
                System.out.println("Error: " + ioe);
            }
        }
        try {
            DataOutputStream FlujoEscritura;
            
            //Comienza el juego

            ArrayList<Integer> numerosSalidos = new ArrayList();
            Random random = new Random();
            boolean bandera = true;
            int salido;
            while (bandera) {
                //Envía números aleatorios para el juego a cada cliente
                salido = random.nextInt(75 - 1 + 1) + 1;
                while (numerosSalidos.contains(salido)) {
                    salido = random.nextInt(75 - 1 + 1) + 1;
                }
                numeros.append(String.valueOf(salido) + "\n");
                numerosSalidos.add(salido);
                for (int i = 0; i < jugadores.size(); i++) {
                    FlujoEscritura = new DataOutputStream(new BufferedOutputStream(sockets.get(i).getOutputStream()));
                    FlujoEscritura.writeUTF(String.valueOf(salido));
                    FlujoEscritura.flush();
                    jugadores.get(i).getCarton().verificarNumero(salido);
                }
                //Verifica premios
                for (int i = 0; i < jugadores.size(); i++) {
                    if (jugadores.get(i).getCarton().verificarPremioCartonLleno()) {
                        System.out.println("El ganador es: " + jugadores.get(i).getNombre());
                        bandera = false;
                        String ganador = jugadores.get(i).getNombre();
                        JOptionPane.showMessageDialog(null,"El ganador es "+ganador);
                        
                    }
                }
                Thread.sleep(1000);
            }

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

    }

    public JTextArea getTextArea() {
        return numeros;
    }

    public void setCallNumber(int number) {
        Color backgroundColor = Color.BLACK;
        Color foregroundColor = Color.WHITE;
        setCellLabel(number, backgroundColor, foregroundColor);
    }

    public void resetCallNumber(int number) {
        Color backgroundColor = new Color(238, 238, 238);
        Color foregroundColor = Color.BLACK;
        setCellLabel(number, backgroundColor, foregroundColor);
    }
  
    private void setCellLabel(int number, Color backgroundColor,
            //selector de nunmeros
            Color foregroundColor) {
        if (number >= 1 && number <= 15) {
            callLabels[number].setForeground(foregroundColor);
            callLabels[number].getParent().setBackground(backgroundColor);
        } else if (number >= 16 && number <= 30) {
            callLabels[number + 1].setForeground(foregroundColor);
            callLabels[number + 1].getParent().setBackground(backgroundColor);
        } else if (number >= 31 && number <= 45) {
            callLabels[number + 2].setForeground(foregroundColor);
            callLabels[number + 2].getParent().setBackground(backgroundColor);
        } else if (number >= 46 && number <= 60) {
            callLabels[number + 3].setForeground(foregroundColor);
            callLabels[number + 3].getParent().setBackground(backgroundColor);
        } else if (number >= 61 && number <= 75) {
            callLabels[number + 4].setForeground(foregroundColor);
            callLabels[number + 4].getParent().setBackground(backgroundColor);
        }
    }

}
