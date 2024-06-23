import java.util.InputMismatchException;
import java.util.Scanner;

import exceptions.NumberException;

public class Validations {
    boolean isValid = false;
    int number = 0;

    public int getValidNumber(Scanner scan) {
        while (!isValid) {
            number = scan.nextInt();
            try {
                if (number < 1)
                    throw new NumberException("The id cannot be zero or negative");
                isValid = true;
            } catch (NumberException | InputMismatchException ex) {
                System.out.printf("%50s", "Enter a valid Employee ID: ");
                isValid = false;
                scan.nextLine();
            }
        }
        return number;
    }
}
