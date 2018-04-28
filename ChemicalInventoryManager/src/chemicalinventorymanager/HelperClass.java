
package chemicalinventorymanager;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * This class just contains a lot of useful methods and variables that are universally used throughout the application
 * @author ADDO_a
 */
public class HelperClass {
    
    //Makes TextFields only accept numeric input with a max of one radix point
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
}
