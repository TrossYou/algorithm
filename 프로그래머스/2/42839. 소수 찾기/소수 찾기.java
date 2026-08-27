import java.util.*;

class Solution {
    private int answer = 0;
    private int[] numArr;
    private int numLength;
    private Set<Integer> numSet;
    
    
    public int solution(String numbers){
        numLength = numbers.length();
        numArr = new int[numLength];
        numSet = new HashSet<>();
        // 소수: 1의자리는 홀수

        // 정렬을 해볼까? -> 숫자 작은 것 부터 하는게 의미가 있..? => 이미 계산한 수..가 의미가 있을까? 없을듯..
        // 해시맵? -> 0: 1, 1:2 -> 중복을 막을 수 있지 않을까? => 너무 빡센데..마냥 중복이 아님
        // 소수인 지 직접 확인? 뭔가 누적할 수 없..을 듯..? 
        for(int i = 0; i < numLength; i++) numArr[i] = numbers.charAt(i) - '0';
        
        // 모든 순열 만들기
        makeNum(new boolean[numLength], 0);
        
        return answer;
    }
    
    private void makeNum(boolean[] isContain, int num){ 
        // 계산하지 않은 것들만 추가 확인
        if(!numSet.contains(num)){
            answer += isDec(num);
            numSet.add(num);
        }
        
        for(int i = 0; i < numLength; i++){
            if(isContain[i]) continue;
            
            // 현재 것을 num에 추가
            isContain[i] = true;
            makeNum(isContain, num*10 + numArr[i]);
            
            // 현재 것 넘어가기
            isContain[i] = false;
        }
        return;
    }
    
    private int isDec(int num){
        if(num == 0 || num == 1) return 0;
        for(int i = 2; i < num; i++){ 
            if( num % i == 0 ) {
                return 0; // 다른 약수가 존재
            }
        }
        
        return 1; // 소수임
    }
}