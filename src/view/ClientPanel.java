
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class ClientPanel extends javax.swing.JPanel {

    JFileChooser chooser;
    String filePath;

    public ClientPanel() {
        initComponents();
        chooser = new JFileChooser();
        
    }
    
    public JButton getBtExit() {
        return btExit;
    }

    public JButton getBtSend() {
        return btSend;
    }

    public JTextArea getTaInput() {
        return taInput;
    }
    
    public JTextPane getTpMessage() {
        return tpMessage;
    }

    
    public JList<String> getOnlineList() {
        return onlineList;
    }

    public JList<String> getOnlineListThisRoom() {
        return onlineListThisRoom;
    }

    public JLabel getLbRoom() {
        return lbRoom;
    }

    public JLabel getLbLike() {
        return lbLike;
    }

    public JLabel getLbPacMan() {
        return lbPacman;
    }

    public JLabel getLbCry() {
        return lbCry;
    }

    public JLabel getLbDislike() {
        return lbDislike;
    }

    public JLabel getLbGrin() {
        return lbGrin;
    }

    public JLabel getLbSmile() {
        return lbSmile;
    }
        public JLabel getLbAngel() {
        return lbAngel;
    }
    public JLabel getLbAngry() {
        return lbAngry;
    }
    public JLabel getLbSmilee() {
        return lbSmilee;
    }
    public JLabel getLbConsider() {
        return lbConsider;
    }
    public JLabel getLbCute() {
        return lbCute;
    }
    public JLabel getLbHaha() {
        return lbHaha;
    }
    public JLabel getLbHeart() {
        return lbHeart;
    }
    public JLabel getLbLol() {
        return lbLol;
    }
    public JLabel getLbVomissement() {
        return lbVomissement;
    }
    public JLabel getLbLove() {
        return lbLove;
    }

    public void appendMessage2(String msg1, String msg2, Color c13, Color c2) {  
        tpMessage.setEditable(true);
        int len = tpMessage.getDocument().getLength();
        
        StyleContext sc = StyleContext.getDefaultStyleContext();
        
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c13);
        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Impact"); 
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);     
        aset = sc.addAttribute(aset, StyleConstants.FontSize, 14);    
        
        tpMessage.setCaretPosition(len); 
        tpMessage.setCharacterAttributes(aset, false);  
        tpMessage.replaceSelection(msg1);  
        len = len + msg1.length();
        
        sc = StyleContext.getDefaultStyleContext();
        
        aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c2); 
        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Arial");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);    
        aset = sc.addAttribute(aset, StyleConstants.FontSize, 14); 
        
        tpMessage.setCaretPosition(len);
        tpMessage.setCharacterAttributes(aset, false);
        tpMessage.replaceSelection(msg2+"\n");
        
        len = len + msg2.length();
        tpMessage.setCaretPosition(len);
        tpMessage.setEditable(false);
    }
    public void appendMessage2(String message, Color color) {
        int len = tpMessage.getDocument().getLength();
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);
        
        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Comic Sans MS"); 
        
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);     
        aset = sc.addAttribute(aset, StyleConstants.FontSize, 14);     
        
        tpMessage.setCaretPosition(len); 
        tpMessage.setCharacterAttributes(aset, false);
        tpMessage.replaceSelection(message+"\n"); 
        
    }  
    public void appendMessage(String msg1, String msg2, Color c1, Color c2) {  
        int len = tpMessage.getDocument().getLength();
        StyledDocument doc = (StyledDocument) tpMessage.getDocument();
        
        SimpleAttributeSet sas = new SimpleAttributeSet();
        StyleConstants.setFontFamily(sas, "Serif");
        StyleConstants.setBold(sas, true);
        StyleConstants.setFontSize(sas, 14);
        StyleConstants.setForeground(sas, c1);
        
        try {
            doc.insertString(len, msg1, sas);
        } catch (BadLocationException ex) {
            Logger.getLogger(ClientPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        doc = (StyledDocument) tpMessage.getDocument();
        len = len+msg1.length();
        
        sas = new SimpleAttributeSet();
        StyleConstants.setFontFamily(sas, "Arial");
        StyleConstants.setFontSize(sas, 14);
        StyleConstants.setForeground(sas, c2);
        
        try {
            doc.insertString(len, msg2+"\n", sas);     
        } catch (BadLocationException ex) {
            Logger.getLogger(ClientPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tpMessage.setCaretPosition(len+msg2.length());
    }
    
    public void appendMessage(String message, Color color) {
        int len = tpMessage.getDocument().getLength();
        StyledDocument doc = (StyledDocument) tpMessage.getDocument();
        
        SimpleAttributeSet sas = new SimpleAttributeSet();
        StyleConstants.setFontFamily(sas, "Comic Sans MS");
        StyleConstants.setItalic(sas, true);
        StyleConstants.setFontSize(sas, 14);
        StyleConstants.setForeground(sas, color);
        
        try {
            doc.insertString(len, message+"\n", sas);
        } catch (BadLocationException ex) {
            Logger.getLogger(ClientPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tpMessage.setCaretPosition(len+message.length());
    }
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btSend = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        taInput = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        tpMessage = new javax.swing.JTextPane();
        lbRoom = new javax.swing.JLabel();
        lbLike = new javax.swing.JLabel();
        lbDislike = new javax.swing.JLabel();
        lbPacman = new javax.swing.JLabel();
        lbSmile = new javax.swing.JLabel();
        lbGrin = new javax.swing.JLabel();
        lbCry = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        onlineList = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        onlineListThisRoom = new javax.swing.JList<>();
        btExit = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lbAngel = new javax.swing.JLabel();
        lbAngry = new javax.swing.JLabel();
        lbSmilee = new javax.swing.JLabel();
        lbConsider = new javax.swing.JLabel();
        lbCute = new javax.swing.JLabel();
        lbHaha = new javax.swing.JLabel();
        lbHeart = new javax.swing.JLabel();
        lbLol = new javax.swing.JLabel();
        lbLove = new javax.swing.JLabel();
        lbVomissement = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(837, 600));
        setMinimumSize(new java.awt.Dimension(837, 600));

        btSend.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        btSend.setForeground(new java.awt.Color(102, 102, 255));
        btSend.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/SEND.png"))); // NOI18N
        btSend.setBorderPainted(false);
        btSend.setContentAreaFilled(false);

        taInput.setColumns(20);
        taInput.setRows(5);
        jScrollPane3.setViewportView(taInput);

        tpMessage.setEditable(false);
        tpMessage.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jScrollPane2.setViewportView(tpMessage);

        lbRoom.setFont(new java.awt.Font("Constantia", 0, 24)); // NOI18N
        lbRoom.setForeground(new java.awt.Color(255, 153, 0));
        lbRoom.setText("Groupe");

        lbLike.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/like2.png"))); // NOI18N

        lbDislike.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dislike.png"))); // NOI18N

        lbPacman.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pacman.png"))); // NOI18N

        lbSmile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/smile.png"))); // NOI18N

        lbGrin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/grin.png"))); // NOI18N

        lbCry.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cry.png"))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo.png"))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Sitka Text", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 153, 0));
        jLabel5.setText("TalkUp");

        jLabel6.setFont(new java.awt.Font("Sitka Subheading", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 153, 0));
        jLabel6.setText("Lier deux mondes ...");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel6)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Constantia", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 204, 0));
        jLabel4.setText("Participants du groupe");

        onlineList.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        onlineList.setForeground(new java.awt.Color(51, 51, 255));
        jScrollPane4.setViewportView(onlineList);

        jLabel1.setFont(new java.awt.Font("Constantia", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 204, 0));
        jLabel1.setText("Contacts en ligne ");

        onlineListThisRoom.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        onlineListThisRoom.setForeground(new java.awt.Color(51, 51, 255));
        jScrollPane5.setViewportView(onlineListThisRoom);

        btExit.setFont(new java.awt.Font("Comic Sans MS", 1, 16)); // NOI18N
        btExit.setForeground(new java.awt.Color(255, 102, 102));
        btExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/quit.png"))); // NOI18N
        btExit.setBorderPainted(false);
        btExit.setContentAreaFilled(false);
        btExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExitActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Sitka Subheading", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("Quitter le groupe");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jLabel4))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(btExit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btExit)
                    .addComponent(jLabel2))
                .addContainerGap())
        );

        lbAngel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/angel.png"))); // NOI18N

        lbAngry.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/angry.png"))); // NOI18N

        lbSmilee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/big_smile.png"))); // NOI18N

        lbConsider.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/consider.png"))); // NOI18N

        lbCute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cute.png"))); // NOI18N

        lbHaha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/haha.png"))); // NOI18N

        lbHeart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/heart.png"))); // NOI18N

        lbLol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/lol.png"))); // NOI18N

        lbLove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/love.png"))); // NOI18N

        lbVomissement.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/vomissement.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbRoom)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbLike)
                                    .addComponent(lbCute))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lbHaha)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lbHeart)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lbLol))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lbDislike)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lbPacman)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lbSmile)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lbGrin)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lbCry)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lbAngel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lbAngry))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lbLove)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lbVomissement)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbSmilee)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbConsider)))
                        .addGap(0, 33, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addGap(10, 10, 10)
                        .addComponent(btSend)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lbRoom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lbSmilee)
                                            .addComponent(lbConsider))
                                        .addGap(54, 54, 54))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(lbGrin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lbLike, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lbDislike, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lbPacman, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lbSmile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lbCry, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(lbAngel)
                                            .addComponent(lbAngry))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lbHaha)
                                            .addComponent(lbCute)
                                            .addComponent(lbHeart)
                                            .addComponent(lbLol)
                                            .addComponent(lbLove)
                                            .addComponent(lbVomissement))
                                        .addContainerGap())))
                            .addComponent(btSend, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        lbLike.getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents
   public JLabel getjLabel2() {

        return jLabel2;
    }
    private void btExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExitActionPerformed
        
    }//GEN-LAST:event_btExitActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btExit;
    private javax.swing.JButton btSend;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lbAngel;
    private javax.swing.JLabel lbAngry;
    private javax.swing.JLabel lbConsider;
    private javax.swing.JLabel lbCry;
    private javax.swing.JLabel lbCute;
    private javax.swing.JLabel lbDislike;
    private javax.swing.JLabel lbGrin;
    private javax.swing.JLabel lbHaha;
    private javax.swing.JLabel lbHeart;
    private javax.swing.JLabel lbLike;
    private javax.swing.JLabel lbLol;
    private javax.swing.JLabel lbLove;
    private javax.swing.JLabel lbPacman;
    private javax.swing.JLabel lbRoom;
    private javax.swing.JLabel lbSmile;
    private javax.swing.JLabel lbSmilee;
    private javax.swing.JLabel lbVomissement;
    private javax.swing.JList<String> onlineList;
    private javax.swing.JList<String> onlineListThisRoom;
    private javax.swing.JTextArea taInput;
    private javax.swing.JTextPane tpMessage;
    // End of variables declaration//GEN-END:variables
}
