import java.util.*;

class Solution {
    public long solution(int[] weights) {
        long answer = 0;
        // 가능한 비율은 2:3, 1:2, 3:4
        Arrays.sort(weights);
        
        
        for(int i = 0; i < weights.length; i++){
            for(int j = i+1; j < weights.length; j++){
                int a = weights[i];
                int b = weights[j];
                if(a == b ||(a*3 == b*2) || (a*2 == b) || (a*4 == b*3)) answer++;
            }
        }
        
        return answer;
    }
}

// 2: 3: 4
// 2: 3
// 1 : 2
// 3 : 4
