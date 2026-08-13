import java.util.*;

class Solution { 
    private int[][] arr; 
    
    public int[] solution(int[][] arr) {
        this.arr = arr;

        return compress(0, 0, arr.length);
    }
    
    int[] compress(int r, int c, int size){
         if(size == 1){ // size가 1까지 오면 현재 값 반환
             return arr[r][c] == 0 ? new int[]{1, 0} : new int[]{0, 1};
         }

        // 좌상단
        int[] res1 = compress(r, c, size/2);
        // 우상단 
        int[] res2 = compress(r, c+size/2, size/2);
        // 좌하단
        int[] res3 = compress(r+size/2, c, size/2);
        // 우하단
        int[] res4 = compress(r+size/2, c+size/2, size/2);
        
        int[] base1 = new int[]{1, 0};
        int[] base2 = new int[]{0, 1};
        
        int[] result = new int[2];
        // 4개 병합가능
        if((Arrays.equals(base1, res1) && Arrays.equals(base1, res2) && Arrays.equals(base1, res3) && Arrays.equals(base1, res4)) || (Arrays.equals(base2, res1) && Arrays.equals(base2, res2) && Arrays.equals(base2, res3) && Arrays.equals(base2, res4))){
            result[0] = res1[0];
            result[1] = res1[1];
        }else{ // 불가능
            result[0] = res1[0]+res2[0]+res3[0]+res4[0];
            result[1] = res1[1]+res2[1]+res3[1]+res4[1];
        }
        return result;
    }
}