```java
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Comparator;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileSystemView;

public class FileExplorer extends JFrame {

    private final JList<File> fileList = new JList<>();
    private final JPanel previewPanel = new JPanel(new BorderLayout());
    private final JTextArea textArea = new JTextArea();
    private final JFileChooser chooser = new JFileChooser();

    public FileExplorer() {
        setTitle("File Explorer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory());

        File root = chooser.getCurrentDirectory();
        updateFileList(root);

        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    File selected = fileList.getSelectedValue();
                    if (selected != null) {
                        if (selected.isDirectory()) {
                            updateFileList(selected);
                        } else {
                            openFile(selected);
                        }
                    }
                } else if (e.getClickCount() == 1) {
                    previewFile(fileList.getSelectedValue());
                }
            }
        });

        JScrollPane listScroll = new JScrollPane(fileList);
        JScrollPane textScroll = new JScrollPane(textArea);
        textArea.setEditable(false);
        previewPanel.add(textScroll, BorderLayout.CENTER);

        getContentPane().add(listScroll, BorderLayout.WEST);
        getContentPane().add(previewPanel, BorderLayout.CENTER);
        listScroll.setPreferredSize(new Dimension(250, getHeight()));
    }

    private void updateFileList(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            Arrays.sort(files, Comparator.comparing(File::isFile).thenComparing(File::getName, String.CASE_INSENSITIVE_ORDER));
            fileList.setListData(files);
            setTitle("File Explorer - " + directory.getAbsolutePath());
        }
    }

    private void openFile(File file) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }
        } catch (IOException ex) {
            // ignore or log
        }
    }

    private void previewFile(File file) {
        previewPanel.removeAll();
        if (file == null) {
            previewPanel.revalidate();
            previewPanel.repaint();
            return;
        }

        String name = file.getName().toLowerCase();
        if (name.endsWith(".txt") || name.endsWith(".java") || name.endsWith(".log") || name.endsWith(".md")) {
            try {
                String content = new String(Files.readAllBytes(file.toPath()));
                textArea.setText(content);
                previewPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);
            } catch (IOException ignored) {}
        } else if (name.matches(".*\\.(png|jpg|jpeg|gif|bmp)")) {
            try {
                Image img = ImageIO.read(file);
                if (img != null) {
                    ImageIcon icon = new ImageIcon(img.getScaledInstance(previewPanel.getWidth(),
                            previewPanel.getHeight(), Image.SCALE_SMOOTH));
                    previewPanel.add(new JScrollPane(new javax.swing.JLabel(icon)), BorderLayout.CENTER);
                }
            } catch (IOException ignored) {}
        } else {
            textArea.setText("No preview available for this file type.");
            previewPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        }
        previewPanel.revalidate();
        previewPanel.repaint();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new FileExplorer().setVisible(true));
    }
}
```