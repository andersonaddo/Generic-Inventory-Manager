
package chemicalinventorymanager;

import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

/**
 * This class just contains a lot of useful methods and variables that are universally used throughout the application
 * @author ADDO_a
 */
public class HelperClass {
    
    //Makes TextFields only accept positive numeric input with a max of one radix point
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
   
   
       //Makes TextFields reject whitespace
       public static void disallowSpaces(TextField textField) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            
            //Using regex to fing and eliminate whitespace
            Pattern pattern = Pattern.compile("\\s");
            Matcher matcher = pattern.matcher(change.getText());
            if (matcher.find()){
                change.setText(change.getText().replaceAll("\\s",""));
            }
            return change;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        textField.setTextFormatter(textFormatter);
    }
   
   
       /**
        * Used to alert the user when he/she enters something invalid as input
        * @author ADDO_a
        */
       public static void alertInvalidInput(String message){
           Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("You didn't enter something right!");
            alert.setContentText(message);

            alert.showAndWait();
        }
       
        /**
        * Used to alert the user a general error is encountered
        * @author ADDO_a
        */
       public static void alertError(Exception e){
           Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Faced");
            alert.setHeaderText(null);
            alert.setContentText("We encountered an error! \nPerhaps you enetered something that was too long, or contained either an inverted comma (\') or speech marks (\").\n"
                    + "Double check your inputs, and call the developers for help."
                    + "\n\nError: " + e);

            alert.showAndWait();
       }
       
    public static boolean confirmUser(String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure?");
        alert.setContentText(message);

        ButtonType confirm = new ButtonType("Yes");
        ButtonType cancel = new ButtonType("No", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(confirm, cancel);

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == confirm;

    }

    public static void showSuccess(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
       
}
