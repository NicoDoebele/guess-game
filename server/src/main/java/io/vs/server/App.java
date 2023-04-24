/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package io.vs.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
    public static void main(String[] args) {
        int serverPort = 7000;
        try(ServerSocket listenSocket = new ServerSocket(serverPort)){
            while(true) {
                Socket clientSocket = listenSocket.accept();
                DataInputStream in = new DataInputStream( clientSocket.getInputStream());
                DataOutputStream out = new DataOutputStream( clientSocket.getOutputStream());
                int state = in.read();
                if (state == 0) {
                    GuessGame guessGame = new GuessGame(clientSocket);
                    guessGame.start();
                    out.write(0);
                }
            }
        } catch(Exception e) {
            System.out.println("Listen :"+e.getMessage());
        }
    }
}