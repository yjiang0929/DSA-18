import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoinsOnAClock {

    public static void backtrack(char[] clock, int pennies, int nickels, int dimes, int hours, List<char[]> result) {
        // add penny
        if (pennies>0) {
            clock[hours] = 'p';
            hours = (hours + 1) % clock.length;
            pennies--;
            if (pennies==0 & nickels==0 & dimes==0) {
                result.add(clock.clone());
            }
            if (clock[hours] == '.' ) {
                backtrack(clock, pennies, nickels, dimes, hours, result);
            }
            pennies++;
            hours = (hours + clock.length -1) % clock.length;
            clock[hours] = '.';
        }
        // add nickel
        if (nickels>0) {
            clock[hours] = 'n';
            hours = (hours + 5) % clock.length;
            nickels--;
            if (pennies==0 & nickels==0 & dimes==0) {
                result.add(clock.clone());
            }
            if (clock[hours] == '.') {
                backtrack(clock, pennies, nickels, dimes, hours, result);
            }
            nickels++;
            hours = (hours + clock.length * 5- 5) % clock.length;
            clock[hours] = '.';
        }
        // add dime
        if (dimes>0) {
            clock[hours] = 'd';
            hours = (hours + 10) % clock.length;
            dimes--;
            if (pennies==0 & nickels==0 & dimes==0) {
                result.add(clock.clone());
            }
            if (clock[hours] == '.') {
                backtrack(clock, pennies, nickels, dimes, hours, result);
            }
            dimes++;
            hours = (hours + clock.length * 10 - 10) % clock.length;
            clock[hours] = '.';
        }
    }

    public static List<char[]> coinsOnAClock(int pennies, int nickels, int dimes, int hoursInDay) {
        List<char[]> result = new ArrayList<>();
        char[] clock = new char[hoursInDay];
        Arrays.fill(clock, '.');
        backtrack(clock, pennies, nickels, dimes, 0, result);
        return result;
    }
}
