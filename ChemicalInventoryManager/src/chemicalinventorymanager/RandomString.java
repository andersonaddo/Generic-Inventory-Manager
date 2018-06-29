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
    * Generate a random string. It will be split into threes using dashes. Length doesn't count these dashes
    */
    public static String nextString(int length) {
        length += (int)Math.floor(length/3);
        char[] buffer = new char[length];
        Random random = new Random();
        
        for (int idx = 0; idx < length; idx++){
            
            if ((idx + 1) % 4 == 0){
                buffer[idx] = '-';
                continue;
            } 
            
            //This is a lazy way to convet a string to a char
            buffer[idx] = alphanum[random.nextInt(alphanum.length)].charAt(0); 
        }
            
        
        return new String(buffer);
    }
}
