import java.util.*;

class Solution {
    int[] root;
    
    public int solution(int n, int[][] costs) {
        int answer = 0;
        int groupCnt = n; // group 수
        root = new int[n];
        for(int i = 0; i < n; i++) root[i] = i; //root 초기화
        
        // costs 오름차순 Queue에 정렬 
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[2], o2[2]));
        
        for(int[] cost: costs) pq.add(cost);

        while(groupCnt > 1 && !pq.isEmpty()){ // 총 그룹 수가 1이어도 종료
            int[] bridge = pq.poll();
            
            int rootA = find(bridge[0]);
            int rootB = find(bridge[1]);
            
            if(rootA == rootB) continue;
            else{
                answer += bridge[2];
                union(rootA, rootB);
                groupCnt--;
            }
        }
        
        return answer;
    }
    
    // 번호의 루트 찾기
    int find(int island){
        if(root[island] == island) return island; // 본인이 root면 본인 반환
        root[island] = find(root[island]);
        return root[island];
    }
    
    // 두 그룹 합치기
    void union(int islandA, int islandB){
        root[islandB] = islandA;
    }
}