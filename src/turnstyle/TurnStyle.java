/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turnstyle;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 *
 * @author butwhole
 */
public class TurnStyle {
    private Counter c = new Counter();
    static String ip;
    static int port;
    java.net.ServerSocket serverSock;
    ArrayList<TurnThread> list = new ArrayList();
    
    
public void startServer() throws IOException{
        serverSock = new ServerSocket();
        serverSock.bind(new InetSocketAddress(ip,port));
        System.out.println("server started, listening on port: "+port);
        while(true){
            java.net.Socket socket = serverSock.accept();//Remember Blocking Call
            
            TurnThread tt = new TurnThread(socket,c);
            tt.start();
            
        }
    }
    public static void main(String[] args) throws IOException {
            port = 8088;
        ip = "localhost";
        new TurnStyle().startServer();
    
    }
    
}
