import java.util.*;

class Solution {
    public boolean solution(String[] phone_book) {
        int totalLen = phone_book.length;
        Arrays.sort(phone_book);
        
        // 사전순 정렬
        for(int i = 0; i < totalLen-1; i++){
            String str1 = phone_book[i];
            String str2 = phone_book[i+1];
            
            if(str1.length() < str2.length() && str1.equals(str2.substring(0, str1.length()))) return false;
        }
        
        return true;
    }
}