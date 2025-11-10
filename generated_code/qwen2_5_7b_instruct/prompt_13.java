```java
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private Map<String, PrintWriter> clients = new HashMap<>();

    public ChatServer() throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Chat Server started on port " + PORT);
    }

    public void run() {
        try {
            while (true) {
                new Handler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Handler extends Thread {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                String name = in.readLine();
                clients.put(name, out);
                broadcast(name + " joined the chat.");
                out.println("Welcome, " + name + "!");

                String message;
                while ((message = in.readLine()) != null) {
                    broadcast(name + ": " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                socket.close();
                clients.remove(getName(out));
                broadcast(getName(out) + " left the chat.");
            }
        }

        private void broadcast(String message) {
            for (PrintWriter client : clients.values()) {
                client.println(message);
            }
        }

        private String getName(PrintWriter out) {
            for (Map.Entry<String, PrintWriter> entry : clients.entrySet()) {
                if (entry.getValue() == out) {
                    return entry.getKey();
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {
        try {
            new ChatServer().run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```