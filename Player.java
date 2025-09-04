/**
 * Player class manages player state including name, lives, hints, and inventory
 */
public class Player {
    private String name;
    private int lives;
    private int hints;
    private int maxHints;
    private java.util.List<String> inventory;
    private int currentRoom;
    
    public Player(String name) {
        this.name = name;
        this.lives = 3;
        this.maxHints = 3;
        this.hints = maxHints;
        this.inventory = new java.util.ArrayList<>();
        this.currentRoom = 0;
    }
    
    // Getters and setters
    public String getName() { return name; }
    public int getLives() { return lives; }
    public int getHints() { return hints; }
    public int getMaxHints() { return maxHints; }
    public int getCurrentRoom() { return currentRoom; }
    public java.util.List<String> getInventory() { return inventory; }
    
    public void setCurrentRoom(int room) { this.currentRoom = room; }
    
    // Life management
    public void loseLife() {
        if (lives > 0) {
            lives--;
        }
    }
    
    public boolean isAlive() {
        return lives > 0;
    }
    
    // Hint management
    public boolean useHint() {
        if (hints > 0) {
            hints--;
            return true;
        }
        return false;
    }
    
    public boolean hasHints() {
        return hints > 0;
    }
    
    // Inventory management
    public void addItem(String item) {
        if (!inventory.contains(item)) {
            inventory.add(item);
        }
    }
    
    public boolean hasItem(String item) {
        return inventory.contains(item);
    }
    
    public void removeItem(String item) {
        inventory.remove(item);
    }
    
    // Progress to next room
    public void nextRoom() {
        currentRoom++;
    }
    
    // Reset player for new game
    public void reset() {
        this.lives = 3;
        this.hints = maxHints;
        this.inventory.clear();
        this.currentRoom = 0;
    }
}
