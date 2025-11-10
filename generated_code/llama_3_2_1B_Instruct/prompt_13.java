```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ChatApplication {
    private Map<String, List<Room>> rooms;
    private Map<String, List<String>> messageMap;

    public ChatApplication() {
        rooms = new HashMap<>();
        messageMap = new HashMap<>();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Create Room");
            System.out.println("2. Join Room");
            System.out.println("3. Create Message");
            System.out.println("4. List Rooms");
            System.out.println("5. List Messages");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (option) {
                case 1:
                    createRoom(scanner);
                    break;
                case 2:
                    joinRoom(scanner);
                    break;
                case 3:
                    createMessage(scanner);
                    break;
                case 4:
                    listRooms();
                    break;
                case 5:
                    listMessages();
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void createRoom(Scanner scanner) {
        System.out.print("Enter room name: ");
        String roomName = scanner.nextLine();
        rooms.put(roomName, new ArrayList<>());
        System.out.println("Room created successfully.");
    }

    private void joinRoom(Scanner scanner) {
        System.out.print("Enter room name: ");
        String roomName = scanner.nextLine();
        if (rooms.containsKey(roomName)) {
            List<Room> roomsList = rooms.get(roomName);
            System.out.print("Enter room ID: ");
            int roomId = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over
            for (int i = 0; i < roomsList.size(); i++) {
                if (roomsList.get(i).getId() == roomId) {
                    rooms.get(roomName).add(roomsList.get(i));
                    System.out.println("Joined room successfully.");
                    return;
                }
            }
            System.out.println("Room not found.");
        } else {
            System.out.println("Room not found.");
        }
    }

    private void createMessage(Scanner scanner) {
        System.out.print("Enter sender's ID: ");
        int senderId = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        System.out.print("Enter message: ");
        String message = scanner.nextLine();
        System.out.println("Message sent successfully.");
        messageMap.put(senderId, message);
    }

    private void listRooms() {
        System.out.println("Rooms:");
        for (Map.Entry<String, List<Room>> entry : rooms.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().size());
        }
    }

    private void listMessages() {
        System.out.println("Messages:");
        for (Map.Entry<String, List<String>> entry : messageMap.entrySet()) {
            System.out.println(entry.getKey() + ":");
            for (String message : entry.getValue()) {
                System.out.println(message);
            }
        }
    }
}

class Room {
    private int id;
    private List<ChatMessage> messages;

    public Room(int id) {
        this.id = id;
        this.messages = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void addMessage(ChatMessage message) {
        messages.add(message);
    }
}

class ChatMessage {
    private String senderId;
    private String message;

    public ChatMessage(String senderId, String message) {
        this.senderId = senderId;
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getMessage() {
        return message;
    }
}
```