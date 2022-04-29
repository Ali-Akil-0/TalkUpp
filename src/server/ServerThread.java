
package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.mail.internet.*; 
import java.util.Properties;  
import javax.mail.*;  

public class ServerThread extends Thread {
    Socket socketOfServer;
    BufferedWriter bw;
    BufferedReader br;
    String clientName, clientPass, clientRoom, clientMail;
    public static Hashtable<String, ServerThread> listUser = new Hashtable<>();
    public static final String NICKNAME_EXIST = "Cet email est déjà utilisé, veuillez entrer un email correct !";
    public static final String NICKNAME_VALID = "Email accepté";
    public static final String NICKNAME_INVALID = "Email ou nom d'utilisateur incorrecte";
    public static final String SIGNUP_SUCCESS = "Connexion réussie";
    public static final String ACCOUNT_EXIST = "Ce compte existe déjà !";
    
    public JTextArea taServer;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    
    StringTokenizer tokenizer;
    private final int BUFFER_SIZE = 1024;
    
    String senderName, receiverName;    
    static Socket senderSocket, receiverSocket;     
    
    UserDatabase userDB;
    
    static boolean isBusy = false;   
    
    
    public static void envoyermail(String adrs) {
    	String mailscol = "noreplytestjava" ;
    	String mdp = "123456789test";
    	    Properties p = new Properties();
    	    p.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    	    p.put("mail.smtp.ssl.protocols", "TLSv1.2");
    	    p.put("mail.smtp.port", "587");
    	    p.put("mail.smtp.host", "smtp.gmail.com");
    	    p.put("mail.smtp.auth", "true");
    	    p.put("mail.smtp.starttls.enable","true");
    	    p.put("mail.smtp.auth", "true"); 
    	   
    	    
    	    p.put("mail.smtp.socketFactory.fallback", "false");

    	    Session s = Session.getDefaultInstance(p,
    	      new javax.mail.Authenticator() {
    	      protected PasswordAuthentication getPasswordAuthentication() {
    	         return new PasswordAuthentication(mailscol, mdp);
    	      }
    	    });   

    	    try {
    	      MimeMessage m = new MimeMessage(s);
    	      m.addRecipient(Message.RecipientType.TO,new InternetAddress(adrs));
    	      m.setSubject("Inscription à TalkUp");
    	      m.setText("Vous etes desormais inscrit à TalkUp");
    	      Transport.send(m);
    	      System.out.println("Message envoyé avec succès");
    	    } catch (MessagingException e) {
    	      e.printStackTrace();
    	    }
    	  
    	
    }
    
    
    public ServerThread(Socket socketOfServer) {
        this.socketOfServer = socketOfServer;
        this.bw = null;
        this.br = null;
        
        clientName = "";
        clientMail = "";
        clientPass = "";
        clientRoom = "";
        
        userDB = new UserDatabase();
        userDB.connect();
    }
    
    public void appendMessage(String message) {
        taServer.append(message);
        taServer.setCaretPosition(taServer.getText().length() - 1);   
    }
    
    public String recieveFromClient() {
        try {
            return br.readLine();
        } catch (IOException ex) {
            System.out.println(clientName+" s'est déconnecté");
        }
        return null;
    }
    
