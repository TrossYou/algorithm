import java.util.*;

class Node{
    String str;
    int num;
    int idx;
    
    Node(String str, int num, int idx){
        this.str = str;
        this.num = num;
        this.idx = idx;
    }
}

class Solution {
    public int solution(String begin, String target, String[] words) {
        int answer = 0;
        boolean[] isDone = new boolean[words.length]; // 이미 사용한 단어인지 -> 루프 제거
        
        // target이 없는 경우를 처리하기 위해 target부터 시작해서 begin으로 가는 것
        Queue<Node> q = new LinkedList<>();
        // target인덱스 찾기 -> 없으면 Return 0
        int targetIdx = -1;
        for(int i = 0; i < words.length; i++){
            if(words[i].equals(target)){
                targetIdx = i;
                break;
            }
        }
        if(targetIdx == -1) return 0;
        q.add(new Node(target, 0,targetIdx));
        
        while(!q.isEmpty()){
            Node node = q.poll();
            isDone[node.idx] = true;
            if(check(begin, node.str)) return node.num+1; // 종료
            
            for(int i = 0; i < words.length; i++){
                // System.out.println("node: "+node.str+", word: "+words[i]);
                if(node.str.equals(words[i])) continue;
                if(!isDone[i] && check(node.str, words[i])){ // 조건 충족
                    q.add(new Node(words[i], node.num+1, i));
                    // System.out.println("word: "+words[i]+", node.num: "+(node.num+1)+"삽입");
                }    
            }
        }
        return answer;
    }
    
    public boolean check(String str1, String str2){
        int diffCnt = 0;
        int idx = 0;
        while(diffCnt <= 1 && idx < str1.length()){
            if(str1.charAt(idx) != str2.charAt(idx)) diffCnt++;
            idx++;
        }
        
        return diffCnt > 1 ? false : true;
    }
}