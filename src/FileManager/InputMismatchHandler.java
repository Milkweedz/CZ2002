package FileManager;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class handles all of the input mismatches. When the application prompts the user to enter data of a certain type
 * and the user enters a different type, methods in this class will return an error message and force the user to enter
 * the correct data type.
 *
 * @author Ng Man Chun
 * @version 1.0
 * @since 2018-11-15
 */
public class InputMismatchHandler {

    /**
     * This method ensures that the value input is an integer.
     * @return integer value
     */
    public int checkInt(){
        Scanner scan = new Scanner(System.in);
        int value = 0;

        boolean correct = false;
        while(!correct){
            try {
                value = scan.nextInt();
                correct = true;
            } catch (InputMismatchException ex){
                System.out.println("Not an integer! Try again.");
                scan.next();
            }
        }

        return value;
    }

    /**
     * This method ensures that the value input is an integer and in a specific range.
     * It is an overloaded version of checkInt(void)
     * @param min
     * @param max
     * @return integer value
     */
    public int checkInt(int min, int max){
        int value;

        while(true){
            value = checkInt();
            if (value >= min && value <= max) break;
            else System.out.println("Input not within range! Try again.");
        }

        return value;
    }

    /**
     * This method ensures that the value input is an float.
     * @return float value
     */
    public float checkFloat(){
        Scanner scan = new Scanner(System.in);
        float value = 0;

        boolean correct = false;
        while(!correct){
            try {
                value = scan.nextFloat();
                correct = true;
            } catch (InputMismatchException ex){
                System.out.println("Not a float! Try again.");
                scan.next();
            }
        }

        return value;
    }

    /**
     * This method ensures that the value input is a float and in a specific range.
     * It is an overloaded version of checkFloat(void)
     * @param min
     * @param max
     * @return float value
     */
    public float checkFloat(float min, float max){
        float value;

        while(true){
            value = checkFloat();
            if (value >= min && value <= max) break;
            else System.out.println("Input not within range! Try again.");
        }

        return value;
    }

    /**
     * This method ensures that the string input matches one of the strings given in the options string array.
     * It prevents users from inputting a string that is not valid.
     * @param options
     * @return a valid string
     */
    public String checkString(String[] options){
        Scanner scan = new Scanner(System.in);
        String value = "";

        boolean correct = false;
        while(!correct){
            value = scan.next().toUpperCase();
            for(int i=0; i<options.length;i++) {
                if (value.equals(options[i])) {
                    correct = true;
                }
            }
            if (!correct) {
                System.out.println("Input not correct! Follow the stipulated format and try again.");
            }
        }

        return value;
    }
}