    public void sendToClient(String response) {    
        try {
            bw.write(response);
            bw.newLine();
            bw.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendToSpecificClient(ServerThread socketOfClient, String response) {   
        try {
            BufferedWriter writer = socketOfClient.bw;
            writer.write(response);
            writer.newLine();
            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendToSpecificClient(Socket socket, String response) {     
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write(response);
            writer.newLine();
            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void notifyToAllUsers(String message) {
        Enumeration<ServerThread> clients = listUser.elements();
        ServerThread st;
        BufferedWriter writer;
        
        while(clients.hasMoreElements()) {
            st = clients.nextElement();
            writer = st.bw;

            try {
                writer.write(message);
                writer.newLine();
                writer.flush();
            } catch (IOException ex) {
                Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void notifyToUsersInRoom(String message) {
        Enumeration<ServerThread> clients = listUser.elements();
        ServerThread st;
        BufferedWriter writer;
        
        while(clients.hasMoreElements()) {
            st = clients.nextElement();
            if(st.clientRoom.equals(this.clientRoom)) {    
                writer = st.bw;

                try {
                    writer.write(message);
                    writer.newLine();
                    writer.flush();
                } catch (IOException ex) {
                    Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void notifyToUsersInRoom(String room, String message) {     
        Enumeration<ServerThread> clients = listUser.elements();
        ServerThread st;
        BufferedWriter writer;
        
        while(clients.hasMoreElements()) {
            st = clients.nextElement();
            if(st.clientRoom.equals(room)) {
                writer = st.bw;

                try {
                    writer.write(message);
                    writer.newLine();
                    writer.flush();
                } catch (IOException ex) {
                    Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void closeServerThread() {
        try {
            br.close();
            bw.close();
            socketOfServer.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getAllUsers() {
        StringBuffer kq = new StringBuffer();
        String temp = null;
        
        Enumeration<String> keys = listUser.keys();
        if(keys.hasMoreElements()) {
            String str = keys.nextElement();
            kq.append(str);
        }
        
        while(keys.hasMoreElements()) {
            temp = keys.nextElement();
            kq.append("|").append(temp);
        }
        
        return kq.toString();
    }
        public String getOnlineUsers() {
        StringBuffer kq = new StringBuffer();
        String temp = null;

        Enumeration<String> keys = listUser.keys();
        if (keys.hasMoreElements()) {
            String str = keys.nextElement();
            kq.append(str);
        }

        while (keys.hasMoreElements()) {
            temp = keys.nextElement();
            kq.append("|").append(temp);
        }

        return kq.toString();
    }
    
    
    
    public String getUsersThisRoom() {
        StringBuffer kq = new StringBuffer();
        String temp = null;
        ServerThread st;
        Enumeration<String> keys = listUser.keys();
        
        while(keys.hasMoreElements()) {
            temp = keys.nextElement();
            st = listUser.get(temp);
            if(st.clientRoom.equals(this.clientRoom))  kq.append("|").append(temp);
        }
        
        if(kq.equals("")) return "|";
        return kq.toString();   
    }
    
    public String getUsersAtRoom(String room) {
        StringBuffer kq = new StringBuffer();
        String temp = null;
        ServerThread st;
        Enumeration<String> keys = listUser.keys();
        
        while(keys.hasMoreElements()) {
            temp = keys.nextElement();
            st = listUser.get(temp);
            if(st.clientRoom.equals(room))  kq.append("|").append(temp);
        }
        
        if(kq.equals("")) return "|";
        return kq.toString();  
    }
    
    public void clientQuit() {

        if(clientName != null) {
            
            this.appendMessage("\n["+sdf.format(new Date())+"] Client \""+clientName+"\" s'est déconnecté");
            listUser.remove(clientName);
            if(listUser.isEmpty()) this.appendMessage("\n["+sdf.format(new Date())+"] Aucun utilisateur n'est connecté au serveur\n");
            notifyToAllUsers("CMD_ONLINE_USERS|"+getAllUsers());
            notifyToUsersInRoom("CMD_ONLINE_THIS_ROOM"+getUsersThisRoom());
            notifyToUsersInRoom(clientName+" a quitté l'application !");
        }
    }
    
    public void changeUserRoom() {     
        ServerThread st = listUser.get(this.clientName);
        st.clientRoom = this.clientRoom;
        listUser.put(this.clientName, st);   
   
    }
    
    public void removeUserRoom() {
        ServerThread st = listUser.get(this.clientName);
        st.clientRoom = this.clientRoom;
        listUser.put(this.clientName, st);

    }
    
    @Override
    public void run() {
        try {
   
            bw = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
            
            boolean isUserExist = true;
            String message, sender, receiver, fileName;
            StringBuffer str;
            String cmd, icon;
            while(true) {  
                try {
                    message = recieveFromClient();
                    tokenizer = new StringTokenizer(message, "|");
                    cmd = tokenizer.nextToken();
                    
                    switch (cmd) {
                        case "CMD_CHAT":
                            str = new StringBuffer(message);
                            str = str.delete(0, 9);
                            notifyToUsersInRoom("CMD_CHAT|" + this.clientName+"|"+str.toString());    
                            break;
                            
                        case "CMD_PRIVATECHAT":
/////
                            String privateSender = tokenizer.nextToken();
                            String privateReceiver = tokenizer.nextToken();
                            String messageContent = message.substring(cmd.length()+privateSender.length()+privateReceiver.length()+3, message.length());
                            System.out.println("sender2 : "+privateSender);
                             System.out.println("reciever : "+privateReceiver);
                             
                              System.out.println(" In recieve");
                           String[] messages = new String[100]; 
                           try{
                               messages = userDB.selectMessages(privateSender, privateReceiver);
                                System.out.println("messages");
              int i = 0 ;
              while(i<messages.length){
                  if(messages[i]!=null){
                     System.out.println(messages[i]);
                  }
                  i++ ;
              }
              System.out.println("messages");
              
               System.out.println("before sending anything : "+messages[0]);
             } catch(Exception e){
              System.out.println(e.getMessage());
            }
                            try{
                                userDB.deleteMessgaes(privateReceiver);
                            }
                            catch(Exception e){
                                System.out.println(e.getMessage());
                            }
                             System.out.println("whaat is here ..?????"+messages[0]);
                            ServerThread st_receiver = listUser.get(privateReceiver);
                            int  k =0 ;  
                            while(k<messages.length){
                                if(messages[k]!=null){
                                    sendToSpecificClient(st_receiver, "CMD_PRIVATECHAT|" + privateSender + "|" + messages[k]);
                                }
                            
                            k++ ; 
              
                            }
                            sendToSpecificClient(st_receiver, "CMD_PRIVATECHAT|" + privateSender + "|" + messageContent);
                            
                            System.out.println("[ServerThread] message = "+messageContent);
                            break;
                            /////
                            
                        case "CMD_ROOM":
                            clientRoom = tokenizer.nextToken();
                            changeUserRoom();
                            notifyToAllUsers("CMD_USERS|" + getAllUsers());
                            notifyToAllUsers("CMD_ONLINE_USERS|"+getAllUsers());
                            notifyToUsersInRoom("CMD_ONLINE_THIS_ROOM"+getUsersThisRoom());
                            notifyToUsersInRoom(clientName+" has just entered!");
                            break;
                            
                        case "CMD_LEAVE_ROOM":
                            String room = clientRoom;
                            clientRoom = "";    
                            removeUserRoom();
                            notifyToUsersInRoom(room, "CMD_ONLINE_THIS_ROOM"+getUsersAtRoom(room));
                            notifyToUsersInRoom(room, clientName+" a quitté le groupe");      
                            
                            break;
                            
                        case "CMD_CHECK_NAME":
                            clientName = tokenizer.nextToken();
                            clientPass = tokenizer.nextToken();
                            isUserExist = listUser.containsKey(clientName);
                            
                            if(isUserExist) { 
                                sendToClient(NICKNAME_EXIST);
                            }
                            else {  
                                int kq = userDB.checkUser(clientName, clientPass);
                                if(kq == 1) {
                                    sendToClient(NICKNAME_VALID);
                                 
                                    this.appendMessage("\n["+sdf.format(new Date())+"] Client \""+clientName+"\" s'est connecté au serveur");
                                    listUser.put(clientName, this);    
                                } else sendToClient(NICKNAME_INVALID);
                            }
                            break;
                            
                        case "CMD_SIGN_UP":
                            String name = tokenizer.nextToken();
                            String pass = tokenizer.nextToken();
                            String mail = tokenizer.nextToken();
                            //clientMail = mail ;
                            System.out.println("nom d utilisateur, mot de passe, mail = "+name+" - "+pass+" - "+mail);
                            isUserExist = listUser.containsKey(name);
                            System.out.println("mot de passe = "+pass);
                            if(isUserExist) {
                                sendToClient(NICKNAME_EXIST);
                            } else {
                                System.out.println("mot de passe = "+pass);
                                System.out.println("weeeeeeeeeeeeee");
                                ServerThread.envoyermail(mail);
                                System.out.println("mot de passe = "+pass);
                                int kq = userDB.insertUser(new User(name, pass, mail));
                                if(kq > 0) {
                                    sendToClient(SIGNUP_SUCCESS);
                                } else sendToClient(ACCOUNT_EXIST);
                            }
                            break;
                            
                        case "CMD_ONLINE_USERS":
                            notifyToAllUsers("CMD_USERS|" + getAllUsers());
                            notifyToAllUsers("CMD_ONLINE_USERS|" + getOnlineUsers());
                            sendToClient("CMD_ONLINE_USERS|"+getAllUsers());
                            notifyToUsersInRoom("CMD_ONLINE_THIS_ROOM"+getUsersThisRoom());
                            break;
                        
                        case "CMD_SENDFILETOSERVER":  
                            sender = tokenizer.nextToken();
                            receiver = tokenizer.nextToken();
                            fileName = tokenizer.nextToken();
                            int len = Integer.valueOf(tokenizer.nextToken());
                            
                            String path = System.getProperty("user.dir") + "\\sendfile\\" +fileName;
                            

                            BufferedInputStream bis = new BufferedInputStream(socketOfServer.getInputStream());   
                            FileOutputStream fos = new FileOutputStream(path);   
                            
                            byte[] buffer = new byte[BUFFER_SIZE];
                            int count = -1;
                            while((count = bis.read(buffer)) > 0) {  
                                fos.write(buffer, 0, count);         
                            }

                            Thread.sleep(300);
                            bis.close();
                            fos.close();
                            socketOfServer.close();
                      
                            ServerThread stSender = listUser.get(sender);      
                            ServerThread stReceiver = listUser.get(receiver);
                            
                            sendToSpecificClient(stSender, "CMD_FILEAVAILABLE|"+fileName+"|"+receiver+"|"+sender);
                            sendToSpecificClient(stReceiver, "CMD_FILEAVAILABLE|"+fileName+"|"+sender+"|"+sender);
                            
                            isBusy = false;
                            break;
                            
                        case "CMD_DOWNLOADFILE":   
                            fileName = tokenizer.nextToken();
                            path = System.getProperty("user.dir") + "\\sendfile\\" + fileName;
                            FileInputStream fis = new FileInputStream(path);
                            BufferedOutputStream bos = new BufferedOutputStream(socketOfServer.getOutputStream());
                            
                            byte []buffer2 = new byte[BUFFER_SIZE];
                            int count2=0;
                            
                            while((count2 = fis.read(buffer2)) > 0) {
                                bos.write(buffer2, 0, count2);   
                            }

                            bos.close();
                            fis.close();
                            socketOfServer.close();
                            
                            break;
                            
                        case "CMD_ICON":
                            icon = tokenizer.nextToken();
                            notifyToUsersInRoom("CMD_ICON|"+icon+"|"+this.clientName);
                            break;
                            
                        default:
                            notifyToAllUsers(message);
                            break;
                    }
                    
                } catch (Exception e) {
                    clientQuit();
                    break;
                }
            }
        } catch (IOException ex) {
            clientQuit();
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
  
    }
}