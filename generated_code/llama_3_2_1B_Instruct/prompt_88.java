```java
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TextAdventureEngine {

    public static void main(String[] args) {
        Room room = new Room("Hallway");
        room.addItem("key");
        room.addItem("sword");

        Map<String, Room> rooms = new HashMap<>();
        rooms.put("Hallway", room);

        Player player = new Player();
        player.setInventory(new Inventory());
        player.setRoom(rooms.get("Hallway"));

        while (true) {
            Command command = new Command();
            player.execute(command);
        }
    }
}

class Room {
    private String name;
    private Map<String, Object> items;
    private Map<String, Room> exits;

    public Room(String name) {
        this.name = name;
        items = new HashMap<>();
        exits = new HashMap<>();
    }

    public void addItem(String itemName, Object itemValue) {
        items.put(itemName, itemValue);
    }

    public void addExits(String direction) {
        exits.put(direction, new Room(direction));
    }

    public String getName() {
        return name;
    }
}

class Item {
    private String name;
    private String description;
    private int value;

    public Item(String name, String description, int value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }
}

class Command {
    private String action;

    public Command(String action) {
        this.action = action;
    }

    public String execute(Player player) {
        return execute(player, action);
    }

    public abstract String execute(Player player, String action);
}

class Player {
    private String name;
    private String roomName;
    private Inventory inventory;
    private Room currentRoom;

    public Player() {
        this.name = "Player";
        this.roomName = "Player's Room";
        this.inventory = new Inventory();
        this.currentRoom = new Room(roomName);
    }

    public String getName() {
        return name;
    }

    public String getRoomName() {
        return roomName;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setRoom(Room room) {
        currentRoom = room;
    }
}

class Inventory {
    private Map<String, Object> items;

    public Inventory() {
        this.items = new HashMap<>();
    }

    public void addItem(String itemName, Object itemValue) {
        items.put(itemName, itemValue);
    }

    public Object getItem(String itemName) {
        return items.get(itemName);
    }
}

class CommandExecutor {
    public Command execute(String action, Player player, Command command) {
        if (action.equals("go")) {
            Room targetRoom = (Room) command.getArgument(0);
            player.setRoom(targetRoom);
            return command;
        } else if (action.equals("take")) {
            String itemName = (String) command.getArgument(0);
            if (items.containsKey(itemName)) {
                items.remove(itemName);
                player.setInventory(new Inventory());
                player.setRoom(player.getCurrentRoom());
                return command;
            } else {
                return command;
            }
        } else if (action.equals("drop")) {
            String itemName = (String) command.getArgument(0);
            if (items.containsKey(itemName)) {
                items.remove(itemName);
                return command;
            } else {
                return command;
            }
        } else {
            return command;
        }
    }
}
```