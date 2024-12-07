
#
# **Documentation: Live Code Generator Application**
#

## **Overview**
The **Live Code Generator** is a Swing-based Java application designed to create class templates interactively. Users can input class details, attributes, methods, and additional configurations to generate and preview Java code dynamically. The generated code can also be saved to a file.

## **Features**
1. **Dynamic Code Preview**: See real-time updates of the generated Java code as you input details.
1. **Customizable Options**:
   1. Class name
   1. Access modifier
   1. Superclass and interfaces
   1. Import statements
   1. Attributes and methods
   1. Constructor generation
   1. Getters and setters
1. **Save Functionality**: Save the generated code to a .java file.
1. **Clear Fields**: Reset all input fields to start fresh.
## **Key Concepts** 
### **Swing Framework:**
Used for creating the graphical user interface (GUI), including **JFrame, JPanel, JTextField, JTextArea, JButton**, and other components.
### **Event Handling:**
- **ActionListener** is used for button actions (e.g., Save and Clear).
- **DocumentListener** is used for detecting changes in text fields and areas for live preview updates.
### **Object-Oriented Programming (OOP):**
- **Encapsulation**: Private fields (e.g., classNameField) are used to encapsulate data.
- **Inheritance**: The program supports adding a superclass and interfaces dynamically.
- **Polymorphism**: Overriding methods of ActionListener and DocumentListener interfaces.
### **I/O (Input/Output):**
**FileWriter** and **JFileChooser** are used for file handling to save generated code.
### **Layout Management:**
Various layout managers like **BorderLayout**, **GridLayout**, and **FlowLayout** are used to arrange components.

**Collections:**

**StringBuilder** is used for efficiently building the generated code.

**Error Handling:**

**try-catch** blocks handle **IOException** when saving files.

**Multithreading:**

**SwingUtilities.invokeLater** ensures the GUI runs on the Event Dispatch Thread (EDT) for thread safety.

**Dynamic Code Generation:**

Combines user input and templates to generate Java code dynamically.

**How to Start** 

Using Command Line 
| :- |
Open a terminal/command prompt go to project folder and navigate to the src folder: 
| :- |
|cd src| 
| :- |
Compile the code: 
| :- |
|javac app.java| 
| :- |
This generates a app.class file. 
| :- |
Run the program:	 
| :- |
|java app| 
| :- |

##
## **Components**
### **Main Frame**
- **Title**: Live Code Generator
- **Layout**: BorderLayout
- **Size**: 1000 x 700
- **Split Pane**: Divides the input panel and the code preview panel.
### **Input Panel**
1. **Class Name Field**: Input the name of the class.
1. **Access Modifier Dropdown**: Select from public, private, protected, or default.
1. **Superclass and Interfaces Fields**: Specify a superclass and implemented interfaces.
1. **Import Statements Area**: Add import statements (one per line).
1. **Attributes Input**: Add class attributes in type name format (e.g., String name).
1. **Methods Input**: Add method declarations in returnType name format (e.g., void display).
1. **Options**:
   1. Generate Constructor: Automatically generates a constructor using the provided attributes.
   1. Generate Getters/Setters: Adds getter and setter methods for attributes.
### **Preview Panel**
- Displays the dynamically generated Java code in a non-editable text area.
### **Buttons**
1. **Save Code**: Save the generated code to a .java file.
1. **Clear Fields**: Reset all inputs after confirmation.

## **Key Methods**
### **addLivePreviewListeners()**
- Attaches DocumentListener and ActionListener to relevant input fields to dynamically update the code preview.
### **updatePreview()**
- Generates and updates the Java code in the preview area based on current user inputs.
### **saveCode(ActionEvent e)**
- Prompts the user to save the generated code as a .java file using a JFileChooser.
### **clearFields(ActionEvent e)**
- Resets all fields and options to their default states after confirmation.

## **Code Structure**
### **Imports**

|import java.awt.\*;<br>import java.awt.event.ActionEvent;<br>import java.io.File;<br>import java.io.FileWriter;<br>import java.io.IOException;<br>import javax.swing.\*;<br>import javax.swing.event.DocumentEvent;<br>import javax.swing.event.DocumentListener;|
| :- |

### **Main Class**

|public class app {<br>`    `// Frame and Component Declarations<br>`    `final private JFrame frame;<br>`    `final private JTextField classNameField;<br>`    `final private JTextArea attributesArea, methodsArea, previewArea, importsArea;<br>`    `final private JCheckBox generateConstructorCheckbox, generateGettersSettersCheckbox;<br>`    `final private JTextField superClassField, interfacesField;<br>`    `final private JComboBox<String> accessModifierDropdown;<br><br>`    `public static void main(String[] args) {<br>`        `SwingUtilities.invokeLater(app::new);<br>`    `}<br>    <br>`    `public app() {<br>`        `// Initialize Components and Layout<br>`    `}<br>}|
| :- |

### **Methods Breakdown**
#### ***updatePreview()***
- Builds the Java class template:
  - Adds import statements.
  - Creates the class declaration.
  - Includes attributes.
  - Optionally generates:
    - Constructor
    - Getters and setters
  - Adds user-defined methods.
#### ***saveCode(ActionEvent e)***
- Saves the previewed code to a user-specified file.
- Uses FileWriter for file handling.
#### ***clearFields(ActionEvent e)***
- Clears all input fields, checkboxes, and resets dropdowns.

## **Usage Instructions**
1. Run the application.
1. Fill out the fields for the class name, attributes, methods, and other options.
1. View the generated code live in the preview area.
1. Use the **Save Code** button to export the file.
1. Use the **Clear Fields** button to reset all fields.

## **Example Inputs and Outputs**
### **Inputs**
- **Class Name**: Person
- **Attributes**:

|String name<br>int age|
| :- |

- **Methods**:

  |void display|
  | :- |

- **Options**:
  - Enable Generate Constructor
  - Enable Generate Getters/Setters
### **Output**

|<p>public class Person {<br><br>`    `private String name;<br>`    `private int age;<br><br>`    `public Person(String name, int age) {<br>`        `this.name = name;<br>`        `this.age = age;<br>`    `}<br><br>`    `public String getName() {<br>`        `return name;<br>`    `}<br><br>`    `public void setName(String name) {<br>`        `this.name = name;<br>`    `}<br><br>`    `public int getAge() {<br>`        `return age;<br>`    `}<br><br>`    `public void setAge(int age) {<br>`        `this.age = age;<br>`    `}<br><br>`    `public void display() {<br>`        `// TODO: Implement this method<br>`    `}<br>}</p><p></p>|
| :- |
## **Error Handling**
- Alerts the user if:
  - No code is available to save.
  - File saving fails due to an IOException.

