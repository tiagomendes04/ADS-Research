import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FileExplorer extends JFrame {

    private static final String[] COLUMN_NAMES = {"Name", "Type", "Size", "Last Modified"};
    private JTable fileTable;
    private DefaultTableModel tableModel;
    private JTextField currentPathField;
    private File currentDirectory;
    private List<File> currentFileList; // To map table rows back to File objects

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
    private final FileSystemView fileSystemView = FileSystemView.getFileSystemView();

    public FileExplorer() {
        setTitle("Java File Explorer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        navigateTo(fileSystemView.getDefaultDirectory()); // Start at user's home directory
    }

    private void initComponents() {
        // Top Panel for Path and Navigation
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        currentPathField = new JTextField();
        currentPathField.setEditable(false);
        topPanel.add(currentPathField, BorderLayout.CENTER);

        JButton goUpButton = new JButton("Go Up");
        goUpButton.addActionListener(e -> navigateUp());
        topPanel.add(goUpButton, BorderLayout.WEST);

        add(topPanel, BorderLayout.NORTH);

        // File Table
        tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells uneditable
            }

            @Override
            public Class<?> getColumnClass(int column) {
                // For sorting capabilities if custom sorters are added later
                if (column == 2) return Long.class; // Size column
                return String.class;
            }
        };

        fileTable = new JTable(tableModel);
        fileTable.setAutoCreateRowSorter(true); // Enable sorting by clicking column headers
        fileTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileTable.getTableHeader().setReorderingAllowed(false); // Prevent column reordering
        fileTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    openSelectedFile();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(fileTable);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel for Open Button (optional, double-click is primary)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton openButton = new JButton("Open");
        openButton.addActionListener(e -> openSelectedFile());
        bottomPanel.add(openButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void navigateTo(File directory) {
        if (directory == null || !directory.exists()) {
            JOptionPane.showMessageDialog(this, "Directory does not exist or is inaccessible.", "Navigation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!directory.isDirectory()) {
            // If it's a file, we want to go to its parent
            openFile(directory);
            return;
        }

        currentDirectory = directory;
        currentPathField.setText(currentDirectory.getAbsolutePath());
        refreshFileList();
    }

    private void navigateUp() {
        if (currentDirectory != null && currentDirectory.getParentFile() != null) {
            navigateTo(currentDirectory.getParentFile());
        } else {
            // If at root, try to show file system roots
            navigateTo(fileSystemView.getDefaultDirectory().getParentFile()); // Example, might need refinement
        }
    }

    private void refreshFileList() {
        tableModel.setRowCount(0); // Clear existing data
        currentFileList = new ArrayList<>();

        File[] files = currentDirectory.listFiles();

        if (files == null) {
            JOptionPane.showMessageDialog(this, "Could not list contents of this directory. Permissions denied?", "Access Denied", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<File> directories = new ArrayList<>();
        List<File> regularFiles = new ArrayList<>();

        for (File file : files) {
            if (file.isDirectory()) {
                directories.add(file);
            } else {
                regularFiles.add(file);
            }
        }

        // Sort directories and files alphabetically
        directories.sort(Comparator.comparing(File::getName, String.CASE_INSENSITIVE_ORDER));
        regularFiles.sort(Comparator.comparing(File::getName, String.CASE_INSENSITIVE_ORDER));

        // Add directories first, then files
        for (File dir : directories) {
            addFileRow(dir);
        }
        for (File file : regularFiles) {
            addFileRow(file);
        }
    }

    private void addFileRow(File file) {
        currentFileList.add(file);
        tableModel.addRow(new Object[]{
                file.getName(),
                getFileType(file),
                getFileSize(file),
                getFileLastModified(file)
        });
    }

    private String getFileType(File file) {
        if (fileSystemView.isDrive(file)) {
            return "Drive";
        } else if (fileSystemView.isComputerNode(file)) {
            return "Computer";
        } else if (fileSystemView.isFileSystemRoot(file)) {
            return "Root";
        } else if (file.isDirectory()) {
            return "Directory";
        } else {
            String name = file.getName();
            int dotIndex = name.lastIndexOf('.');
            if (dotIndex > 0 && dotIndex < name.length() - 1) {
                return name.substring(dotIndex + 1).toUpperCase() + " File";
            }
            return "File";
        }
    }

    private String getFileSize(File file) {
        if (file.isDirectory()) {
            return ""; // Directories don't have a simple "size"
        }
        long length = file.length();
        if (length <= 0) return "0 Bytes";
        final String[] units = new String[]{"Bytes", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(length) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(length / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    private String getFileLastModified(File file) {
        try {
            BasicFileAttributes attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            return dateFormat.format(new Date(attrs.lastModifiedTime().toMillis()));
        } catch (IOException e) {
            return dateFormat.format(new Date(file.lastModified())); // Fallback
        }
    }

    private void openSelectedFile() {
        int selectedRow = fileTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a file or directory.", "No Selection", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Get the actual file object from our list, considering table sorting
        int modelRow = fileTable.convertRowIndexToModel(selectedRow);
        File selectedFile = currentFileList.get(modelRow);

        if (selectedFile.isDirectory()) {
            navigateTo(selectedFile);
        } else {
            openFile(selectedFile);
        }
    }

    private void openFile(File file) {
        if (!Desktop.isDesktopSupported()) {
            JOptionPane.showMessageDialog(this, "Desktop operations are not supported on your system.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Desktop desktop = Desktop.getDesktop();
        if (!desktop.isSupported(Desktop.Action.OPEN)) {
            JOptionPane.showMessageDialog(this, "Opening files is not supported on your system.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            desktop.open(file);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error opening file: " + ex.getMessage(), "File Open Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new FileExplorer().setVisible(true);
        });
    }
}