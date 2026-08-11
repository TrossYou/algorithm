import java.util.*;

class Solution {
    public int solution(int[] elements) {
        Set<Integer> set = new HashSet<>();
    
        for(int start = 0; start < elements.length; start++){
            int sum = 0;
            for(int num = 0; num < elements.length; num++){ 
                sum += elements[(start+num)%elements.length];
                set.add(sum);
            }
        }
        
        return set.size();
    }
}