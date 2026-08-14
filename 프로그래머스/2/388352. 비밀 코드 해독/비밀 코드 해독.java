import java.util.*;

class Solution {
    List<boolean[]> candidateList = new ArrayList<>();
    boolean[] isCandidate; // candidateList의 인덱스가 유효한지 표시
    int n;
    
    public int solution(int n, int[][] q, int[] ans) {
        this.n = n;
        
        // (1) 후보 조합 모두 만들기 nC5개의 boolean[31];
        comb(0, 1, new boolean[n+1]);    
        isCandidate = new boolean[candidateList.size()];
        Arrays.fill(isCandidate, true); // 모든 후보는 일단 유효함.
        
        // (2) q를 순회 하며, 후보 제거
        for(int i = 0; i < q.length; i++){
            for(int j = 0; j < candidateList.size(); j++){
                if(!isCandidate[j]) continue;
                check(q[i], candidateList.get(j), j, ans[i]);
            }
        }

        // (3) 남은 후보 반환
        int answer = 0;
        for(int i = 0; i < candidateList.size(); i++){
            if(isCandidate[i]) answer++;
        }
        return answer;
    }
    
    // num: 지금까지 포함된 수
    // idx: 현재 진행해야 하는 숫자
    // containArr: 지금까지의 포함여부 배열 
    void comb(int num, int idx, boolean[] containArr){
        if(num == 5){
            candidateList.add(containArr.clone());
            return;
        }
        
        if(idx > n){ // 현재 살펴야 하는 수가 n보다 크면 종료
            return;
        }
        
        // 현재 인덱스를 넣고 재귀
        containArr[idx] = true;
        comb(num+1, idx+1, containArr);
        // 현재 인덱스 제거 후 재귀
        containArr[idx] = false;
        comb(num, idx+1, containArr);
    }
    
    void check(int[] line, boolean[] candidate,int candidateNum, int target){
        int cnt = 0;
        for(int num: line){
            if(candidate[num]) cnt++;
        }
        
        if(cnt != target) {
            isCandidate[candidateNum] = false;
        }
        return;
    }
}