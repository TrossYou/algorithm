class Solution {
    String[] board;
    
    public int solution(String[] board) {
        int oCnt = 0, xCnt = 0; // o,x개수 o == x 또는 o == x+1 이어야 함.
        this.board = board;
            
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(board[i].charAt(j) == 'O') oCnt++;
                else if(board[i].charAt(j) == 'X') xCnt++;
            }
        }
        
        if(!(oCnt == xCnt || oCnt == xCnt+1)) return 0; // cnt 수가 다르면 불가능함.
    
        
        // 이미 성공 했는지 확인       
        return check(oCnt == xCnt ? false : true) ? 1 : 0;
    }
    
    // turn 까지 했을 때, 유효한지
    // turn: true(O), false(X)
    boolean check(boolean turn){
        // o와 x의 성공 횟수 
        int oSuccessCnt = 0; 
        int xSuccessCnt = 0;
        
        // 대각선 검사
        char v = board[1].charAt(1);
        if(v != '.'){ // 가운데가 O,X인 경우만 검사
            // \ 대각선 검사
            if(board[0].charAt(0) == v && board[2].charAt(2) == v) {
                if(v == 'O') oSuccessCnt++;
                else xSuccessCnt++;
            }
            
            // / 대각선 검사
            if(board[0].charAt(2) == v && board[2].charAt(0) == v){
                if(v == 'O') oSuccessCnt++;
                else xSuccessCnt++;
            }
        }
        
        // 행 검사
        for(int r = 0; r < 3; r++){
            v = board[r].charAt(0);
            if(v == '.') continue;
            for(int c = 1; c < 3; c++){
                if(board[r].charAt(c) != v) break; // 해당 행은 불가능함
                if(c == 2){
                    if(v == 'O') oSuccessCnt++;
                    else xSuccessCnt++;  
                } 
            }
        }
        
        // 열 검사
        for(int c = 0; c < 3; c++){
            v = board[0].charAt(c);
            if(v == '.') continue;
            for(int r = 1; r < 3; r++){
                if(board[r].charAt(c) != v) break; // 해당 열은 불가능함
                if(r == 2){
                    if(v == 'O') oSuccessCnt++;
                    else xSuccessCnt++;  
                } 
            }
        }
        
        
        // successCnt가 turn 이 아닌데 1이상이면 틀림.  - successCnt가 turn이라면 마지막에 성공한거라 가능
        // successCnt가 2이상인 경우, 불가능
        // o가 마지막
        if((turn && xSuccessCnt >= 1) || (!turn && oSuccessCnt >=1) ) return false;
        
        return true;
    }
}

