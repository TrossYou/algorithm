import java.util.*;

class Solution {
    public int[] solution(int[] progresses, int[] speeds) {
        List<Integer> answerList = new ArrayList<>();
        Queue<Integer> que = new LinkedList<>();
        
        for(int i = 0; i < progresses.length; i++){
            int val = (100 - progresses[i])/speeds[i];
            val += (100 - progresses[i])%speeds[i] == 0 ? 0 : 1;
            if(!que.isEmpty()){
                int first = que.peek();
                // -> stack으로 쌓고 더 큰 것 나오면 add
                if(val > first){ 
                    answerList.add(que.size());
                    que.clear();
                }
            }
            que.offer(val);
        }
        answerList.add(que.size());
        
        int[] answer = new int[answerList.size()];
        for(int i = 0; i < answerList.size(); i++) answer[i] = answerList.get(i);
        
        return answer;
    }
}