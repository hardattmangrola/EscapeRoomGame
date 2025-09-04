import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * GameEngine manages the overall game state, progression, and game loop
 */
public class GameEngine {
    private Player player;
    private List<Room> rooms;
    private int currentRoomIndex;
    private boolean gameRunning;
    private boolean gameWon;
    private Timer puzzleTimer;
    private int timeLimit; // in seconds
    private int timeRemaining;
    private UIManager uiManager;
    
    public GameEngine() {
        this.rooms = new ArrayList<>();
        this.gameRunning = false;
        this.gameWon = false;
        this.timeLimit = 60; // 60 seconds per puzzle
        this.timeRemaining = timeLimit;
        initializeRooms();
    }
    
    public void setUIManager(UIManager uiManager) {
        this.uiManager = uiManager;
    }
    
    // Initialize all rooms with puzzles
    private void initializeRooms() {
        // Room 1: Welcome Room - Riddle
        Room room1 = new Room(0, "The Entrance", 
            "You find yourself in a dimly lit entrance hall. Ancient symbols cover the walls, and a single door stands before you.",
            new java.awt.Color(139, 69, 19)); // Brown
        room1.setPuzzle(new RiddlePuzzle(
            "I speak without a mouth and hear without ears. I have no body, but come alive with wind. What am I?",
            "echo",
            "Think about what happens when you shout in a canyon..."
        ));
        rooms.add(room1);
        
        // Room 2: Caesar Cipher Room
        Room room2 = new Room(1, "The Cipher Chamber",
            "The door creaks open to reveal a chamber filled with ancient scrolls. Cryptic messages line the walls.",
            new java.awt.Color(75, 0, 130)); // Indigo
        String caesarText = CipherUtils.caesarEncrypt("OPEN THE DOOR", 3);
        room2.setPuzzle(new CaesarCipherPuzzle(
            caesarText,
            "OPEN THE DOOR",
            "Each letter is shifted by 3 positions in the alphabet",
            3
        ));
        room2.addRequiredItem("key");
        rooms.add(room2);
        
        // Room 3: Math Puzzle Room
        Room room3 = new Room(2, "The Mathematical Maze",
            "You enter a room with numbers floating in the air. Mathematical equations glow on the walls.",
            new java.awt.Color(0, 100, 0)); // Dark Green
        String[] mathPuzzle = CipherUtils.generateMathPuzzle();
        room3.setPuzzle(new MathPuzzle(
            mathPuzzle[0],
            mathPuzzle[1],
            "Remember your basic arithmetic operations"
        ));
        rooms.add(room3);
        
        // Room 4: Word Scramble Room
        Room room4 = new Room(3, "The Word Vault",
            "Bookshelves line every wall, filled with scrambled words. A single word holds the key to freedom.",
            new java.awt.Color(128, 0, 128)); // Purple
        String scrambledWord = CipherUtils.scrambleWord("FREEDOM");
        room4.setPuzzle(new WordScramblePuzzle(
            scrambledWord,
            "FREEDOM",
            "The word has 7 letters and means liberation"
        ));
        rooms.add(room4);
        
        // Room 5: Substitution Cipher Room
        Room room5 = new Room(4, "The Final Chamber",
            "The final room. Ancient runes cover every surface. Decode the final message to escape!",
            new java.awt.Color(139, 0, 0)); // Dark Red
        String subKey = CipherUtils.generateSubstitutionKey();
        String subText = CipherUtils.substitutionEncrypt("ESCAPE SUCCESS", subKey);
        room5.setPuzzle(new SubstitutionCipherPuzzle(
            subText,
            "ESCAPE SUCCESS",
            "Each letter is replaced with another letter according to a pattern",
            subKey
        ));
        rooms.add(room5);
    }
    
    // Start a new game
    public void startNewGame(String playerName) {
        this.player = new Player(playerName);
        this.currentRoomIndex = 0;
        this.gameRunning = true;
        this.gameWon = false;
        this.timeRemaining = timeLimit;
        
        // Give player starting items
        player.addItem("key");
        
        if (uiManager != null) {
            uiManager.showGameScreen();
            uiManager.updateHUD();
        }
    }
    
    // Get current room
    public Room getCurrentRoom() {
        if (currentRoomIndex < rooms.size()) {
            return rooms.get(currentRoomIndex);
        }
        return null;
    }
    
    // Submit answer for current puzzle
    public boolean submitAnswer(String answer) {
        Room currentRoom = getCurrentRoom();
        if (currentRoom == null || currentRoom.getPuzzle() == null) {
            return false;
        }
        
        if (currentRoom.getPuzzle().validateAnswer(answer)) {
            // Correct answer
            currentRoom.complete();
            stopTimer();
            
            if (currentRoomIndex == rooms.size() - 1) {
                // Last room completed - player wins!
                gameWon = true;
                gameRunning = false;
                if (uiManager != null) {
                    uiManager.showVictoryScreen();
                }
            } else {
                // Move to next room
                currentRoomIndex++;
                Room nextRoom = getCurrentRoom();
                if (nextRoom != null) {
                    nextRoom.setUnlocked(true);
                }
                if (uiManager != null) {
                    uiManager.updateHUD();
                    uiManager.showRoomTransition();
                }
            }
            return true;
        } else {
            // Wrong answer - lose a life
            player.loseLife();
            if (uiManager != null) {
                uiManager.updateHUD();
                uiManager.showWrongAnswerMessage();
            }
            
            if (!player.isAlive()) {
                // Game over
                gameRunning = false;
                if (uiManager != null) {
                    uiManager.showGameOverScreen();
                }
            }
            return false;
        }
    }
    
    // Use hint for current puzzle
    public String useHint() {
        if (!player.hasHints()) {
            return "No hints remaining!";
        }
        
        if (player.useHint()) {
            Room currentRoom = getCurrentRoom();
            if (currentRoom != null && currentRoom.getPuzzle() != null) {
                if (uiManager != null) {
                    uiManager.updateHUD();
                }
                return currentRoom.getPuzzle().getHint();
            }
        }
        return "No hint available for this puzzle.";
    }
    
    // Start puzzle timer
    public void startTimer() {
        stopTimer(); // Stop any existing timer
        timeRemaining = timeLimit;
        
        puzzleTimer = new Timer();
        puzzleTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeRemaining--;
                if (uiManager != null) {
                    uiManager.updateTimer(timeRemaining);
                }
                
                if (timeRemaining <= 0) {
                    // Time's up - lose a life
                    player.loseLife();
                    if (uiManager != null) {
                        uiManager.updateHUD();
                        uiManager.showTimeUpMessage();
                    }
                    
                    if (!player.isAlive()) {
                        gameRunning = false;
                        if (uiManager != null) {
                            uiManager.showGameOverScreen();
                        }
                    }
                    stopTimer();
                }
            }
        }, 1000, 1000); // Update every second
    }
    
    // Stop puzzle timer
    public void stopTimer() {
        if (puzzleTimer != null) {
            puzzleTimer.cancel();
            puzzleTimer = null;
        }
    }
    
    // Quit game
    public void quitGame() {
        gameRunning = false;
        stopTimer();
        if (uiManager != null) {
            uiManager.showMainMenu();
        }
    }
    
    // Getters
    public Player getPlayer() { return player; }
    public List<Room> getRooms() { return rooms; }
    public boolean isGameRunning() { return gameRunning; }
    public boolean isGameWon() { return gameWon; }
    public int getTimeRemaining() { return timeRemaining; }
    public int getCurrentRoomIndex() { return currentRoomIndex; }
    public int getTotalRooms() { return rooms.size(); }
}
