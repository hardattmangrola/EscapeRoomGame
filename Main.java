/**
 * Main entry point for the Escape Room Game
 * This class initializes the game engine and UI manager
 */
public class Main {
    public static void main(String[] args) {
        // Set look and feel to system default for better appearance
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // If setting look and feel fails, continue with default
            System.out.println("Could not set system look and feel: " + e.getMessage());
        }
        
        // Create and configure the game
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                // Initialize game engine
                GameEngine gameEngine = new GameEngine();
                
                // Initialize UI manager
                UIManager uiManager = new UIManager();
                
                // Connect game engine and UI manager
                gameEngine.setUIManager(uiManager);
                uiManager.setGameEngine(gameEngine);
                
                // Show splash screen
                showSplashScreen();
                
                // Start the game
                uiManager.showMainMenu();
                
            } catch (Exception e) {
                System.err.println("Error starting the game: " + e.getMessage());
                e.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(null, 
                    "Error starting the game: " + e.getMessage(), 
                    "Error", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    /**
     * Display a splash screen with game logo and information
     */
    private static void showSplashScreen() {
        javax.swing.JWindow splashWindow = new javax.swing.JWindow();
        splashWindow.setSize(800, 600);
        splashWindow.setLocationRelativeTo(null);
        splashWindow.setAlwaysOnTop(true);
        
        // Create splash panel
        javax.swing.JPanel splashPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        splashPanel.setBackground(new java.awt.Color(10, 10, 30));
        splashPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 150, 255), 3));
        
        // ASCII Art Logo
        String logo = """
╔══════════════════════════════════════════════════════════════╗
║                                                              ║
║    ███████╗███████╗ ██████╗ █████╗ ██████╗ ███████╗         ║
║    ██╔════╝██╔════╝██╔════╝██╔══██╗██╔══██╗██╔════╝         ║
║    ███████╗█████╗  ██║     ███████║██████╔╝█████╗           ║
║    ╚════██║██╔══╝  ██║     ██╔══██║██╔══██╗██╔══╝           ║
║    ███████║███████╗╚██████╗██║  ██║██║  ██║███████╗         ║
║    ╚══════╝╚══════╝ ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝         ║
║                                                              ║
║                    🚪 ESCAPE ROOM GAME 🚪                    ║
║                                                              ║
║              Solve puzzles, escape rooms, and                ║
║              challenge your mind in this                     ║
║              immersive adventure!                            ║
║                                                              ║
║              Version 1.0 - Created with Java 24             ║
║                                                              ║
╚══════════════════════════════════════════════════════════════╝
""";
        
        javax.swing.JTextArea logoArea = new javax.swing.JTextArea(logo);
        logoArea.setEditable(false);
        logoArea.setFont(new java.awt.Font("Courier New", java.awt.Font.BOLD, 14));
        logoArea.setBackground(new java.awt.Color(10, 10, 30));
        logoArea.setForeground(new java.awt.Color(0, 255, 255)); // Bright cyan
        logoArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 30, 30, 30));
        logoArea.setOpaque(true);
        logoArea.setLineWrap(false);
        logoArea.setWrapStyleWord(false);
        
        // Loading message
        javax.swing.JLabel loadingLabel = new javax.swing.JLabel("Loading game...", javax.swing.JLabel.CENTER);
        loadingLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
        loadingLabel.setForeground(java.awt.Color.WHITE);
        loadingLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 20, 0));
        
        splashPanel.add(logoArea, java.awt.BorderLayout.CENTER);
        splashPanel.add(loadingLabel, java.awt.BorderLayout.SOUTH);
        
        splashWindow.add(splashPanel);
        splashWindow.setVisible(true);
        
        // Simulate loading time
        try {
            Thread.sleep(5000); // 5 seconds to see the ASCII art
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        splashWindow.dispose();
    }
}
