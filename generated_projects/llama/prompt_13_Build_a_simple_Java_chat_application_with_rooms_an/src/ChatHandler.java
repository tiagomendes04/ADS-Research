import java.io.*;
import java.net.*;
import java.util.*;

public class ChatHandler implements Runnable {
    private Socket socket;
    private ChatServer server;

    public ChatHandler(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String room = in.readLine();
            server.joinRoom(socket, room);

            String message;
            while ((message = in.readLine()) != null) {
                server.broadcast(room, message);
            }

            server.leaveRoom(socket, room);
        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error closing socket: " + e.getMessage());
            }
        }
    }
}