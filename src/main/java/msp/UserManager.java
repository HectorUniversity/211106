package main.java.msp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Logger;

public class UserManager {
    public static final String CLASS_NAME = UserManager.class.getSimpleName();
    public static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    private HashMap<String, Socket> connection;

    public UserManager() {
        super();
        connection = new HashMap<String, Socket>();
    }

    public boolean connect(String user, Socket socket) {
        boolean result = true;

        Socket s = connection.put(user, socket);
        if (s != null) {
            result = false;
        }
        return result;
    }
    public boolean disconnect(String user, Socket socket) {

        return connection.remove(user,socket);
    }
    public Socket get(String user) {
        Socket s = connection.get(user);
        return s;
    }
    public void list(PrintWriter output) {
        connection.forEach((k,v) -> output.println("USUARIO--"+k));
    }
    public void send(String message) {
        Collection<Socket> conexiones = connection.values();

        for(Socket s: conexiones){
            try {
                PrintWriter output = new PrintWriter(s.getOutputStream(), true);
                output.println(message);
            } catch (IOException e) {
                LOGGER.severe(e.getMessage());
                e.printStackTrace();
            }

        }

    }
}
