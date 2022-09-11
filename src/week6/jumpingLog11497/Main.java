package week6.jumpingLog11497;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    static int wood;

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(st.nextToken());       // 테스트 케이스의 갯수

        for(int t = 1; t <= T; t++) {

            wood = Integer.parseInt(in.readLine());     // 통나무의 갯수
            LinkedList<Integer> store = new LinkedList<>();

            // 내림차순으로 저장
            PriorityQueue<Integer> height = new PriorityQueue<Integer>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2 - o1;
                }
            });

            st = new StringTokenizer(in.readLine());
            for ( int i = 0; i < wood; i++) {
                height.offer(Integer.parseInt(st.nextToken()));
            }

            while( !height.isEmpty() ) {
                store.addFirst(height.poll());

                if( height.isEmpty() ) break;

                store.addLast(height.poll());
            }
//            System.out.println(store);
            sb.append(getLevel(store)).append("\n");
        }
        System.out.println(sb);
    }
    // 난이도를 구하는 함수
    private static int getLevel(LinkedList<Integer> store) {

        int dept = 0;                   // 높이차이
        int maxHeight =  -1;            // 가장높은 높이

        // 최대 높이를 구한다. ( 즉, 난이도 구하기 )
        for(int i = 0; i < wood; i++) {
            int tmp = i - 1 < 0 ? wood - 1 : i - 1;
            dept = Math.abs( store.get(tmp) - store.get(i) );
            if( dept > maxHeight ) {
                maxHeight = dept;
            }
        }

        return maxHeight;
    }
}
