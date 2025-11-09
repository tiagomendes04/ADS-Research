```java
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

public class FileExplorer extends JFrame {
    private JList<String> fileList;
    private DefaultListModel<String> listModel;
    private File currentDirectory;
    private JTextField pathField;
    private JFileChooser fileChooser;

    public FileExplorer() {
        setTitle("File Explorer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        fileList = new JList<>(listModel);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(fileList);
        add(scrollPane, BorderLayout.CENTER);

        pathField = new JTextField();
        pathField.setEditable(false);
        add(pathField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton upButton = new JButton("Up");
        JButton openButton = new JButton("Open");
        JButton refreshButton = new JButton("Refresh");

        buttonPanel.add(upButton);
        buttonPanel.add(openButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        fileChooser = new JFileChooser(FileSystemView.getFileSystemView());

        upButton.addActionListener(e -> navigateUp());
        openButton.addActionListener(e -> openFile());
        refreshButton.addActionListener(e -> refreshDirectory());
        fileList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDoubleClicked(MouseEvent e) {
                openFile();
            }
        });

        currentDirectory = new File(System.getProperty("user.dir"));
        refreshDirectory();
    }

    private void navigateUp() {
        File parent = currentDirectory.getParentFile();
        if (parent != null) {
            currentDirectory = parent;
            refreshDirectory();
        }
    }

    private void openFile() {
        int selectedIndex = fileList.getSelectedIndex();
        if (selectedIndex != -1) {
            String selected = listModel.getElementAt(selectedIndex);
            File file = new File(currentDirectory, selected);

            if (file.isDirectory()) {
                currentDirectory = file;
                refreshDirectory();
            } else {
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Could not open file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void refreshDirectory() {
        listModel.clear();
        pathField.setText(currentDirectory.getAbsolutePath());

        File[] files = currentDirectory.listFiles();
        if (files != null) {
            Arrays.sort(files, Comparator.comparing(File::isDirectory).reversed().thenComparing(File::getName));
            for (File file : files) {
                listModel.addElement(file.getName());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FileExplorer explorer = new FileExplorer();
            explorer.setVisible(true);
        });
    }
}
```