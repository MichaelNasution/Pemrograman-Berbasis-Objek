/**
 * @author 12S24003 Michael Nasution
 
 */

package academic.model;

import java.util.Arrays;
import java.util.List;

public class InputParser {

    public static List<String> parseCommand(String line) {
        return Arrays.asList(line.split("#"));
    }
}
