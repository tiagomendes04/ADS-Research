```java
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileExplorer extends JFrame {

    private JList<String> list;
    private DefaultListModel<String> listModel;
    private JTextField textField;
    private JButton openButton;
    private JButton openFileButton;
    private JButton openFolderButton;
    private JFileChooser fileChooser;

    public FileExplorer() {
        setLayout(new BorderLayout());

        // Create list model and list
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Create text field and buttons
        textField = new JTextField(20);
        openButton = new JButton("Open File");
        openFileButton = new JButton("Open File");
        openFolderButton = new JButton("Open Folder");

        // Create file chooser
        fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(filter);

        // Create panel for buttons and text field
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(openButton);
        panel.add(openFileButton);
        panel.add(openFolderButton);
        panel.add(textField);

        // Add action listeners
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });

        openFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });

        openFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFolder();
            }
        });

        // Add components to frame
        add(new JScrollPane(list), BorderLayout.CENTER);
        add(panel, BorderLayout.NORTH);

        // Set up frame
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void openFile() {
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            listModel.addElement(file.getName());
            try {
                Scanner scanner = new Scanner(file);
                String content = scanner.useDelimiter("\\Z").next();
                scanner.close();
                if (content.contains("txt")) {
                    // Open text file
                    JTextArea textArea = new JTextArea(20, 40);
                    textArea.setText(content);
                    JDialog dialog = new JDialog(this, "File Content", true);
                    dialog.add(new JScrollPane(textArea));
                    dialog.setSize(400, 300);
                    dialog.setVisible(true);
                } else {
                    // Open other file type
                    JOptionPane.showMessageDialog(this, "Unsupported file type");
                }
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Error opening file");
            }
        }
    }

    private void openFolder() {
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            listModel.clear();
            File[] files = file.listFiles();
            for (File f : files) {
                listModel.addElement(f.getName());
            }
        }
    }

    public static void main(String[] args) {
        new FileExplorer();
    }
}
```