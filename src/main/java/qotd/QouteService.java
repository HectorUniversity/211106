package main.java.qotd;

import main.java.echo.EchoService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QouteService implements Runnable {

    private Socket clientSocket = null;

    private String qoute;

    public QouteService(String frase, Socket client) {
        clientSocket = client;
        qoute = frase;
    }

    @Override
    public void run() {

        try {
            PrintWriter out
                    = new PrintWriter(clientSocket.getOutputStream(), true);

            out.println(qoute);
            clientSocket.close();
        } catch (IOException ex) {
            System.out.println("Error en la conexion");
        }
    }
}
