/**
 * Abstract base class for all puzzle types in the escape room game
 */
public abstract class Puzzle {
    protected String question;
    protected String answer;
    protected String hint;
    protected boolean solved;
    protected PuzzleType type;
    
    public enum PuzzleType {
        RIDDLE, CAESAR_CIPHER, SUBSTITUTION_CIPHER, MATH, WORD_SCRAMBLE, LOGIC
    }
    
    public Puzzle(String question, String answer, String hint, PuzzleType type) {
        this.question = question;
        this.answer = answer;
        this.hint = hint;
        this.type = type;
        this.solved = false;
    }
    
    // Getters
    public String getQuestion() { return question; }
    public String getAnswer() { return answer; }
    public String getHint() { return hint; }
    public boolean isSolved() { return solved; }
    public PuzzleType getType() { return type; }
    
    // Check if the provided answer is correct
    public boolean checkAnswer(String userAnswer) {
        if (userAnswer == null) return false;
        return answer.equalsIgnoreCase(userAnswer.trim());
    }
    
    // Mark puzzle as solved
    public void solve() {
        this.solved = true;
    }
    
    // Abstract method for puzzle-specific validation
    public abstract boolean validateAnswer(String userAnswer);
    
    // Abstract method to get puzzle description
    public abstract String getDescription();
}

/**
 * Riddle puzzle implementation
 */
class RiddlePuzzle extends Puzzle {
    public RiddlePuzzle(String question, String answer, String hint) {
        super(question, answer, hint, PuzzleType.RIDDLE);
    }
    
    @Override
    public boolean validateAnswer(String userAnswer) {
        return checkAnswer(userAnswer);
    }
    
    @Override
    public String getDescription() {
        return "Solve this riddle to proceed:";
    }
}

/**
 * Caesar cipher puzzle implementation
 */
class CaesarCipherPuzzle extends Puzzle {
    private int shift;
    
    public CaesarCipherPuzzle(String encryptedText, String answer, String hint, int shift) {
        super(encryptedText, answer, hint, PuzzleType.CAESAR_CIPHER);
        this.shift = shift;
    }
    
    @Override
    public boolean validateAnswer(String userAnswer) {
        return checkAnswer(userAnswer);
    }
    
    @Override
    public String getDescription() {
        return "Decrypt this Caesar cipher (shift by " + shift + "):";
    }
    
    public int getShift() { return shift; }
}

/**
 * Substitution cipher puzzle implementation
 */
class SubstitutionCipherPuzzle extends Puzzle {
    private String key;
    
    public SubstitutionCipherPuzzle(String encryptedText, String answer, String hint, String key) {
        super(encryptedText, answer, hint, PuzzleType.SUBSTITUTION_CIPHER);
        this.key = key;
    }
    
    @Override
    public boolean validateAnswer(String userAnswer) {
        return checkAnswer(userAnswer);
    }
    
    @Override
    public String getDescription() {
        return "Decrypt this substitution cipher:";
    }
    
    public String getKey() { return key; }
}

/**
 * Math puzzle implementation
 */
class MathPuzzle extends Puzzle {
    public MathPuzzle(String question, String answer, String hint) {
        super(question, answer, hint, PuzzleType.MATH);
    }
    
    @Override
    public boolean validateAnswer(String userAnswer) {
        try {
            int userNum = Integer.parseInt(userAnswer.trim());
            int correctNum = Integer.parseInt(answer);
            return userNum == correctNum;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    @Override
    public String getDescription() {
        return "Solve this math problem:";
    }
}

/**
 * Word scramble puzzle implementation
 */
class WordScramblePuzzle extends Puzzle {
    public WordScramblePuzzle(String scrambledWord, String answer, String hint) {
        super(scrambledWord, answer, hint, PuzzleType.WORD_SCRAMBLE);
    }
    
    @Override
    public boolean validateAnswer(String userAnswer) {
        return checkAnswer(userAnswer);
    }
    
    @Override
    public String getDescription() {
        return "Unscramble this word:";
    }
}

/**
 * Logic puzzle implementation
 */
class LogicPuzzle extends Puzzle {
    public LogicPuzzle(String question, String answer, String hint) {
        super(question, answer, hint, PuzzleType.LOGIC);
    }
    
    @Override
    public boolean validateAnswer(String userAnswer) {
        return checkAnswer(userAnswer);
    }
    
    @Override
    public String getDescription() {
        return "Solve this logic puzzle:";
    }
}
