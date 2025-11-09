import java.io.*;
import java.net.*;
import java.util.*;

public class ChatClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ChatClient(String host, int port) throws UnknownHostException, IOException {
        socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void joinRoom(String room) {
        out.println(room);
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String receiveMessage() {
        try {
            return in.readLine();
        } catch (IOException e) {
            System.out.println("Error receiving message: " + e.getMessage());
            return null;
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error closing socket: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws UnknownHostException, IOException {
        ChatClient client = new ChatClient("localhost", 8000);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter room name:");
        String room = scanner.nextLine();
        client.joinRoom(room);

        while (true) {
            System.out.println("Enter message:");
            String message = scanner.nextLine();
            client.sendMessage(message);

            String receivedMessage = client.receiveMessage();
            if (receivedMessage != null) {
                System.out.println("Received message: " + receivedMessage);
            }
        }
    }
}