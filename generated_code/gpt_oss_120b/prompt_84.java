```java
package com.example.crm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CRMApp {

    public static void main(String[] args) {
        new CRMApp().run();
    }

    private final List<Contact> contacts = new ArrayList<>();
    private final List<Task> tasks = new ArrayList<>();
    private final List<Note> notes = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    private void run() {
        while (true) {
            System.out.println("\n--- Simple CRM ---");
            System.out.println("1. Manage Contacts");
            System.out.println("2. Manage Tasks");
            System.out.println("3. Manage Notes");
            System.out.println("0. Exit");
            System.out.print("Select option: ");
            int choice = readInt();
            switch (choice) {
                case 1 -> manageContacts();
                case 2 -> manageTasks();
                case 3 -> manageNotes();
                case 0 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    // -------------------- Contact Management --------------------
    private void manageContacts() {
        while (true) {
            System.out.println("\n--- Contacts ---");
            System.out.println("1. List Contacts");
            System.out.println("2. Add Contact");
            System.out.println("3. Update Contact");
            System.out.println("4. Delete Contact");
            System.out.println("0. Back");
            System.out.print("Select option: ");
            int choice = readInt();
            switch (choice) {
                case 1 -> listContacts();
                case 2 -> addContact();
                case 3 -> updateContact();
                case 4 -> deleteContact();
                case 0 -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void listContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }
        System.out.println("\nContacts:");
        for (int i = 0; i < contacts.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, contacts.get(i));
        }
    }

    private void addContact() {
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Phone: ");
        String phone = scanner.nextLine().trim();
        contacts.add(new Contact(name, email, phone));
        System.out.println("Contact added.");
    }

    private void updateContact() {
        listContacts();
        if (contacts.isEmpty()) return;
        System.out.print("Select contact number to update: ");
        int index = readInt() - 1;
        if (!validIndex(index, contacts)) {
            System.out.println("Invalid selection.");
            return;
        }
        Contact c = contacts.get(index);
        System.out.print("New name (" + c.getName() + "): ");
        String name = scanner.nextLine().trim();
        System.out.print("New email (" + c.getEmail() + "): ");
        String email = scanner.nextLine().trim();
        System.out.print("New phone (" + c.getPhone() + "): ");
        String phone = scanner.nextLine().trim();
        if (!name.isEmpty()) c.setName(name);
        if (!email.isEmpty()) c.setEmail(email);
        if (!phone.isEmpty()) c.setPhone(phone);
        System.out.println("Contact updated.");
    }

    private void deleteContact() {
        listContacts();
        if (contacts.isEmpty()) return;
        System.out.print("Select contact number to delete: ");
        int index = readInt() - 1;
        if (!validIndex(index, contacts)) {
            System.out.println("Invalid selection.");
            return;
        }
        contacts.remove(index);
        System.out.println("Contact deleted.");
    }

    // -------------------- Task Management --------------------
    private void manageTasks() {
        while (true) {
            System.out.println("\n--- Tasks ---");
            System.out.println("1. List Tasks");
            System.out.println("2. Add Task");
            System.out.println("3. Update Task");
            System.out.println("4. Delete Task");
            System.out.println("0. Back");
            System.out.print("Select option: ");
            int choice = readInt();
            switch (choice) {
                case 1 -> listTasks();
                case 2 -> addTask();
                case 3 -> updateTask();
                case 4 -> deleteTask();
                case 0 -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        System.out.println("\nTasks:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, tasks.get(i));
        }
    }

    private void addTask() {
        System.out.print("Title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Due date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine().trim();
        LocalDate dueDate = LocalDate.parse(dateStr);
        tasks.add(new Task(title, dueDate));
        System.out.println("Task added.");
    }

    private void updateTask() {
        listTasks();
        if (tasks.isEmpty()) return;
        System.out.print("Select task number to update: ");
        int index = readInt() - 1;
        if (!validIndex(index, tasks)) {
            System.out.println("Invalid selection.");
            return;
        }
        Task t = tasks.get(index);
        System.out.print("New title (" + t.getTitle() + "): ");
        String title = scanner.nextLine().trim();
        System.out.print("New due date (" + t.getDueDate() + ") (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine().trim();
        if (!title.isEmpty()) t.setTitle(title);
        if (!dateStr.isEmpty()) t.setDueDate(LocalDate.parse(dateStr));
        System.out.println("Task updated.");
    }

    private void deleteTask() {
        listTasks();
        if (tasks.isEmpty()) return;
        System.out.print("Select task number to delete: ");
        int index = readInt() - 1;
        if (!validIndex(index, tasks)) {
            System.out.println("Invalid selection.");
            return;
        }
        tasks.remove(index);
        System.out.println("Task deleted.");
    }

    // -------------------- Note Management --------------------
    private void manageNotes() {
        while (true) {
            System.out.println("\n--- Notes ---");
            System.out.println("1. List Notes");
            System.out.println("2. Add Note");
            System.out.println("3. Update Note");
            System.out.println("4. Delete Note");
            System.out.println("0. Back");
            System.out.print("Select option: ");
            int choice = readInt();
            switch (choice) {
                case 1 -> listNotes();
                case 2 -> addNote();
                case 3 -> updateNote();
                case 4 -> deleteNote();
                case 0 -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void listNotes() {
        if (notes.isEmpty()) {
            System.out.println("No notes found.");
            return;
        }
        System.out.println("\nNotes:");
        for (int i = 0; i < notes.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, notes.get(i));
        }
    }

    private void addNote() {
        System.out.print("Title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Content: ");
        String content = scanner.nextLine().trim();
        notes.add(new Note(title, content));
        System.out.println("Note added.");
    }

    private void updateNote() {
        listNotes();
        if (notes.isEmpty()) return;
        System.out.print("Select note number to update: ");
        int index = readInt() - 1;
        if (!validIndex(index, notes)) {
            System.out.println("Invalid selection.");
            return;
        }
        Note n = notes.get(index);
        System.out.print("New title ("