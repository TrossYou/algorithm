class Solution {
    public int solution(int n) {
        final int MOD = 1_000_000_007;
        
        if(n == 1) return 1;
        if(n == 2) return 2;
        
        int first = 1;
        int second = 2;
    
        // 피보나치
        for(int i = 3; i <= n; i++){
            int cur = (first + second) % MOD;
            first = second;
            second = cur;
        }
        
        return second;
    }
}