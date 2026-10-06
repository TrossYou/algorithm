import java.util.*;

class Solution {
    public int solution(String[][] clothes) {
        int answer = 1;
        Map<String, Integer> map = new HashMap<>();
        
        for(String[] strArr: clothes){
            map.putIfAbsent(strArr[1], 0);
            map.put(strArr[1], map.get(strArr[1])+1);
        }
        
        
        for(int value: map.values()){
            answer *= value + 1;
        }
        
        return answer-1;
    }
}