import java.util.*;

class Solution {
    public int[] solution(int brown, int yellow) {
        int[] answer = {};
        
        for(int i = 1; i <= Math.sqrt(yellow); i++){
            if(yellow % i == 0 && isAnswer(yellow/i, i, brown)) {
                answer = new int[]{yellow/i+2, i+2};
                break;
            }
        }
        
        return answer;
    }
    
    boolean isAnswer(int width, int height, int brown){
        return brown == 2 * (width+height) + 4;
    }
}