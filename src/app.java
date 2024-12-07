import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class app {
    final private JFrame frame;
    final private JTextField classNameField;
    final private JTextArea attributesArea;
    final private JTextArea methodsArea;
    final private JTextArea previewArea;
    final private JCheckBox generateConstructorCheckbox;
    final private JCheckBox generateGettersSettersCheckbox;
    final private JTextField superClassField;
    final private JTextField interfacesField;
    final private JTextArea importsArea;
    final private JComboBox<String> accessModifierDropdown;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(app::new);
    }

    public app() {
        // Initialize main frame
        frame = new JFrame("Live Code Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(new BorderLayout());

        // Split pane for layout
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(500);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(8, 1, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Class Name Input
        JPanel classNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        classNamePanel.add(new JLabel("Class Name:"));
        classNameField = new JTextField(20);
        classNamePanel.add(classNameField);
        inputPanel.add(classNamePanel);

        // Access Modifier Dropdown
        JPanel modifierPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        modifierPanel.add(new JLabel("Access Modifier:"));
        accessModifierDropdown = new JComboBox<>(new String[]{"public", "private", "protected", "default"});
        modifierPanel.add(accessModifierDropdown);
        inputPanel.add(modifierPanel);

        // Superclass and Interfaces
        JPanel inheritancePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inheritancePanel.add(new JLabel("Superclass:"));
        superClassField = new JTextField(15);
        inheritancePanel.add(superClassField);
        inheritancePanel.add(new JLabel("Interfaces:"));
        interfacesField = new JTextField(15);
        inheritancePanel.add(interfacesField);
        inputPanel.add(inheritancePanel);

        // Import Statements
        JPanel importsPanel = new JPanel(new BorderLayout());
        importsPanel.add(new JLabel("Import Statements (one per line):"), BorderLayout.NORTH);
        importsArea = new JTextArea(3, 20);
        importsPanel.add(new JScrollPane(importsArea), BorderLayout.CENTER);
        inputPanel.add(importsPanel);

        // Attributes Input
        JPanel attributesPanel = new JPanel(new BorderLayout());
        attributesPanel.add(new JLabel("Attributes (type name):"), BorderLayout.NORTH);
        attributesArea = new JTextArea(5, 20);
        attributesPanel.add(new JScrollPane(attributesArea), BorderLayout.CENTER);
        inputPanel.add(attributesPanel);

        // Methods Input
        JPanel methodsPanel = new JPanel(new BorderLayout());
        methodsPanel.add(new JLabel("Methods (returnType name):"), BorderLayout.NORTH);
        methodsArea = new JTextArea(5, 20);
        methodsPanel.add(new JScrollPane(methodsArea), BorderLayout.CENTER);
        inputPanel.add(methodsPanel);

        // Options
        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        generateConstructorCheckbox = new JCheckBox("Generate Constructor");
        generateGettersSettersCheckbox = new JCheckBox("Generate Getters/Setters");
        optionsPanel.add(generateConstructorCheckbox);
        optionsPanel.add(generateGettersSettersCheckbox);
        inputPanel.add(optionsPanel);

        // Add input panel to the split pane
        splitPane.setLeftComponent(new JScrollPane(inputPanel));

        // Preview Panel
        JPanel previewPanel = new JPanel(new BorderLayout());
        previewPanel.setBorder(BorderFactory.createTitledBorder("Live Code Preview"));
        previewArea = new JTextArea();
        previewArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        previewArea.setEditable(false);
        previewArea.setBackground(Color.LIGHT_GRAY);
        previewPanel.add(new JScrollPane(previewArea), BorderLayout.CENTER);
        splitPane.setRightComponent(previewPanel);

        // Add split pane to frame
        frame.add(splitPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveButton = new JButton("Save Code");
        JButton clearButton = new JButton("Clear Fields");
        buttonPanel.add(saveButton);
        buttonPanel.add(clearButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        saveButton.addActionListener(this::saveCode);
        clearButton.addActionListener(this::clearFields);

        // Add listeners for live preview
        addLivePreviewListeners();

        // Display frame
        frame.setVisible(true);
    }

    private void addLivePreviewListeners() {
        DocumentListener livePreviewListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updatePreview(); }
            public void removeUpdate(DocumentEvent e) { updatePreview(); }
            public void changedUpdate(DocumentEvent e) { updatePreview(); }
        };

        // Attach to relevant fields
        classNameField.getDocument().addDocumentListener(livePreviewListener);
        attributesArea.getDocument().addDocumentListener(livePreviewListener);
        methodsArea.getDocument().addDocumentListener(livePreviewListener);
        superClassField.getDocument().addDocumentListener(livePreviewListener);
        interfacesField.getDocument().addDocumentListener(livePreviewListener);
        importsArea.getDocument().addDocumentListener(livePreviewListener);
        generateConstructorCheckbox.addActionListener(e -> updatePreview());
        generateGettersSettersCheckbox.addActionListener(e -> updatePreview());
        accessModifierDropdown.addActionListener(e -> updatePreview());
    }

    private void updatePreview() {
        String className = classNameField.getText().trim();
        String attributes = attributesArea.getText().trim();
        String methods = methodsArea.getText().trim();
        String accessModifier = (String) accessModifierDropdown.getSelectedItem();
        String superClass = superClassField.getText().trim();
        String interfaces = interfacesField.getText().trim();
        String imports = importsArea.getText().trim();

        StringBuilder code = new StringBuilder();

        // Add import statements
        if (!imports.isEmpty()) {
            String[] importLines = imports.split("\n");
            for (String line : importLines) {
                code.append("import ").append(line.trim()).append(";\n");
            }
            code.append("\n");
        }

        // Add class declaration
        code.append(accessModifier).append(" class ").append(className);
        if (!superClass.isEmpty()) code.append(" extends ").append(superClass);
        if (!interfaces.isEmpty()) code.append(" implements ").append(interfaces);
        code.append(" {\n\n");

        // Add attributes
        if (!attributes.isEmpty()) {
            String[] attrLines = attributes.split("\n");
            for (String line : attrLines) {
                code.append("    private ").append(line.trim()).append(";\n");
            }
            code.append("\n");
        }

        // Add constructor
        if (generateConstructorCheckbox.isSelected() && !attributes.isEmpty()) {
            code.append("    public ").append(className).append("(");
            String[] attrLines = attributes.split("\n");
            for (int i = 0; i < attrLines.length; i++) {
                String[] parts = attrLines[i].trim().split(" ");
                if (parts.length == 2) {
                    code.append(parts[0]).append(" ").append(parts[1]);
                    if (i < attrLines.length - 1) code.append(", ");
                }
            }
            code.append(") {\n");
            for (String line : attrLines) {
                String[] parts = line.trim().split(" ");
                if (parts.length == 2) {
                    code.append("        this.").append(parts[1]).append(" = ").append(parts[1]).append(";\n");
                }
            }
            code.append("    }\n\n");
        }

        // Add getters and setters
        if (generateGettersSettersCheckbox.isSelected() && !attributes.isEmpty()) {
            String[] attrLines = attributes.split("\n");
            for (String line : attrLines) {
                String[] parts = line.trim().split(" ");
                if (parts.length == 2) {
                    String type = parts[0];
                    String name = parts[1];
                    String capitalized = name.substring(0, 1).toUpperCase() + name.substring(1);

                    code.append("    public ").append(type).append(" get").append(capitalized).append("() {\n")
                        .append("        return ").append(name).append(";\n")
                        .append("    }\n\n");

                    code.append("    public void set").append(capitalized).append("(").append(type).append(" ").append(name).append(") {\n")
                        .append("        this.").append(name).append(" = ").append(name).append(";\n")
                        .append("    }\n\n");
                }
            }
        }

        // Add methods
        if (!methods.isEmpty()) {
            String[] methodLines = methods.split("\n");
            for (String line : methodLines) {
                String[] parts = line.trim().split(" ");
                if (parts.length == 2) {
                    code.append("    public ").append(parts[0]).append(" ").append(parts[1]).append("() {\n")
                        .append("        // TODO: Implement this method\n")
                        .append("    }\n\n");
                }
            }
        }

        code.append("}");

        previewArea.setText(code.toString());
    }

    private void saveCode(ActionEvent e) {
        if (previewArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No code to save!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File(classNameField.getText().trim() + ".java"));
        int choice = fileChooser.showSaveDialog(frame);

        if (choice == JFileChooser.APPROVE_OPTION) {
            try (FileWriter writer = new FileWriter(fileChooser.getSelectedFile())) {
                writer.write(previewArea.getText());
                JOptionPane.showMessageDialog(frame, "Code saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearFields(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to clear all fields?", "Confirm Clear", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            classNameField.setText("");
            attributesArea.setText("");
            methodsArea.setText("");
            previewArea.setText("");
            generateConstructorCheckbox.setSelected(false);
            generateGettersSettersCheckbox.setSelected(false);
            accessModifierDropdown.setSelectedIndex(0);
            superClassField.setText("");
            interfacesField.setText("");
            importsArea.setText("");
        }
    }
}
