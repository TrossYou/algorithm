import java.util.*;

class Solution {
    public int solution(int[] scoville, int K) {
        int answer = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        
        for(int scov : scoville){
            pq.add(scov);
        }
        
        int firstScov = -1;
        int secondScov = -1;
        while(pq.size() >= 2){
            firstScov = pq.poll();
            if(firstScov >= K) break; // 최저가 K이상이면 횟수 반환
            secondScov = pq.poll();
            pq.add(firstScov + 2 * secondScov);
            answer++;
        }
        
        return pq.peek() < K ? -1 : answer;
    }
}