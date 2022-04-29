
package view;

import controller.ClientFrame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import view.ClientPanel;
import view.LoginPanel;
import view.PrivateChat;
import view.RoomPanel;
import view.SignUpPanel;
import view.WelcomePanel;

import controller.ReceivingFileThread;
import controller.SendFileFrame;
import controller.SendingFileThread;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.counting;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class PrivateChat extends javax.swing.JFrame {

    public static String sender, receiver;   
    public String serverHost;
    public BufferedWriter bw;
    public BufferedReader br;
    HTMLEditorKit htmlKit;
    HTMLDocument htmlDoc;   
    public static String nickname;

    Socket socketOfSender ;
    public Boolean audiocheck = false ; 

    
    public PrivateChat() {
        initComponents();
        htmlKit = new HTMLEditorKit();
        htmlDoc = new HTMLDocument();
        tpMessage_pc.setEditorKit(htmlKit);
        tpMessage_pc.setDocument(htmlDoc);
    }

    public PrivateChat(String sender, String receiver, String serverHost, BufferedWriter bw, BufferedReader br) {
        initComponents();
        this.sender = sender;
        this.receiver = receiver;
        this.serverHost = serverHost;
        this.bw = bw;
        this.br = br;
        
        htmlKit = new HTMLEditorKit();
        htmlDoc = new HTMLDocument();
        tpMessage_pc.setEditorKit(htmlKit);
        tpMessage_pc.setDocument(htmlDoc);
    }

    public JLabel getLbReceiver() {
        return lbReceiver;
    }
    
    public void sendToServer(String line) {
        try {
            this.bw.write(line);
            this.bw.newLine(); 
            this.bw.flush();
        } catch (java.net.SocketException e) {
            JOptionPane.showMessageDialog(this, "Serveur non lancé, impossible d'envoyer un message", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (java.lang.NullPointerException e) {
            System.out.println("[sendToServer()] Serveur non lancé !");
        } catch (IOException ex) {
            Logger.getLogger(PrivateChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String recieveFromServer() {
        try {
            return this.br.readLine();  
        } catch (java.lang.NullPointerException e) {
            System.out.println("[recieveFromServer()] Serveur non lancé !");
        } catch (IOException ex) {
            Logger.getLogger(PrivateChat.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public void appendMessage(String msg1, String msg2, Color c3, Color c4) {  
        int len = tpMessage_pc.getDocument().getLength();
        StyledDocument doc = (StyledDocument) tpMessage_pc.getDocument();
        
        SimpleAttributeSet sas = new SimpleAttributeSet();
        StyleConstants.setFontFamily(sas, "Tahoma");
        StyleConstants.setBold(sas, true);
        StyleConstants.setFontSize(sas, 14);
        StyleConstants.setForeground(sas, c3);
        
        try {
            doc.insertString(len, msg1, sas);
        } catch (BadLocationException ex) {
            Logger.getLogger(ClientPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        doc = (StyledDocument) tpMessage_pc.getDocument();
        len = len+msg1.length();
        
        sas = new SimpleAttributeSet();
        StyleConstants.setFontFamily(sas, "Arial");
        StyleConstants.setFontSize(sas, 14);
        StyleConstants.setForeground(sas, c4);
        
        try {
            doc.insertString(len, msg2+"\n", sas);   
        } catch (BadLocationException ex) {
            Logger.getLogger(ClientPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tpMessage_pc.setCaretPosition(len+msg2.length());
    }
    
    public void appendMessage_Left(String msg1, String msg2) {     
        try {
            htmlKit.insertHTML(htmlDoc, htmlDoc.getLength(), "<p style=\"color:black; padding: 3px; margin-top: 4px; margin-right:35px; text-align:left; font:normal 12px Tahoma;\"><span style=\"background-color:#f3f3f3;\"><b>" + msg1 + "</b><span style=\"color:black;\">" + msg2 + "</span></span></p>", 0, 0, null);
        } catch (BadLocationException | IOException ex) {
            Logger.getLogger(PrivateChat.class.getName()).log(Level.SEVERE, null, ex);
        }
        tpMessage_pc.setCaretPosition(tpMessage_pc.getDocument().getLength());
    }
    
    public void appendMessage_Left(String msg1, String msg2, String color1, String color2) {     
        try {
            htmlKit.insertHTML(htmlDoc, htmlDoc.getLength(), "<p style=\"color:" + color1 + "; padding: 3px; margin-top: 4px; margin-right:35px; text-align:left; font:normal 12px Tahoma;\"><span><b>" + msg1 + "</b><span style=\"color:" + color2 + ";\">" + msg2 + "</span></span></p><br/>", 0, 0, null);
        } catch (BadLocationException | IOException ex) {
            Logger.getLogger(PrivateChat.class.getName()).log(Level.SEVERE, null, ex);
        }
        tpMessage_pc.setCaretPosition(tpMessage_pc.getDocument().getLength());
    }
    
    public void appendMessage_Right(String msg1, String msg2) {  
        try { 
            
            htmlKit.insertHTML(htmlDoc, htmlDoc.getLength(), "<p style=\"color:white; padding: 3px; margin-top: 4px; margin-left:35px; text-align:right; font:normal 12px Tahoma;\"><span style=\"background-color: #889eff; -webkit-border-radius: 10px;\">" + msg2 + "</span></p>", 0, 0, null);
        } catch (BadLocationException | IOException ex) {
            Logger.getLogger(PrivateChat.class.getName()).log(Level.SEVERE, null, ex);
        }
        tpMessage_pc.setCaretPosition(tpMessage_pc.getDocument().getLength());
    }
    
    public void appendMessage_Right(String msg1) {     
        try { 
        htmlKit.insertHTML(htmlDoc, htmlDoc.getLength(), "<p style=\"color:white; padding: 3px; margin-top: 4px; margin-left:35px; text-align:right; font:normal 12px Tahoma;\"><span style=\"background-color: #889eff; -webkit-border-radius: 10px;\">" + msg1 + "</span></p>", 0, 0, null);
        } catch (BadLocationException | IOException ex) {
            Logger.getLogger(PrivateChat.class.getName()).log(Level.SEVERE, null, ex);
        }
        tpMessage_pc.setCaretPosition(tpMessage_pc.getDocument().getLength());
    }

    public void insertButton(String sender, String fileName) {
        JButton bt = new JButton(fileName);
        bt.setName(fileName);
        bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                downloadFile(fileName);
            }
        });
        appendMessage_Left(sender, " Envoie un message ", "#000000", "#FF9900");
        tpMessage_pc.setCaretPosition(tpMessage_pc.getDocument().getLength() - 1);
        tpMessage_pc.insertComponent(bt);
    }

    private void downloadFile(String buttonName) {
        String myDownloadFolder;
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int kq = chooser.showSaveDialog(this);
        if(kq == JFileChooser.APPROVE_OPTION) {
            myDownloadFolder = chooser.getSelectedFile().getAbsolutePath();
        } else {
            myDownloadFolder = "D:";
            JOptionPane.showMessageDialog(this, "Le dossier par défaut de sauvegarde est D:\\", "Notice", JOptionPane.INFORMATION_MESSAGE);
        }

        try {
            Socket socketOfReceiver = new Socket(serverHost, 9999);  
            new ReceivingFileThread(socketOfReceiver, myDownloadFolder, buttonName, this).start();    
            System.out.println("Reception du fichier");
        } catch (IOException ex) {
            Logger.getLogger(PrivateChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tpMessage_pc = new javax.swing.JTextPane();
        tfInput_pc = new javax.swing.JTextField();
        btSend_pc = new javax.swing.JButton();
        btFile_pc = new javax.swing.JButton();
        lbReceiver = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tpMessage_pc.setEditable(false);
        jScrollPane1.setViewportView(tpMessage_pc);

        tfInput_pc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tfInput_pc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfInput_pcActionPerformed(evt);
            }
        });

        btSend_pc.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        btSend_pc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/SEND.png"))); // NOI18N
        btSend_pc.setToolTipText("send a message");
        btSend_pc.setBorderPainted(false);
        btSend_pc.setContentAreaFilled(false);
        btSend_pc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSend_pcActionPerformed(evt);
            }
        });

        btFile_pc.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        btFile_pc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clip.png"))); // NOI18N
        btFile_pc.setToolTipText("send a file");
        btFile_pc.setBorderPainted(false);
        btFile_pc.setContentAreaFilled(false);
        btFile_pc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFile_pcActionPerformed(evt);
            }
        });

        lbReceiver.setFont(new java.awt.Font("Sitka Text", 0, 24)); // NOI18N
        lbReceiver.setText("Contact");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Profil.png"))); // NOI18N

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/videocall.png"))); // NOI18N

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/call.png"))); // NOI18N
        jLabel8.setText("jLabel8");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dots.png"))); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/audio.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbReceiver, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(29, 29, 29))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(tfInput_pc, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2)
                                .addGap(4, 4, 4)
                                .addComponent(btFile_pc)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btSend_pc))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 30, Short.MAX_VALUE)))
                .addGap(88, 88, 88))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(lbReceiver)))
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfInput_pc, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btFile_pc)
                            .addComponent(jButton2)
                            .addComponent(btSend_pc))))
                .addGap(20, 20, 20))
        );

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Sitka Text", 2, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 153, 0));
        jLabel2.setText("TalkUp");

        jLabel3.setFont(new java.awt.Font("Sitka Subheading", 2, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 153, 0));
        jLabel3.setText("Lier deux mondes ...");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addGap(12, 12, 12))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfInput_pcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfInput_pcActionPerformed
        sendMessage();
    }//GEN-LAST:event_tfInput_pcActionPerformed

    private void btSend_pcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSend_pcActionPerformed
        sendMessage();
    }//GEN-LAST:event_btSend_pcActionPerformed

    private void btFile_pcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFile_pcActionPerformed
        openSendFileFrame();
    }//GEN-LAST:event_btFile_pcActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
                        InfoContactFrame ac = new InfoContactFrame();
		        ac.setVisible(true);
		        ac.setVisible(true);
                        ac.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                        InfoContactFrame.AfficherDetail();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        audioSending();
        
        
        
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void sendMessage() {
        String msg = tfInput_pc.getText();
        if(msg.equals("")) return;
        appendMessage_Right(msg);
        sendToServer("CMD_PRIVATECHAT|" + this.sender + "|" + this.receiver + "|" + msg);
        tfInput_pc.setText("");
    }
    /******************************/

    
    private void openSendFileFrame() {
        SendFileFrame sendFileFrame = new SendFileFrame(serverHost, sender);
        
        sendFileFrame.thePersonIamChattingWith = receiver;
        sendFileFrame.getTfReceiver().setText(receiver);
        sendFileFrame.setVisible(true);
        sendFileFrame.setLocation(450, 250);
        sendFileFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    /**********************************************/

    /**
     * @param args the command line arguments
     */
    private void audioSending() {
        RecordTest audio = new RecordTest();
        audio.setVisible(true);
        audiocheck=true ;
        audio.setLocation(450, 250);
        audio.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        SendFileFrame sendFileFrame = new SendFileFrame(serverHost, sender);
        sendFileFrame.thePersonIamChattingWith = receiver;
        sendFileFrame.getTfReceiver().setText(receiver);
        sendFileFrame.setVisible(true);
        sendFileFrame.setLocation(450, 250);
        sendFileFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        try {
            socketOfSender = new Socket(serverHost, 9999);  
            new SendingFileThread(sender, receiver, "audios/record.wav", socketOfSender, sendFileFrame, null).start();    //socketOfSender is the one who sends the file
      
        } catch (IOException ex) {
            Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PrivateChat().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btFile_pc;
    private javax.swing.JButton btSend_pc;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbReceiver;
    private javax.swing.JTextField tfInput_pc;
    private javax.swing.JTextPane tpMessage_pc;
    // End of variables declaration//GEN-END:variables
}
