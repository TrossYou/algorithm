import java.util.*;

class Solution {
    public int[] solution(String[] genres, int[] plays) {
        // int[0]: 총합, int[1]: 1등 인덱스, int[2]: 2등 인덱스
        HashMap<String, int[]> map = new HashMap<>();
        
        for(int i = 0; i < genres.length; i++){
            String genre = genres[i];
            int play = plays[i];

            // genre가 없다면, 추가
            map.putIfAbsent(genre, new int[]{0, -1, -1});
            
            int[] item = map.get(genre);
            item[0] += play; // 총 합 더하기
            // 1등 없거나 더 크면 인덱스 바꾸기
            if(map.get(genre)[1] == -1 || plays[item[1]] < play){
                item[2] = item[1];
                item[1] = i;
            }
            // 2등이 없거나 더 크면 인덱서 바꾸기
            else if(map.get(genre)[2] == - 1 || plays[item[2]] < play) item[2] = i;
        }
        
        List<Integer> answerList = new ArrayList<>();
        
        // 장르별 item[0]이 가장 큰 순서대로 정렬하고 싶다!!!
        PriorityQueue<Map.Entry<String, int[]>> pq = new PriorityQueue<>((o1, o2) -> o2.getValue()[0] - o1.getValue()[0]);
        
        for(Map.Entry<String, int[]> entry: map.entrySet()){
            pq.offer(entry);            
        }
        
        while(!pq.isEmpty()){
            Map.Entry<String, int[]> entry = pq.poll();
            if(entry.getValue()[1] != -1) answerList.add(entry.getValue()[1]);
            if(entry.getValue()[2] != -1) answerList.add(entry.getValue()[2]);
        }
        
        int[] answer = new int[answerList.size()];
        for(int i = 0; i < answer.length; i++) answer[i] = answerList.get(i);
        
        return answer;
    }
}

// 장르별 합 -> PQ
// 장르별 앞에 두개 ->HashMap<String, int[]> 1등 / 2등 인덱스 저장