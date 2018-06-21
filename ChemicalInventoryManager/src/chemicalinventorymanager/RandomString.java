package chemicalinventorymanager;

/**
 *
 * @author ADDO_a
 */
import java.util.Locale;
import java.util.Random;

public class RandomString {
    
    public static final String lower = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase(Locale.ENGLISH);
    public static final String digits = "0123456789";
    public static final String[] alphanum = (lower + digits).split("");
    
    /**
    * Generate a random string.
    */
    public static String nextString(int length) {
        char[] buffer = new char[length];
        Random random = new Random();
        
        for (int idx = 0; idx < length; ++idx){
            //This is a lazy way to convet a string to a char
            buffer[idx] = alphanum[random.nextInt(length)].charAt(0); 
        }
            
        
        return new String(buffer);
    }
}
