package FileManager;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputMismatchHandler {
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

    public int checkInt(int min, int max){
        int value;

        while(true){
            value = checkInt();
            if (value >= min && value <= max) break;
            else System.out.println("Input not within range! Try again.");
        }

        return value;
    }

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

    public float checkFloat(float min, float max){
        float value;

        while(true){
            value = checkFloat();
            if (value >= min && value <= max) break;
            else System.out.println("Input not within range! Try again.");
        }

        return value;
    }

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
