import java.util.*;

class Solution {
    Map<String, List<String>> map = new HashMap<>();
    List<String> path = new ArrayList<>();
    int ticketNum;
    
    public String[] solution(String[][] tickets) {
        String[] answer;
        ticketNum = tickets.length;
        
        // key: 출발 도시, values: 도착 도시 list
        for(String[] ticket: tickets){
            map.putIfAbsent(ticket[0], new ArrayList<>());
            map.get(ticket[0]).add(ticket[1]); // 현재 도시 저장
        }
        
        // dfs 중 최초 발견 시 바로 종료하기 위한 정렬
        for(List<String> arrivals : map.values()) Collections.sort(arrivals);
        
        path.add("ICN");
        dfs("ICN");
        
        return path.toArray(String[]::new);
    }
    
    
    boolean dfs(String depart){
        // 기저조건: path의 길이가 티켓 수 + 1인 경우
        if(path.size() == ticketNum + 1) return true;
        
        // 만약 depart에서 출발하는 티켓이 없는 경우 false
        List<String> arrivals = map.get(depart);
        if(arrivals == null) return false;
        
        for(int i = 0; i < arrivals.size(); i++){
            String arrival = arrivals.remove(i);
            path.add(arrival);
            
            if(dfs(arrival)) return true; // 성공이면 바로 종료
            
            // 경로가 불가능하다면, 빼고 다음 티켓
            path.remove(path.size()-1);
            arrivals.add(i,arrival);
        }
        
        return false;
    }
}