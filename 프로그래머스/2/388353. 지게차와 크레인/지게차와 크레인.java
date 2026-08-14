import java.util.*;

class Solution {
    int answer,n,m;
    // 순서대로 상우하좌
    int[] dr = new int[]{-1, 0, 1, 0};
    int[] dc = new int[]{0, 1, 0, -1};
    
    boolean[][] isPossible;
    boolean[][] isDone;
    
    class Point{
        int r,c;
        Point(int r, int c){
            this.r = r;
            this.c = c;
        }
    }
    
    public int solution(String[] storage, String[] requests) {
        n = storage.length;
        m = storage[0].length();
        answer = n * m;
        
        // 외부 접근 가능한 좌표 저장 -> boolean[][]
        isPossible = new boolean[n][m];
        // 해결된 컨테이너 좌표 저장
        isDone = new boolean[n][m];
            
        // 접근 가능한 좌표 초기화
        Arrays.fill(isPossible[0], true);
        Arrays.fill(isPossible[n-1], true); 
        for(int i = 1; i < n-1; i++){
            isPossible[i][0] = true;
            isPossible[i][m-1] = true;
        }
        
        // 각 컨테이너 이름 별, 좌표 저장 -> hashmap<"A", List<point>>
        Map<Character, Queue<Point>> map = new HashMap<>();
        for(int i = 0; i < n; i++){
            String str = storage[i];
            for(int j = 0; j < m; j++){
                char word = str.charAt(j);
                map.putIfAbsent(word, new LinkedList<>());
                map.get(word).add(new Point(i, j));
            }
        }
        
        // Input이 1글자라면, 이름 리스트를 보며, 외부 접근 가능한 좌표인지 확인해서 제거 => 외부 접근 좌표 갱신
        // Input이 2글자라면, hashmap에서 아예 제거.
        for(String str : requests){
            char word = str.charAt(0);
            if(!map.containsKey(word)) {
                // System.out.println("현재 word: "+word+"없음");
                continue;
            }
            
            // System.out.println("--- "+word+"시작 ---");
            if(str.length() == 1){
                // System.out.println("[ A ]");
                Queue<Point> que = map.get(word);
                List<Point> toDelete = new ArrayList<>(); // 제거해야하는 좌표 모음
                
                int size = que.size();
                for(int i = 0; i < size; i++){
                    Point p = que.poll();
                    int r = p.r;
                    int c = p.c;
                    if(isPossible[r][c]){ // r,c 제거
                        toDelete.add(new Point(r, c));
                    }else{
                        // System.out.println("[1단계]"+r+", "+c+"에서 제거 불가능: 다시 추가");
                        que.add(p);
                    }
                }
                for(Point point: toDelete){
                    isDone[point.r][point.c] = true;
                    answer--;
                    // System.out.println(point.r+", "+point.c+"에서 제거함 -> answer: "+answer);
                    delete(point.r, point.c);
                }
            }else{
                // System.out.println("[ B ]");
                // isPossible 갱신
                Queue<Point> que = map.get(word);
                while(!que.isEmpty()){
                    Point p = que.poll();
                    int r = p.r;
                    int c = p.c;
                    // r,c 제거
                    isDone[r][c] = true;
                    answer--;
                    // System.out.println(r+", "+c+"에서 제거함 -> answer: "+answer);
                    if(isPossible[r][c]) delete(r,c); // 외각일 떄, isPossible만 개선
                }
                map.remove(word);
                // System.out.println(word+"키 제거");
            }
        }
        // 총 컨테이너 수 - 제거되는 수 계산
        return answer;
    }
    
    void delete(int r, int c){
        // System.out.println("delete: r"+r+", c"+c+"호출됨");
        for(int i = 0; i < 4; i++){
            int nr = r + dr[i];
            int nc = c + dc[i];
            if(nr >= 0 && nr < n && nc >= 0 && nc < m){ 
                if(isPossible[nr][nc]) continue;
                isPossible[nr][nc]  = true;

                if(isDone[nr][nc]){
                    // System.out.println(nr+", "+nc+"는 접근 불가능한 곳인데 이미 처리됨");
                    delete(nr, nc); // 외각 아니었는데 제거되었었으나 이제 외각이 됨.
                }
                // System.out.println(nr+", "+nc+": isPossible true로 변경");
            }
        }
        // System.out.println(r+", "+c+"에서 isPossible");
        // for(int i = 0; i < n; i++){
            // for(int j = 0; j < m; j++) System.out.printf(isPossible[i][j]+", ");
            // System.out.println();
        // }
    }
}