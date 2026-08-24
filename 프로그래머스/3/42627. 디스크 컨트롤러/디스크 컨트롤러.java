import java.util.*;

class Solution {
    class Work{
        int num, requestTime, turnaroundTime;
        
        Work(int num, int requestTime, int turnaroundTime){
            this.num = num;
            this.requestTime  = requestTime;
            this.turnaroundTime = turnaroundTime;
        }
    
        int getResponseTime(int endTime){
            return endTime - requestTime;
        }
    }
    public int solution(int[][] jobs) {
        int answer = 0;
        PriorityQueue<Work> leftPQ = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.requestTime, o2.requestTime)); // 남은 작업 목록(요청 시간 순서)
        
        // 작업 가능한 작업 목록(3중 우선순위 순서)
        PriorityQueue<Work> possiblePQ = new PriorityQueue<>((o1, o2) -> { 
            if(o1.turnaroundTime != o2.turnaroundTime) return Integer.compare(o1.turnaroundTime, o2.turnaroundTime);
            else if(o1.requestTime != o2.requestTime) return Integer.compare(o1.requestTime, o2.requestTime);
            else return Integer.compare(o1.num, o2.num);
        });

        // pq 채우기 - num, requestTime, turnaroundTime
        for(int i = 0; i < jobs.length; i++){
            leftPQ.add(new Work(i, jobs[i][0], jobs[i][1]));
        }
        
        int curTime = leftPQ.peek().requestTime; // 가장 첫 시작 시간
        while(!leftPQ.isEmpty()){
            if(leftPQ.peek().requestTime > curTime) break;
            possiblePQ.add(leftPQ.poll());
        }
        
        // 작업 가능한 pq 처리
        while(!possiblePQ.isEmpty()){
            Work w  = possiblePQ.poll();
            
            curTime += w.turnaroundTime;
            answer += w.getResponseTime(curTime);
        
            while(!leftPQ.isEmpty()){
                Work nextW = leftPQ.peek();
                // 진행 가능
                if(nextW.requestTime <= curTime) possiblePQ.add(leftPQ.poll());
                else{
                    if(!possiblePQ.isEmpty()) break;
                    else{
                        curTime = nextW.requestTime;
                        possiblePQ.add(leftPQ.poll());
                    }
                }
            }
        }
            
        return answer/jobs.length;
    }
}