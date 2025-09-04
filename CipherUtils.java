import java.util.Random;

/**
 * Utility class for various cipher operations including Caesar and Substitution ciphers
 */
public class CipherUtils {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random random = new Random();
    
    /**
     * Encrypts text using Caesar cipher
     * @param text The text to encrypt
     * @param shift The shift value
     * @return Encrypted text
     */
    public static String caesarEncrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();
        text = text.toUpperCase();
        
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                int originalPosition = ALPHABET.indexOf(c);
                int newPosition = (originalPosition + shift) % 26;
                if (newPosition < 0) {
                    newPosition += 26;
                }
                result.append(ALPHABET.charAt(newPosition));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
    
    /**
     * Decrypts text using Caesar cipher
     * @param text The encrypted text
     * @param shift The shift value used for encryption
     * @return Decrypted text
     */
    public static String caesarDecrypt(String text, int shift) {
        return caesarEncrypt(text, -shift);
    }
    
    /**
     * Generates a random Caesar cipher shift value
     * @return Random shift value between 1 and 25
     */
    public static int generateCaesarShift() {
        return random.nextInt(25) + 1;
    }
    
    /**
     * Encrypts text using Substitution cipher
     * @param text The text to encrypt
     * @param key The substitution key (26-character string)
     * @return Encrypted text
     */
    public static String substitutionEncrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        text = text.toUpperCase();
        key = key.toUpperCase();
        
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                int position = ALPHABET.indexOf(c);
                if (position >= 0 && position < key.length()) {
                    result.append(key.charAt(position));
                } else {
                    result.append(c);
                }
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
    
    /**
     * Decrypts text using Substitution cipher
     * @param text The encrypted text
     * @param key The substitution key used for encryption
     * @return Decrypted text
     */
    public static String substitutionDecrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        text = text.toUpperCase();
        key = key.toUpperCase();
        
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                int position = key.indexOf(c);
                if (position >= 0 && position < ALPHABET.length()) {
                    result.append(ALPHABET.charAt(position));
                } else {
                    result.append(c);
                }
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
    
    /**
     * Generates a random substitution cipher key
     * @return Random 26-character substitution key
     */
    public static String generateSubstitutionKey() {
        StringBuilder key = new StringBuilder(ALPHABET);
        for (int i = 0; i < key.length(); i++) {
            int j = random.nextInt(key.length());
            char temp = key.charAt(i);
            key.setCharAt(i, key.charAt(j));
            key.setCharAt(j, temp);
        }
        return key.toString();
    }
    
    /**
     * Creates a simple word scramble
     * @param word The word to scramble
     * @return Scrambled word
     */
    public static String scrambleWord(String word) {
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int j = random.nextInt(chars.length);
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        return new String(chars);
    }
    
    /**
     * Generates a random math puzzle
     * @return Array containing [question, answer] as strings
     */
    public static String[] generateMathPuzzle() {
        int a = random.nextInt(20) + 1;
        int b = random.nextInt(20) + 1;
        int operation = random.nextInt(4);
        
        String question;
        int answer;
        
        switch (operation) {
            case 0: // Addition
                question = a + " + " + b + " = ?";
                answer = a + b;
                break;
            case 1: // Subtraction
                if (a < b) {
                    int temp = a;
                    a = b;
                    b = temp;
                }
                question = a + " - " + b + " = ?";
                answer = a - b;
                break;
            case 2: // Multiplication
                a = random.nextInt(10) + 1;
                b = random.nextInt(10) + 1;
                question = a + " × " + b + " = ?";
                answer = a * b;
                break;
            case 3: // Division
                answer = random.nextInt(10) + 1;
                b = random.nextInt(5) + 1;
                a = answer * b;
                question = a + " ÷ " + b + " = ?";
                break;
            default:
                question = "1 + 1 = ?";
                answer = 2;
        }
        
        return new String[]{question, String.valueOf(answer)};
    }
}
