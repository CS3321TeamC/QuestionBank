package QuestionBank;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;

import static javax.swing.JOptionPane.showMessageDialog;

public class UI {
    private static UI instance = null;
    private JFrame mainFrame;
    private JLabel headerlabel;
    private JPanel controlPanel;
    private JFileChooser fileChooser;
    private BankFacade bankFacade = BankFacade.GetInstance();

    //Constructor - enforce singleton pattern
    private UI() {
        PrepareGUI();
    }
    protected static UI GetInstance() {
        if (instance == null) {
            instance = new UI();
        }
        return instance;
    }

    public static void main(String[] args) {
        UI gui = GetInstance();
        gui.ShowWindow();
    }

    /**
     * Method that prepares the GUI
     */
    private void PrepareGUI(){
        mainFrame = new JFrame("Question Bank Window");
        mainFrame.setSize(600, 600);
        //mainFrame.setLayout(new GridLayout(2, 1));

        //Create label for the window
        headerlabel = new JLabel("", JLabel.CENTER);
        headerlabel.setBorder(new EmptyBorder(40, 10, 10, 10));

        //Shut down the program upon closing the window
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        //Create a panel for housing the buttons
        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.LINE_AXIS));

        //Add the labels and panel to the frame
        mainFrame.add(headerlabel, BorderLayout.NORTH);
        mainFrame.add(controlPanel);

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    /**
     * Method that does final prep work and shows the window
     */
    private void ShowWindow(){
        headerlabel.setText("What would you like to do?");

        //Create buttons
        JButton seeAllQuestionsButton = new JButton("See all questions in bank");
        JButton addQuestionButton = new JButton("Add a new question");
        JButton seeAllTagsButton = new JButton("See all tags in bank");
        JButton saveBankButton = new JButton("Save the Bank");
        JButton loadQuestionsButton = new JButton("Load Questions");
        JButton deleteBankButton = new JButton("Delete Bank");

        //Set Button alignments
        seeAllQuestionsButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        addQuestionButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        seeAllTagsButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        saveBankButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        loadQuestionsButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        deleteBankButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

        //Set button commands
        seeAllQuestionsButton.setActionCommand("SeeAll");
        addQuestionButton.setActionCommand("AddNew");
        seeAllTagsButton.setActionCommand("SeeTags");
        saveBankButton.setActionCommand("SaveBank");
        loadQuestionsButton.setActionCommand("LoadQuestions");
        deleteBankButton.setActionCommand("DeleteBank");

        //Add listeners to buttons
        seeAllQuestionsButton.addActionListener(new ButtonClickListener());
        addQuestionButton.addActionListener(new ButtonClickListener());
        seeAllTagsButton.addActionListener(new ButtonClickListener());
        saveBankButton.addActionListener(new ButtonClickListener());
        loadQuestionsButton.addActionListener(new ButtonClickListener());
        deleteBankButton.addActionListener(new ButtonClickListener());

        //Make two panels for buttons, based on function
        JPanel editPanel = new JPanel();
        editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));
        editPanel.setBorder(new EmptyBorder(10,40,10,10));
        controlPanel.add(editPanel);
        controlPanel.add(Box.createHorizontalGlue());
        JPanel statePanel = new JPanel();
        statePanel.setLayout(new BoxLayout(statePanel, BoxLayout.Y_AXIS));
        statePanel.setBorder(new EmptyBorder(10, 10, 10, 40));
        controlPanel.add(statePanel);

        //Add buttons to the panels in the frame
        editPanel.add(seeAllQuestionsButton);
        editPanel.add(addQuestionButton);
        editPanel.add(seeAllTagsButton);
        statePanel.add(saveBankButton);
        statePanel.add(loadQuestionsButton);
        statePanel.add(deleteBankButton);

        mainFrame.setVisible(true);
    }

    /**
     * Method that hides the main screen
     */
    protected void Hide() {
        mainFrame.setVisible(false);
        mainFrame.setEnabled(false);
    }

    /**
     * Method that un-hides the main screen
     */
    protected void UnHide() {
        mainFrame.setVisible(true);
        mainFrame.setEnabled(true);
    }

    /**
     * Private, internal class for defining Listeners for each button
     */
    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            switch (command){
                case "SeeAll" -> SeeAll();
                case "AddNew" -> AddNew();
                case "SeeTags" -> SeeTags();
                case "SaveBank" -> SaveBank();
                case "LoadQuestions" -> {
                    try {
                        LoadQuestions();
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                case "DeleteBank" -> DeleteBank();
                default -> throw new IllegalArgumentException("Command '" + command + "' not found");
            }
        }

        private void SeeAll() {
            new AllQuestionsSubScreen();
        }
        private void AddNew() {
            new AddQuestionSubScreen();
        }
        private void SeeTags() {
            new AllTagsSubScreen();
        }

        // State related actions //
        private void SaveBank() {
            fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int approveVal = fileChooser.showSaveDialog(null);

            if (approveVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String file_name = file.getName();
                String file_path = file.getParent() + "\\";
                bankFacade.save(file.getParentFile(), file_name);
            }
        }

        private void LoadQuestions() throws FileNotFoundException {
            fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int approveVal = fileChooser.showOpenDialog(null);

            if (approveVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                bankFacade.load(file);
            }
        }

        private void DeleteBank() {
            bankFacade.resetBank();
            showMessageDialog(null, "Bank has been reset.");
        }
    }
}
