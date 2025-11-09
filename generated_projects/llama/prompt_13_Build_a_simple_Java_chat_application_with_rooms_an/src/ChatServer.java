import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private ServerSocket serverSocket;
    private Map<String, List<Socket>> rooms;

    public ChatServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        rooms = new HashMap<>();
    }

    public void start() throws IOException {
        System.out.println("Server started. Listening for incoming connections...");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Incoming connection from " + socket.getInetAddress());
            Thread thread = new Thread(new ChatHandler(socket, this));
            thread.start();
        }
    }

    public void joinRoom(Socket socket, String room) {
        rooms.computeIfAbsent(room, k -> new ArrayList<>()).add(socket);
    }

    public void leaveRoom(Socket socket, String room) {
        rooms.get(room).remove(socket);
    }

    public void broadcast(String room, String message) {
        rooms.get(room).forEach(socket -> {
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(message);
            } catch (IOException e) {
                System.out.println("Error broadcasting message: " + e.getMessage());
            }
        });
    }

    public static void main(String[] args) throws IOException {
        ChatServer server = new ChatServer(8000);
        server.start();
    }
}