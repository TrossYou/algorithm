import java.util.*;

class Solution {
    public int[] solution(int[] prices) {
        int[] answer = new int[prices.length];
        Deque<Integer> stack = new ArrayDeque<>();
        
        for(int i = prices.length-1; i >= 0 ; i--){
            int cur = prices[i];
            int cnt = 0;
            for(int element : stack){
                cnt++;
                if(element < cur) break;
            }
            answer[i] = cnt;
            stack.push(cur);
        }
        
        // 0 -> 3
        // 1 -> 3 2
        // (2가 작아요.) 1 -> 3 2 3
        // 3 -> 3 2 3 2
        // 4 -> 
        
        return answer;
    }
}