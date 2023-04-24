package io.vs.server;

import java.io.DataOutputStream;
import java.net.Socket;
import java.io.DataInputStream;

public class GuessGame extends Thread {

    private int numberToGuess;
    private int timesGuessed = 0;
    private Socket socket;
    private boolean gameIsRunning = true;

    public GuessGame(Socket socket) {
        this.socket = socket;
        this.numberToGuess = 6;
    }

    private int makeGuess(int guess) {

        timesGuessed++;

        if (guess == numberToGuess) return 1;
        if (guess != numberToGuess && timesGuessed >= 3) {
            gameIsRunning = false;
            return 9;
        }
        else if (guess != numberToGuess) return 2;

        throw new Error("Invalid GameState error.");
    }
    
    @Override
    public void run(){
        try {
            while (gameIsRunning) {
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                int guess = in.read();
                int result = makeGuess(guess);
                out.write(result);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
