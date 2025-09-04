import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Room class represents each level/room in the escape room game
 */
public class Room {
    private int roomNumber;
    private String name;
    private String description;
    private Color backgroundColor;
    private Puzzle puzzle;
    private boolean unlocked;
    private boolean completed;
    private String backgroundImagePath;
    private List<String> requiredItems;
    private String doorDescription;
    
    public Room(int roomNumber, String name, String description, Color backgroundColor) {
        this.roomNumber = roomNumber;
        this.name = name;
        this.description = description;
        this.backgroundColor = backgroundColor;
        this.unlocked = (roomNumber == 0); // First room is always unlocked
        this.completed = false;
        this.requiredItems = new ArrayList<>();
        this.doorDescription = "A mysterious door awaits...";
    }
    
    // Getters and setters
    public int getRoomNumber() { return roomNumber; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Color getBackgroundColor() { return backgroundColor; }
    public Puzzle getPuzzle() { return puzzle; }
    public boolean isUnlocked() { return unlocked; }
    public boolean isCompleted() { return completed; }
    public String getBackgroundImagePath() { return backgroundImagePath; }
    public List<String> getRequiredItems() { return requiredItems; }
    public String getDoorDescription() { return doorDescription; }
    
    public void setPuzzle(Puzzle puzzle) { this.puzzle = puzzle; }
    public void setUnlocked(boolean unlocked) { this.unlocked = unlocked; }
    public void setBackgroundImagePath(String path) { this.backgroundImagePath = path; }
    public void setDoorDescription(String description) { this.doorDescription = description; }
    
    // Add required item for room access
    public void addRequiredItem(String item) {
        if (!requiredItems.contains(item)) {
            requiredItems.add(item);
        }
    }
    
    // Check if player has all required items
    public boolean canEnter(Player player) {
        if (!unlocked) return false;
        
        for (String item : requiredItems) {
            if (!player.hasItem(item)) {
                return false;
            }
        }
        return true;
    }
    
    // Complete the room
    public void complete() {
        this.completed = true;
        if (puzzle != null) {
            puzzle.solve();
        }
    }
    
    // Check if puzzle is solved
    public boolean isPuzzleSolved() {
        return puzzle != null && puzzle.isSolved();
    }
    
    // Get room status description
    public String getStatusDescription() {
        if (!unlocked) {
            return "This room is locked. Complete the previous room to unlock it.";
        } else if (!canEnter(null)) {
            return "You need certain items to enter this room.";
        } else if (completed) {
            return "This room has been completed!";
        } else if (puzzle != null && !puzzle.isSolved()) {
            return "Solve the puzzle to proceed to the next room.";
        } else {
            return "This room is ready to be explored.";
        }
    }
    
    // Get full room description with puzzle info
    public String getFullDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(name).append(" ===\n");
        sb.append(description).append("\n\n");
        
        if (puzzle != null && !puzzle.isSolved()) {
            sb.append(puzzle.getDescription()).append("\n");
            sb.append("Question: ").append(puzzle.getQuestion()).append("\n");
            // Don't display hint in room description - only show when hint button is pressed
        }
        
        if (!requiredItems.isEmpty()) {
            sb.append("\nRequired items: ");
            sb.append(String.join(", ", requiredItems));
            sb.append("\n");
        }
        
        sb.append("\n").append(doorDescription);
        
        return sb.toString();
    }
}
