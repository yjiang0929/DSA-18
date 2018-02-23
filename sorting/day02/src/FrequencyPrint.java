import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FrequencyPrint {

    static String frequencyPrint(String s) {
        String[] words = s.split(" ");
        HashMap<String, Integer> dict = new HashMap<>();
        for (String word : words) {
            if (dict.containsKey(word)) {
                dict.put(word,dict.get(word)+1);
            } else {
                dict.put(word,1);
            }
        }
        String[] counter = new String[words.length];
        for (Map.Entry<String, Integer> pair : dict.entrySet()) {
            if (counter[pair.getValue()] == null) {
                counter[pair.getValue()] = pair.getKey();
            } else {
                counter[pair.getValue()] = counter[pair.getValue()] + " " + pair.getKey();
            }
        }
        String res = "";
        for (int i=0;i<counter.length;i++) {
            if (counter[i] != null) {
                String[] counts = counter[i].split(" ");
                for (String count : counts) {
                    for (int j=0;j<i;j++) {
                        res = res + count + " ";
                    }
                }
            }
        }
        return res.substring(0,res.length()-1);
    }

}
