import java.util.*;

class Solution {
    Map<String, String> codeMap = new HashMap<>();
    
    class Music{
        int playTime;
        String title;
        String codes; // 인덱스 string
        String playCodes; // 실제 재생된 코드
        
        public Music(int playTime,String title, String codes){
            this.playTime = playTime;
            this.title = title;
            this.codes = codes;
            playCodes = "";
        }
        
        // 실제 플레이 된 코드 문자열. 
        public void setPlayCodes(){
            int len = codes.length(); // 노래 길이
                        
            if(playTime <= len){ // 실행시간이 노래 길이보다 짧으면 처음부터 거기까지
                playCodes = codes.substring(0, playTime);
            }else{
                int repeat = playTime / len; // 반복 재생된 횟수
                int leftLen = playTime % len; // 반복되고 남은 횟수
                for(int i = 0; i < repeat; i++) playCodes += codes;
                playCodes += codes.substring(0, leftLen);
            }
        }
        
    }
    
    public String solution(String m, String[] musicinfos) {
        //codeMap 세팅
        SetCodeMap();
        
        List<Music> musicList = new ArrayList<>();
        
        for(String info : musicinfos){
            String[] music = info.split(",");
            Music newMusic = new Music(getPlayTime(music[0], music[1]),music[2], getCodes(music[3]));
            newMusic.setPlayCodes();
            musicList.add(newMusic); // Music객체로 저장
        }    
        // musicList를 play시간 긴 순서로 정렬
        Collections.sort(musicList, (o1, o2) -> Integer.compare(o2.playTime, o1.playTime));
        
        Music tmp = musicList.get(0);
        
        String targetCode = getCodes(m);
        for(Music music: musicList){
            if(music.playTime < targetCode.length()) break; 
            if(music.playCodes.contains(targetCode)) return music.title;
        }        
        
        return "(None)";
    }

    void SetCodeMap(){ // 코드를 16진수로 바꾸기
        codeMap.put("C", "0");
        codeMap.put("C#", "1");
        codeMap.put("D", "2");
        codeMap.put("D#", "3");
        codeMap.put("E", "4");
        codeMap.put("F", "5");
        codeMap.put("F#", "6");
        codeMap.put("G", "7");
        codeMap.put("G#", "8");
        codeMap.put("A", "9");
        codeMap.put("A#", "A");
        codeMap.put("B", "B");
    }
    
    int getPlayTime(String startTime, String endTime){
        int startHH = Integer.parseInt(startTime.substring(0, 2));
        int endHH = Integer.parseInt(endTime.substring(0, 2));
        int startMM = Integer.parseInt(startTime.substring(3));
        int endMM = Integer.parseInt(endTime.substring(3));
        
        int hours = endHH - startHH;
        int mins = endMM - startMM;
        
        return hours*60 + mins;
    }
    
    String getCodes(String codeString){
        int idx = 0;
        int strLen = codeString.length(); // 코드 문자열 길이
        String res = "";
        
        String tmp = "";
        while(idx < strLen){
            tmp += codeString.charAt(idx++);
        
            if(idx < strLen && codeString.charAt(idx) == '#') tmp += codeString.charAt(idx++); // #도 같이
            res += codeMap.get(tmp); // 인덱스 번호로 코드 저장.
            tmp = ""; // tmp 초기화
        }
        
        return res;
    }
}