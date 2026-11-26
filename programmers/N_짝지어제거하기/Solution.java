package N_짝지어제거하기;

import java.util.*;

class Solution
{
    public int solution(String s)
    {
        int answer = -1;
        String front = "";
        String back = "";
        
        for(int i = 0; i < s.len-1; i++){
            if(s.charAt(i) == s.charAt(i+1)){
                front = s.slice(0, i);
                back = s.slice(i+2,s.len);
                s = front + back;
                if(i>2) i -= 2;
            }
        }
        System.out.println("Hello Java");
        return answer;
    }
}