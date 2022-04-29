
package server;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class ServerFrame extends JFrame implements Runnable {    
    
    JButton btStart, btStop;
    JTextArea taInfo;
    ServerSocket serverSocket;
    
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    ServerThread serverThread;
    
    public ServerFrame() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize((new Dimension(837, 600)));
        
        JLabel lbStateServer = new JLabel("Infos sur le serveur\n");
        lbStateServer.setFont(new java.awt.Font("Costantia", 1, 24));
        lbStateServer.setForeground(new java.awt.Color(255, 153, 0));
        lbStateServer.setBackground(new java.awt.Color(255, 255, 255));
        
        taInfo = new JTextArea();
        taInfo.setEditable(false);
        taInfo.setFont(new java.awt.Font("Costantia", 0, 16));
        taInfo.setBackground(new java.awt.Color(255, 255, 255)); 
        taInfo.setForeground(new java.awt.Color(0, 0, 0));  
        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(taInfo);
        scroll.setPreferredSize(new Dimension(837, 600));
        
        btStart = new JButton("Lancer Serveur");
        btStart.setBackground(new java.awt.Color(102, 204, 0));
        btStart.setForeground(new java.awt.Color(102, 204, 0));
        btStart.setFont(new java.awt.Font("Costantia", 0, 12));
        btStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btStartEvent(ae);
            }
        });
        
        btStop = new JButton("Quitter");
        btStop.setBackground(new java.awt.Color(102, 204, 0));
        btStop.setForeground(new java.awt.Color(102, 204, 0));
        btStop.setFont(new java.awt.Font("Costantia", 0, 12));
        btStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btStopEvent(ae);
            }
        });
        btStart.setEnabled(true);
        btStop.setEnabled(false);
        
        JPanel panelBtn = new JPanel();
        panelBtn.add(btStart);
        panelBtn.add(btStop);
        
        JPanel p1 = new JPanel();
        p1.setPreferredSize(new Dimension(30, 30));
        JPanel p2 = new JPanel();
        p2.setPreferredSize(new Dimension(30, 30));
        
        panel.add(lbStateServer, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(panelBtn, BorderLayout.SOUTH);
        panel.add(p1, BorderLayout.WEST);
        panel.add(p2, BorderLayout.EAST);
        
        
        this.add(panel);
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void appendMessage(String message) {
        taInfo.append(message);
        taInfo.setCaretPosition(taInfo.getText().length() - 1);
    }
    
    private void startServer() {
        try {
            serverSocket = new ServerSocket(9999); 
            appendMessage("["+sdf.format(new Date())+"] Le serveur est prêt !");
            appendMessage("\n["+sdf.format(new Date())+"] Aucun utilisateur n'est connecté\n");
            
            while(true) {
                Socket socketOfServer = serverSocket.accept();  
                serverThread = new ServerThread(socketOfServer);
                serverThread.taServer = this.taInfo;
                serverThread.start();
            }
            
        } catch (java.net.SocketException e) {
            System.out.println("Le serveur est arrêté");
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erreur ! Le port est occupé !");
            System.out.println("Le serveur est occupé ! ");
            JOptionPane.showMessageDialog(this, "Ce port est occupé par le serveur", "Erreur", JOptionPane.ERROR_MESSAGE);
            this.setVisible(false);
            System.exit(0);
        }
    }

    private void btStartEvent(ActionEvent ae) {
        Connection conn = new UserDatabase().connect();
        if(conn == null) {
            JOptionPane.showMessageDialog(this, "Veuillez lancer la base de données !", "Erreur!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        new Thread(this).start();
        this.btStart.setEnabled(false);
        this.btStop.setEnabled(true);
    }
    
    private void btStopEvent(ActionEvent ae) {
        int kq = JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment arrêter le serveur", "Alerte", JOptionPane.YES_NO_OPTION);
        if(kq == JOptionPane.YES_OPTION) {
            try {
                serverThread.notifyToAllUsers("Attention: Le serveur est arrêté !");
                serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        ServerFrame serverFrame = new ServerFrame();
        serverFrame.setVisible(true);
    }

    @Override
    public void run() {
        this.startServer();
    }
}