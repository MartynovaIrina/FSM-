import java.util.*;

public class Main {
    public static void main(String[] args) throws WrongExpressionException{

        String[] v1PossibleLetterArray = new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i",
                "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        HashSet<String> v1PossibleLetter = new HashSet<>(Arrays.asList(v1PossibleLetterArray));

        String[] v2PossibleSymbolArray = new String[] {"!", "@", "#", "$", "%", "&", "?"};
        HashSet<String> v2PossibleSymbol = new HashSet<>(Arrays.asList(v2PossibleSymbolArray));

        String[] v3PossibleOperatorsArray = new String[] {"-", "+", "*", "/"};
        HashSet<String> v3PossibleOperators = new HashSet<>(Arrays.asList(v3PossibleOperatorsArray));

        String[] v7PossibleNumberArray = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        HashSet<String> v7PossibleNumber = new HashSet<>(Arrays.asList(v7PossibleNumberArray));

        String v4Equal = "=";

        String v5EndOfLine = ";";

        String v6Space = " ";

        String[] QTransitionsString = new String[] { "q1", "q2", "q3", "q4", "q5", "q6", "q7_0", "q7", "q8"};
        HashSet<String> QTransitions = new HashSet<>(Arrays.asList(QTransitionsString));

        String SStartState = "q0";


        String[] FEndStatesString = new String[] {"q9"};
        HashSet<String> FEndStates = new HashSet<>(Arrays.asList(FEndStatesString));

        HashSet<String> alphabet = new HashSet<>();

        alphabet.addAll(v1PossibleLetter);
        alphabet.addAll(v2PossibleSymbol);
        alphabet.addAll(v3PossibleOperators);
        alphabet.add(v4Equal);
        alphabet.add(v5EndOfLine);
        alphabet.add(v6Space);
        alphabet.addAll(v7PossibleNumber);

        HashMap<String, HashMap<String, String>> transitions = new HashMap<>();
        HashMap<String, String> transitionsPossible = new HashMap<>();

       // HashMap<String, String> temp = new HashMap<>();

        //переходим по символу i
        transitionsPossible.put("i", "q1");
        transitions.put("q0", (HashMap)transitionsPossible.clone());

        //переходим по символу n
        transitionsPossible.clear();
        transitionsPossible.put("n", "q2");
        transitions.put("q1", (HashMap)transitionsPossible.clone());

        //переходим по символу t
        transitionsPossible.clear();
        transitionsPossible.put("t", "q3");
        transitions.put("q2", (HashMap)transitionsPossible.clone());

        //переходим только если появилась буква, иначе стоим
        transitionsPossible.clear();
        transitionsPossible.putAll(getAllCombos(v1PossibleLetter, "q4"));
        transitionsPossible.put(" ", "q3");
        transitions.put("q3", (HashMap)transitionsPossible.clone());

        //переходим дальше только если появился знак равно или ;
        transitionsPossible.clear();
        transitionsPossible.putAll(getAllCombos(v1PossibleLetter, "q4"));
        transitionsPossible.put(" ", "q4");
        transitionsPossible.put("=", "q5");
        transitionsPossible.put(";", "q9");
        transitions.put("q4", (HashMap)transitionsPossible.clone());

        //переходим дальше если появилась хоть 1 цифра
        transitionsPossible.clear();
        transitionsPossible.putAll(getAllCombos(v7PossibleNumber, "q6"));
        transitionsPossible.put(" ", "q5");
        transitions.put("q5", (HashMap)transitionsPossible.clone());

        /*
        пришли сюда гарантированно с 1 цифрой
        переходим дальше только если появился один из операторов или если ;
        или знак пробела, то есть конец числа
         */
        transitionsPossible.clear();
        transitionsPossible.putAll(getAllCombos(v7PossibleNumber, "q6"));
        transitionsPossible.put(" ", "q7");
        transitionsPossible.put(";", "q9");
        transitionsPossible.putAll(getAllCombos(v3PossibleOperators, "q8"));
        transitions.put("q6", (HashMap)transitionsPossible.clone());

        //это состояние необходимо т.к. в q6 может случиться переход по пробелу, то есть конец числа
        transitionsPossible.clear();
        transitionsPossible.putAll(getAllCombos(v3PossibleOperators, "q8"));
        transitionsPossible.put(" ", "q7");
        transitionsPossible.put(";", "q9");
        transitions.put("q7", (HashMap)transitionsPossible.clone());

        //переходим в q6 так как гарантирована 1 цифра, завершить по ; не может т.к. пришли сюда с оператором
        transitionsPossible.clear();
        transitionsPossible.putAll(getAllCombos(v7PossibleNumber, "q6"));
        transitionsPossible.put(" ", "q8");
        transitions.put("q8", (HashMap)transitionsPossible.clone());

        //System.out.println(transitions);

        FSM fsm = new FSM(alphabet, QTransitions, SStartState, FEndStates, transitions);
        System.out.println(fsm.test("int     lllla  = 1 + 3554332 * 11l"));
    }
    public static  HashMap<String, String> getAllCombos(HashSet<String> alphabet, String targetState){
        HashMap<String, String> cartesianProduct = new HashMap<>();
        for (String currentSymbol : alphabet) {
            cartesianProduct.put(currentSymbol, targetState);
        }
        return cartesianProduct;
    }
}