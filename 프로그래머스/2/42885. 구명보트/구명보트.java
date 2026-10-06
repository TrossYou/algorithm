import java.util.*;

class Solution {
    public int solution(int[] people, int limit) {
        int answer = 0;
        Arrays.sort(people);

        int minIdx = 0;
        int maxIdx = people.length-1;
        
        while(minIdx <= maxIdx){
            if(people[maxIdx] + people[minIdx] <= limit) minIdx++;
            maxIdx--;
            answer++;
        }
        
        return answer;
    }
}