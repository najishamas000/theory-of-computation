import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
 * @author Naji Shamas
 * @version 1.3.1
 */
public class TableDrivenFSA implements java.io.Serializable {
    /** Serialization version ID. */
    private static final long serialVersionUID = 202013L;

    /** Default delimiter. */
    private static final String DELIMITER = ",";

    /** Initial state. */
    private static final int INITIAL_STATE = 0;

    /** State transition table. */
    private int[][] stateTransitionTable;
    /** Alphabet symbols, ordered by column of state transition table. */
    private String[] alphabet;
    /** Identified accept states. */
    private int[] acceptStates;

    /**
     * Create an automaton using specifications from a text file.
     * @param tableFile the name of the file containing the comma-delimited transition table.
     *                  The first row gives the alphabet symbols in order.
     *                  Each subsequent row gives the new states corresponding
     *                  to the input symbols.
     *                  The last row identifies the accept states.
     */
    public TableDrivenFSA(final String tableFile) {
        List<String> rows = new ArrayList<>();
        // Read in data from file.
        try {
            rows = new ArrayList<>(Files.readAllLines(Paths.get(tableFile),
                                   StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.println("Error during attempt to read table file. " + e);
        }
        // Process data assuming it is in correct format.
        int numStates = rows.size() - 2;
        if (numStates > 0) {
            this.alphabet = rows.get(0).split(DELIMITER);
            this.stateTransitionTable
                    = new int[numStates][this.alphabet.length];
            for (int i = 1; i <= numStates; i++) {
                int column = 0;
                this.stateTransitionTable[i - 1] = new int[this.alphabet.length];
                for (String s : rows.get(i).split(DELIMITER)) {
                    this.stateTransitionTable[i - 1][column++] = Integer.parseInt(s);
                }
            }
            String[] accepts = rows.get(rows.size() - 1)
                                   .substring(1, rows.get(rows.size() - 1).length() - 1)
                                   .split(DELIMITER);
            this.acceptStates = Arrays.stream(accepts)
                                      .mapToInt(x -> Integer.valueOf(x))
                                      .toArray();
        }
    }

    @Override
    public String toString() {
        String retVal = "";
        if (this.alphabet != null) {
            retVal = Stream.of(this.alphabet).collect(Collectors.joining(DELIMITER));
            retVal += "\n";
        }
        if (this.stateTransitionTable != null) {
            for (int[] row : this.stateTransitionTable) {
                retVal += Arrays.stream(row)
                                .mapToObj(String::valueOf)
                                .collect(Collectors.joining(DELIMITER));
                retVal += "\n";
            }
        }
        if (this.acceptStates != null) {
            retVal += "{";
            retVal += Arrays.stream(this.acceptStates)
                            .mapToObj(String::valueOf)
                            .collect(Collectors.joining(DELIMITER));
            retVal += "}\n";
        }
        return retVal;
    }

    /**
     * Determines the next state given a current state and an input symbol.
     * @param currentState the current state
     * @param inputSymbol the input symbol
     * @return the next state according to the state transition table;
     *         if any parameter is invalid,
     *         returns the value of the state parameter
     */
    public int nextState(final int currentState, final String inputSymbol) {
        //Handle null input.
        if(inputSymbol != null) {
            String alph = String.join(",",alphabet);
            alph = alph.replace(",","");
            int x = alph.indexOf(inputSymbol); 
            //try...catch statements to handle invalid input.
            try {
            
                int ret = stateTransitionTable[currentState][x]; //Find value at index of stateTransitionTable.
                return ret;
            
            } catch(Exception e) {
                return currentState;
            }
        }
        else {
            return currentState;
        }
        
        
    }

    /**
     * Process a given input string to determine FSA acceptance.
     * @param inputString the string to process (ignores null input)
     * @return true if the end state is an accept state, false otherwise
     */
    public boolean processString(final String inputString) {
        int start = 0;
        boolean accept;
        if(inputString != null) {

            for(int j = 0; j < inputString.length(); j++) {
                char c = inputString.charAt(j);
                if (j == 0) {
                    start = nextState(start,Character.toString(c));
                    System.out.println(start);
                }
                else {
                    start = nextState(start, Character.toString(c));
                    System.out.println(start);
                }
                //Check if last state is in array of accept states
                if (j == (inputString.length() - 1)) { 
                    final int fnl = start;
                    accept = IntStream.of(acceptStates).anyMatch(x->x==fnl); 
                    return accept;
                }
            }
        }
        return false;
    }
     public static void main(String[] args) {
        TableDrivenFSA _tdf = new TableDrivenFSA("input.txt");
        System.out.println(_tdf.nextState(-1, null)); //Invalid edge case.
        System.out.println(_tdf.processString("clal")); //will not accept.
        System.out.println(_tdf.processString("abbc"));
    }
}

