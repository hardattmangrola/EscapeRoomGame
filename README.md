# Escape Room Game 🚪

A GUI-based Escape Room Game built in Java with immersive features, puzzles, and modern game mechanics.

## Features

### 🎮 Core Game Features
- **Lives System**: Start with 3 lives, lose one for each wrong answer
- **Hints System**: Limited hints per game (3 total)
- **Timer**: Each puzzle has a 60-second countdown
- **Inventory**: Collect items like keys to unlock new rooms
- **Progressive Rooms**: 5 unique rooms with different puzzles

### 🧩 Puzzle Types
- **Riddles**: Answer brain-teasing riddles
- **Caesar Cipher**: Decrypt messages with letter shifting
- **Substitution Cipher**: Decode messages with letter replacement
- **Math Problems**: Solve arithmetic challenges
- **Word Scrambles**: Unscramble jumbled words
- **Logic Puzzles**: Think through complex problems

### 🎨 GUI Features
- **Modern Interface**: Clean, intuitive Java Swing GUI
- **HUD Display**: Shows player name, lives, hints, and timer
- **Room Transitions**: Smooth progression between rooms
- **Visual Feedback**: Color-coded rooms and status indicators
- **Interactive Elements**: Easy-to-use buttons and text fields

## Snapshots

![image_alt](https://github.com/hardattmangrola/EscapeRoomGame/blob/2dc504609694564437aad1daabaa125e718611cd/game1.png)
![image_alt](https://github.com/hardattmangrola/EscapeRoomGame/blob/2dc504609694564437aad1daabaa125e718611cd/game2.png)

## How to Play

### Getting Started
1. **Compile the game**: Run `run_game.bat` or compile manually with `javac *.java`
2. **Start the game**: Run `java Main`
3. **Enter your name** on the main menu
4. **Click "START GAME"** to begin your escape adventure

### Gameplay
1. **Read the room description** carefully
2. **Solve the puzzle** within the time limit
3. **Use hints wisely** - you only have 3 total
4. **Collect items** to unlock new rooms
5. **Progress through all 5 rooms** to escape!

### Controls
- **Text Field**: Type your answers
- **Submit Button**: Submit your answer
- **Use Hint Button**: Get help with the current puzzle
- **Quit Button**: Exit to main menu

## Project Structure

```
Escape Room Game/
├── Main.java              # Entry point with splash screen
├── GameEngine.java        # Game state management and logic
├── Player.java            # Player state (lives, hints, inventory)
├── Room.java              # Room management and progression
├── Puzzle.java            # Abstract puzzle classes and implementations
├── CipherUtils.java       # Cipher encryption/decryption utilities
├── UIManager.java         # GUI components and user interface
├── run_game.bat          # Windows batch file to compile and run
└── README.md             # This file
```

## Technical Details

### Requirements
- **Java 8 or higher**
- **Java Swing** (included with Java)
- **Windows/Linux/Mac** (cross-platform)

### Architecture
- **Object-Oriented Design**: Clean separation of concerns
- **MVC Pattern**: Model (GameEngine), View (UIManager), Controller (Main)
- **Modular Design**: Each class has a specific responsibility
- **Extensible**: Easy to add new puzzle types or rooms

### Key Classes
- **Main**: Application entry point and splash screen
- **GameEngine**: Core game logic, room progression, timer management
- **Player**: Player state management (lives, hints, inventory)
- **Room**: Individual room management and puzzle integration
- **Puzzle**: Abstract base class for all puzzle types
- **CipherUtils**: Utility methods for various cipher operations
- **UIManager**: Complete GUI implementation with multiple screens

## Game Rooms

1. **The Entrance** - Riddle puzzle to get started
2. **The Cipher Chamber** - Caesar cipher decryption
3. **The Mathematical Maze** - Math problem solving
4. **The Word Vault** - Word unscrambling challenge
5. **The Final Chamber** - Substitution cipher for escape

## Tips for Success

- **Read carefully**: Each puzzle has specific requirements
- **Use hints strategically**: You only get 3 for the entire game
- **Watch the timer**: 60 seconds per puzzle
- **Think logically**: Most puzzles have logical solutions
- **Don't rush**: Take time to understand each puzzle

## Troubleshooting

### Compilation Issues
- Ensure Java is installed and in your PATH
- Use `java -version` to check Java installation
- Run `run_game.bat` for automatic compilation and execution

### Runtime Issues
- Make sure all `.class` files are in the same directory
- Check that Java Swing is available (included with standard Java)

## Future Enhancements

- **Sound Effects**: Background music and audio feedback
- **Animations**: Smooth transitions and visual effects
- **More Puzzle Types**: Additional challenge varieties
- **Save System**: Save and load game progress
- **Difficulty Levels**: Adjustable challenge settings
- **Multiplayer**: Cooperative escape room experience

## Credits

Created with Java Swing for a modern, cross-platform gaming experience.

---

**Good luck escaping! 🎯**
