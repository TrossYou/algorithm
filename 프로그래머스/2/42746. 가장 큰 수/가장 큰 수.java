import java.util.*;

class Solution {
    class StrNum{
        String originNum, quardNum = "";
        
        StrNum(int n){
            originNum = Integer.toString(n);
            for(int i = 0; i < 4; i++)
                quardNum += originNum;
        }
        
    }
    public String solution(int[] numbers) {
        String answer = "";
        StrNum[] strNumArr = new StrNum[numbers.length];
        for(int i = 0; i < numbers.length; i++) strNumArr[i] = new StrNum(numbers[i]);
        

        // 세배 한 문자열 사전순 정렬
        Arrays.sort(strNumArr, (o1, o2) -> o2.quardNum.compareTo(o1.quardNum));
        
        for(StrNum k : strNumArr) answer += k.originNum; // 문자열로 더해질 것
        return answer.charAt(0) == '0' ? "0" : answer;
    }
}