import java.util.*;

class Solution {
    public int solution(String[][] clothes) {
        int answer = 1;
        Map<String, Integer> map = new HashMap<>();
        
        for(String[] strArr: clothes){
            map.put(strArr[1], map.getOrDefault(strArr[1], 0)+1);
        }
        
        
        for(int value: map.values()){
            answer *= value + 1;
        }
        
        return answer-1;
    }
}