
package chemicalinventorymanager;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

/**
 * This class just contains a lot of useful methods and variables that are universally used throughout the application
 * @author ADDO_a
 */
public class HelperClass {
    
    //Makes TextFields only accept numeric input with a max of one radix point
    //TODO: Change this logic to a TextFormatter
   public static void makeNumericOnly(TextField textInput){
        textInput.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, 
        String newValue) {
            if (!newValue.matches("\\d* (\\.\\d*)? ")) {
                
                if (newValue.contains(".")){
                    //Reconstructing the string by splitting it in two, one part before and the other after the first decimal point
                    String beforeString = newValue.substring(0, newValue.indexOf(".")); 
                    String afterString = "";
                    
                    //This prevents OutOfBounds error if the string is just an "." or ends with an "."
                    if (newValue.indexOf(".") != newValue.length()-1){
                        afterString = newValue.substring(newValue.indexOf(".") + 1, newValue.length());
                    }

                    afterString = afterString.replaceAll("[^0-9]", "");
                    beforeString = beforeString.replaceAll("[^0-9]", ""); 
                    textInput.setText(beforeString + "." + afterString);
                }else{
                    textInput.setText(newValue.replaceAll("[^0-9]", ""));
                }
            }
        }
        });
    }
   
   public static void makePositiveIntegerOnly(TextField textField){
       Pattern validEditingState = Pattern.compile("(([1-9][0-9]*)|0)?");

        UnaryOperator<TextFormatter.Change> filter = c -> {
            String text = c.getControlNewText();
            if (validEditingState.matcher(text).matches()) {
                return c ;
            } else {
                return null ;
            }
        };

        StringConverter<Integer> converter = new StringConverter<Integer>() {

            @Override
            public Integer fromString(String s) {
                if (s.isEmpty()) {
                    return 0 ;
                } else {
                    return Integer.valueOf(s);
                }
            }


            @Override
            public String toString(Integer d) {
                return d.toString();
            }
        };

        TextFormatter<Integer> textFormatter = new TextFormatter<>(converter, 0, filter);
        textField.setTextFormatter(textFormatter);

       }
}
