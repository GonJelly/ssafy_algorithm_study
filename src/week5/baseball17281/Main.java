package week5.baseball17281;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static int n, out, attacker, max;
    private static int[] position;
    private static int[][] play;

    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
        FileInputStream file = new FileInputStream("./res/baekjoon/baseball.txt");
        BufferedReader in = new BufferedReader(new InputStreamReader(file));
//        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(in.readLine());// 이닝의 수
        position = new int[5];              // 1루, 2루, 3루에 선수가 진출했는지 확인하는 배열
        attacker = 1;                       // 타자 순서
        out = 0;                            // 아웃 횟수
//        boolean[] use = new boolean[10];    // 플레이어 선출 여부
        int[] player = new int[10];         // 경기를 뛸 타자 순서
        play = new int[n+1][10];            // 각 이닝마다 선수가 취할수 있는 플레이 저장
//        use[1] = true;                      // 1번선수는 항상 4번차자이기에 미리 선출

        for(int y = 1; y <= n; y++) {
            StringTokenizer st = new StringTokenizer(in.readLine());
            for(int x = 1; x < 10; x++) {
                play[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        getPlayer(1,0,player);
        System.out.println(max);
        long end = System.nanoTime();
        System.out.println( ( end - start ) / 1_000 + "ms");
    }

    private static void getPlayer(int cnt,int flag, int[] player) {
        // 경기를 뛸 선수가 정해졌으면 경기 시작
        if( cnt == 10 ) {
//            System.out.println(Arrays.toString(player));
            game(player);   // 경기 시작
            return;
        }
        // 4번 타자 정하기 ( 1번 선수 )
        if( cnt == 4 ) {
            player[cnt] = 1;
            getPlayer( cnt + 1,flag | 1<<1 , player);
            return;
        }
        // 4번 타자 이외 타자순서 정하기
        for(int x = 2; x < 10; x++) {
            if( (flag & 1<<x ) != 0 ) continue;;
            // 아직 배치하지 않은 선수라면 추가
            player[cnt] = x;
            getPlayer(cnt + 1,flag | 1<<x ,player);
        }
    }

    private static void game(int[] player) {

        int score = 0;      // 처음 게임 시작 점수
        attacker = 1;       // 처음 타자 순서

        for(int i = 1; i <= n; i++) {
//            System.out.println(Arrays.toString(play[i]));
            out = 0;                // 아웃횟수 초기화
            position = new int[5];  // 도루 초기화
            while( out != 3) {      // 3번 아웃되면 다음 이닝으로
                int current = player[attacker]; // 다음선수 선정
                position[0] = current;          // 타자 안착
                switch ( play[i][current] ) {
                    case 0:
                        position[0] = 0;    // 타자 아웃
                        out++;              // 아웃증가
//                        System.out.println(current + ": out");
                        break;
                    case 1:
                        // 1루 전진
                        score = getScore(1, score);
                        break;
                    case 2:
                        // 2루 전진
                        score = getScore(2, score);
                        break;
                    case 3:
                        // 3루 전진
                        score = getScore(3, score);
                        break;
                    case 4:
                        // 홈런 4루 전진 ( 1바퀴 )
                        score = getScore(4, score);
                        break;
                }
//                System.out.println(Arrays.toString(position));
//                System.out.println("타자 : " + attacker);
                // 다음 타자
                attacker++;
                // 다시 처음부터
                if( attacker > 9) attacker = 1; // 9번째 선수까지 플레이를 했으면 다시 1번째부터 시작
            }
        }
//        System.out.println(score);
        max = Math.max(max,score);

    }

    private static int getScore(int count, int score) {
        // 1루씩 전진
        for( int r = 0; r < count; r++) {
            for (int h = 4; h > 0; h--) {
                position[h] = position[h - 1];
            }
            position[0] = 0; // 타자도 전지하기 때문에 비어준다.
            // 홈에 도착한 선수가 있다면 점수 추가
            if( position[4] != 0) {
                score++;
            }
        }

        return score;
    }
}
