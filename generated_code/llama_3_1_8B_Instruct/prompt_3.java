```java
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileExplorer extends JFrame {
    private JTextArea textArea;
    private JFileChooser fileChooser;
    private JLabel label;
    private JButton browseButton;
    private JButton openButton;
    private JButton saveButton;
    private JFileChooser saveFileChooser;
    private JLabel saveLabel;
    private JButton saveAsButton;
    private JButton saveBrowseButton;

    public FileExplorer() {
        textArea = new JTextArea(20, 40);
        textArea.setEditable(false);
        label = new JLabel("No file selected");
        browseButton = new JButton("Browse");
        openButton = new JButton("Open");
        saveButton = new JButton("Save");
        saveFileChooser = new JFileChooser();
        saveLabel = new JLabel("No file selected");
        saveAsButton = new JButton("Save As");
        saveBrowseButton = new JButton("Browse");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(label);
        controlPanel.add(browseButton);
        controlPanel.add(openButton);
        controlPanel.add(saveButton);
        controlPanel.add(saveLabel);
        controlPanel.add(saveAsButton);
        controlPanel.add(saveBrowseButton);
        panel.add(controlPanel, BorderLayout.NORTH);

        browseButton.addActionListener(e -> {
            fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fileChooser.showOpenDialog(FileExplorer.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                textArea.setText("");
                textArea.append("Directory: " + file.getAbsolutePath() + "\n");
                label.setText(file.getAbsolutePath());
                try {
                    File[] files = file.listFiles();
                    for (File f : files) {
                        if (f.isDirectory()) {
                            textArea.append("  Directory: " + f.getName() + "\n");
                        } else {
                            textArea.append("  File: " + f.getName() + "\n");
                        }
                    }
                } catch (NullPointerException e1) {
                    // Ignore
                }
            }
        });

        openButton.addActionListener(e -> {
            fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showOpenDialog(FileExplorer.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                Scanner scanner;
                try {
                    scanner = new Scanner(file);
                    textArea.setText(scanner.useDelimiter("\\Z").next());
                    scanner.close();
                    label.setText(file.getAbsolutePath());
                } catch (FileNotFoundException e1) {
                    // Ignore
                }
            }
        });

        saveButton.addActionListener(e -> {
            if (saveFileChooser != null) {
                int result = saveFileChooser.showSaveDialog(FileExplorer.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = saveFileChooser.getSelectedFile();
                    String text = textArea.getText();
                    try {
                        java.io.FileWriter writer = new java.io.FileWriter(file);
                        writer.write(text);
                        writer.close();
                        saveLabel.setText(file.getAbsolutePath());
                    } catch (Exception e1) {
                        // Ignore
                    }
                }
            }
        });

        saveAsButton.addActionListener(e -> {
            saveFileChooser = new JFileChooser();
            int result = saveFileChooser.showSaveDialog(FileExplorer.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = saveFileChooser.getSelectedFile();
                String text = textArea.getText();
                try {
                    java.io.FileWriter writer = new java.io.FileWriter(file);
                    writer.write(text);
                    writer.close();
                    saveLabel.setText(file.getAbsolutePath());
                } catch (Exception e1) {
                    // Ignore
                }
            }
        });

        saveBrowseButton.addActionListener(e -> {
            saveFileChooser = new JFileChooser();
            int result = saveFileChooser.showOpenDialog(FileExplorer.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = saveFileChooser.getSelectedFile();
                String text = textArea.getText();
                try {
                    java.io.FileWriter writer = new java.io.FileWriter(file);
                    writer.write(text);
                    writer.close();
                    saveLabel.setText(file.getAbsolutePath());
                } catch (Exception e1) {
                    // Ignore
                }
            }
        });

        add(panel, BorderLayout.CENTER);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new FileExplorer();
    }
}
```