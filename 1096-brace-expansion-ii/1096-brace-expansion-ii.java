import java.util.*;

class Solution {
    private int index;

    public List<String> braceExpansionII(String expression) {
        index = 0;

        Set<String> result = parse(expression);

        List<String> answer = new ArrayList<>(result);
        Collections.sort(answer);

        return answer;
    }

    private Set<String> parse(String s) {
        Set<String> result = new HashSet<>();

        while (index < s.length() && s.charAt(index) != '}') {

            if (s.charAt(index) == '{') {

                index++; // skip '{'

                Set<String> inside = parse(s);

                index++; // skip '}'

                result = combineConcat(result, inside);

            } else if (s.charAt(index) == ',') {

                index++; // skip ','

                Set<String> next = parse(s);

                result = combineUnion(result, next);

                break;

            } else {

                // lowercase letter
                Set<String> letter = new HashSet<>();

                letter.add(String.valueOf(s.charAt(index)));

                index++;

                result = combineConcat(result, letter);
            }
        }

        return result;
    }

    private Set<String> combineUnion(Set<String> a, Set<String> b) {

        Set<String> result = new HashSet<>(a);

        result.addAll(b);

        return result;
    }

    private Set<String> combineConcat(Set<String> a, Set<String> b) {

        if (a.isEmpty()) {
            return new HashSet<>(b);
        }

        Set<String> result = new HashSet<>();

        for (String x : a) {
            for (String y : b) {
                result.add(x + y);
            }
        }

        return result;
    }
}