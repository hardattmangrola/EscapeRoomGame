import javax.swing.*;
import java.awt.*;

/**
 * UIManager handles all GUI components, screens, and user interactions
 */
public class UIManager {
    private JFrame mainFrame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private GameEngine gameEngine;
    
    // Main menu components
    private JPanel mainMenuPanel;
    private JTextField nameField;
    private JButton startButton;
    private JButton instructionsButton;
    private JButton exitButton;
    
    // Game screen components
    private JPanel gamePanel;
    private JLabel playerNameLabel;
    private JLabel livesLabel;
    private JLabel hintsLabel;
    private JLabel timerLabel;
    private JLabel puzzleQuestionLabel;
    private JTextField answerField;
    private JButton submitButton;
    private JButton hintButton;
    private JButton quitButton;
    private JTextArea roomInfoArea;
    
    // Instructions panel
    private JPanel instructionsPanel;
    private JButton backToMenuButton;
    
    // Victory/Game Over panels
    private JPanel victoryPanel;
    private JPanel gameOverPanel;
    private JButton playAgainButton;
    private JButton backToMenuFromGameOverButton;
    
    public UIManager() {
        initializeUI();
    }
    
    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }
    
    private void initializeUI() {
        mainFrame = new JFrame("Escape Room Game");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        createMainMenu();
        createGameScreen();
        createInstructionsScreen();
        createVictoryScreen();
        createGameOverScreen();
        
        mainPanel.add(mainMenuPanel, "MAIN_MENU");
        mainPanel.add(gamePanel, "GAME_SCREEN");
        mainPanel.add(instructionsPanel, "INSTRUCTIONS");
        mainPanel.add(victoryPanel, "VICTORY");
        mainPanel.add(gameOverPanel, "GAME_OVER");
        
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }
    
    private void createMainMenu() {
        mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new BorderLayout());
        mainMenuPanel.setBackground(new Color(15, 15, 35));
        
        // Title
        JLabel titleLabel = new JLabel("ESCAPE ROOM GAME", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(new Color(255, 215, 0)); // Gold
        titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 30, 0));
        
        // Player name input
        JPanel namePanel = new JPanel(new FlowLayout());
        namePanel.setBackground(new Color(15, 15, 35));
        JLabel namePromptLabel = new JLabel("Enter your name:");
        namePromptLabel.setForeground(Color.WHITE);
        namePromptLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameField = new JTextField(15);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        namePanel.add(namePromptLabel);
        namePanel.add(nameField);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBackground(new Color(15, 15, 35));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 200, 50, 200));
        
        startButton = createStyledButton("START GAME");
        instructionsButton = createStyledButton("INSTRUCTIONS");
        exitButton = createStyledButton("EXIT");
        
        startButton.addActionListener(e -> startGame());
        instructionsButton.addActionListener(e -> showInstructions());
        exitButton.addActionListener(e -> System.exit(0));
        
        buttonPanel.add(startButton);
        buttonPanel.add(instructionsButton);
        buttonPanel.add(exitButton);
        
        mainMenuPanel.add(titleLabel, BorderLayout.NORTH);
        mainMenuPanel.add(namePanel, BorderLayout.CENTER);
        mainMenuPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void createGameScreen() {
        gamePanel = new JPanel(new BorderLayout());
        gamePanel.setBackground(new Color(20, 20, 40));
        
        // Top HUD panel
        JPanel hudPanel = createHUDPanel();
        
        // Center game area
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(20, 20, 40));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Room info
        roomInfoArea = new JTextArea();
        roomInfoArea.setEditable(false);
        roomInfoArea.setFont(new Font("Arial", Font.PLAIN, 14));
        roomInfoArea.setBackground(new Color(30, 30, 50));
        roomInfoArea.setForeground(Color.WHITE);
        roomInfoArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JScrollPane scrollPane = new JScrollPane(roomInfoArea);
        scrollPane.setPreferredSize(new Dimension(600, 200));
        
        // Puzzle area
        JPanel puzzlePanel = new JPanel(new BorderLayout());
        puzzlePanel.setBackground(new Color(20, 20, 40));
        puzzlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        puzzleQuestionLabel = new JLabel();
        puzzleQuestionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        puzzleQuestionLabel.setForeground(new Color(255, 165, 0)); // Orange
        puzzleQuestionLabel.setHorizontalAlignment(JLabel.CENTER);
        puzzleQuestionLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Answer input
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(new Color(20, 20, 40));
        JLabel answerPromptLabel = new JLabel("Your answer:");
        answerPromptLabel.setForeground(Color.WHITE);
        answerPromptLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        answerField = new JTextField(20);
        answerField.setFont(new Font("Arial", Font.PLAIN, 14));
        answerField.addActionListener(e -> submitAnswer());
        
        submitButton = createStyledButton("SUBMIT");
        submitButton.addActionListener(e -> submitAnswer());
        submitButton.setVisible(true);
        
        inputPanel.add(answerPromptLabel);
        inputPanel.add(answerField);
        inputPanel.add(submitButton);
        
        puzzlePanel.add(puzzleQuestionLabel, BorderLayout.NORTH);
        puzzlePanel.add(inputPanel, BorderLayout.CENTER);
        
        centerPanel.add(scrollPane, BorderLayout.NORTH);
        centerPanel.add(puzzlePanel, BorderLayout.CENTER);
        
        // Bottom control panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        controlPanel.setBackground(new Color(20, 20, 40));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        
        hintButton = createStyledButton("USE HINT");
        quitButton = createStyledButton("QUIT GAME");
        
        hintButton.addActionListener(e -> useHint());
        quitButton.addActionListener(e -> quitGame());
        
        // Initially hide hint button - only show when puzzle is active
        hintButton.setVisible(false);
        quitButton.setVisible(true);
        
        // Ensure buttons are properly added and visible
        controlPanel.add(hintButton);
        controlPanel.add(quitButton);
        
        // Force layout update
        controlPanel.revalidate();
        controlPanel.repaint();
        
        gamePanel.add(hudPanel, BorderLayout.NORTH);
        gamePanel.add(centerPanel, BorderLayout.CENTER);
        gamePanel.add(controlPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createHUDPanel() {
        JPanel hudPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        hudPanel.setBackground(new Color(40, 40, 70));
        hudPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        playerNameLabel = new JLabel("Player: ", JLabel.LEFT);
        livesLabel = new JLabel("Lives: ", JLabel.LEFT);
        hintsLabel = new JLabel("Hints: ", JLabel.LEFT);
        timerLabel = new JLabel("Time: ", JLabel.LEFT);
        
        Font hudFont = new Font("Arial", Font.BOLD, 14);
        playerNameLabel.setFont(hudFont);
        livesLabel.setFont(hudFont);
        hintsLabel.setFont(hudFont);
        timerLabel.setFont(hudFont);
        
        playerNameLabel.setForeground(Color.WHITE);
        livesLabel.setForeground(new Color(255, 100, 100)); // Light Red
        hintsLabel.setForeground(new Color(255, 215, 0)); // Gold
        timerLabel.setForeground(new Color(100, 200, 255)); // Light Blue
        
        // Initialize timer display
        timerLabel.setText("⏱️ Time: 60s");
        
        hudPanel.add(playerNameLabel);
        hudPanel.add(livesLabel);
        hudPanel.add(hintsLabel);
        hudPanel.add(timerLabel);
        
        return hudPanel;
    }
    
    private void createInstructionsScreen() {
        instructionsPanel = new JPanel(new BorderLayout());
        instructionsPanel.setBackground(new Color(15, 15, 35));
        
        JTextArea instructionsText = new JTextArea();
        instructionsText.setEditable(false);
        instructionsText.setFont(new Font("Arial", Font.PLAIN, 14));
        instructionsText.setBackground(new Color(30, 30, 50));
        instructionsText.setForeground(Color.WHITE);
        instructionsText.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        String instructions = """
            🚪 ESCAPE ROOM GAME - INSTRUCTIONS 🚪
            
            🎯 OBJECTIVE:
            Solve puzzles in each room to progress through the escape room and find your way out!
            
            🎮 GAMEPLAY:
            • You start with 3 lives (❤️) and 3 hints (💡)
            • Each puzzle has a time limit of 60 seconds
            • Wrong answers cost you a life
            • Running out of time costs you a life
            • Use hints to get help with puzzles
            • Complete all 5 rooms to escape!
            
            🧩 PUZZLE TYPES:
            • Riddles: Answer the riddle correctly
            • Caesar Cipher: Decrypt the message (letters are shifted)
            • Substitution Cipher: Each letter is replaced with another
            • Math Problems: Solve the arithmetic
            • Word Scrambles: Unscramble the word
            • Logic Puzzles: Think through the problem
            
            🎒 ITEMS:
            • Collect items like keys to unlock new rooms
            • Some rooms require specific items to enter
            
            💡 TIPS:
            • Read each puzzle carefully
            • Use hints wisely - you only have 3!
            • Pay attention to the timer
            • Think logically and don't rush
            
            🎉 Good luck escaping! 🎉
            """;
        
        instructionsText.setText(instructions);
        
        backToMenuButton = createStyledButton("BACK TO MENU");
        backToMenuButton.addActionListener(e -> showMainMenu());
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(15, 15, 35));
        buttonPanel.add(backToMenuButton);
        
        instructionsPanel.add(instructionsText, BorderLayout.CENTER);
        instructionsPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void createVictoryScreen() {
        victoryPanel = new JPanel(new BorderLayout());
        victoryPanel.setBackground(new Color(0, 50, 0));
        
        JLabel victoryLabel = new JLabel("🎉 CONGRATULATIONS! 🎉", JLabel.CENTER);
        victoryLabel.setFont(new Font("Arial", Font.BOLD, 36));
        victoryLabel.setForeground(Color.YELLOW);
        victoryLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 0));
        
        JLabel messageLabel = new JLabel("You have successfully escaped the room!", JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        playAgainButton = createStyledButton("PLAY AGAIN");
        playAgainButton.addActionListener(e -> showMainMenu());
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(0, 50, 0));
        buttonPanel.add(playAgainButton);
        
        victoryPanel.add(victoryLabel, BorderLayout.NORTH);
        victoryPanel.add(messageLabel, BorderLayout.CENTER);
        victoryPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void createGameOverScreen() {
        gameOverPanel = new JPanel(new BorderLayout());
        gameOverPanel.setBackground(new Color(50, 0, 0));
        
        JLabel gameOverLabel = new JLabel("💀 GAME OVER 💀", JLabel.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36));
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 0));
        
        JLabel messageLabel = new JLabel("You ran out of lives! Better luck next time.", JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        backToMenuFromGameOverButton = createStyledButton("BACK TO MENU");
        backToMenuFromGameOverButton.addActionListener(e -> showMainMenu());
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(50, 0, 0));
        buttonPanel.add(backToMenuFromGameOverButton);
        
        gameOverPanel.add(gameOverLabel, BorderLayout.NORTH);
        gameOverPanel.add(messageLabel, BorderLayout.CENTER);
        gameOverPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(0, 120, 255)); // Bright blue by default
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        button.setPreferredSize(new Dimension(160, 45));
        button.setVisible(true);
        
        // Force blue color to be visible
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);
        
        // Blue hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 180, 255)); // Lighter blue
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 120, 255)); // Original bright blue
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 100, 200)); // Darker blue
            }
        });
        
        return button;
    }
    
    // Screen navigation methods
    public void showMainMenu() {
        cardLayout.show(mainPanel, "MAIN_MENU");
        nameField.setText("");
    }
    
    public void showGameScreen() {
        cardLayout.show(mainPanel, "GAME_SCREEN");
        updateGameDisplay();
        answerField.requestFocus();
    }
    
    public void showInstructions() {
        cardLayout.show(mainPanel, "INSTRUCTIONS");
    }
    
    public void showVictoryScreen() {
        cardLayout.show(mainPanel, "VICTORY");
    }
    
    public void showGameOverScreen() {
        cardLayout.show(mainPanel, "GAME_OVER");
    }
    
    // Game action methods
    private void startGame() {
        String playerName = nameField.getText().trim();
        if (playerName.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Please enter your name!", "Error", JOptionPane.ERROR_MESSAGE);
            nameField.requestFocus();
            return;
        }
        
        if (playerName.length() > 20) {
            JOptionPane.showMessageDialog(mainFrame, "Name too long! Please use 20 characters or less.", "Error", JOptionPane.ERROR_MESSAGE);
            nameField.requestFocus();
            return;
        }
        
        if (gameEngine != null) {
            gameEngine.startNewGame(playerName);
        }
    }
    
    private void submitAnswer() {
        String answer = answerField.getText().trim();
        if (answer.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Please enter an answer!", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (gameEngine != null && gameEngine.getPlayer() != null) {
            if (!gameEngine.getPlayer().isAlive()) {
                JOptionPane.showMessageDialog(mainFrame, "Game Over! No lives remaining.", "Game Over", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            boolean correct = gameEngine.submitAnswer(answer);
            if (correct) {
                answerField.setText("");
                updateGameDisplay();
            } else {
                // Wrong answer - clear field and update HUD
                answerField.setText("");
                updateHUD();
            }
        }
    }
    
    private void useHint() {
        if (gameEngine != null && gameEngine.getPlayer() != null) {
            Player player = gameEngine.getPlayer();
            if (player.hasHints()) {
                String hint = gameEngine.useHint();
                JOptionPane.showMessageDialog(mainFrame, hint, "Hint", JOptionPane.INFORMATION_MESSAGE);
                updateHUD(); // Update HUD to reflect hint usage
            } else {
                JOptionPane.showMessageDialog(mainFrame, 
                    "No hints remaining!", 
                    "No Hints", 
                    JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    private void quitGame() {
        int result = JOptionPane.showConfirmDialog(mainFrame, 
            "Are you sure you want to quit the game?", 
            "Quit Game", 
            JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            if (gameEngine != null) {
                gameEngine.quitGame();
            }
            showMainMenu();
        }
    }
    
    // Update methods
    public void updateHUD() {
        if (gameEngine != null && gameEngine.getPlayer() != null) {
            Player player = gameEngine.getPlayer();
            playerNameLabel.setText("Player: " + player.getName());
            livesLabel.setText("❤️ Lives: " + player.getLives());
            hintsLabel.setText("💡 Hints: " + player.getHints());
            
            // Enable/disable hint button based on available hints
            if (hintButton != null) {
                hintButton.setEnabled(player.hasHints());
                if (!player.hasHints()) {
                    hintButton.setText("NO HINTS");
                } else {
                    hintButton.setText("USE HINT");
                }
            }
        }
    }
    
    public void updateTimer(int timeRemaining) {
        timerLabel.setText("⏱️ Time: " + timeRemaining + "s");
    }
    
    public void updateGameDisplay() {
        if (gameEngine != null) {
            Room currentRoom = gameEngine.getCurrentRoom();
            if (currentRoom != null) {
                roomInfoArea.setText(currentRoom.getFullDescription());
                
                if (currentRoom.getPuzzle() != null && !currentRoom.getPuzzle().isSolved()) {
                    puzzleQuestionLabel.setText(currentRoom.getPuzzle().getQuestion());
                    // Show hint button only when puzzle is active
                    hintButton.setVisible(true);
                    // Start timer when puzzle is displayed
                    gameEngine.startTimer();
                } else {
                    puzzleQuestionLabel.setText("Puzzle completed! Proceeding to next room...");
                    // Hide hint button when puzzle is completed
                    hintButton.setVisible(false);
                }
            }
        }
    }
    
    public void showRoomTransition() {
        JOptionPane.showMessageDialog(mainFrame, 
            "Room completed! Moving to the next room...", 
            "Room Transition", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void showWrongAnswerMessage() {
        JOptionPane.showMessageDialog(mainFrame, 
            "Wrong answer! You lost a life.", 
            "Incorrect", 
            JOptionPane.WARNING_MESSAGE);
    }
    
    public void showTimeUpMessage() {
        JOptionPane.showMessageDialog(mainFrame, 
            "Time's up! You lost a life.", 
            "Time Up", 
            JOptionPane.WARNING_MESSAGE);
    }
}
