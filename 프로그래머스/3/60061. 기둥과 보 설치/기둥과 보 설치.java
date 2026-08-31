import java.util.*;

class Solution {
    boolean[][] pillar; // 기둥 설치 여부
    boolean[][] beam;   // 보 설치 여부
    int n;

    public int[][] solution(int n, int[][] build_frame) {
        this.n = n;
        pillar = new boolean[n+1][n+1];
        beam = new boolean[n+1][n+1];

        for(int[] query : build_frame){
            int x = query[0];
            int y = query[1];
            int type = query[2];
            int action = query[3];

            if(action == 1){ // 설치 로직
                if(canInstall(x, y, type)){
                    if(type == 0) pillar[x][y] = true;
                    else beam[x][y] = true;
                }
            }else if(action == 0){ // 제거 로직: 일단 제거 → 전체 검증 → 실패 시 롤백
                if(type == 0) pillar[x][y] = false;
                else beam[x][y] = false;

                if(!allValid()){
                    if(type == 0) pillar[x][y] = true;
                    else beam[x][y] = true;
                }
            }
        }

        List<int[]> answer = new ArrayList<>();
        for(int i = 0; i <= n; i++){
            for(int j = 0; j <= n; j++){
                if(pillar[i][j]) answer.add(new int[]{i, j, 0});
                if(beam[i][j])   answer.add(new int[]{i, j, 1});
            }
        }

        answer.sort((o1, o2) -> {
            if(o1[0] != o2[0]) return Integer.compare(o1[0], o2[0]);
            if(o1[1] != o2[1]) return Integer.compare(o1[1], o2[1]);
            return Integer.compare(o1[2], o2[2]);
        });

        return answer.toArray(new int[0][]);
    }

    // 기존 로직 유지. map 접근만 pillar/beam으로 바꾸고, 기둥 조건 하나 추가
    boolean canInstall(int x, int y, int type){
        // 기둥: 바닥이거나, 아래에 기둥이 있거나, 보의 한쪽 끝 위
        if(type == 0){
            return y == 0
                || (inRange(x, y-1) && pillar[x][y-1])
                || (inRange(x-1, y) && beam[x-1][y])
                || beam[x][y]; // (x,y)에서 시작하는 보의 왼쪽 끝 위 — 기존 코드에 누락됐던 조건
        }
        // 보: 한쪽 끝 아래에 기둥이 있거나, 양쪽에 보가 있거나
        else if(type == 1){
            return (inRange(x, y-1) && pillar[x][y-1])
                || (inRange(x+1, y-1) && pillar[x+1][y-1])
                || (inRange(x-1, y) && beam[x-1][y] && inRange(x+1, y) && beam[x+1][y]);
        }
        return false;
    }

    // 설치된 모든 구조물이 여전히 설치 조건을 만족하는지 전수 검사
    boolean allValid(){
        for(int i = 0; i <= n; i++){
            for(int j = 0; j <= n; j++){
                if(pillar[i][j] && !canInstall(i, j, 0)) return false;
                if(beam[i][j] && !canInstall(i, j, 1)) return false;
            }
        }
        return true;
    }

    boolean inRange(int x, int y){
        return x >= 0 && x <= n && y >= 0 && y <= n;
    }
}