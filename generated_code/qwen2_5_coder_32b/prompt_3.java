```java
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class FileExplorer extends JFrame implements ActionListener {
    private JTree tree;
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private JButton openButton;
    private DefaultMutableTreeNode root;

    public FileExplorer() {
        setTitle("File Explorer");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        root = new DefaultMutableTreeNode(new File("."));
        buildTree(root);

        tree = new JTree(root);
        scrollPane = new JScrollPane(tree);
        add(scrollPane, "West");

        textArea = new JTextArea();
        textArea.setEditable(false);
        add(textArea, "Center");

        openButton = new JButton("Open");
        openButton.addActionListener(this);
        add(openButton, "South");

        setVisible(true);
    }

    private void buildTree(DefaultMutableTreeNode node) {
        File file = (File) node.getUserObject();
        if (!file.isDirectory()) return;
        File[] files = file.listFiles();
        if (files == null) return;
        for (File f : files) {
            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(f);
            node.add(childNode);
            buildTree(childNode);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openButton) {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (selectedNode != null) {
                File file = (File) selectedNode.getUserObject();
                if (file.isFile()) {
                    try {
                        if (file.getName().endsWith(".txt")) {
                            textArea.read(new java.io.FileReader(file), null);
                        } else {
                            Desktop.getDesktop().open(file);
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error opening file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a file.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    public static void main(String[] args) {
        new FileExplorer();
    }
}
```