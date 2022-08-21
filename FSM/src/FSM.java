import java.util.HashMap;
import java.util.HashSet;

public class FSM{

    HashSet<String> alphabet;
    HashSet<String> states;
    String currState;
    HashSet<String> finiteStates;
    HashMap<String, HashMap<String, String>> transitions;

    public FSM(HashSet<String> alphabet, HashSet<String> states, String currState,
               HashSet<String> finiteStates, HashMap<String, HashMap<String, String>> transitions) {
        this.alphabet = alphabet;
        this.states = states;
        this.currState = currState;
        this.finiteStates = finiteStates;
        this.transitions = transitions;
    }

    private boolean checkExistTransition(String state, char symbolIn){
        String symbol = String.valueOf(symbolIn);
        return (this.transitions.containsKey(state) && this.transitions.get(state).containsKey(symbol));
    }

    public void changeState(char symbol) throws WrongExpressionException{
        if(this.checkExistTransition(this.currState, symbol)){

            String symbolStr = String.valueOf(symbol);
            this.currState = this.transitions.get(this.currState).get(symbolStr);
        }
        else {
            throw new WrongExpressionException("Error, no transition");
        }
    }

    private boolean checkBelongToChar(char symbol) throws WrongExpressionException{
        if (this.alphabet.contains(String.valueOf(symbol))){
            return true;
        }
        else {
            throw new WrongExpressionException("Error. Not a letter.");
        }
    }
    public boolean test(String value) throws WrongExpressionException{
        for (char symbol : value.toCharArray()){
            if (this.checkBelongToChar(symbol)){
                this.changeState(symbol);
            }
            else {
                throw new WrongExpressionException("Error.");
            }
        }
        return this.finiteStates.contains(this.currState);

    }
}
