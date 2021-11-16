package main.java.qotd;

import main.java.echo.EchoService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

public class QouteServer {
    public static final String CLASS_NAME= QouteServer.class.getSimpleName();
    public static final Logger LOGGER= Logger.getLogger(CLASS_NAME);
    public static final int PORT= 1717;

    public static void main(String[] args) {
        int portNumber;
        if (args.length != 1) {
            System.err.println("Usage: java QouteServer <port number>");
            portNumber = PORT;
            //System.exit(1);
        } else {
            portNumber = Integer.parseInt(args[0]);
        }
        ArrayList<String> qoutes=new ArrayList<>();
        try {
            FileReader fileReader= new FileReader("qoutes.txt");
            BufferedReader bufferedReader= new BufferedReader(fileReader);
            String qoute;
            while ((qoute=bufferedReader.readLine())!=null){
                qoutes.add(qoute);
            }
        } catch (FileNotFoundException e) {
            LOGGER.severe(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
            e.printStackTrace();
        }

        try {
            ServerSocket serverSocket
                    = new ServerSocket( portNumber );


            while( qoutes.size()>0 ) {
                String frase= qoutes.remove(0);
                Socket clientSocket = serverSocket.accept();
                Thread serviceProcess = new Thread(new QouteService(frase,clientSocket));

                serviceProcess.start();
            }

        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
