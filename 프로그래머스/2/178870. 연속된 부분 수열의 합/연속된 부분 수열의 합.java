class Solution {
    public int[] solution(int[] sequence, int k) {
        // 시작 인덱스, 길이
        int resIdx = -1;
        int len = sequence.length + 1;
        
        // 인덱스를 돌며, 합을 더해감
        int startIdx = 0;
        int curIdx = 0;
        int sum = sequence[startIdx];
        // sum 이 k작으면, len을 늘리며 진행
        // sum 이 k이면, 지금까지의 len과 비교.
        // sum 이 k보다 크면, startIdx 이동(슬라이딩 윈도우로 합산 재활용)
        while(startIdx <= curIdx && startIdx < sequence.length){
            // System.out.println("---startIdx: "+startIdx+", curIdx: "+curIdx+"---");
            // System.out.println("sum: "+sum);

            if(sum < k) {
                curIdx++;
                if(curIdx >= sequence.length) break;
                sum += sequence[curIdx];
            }else if(sum == k){
                if(curIdx - startIdx + 1< len){
                    resIdx = startIdx;
                    len = curIdx - startIdx+1;
                    // System.out.println("resIdx: "+resIdx+", len: "+len+"으로 갱신");
                    // 가지치기
                    if(len == 1) return new int[]{resIdx, resIdx+len-1};
                }
                // startIdx 증가
                sum -= sequence[startIdx];
                startIdx++;
            }else{
                sum -= sequence[startIdx];
                startIdx++;
            }
        }
        
        return new int[]{resIdx, resIdx+len - 1};
    }
}