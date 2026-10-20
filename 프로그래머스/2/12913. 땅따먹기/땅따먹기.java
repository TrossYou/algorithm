class Solution {
    int solution(int[][] land) {
        int answer = 0;
        int row = land.length;
        int col = land[0].length;
        
        for(int r = 1; r < row; r++){
            for(int c = 0; c < 4; c++){
                int now = land[r][c];
                for(int i = 0; i < 4; i++){
                    if(c == i) continue;
                    now = Math.max(now, land[r][c] + land[r-1][i]);
                }
                land[r][c] = now;
            }
        }
        
        for(int i = 0; i < 4; i++) answer = Math.max(answer, land[row-1][i]);

        return answer;
    }
}

// 4N으로 각 행의 순위 저장
// 1   2   3   5
// 10 11  12  11
// 16 15  13  13