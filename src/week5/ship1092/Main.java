package week5.ship1092;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        long start = System.nanoTime();
        FileInputStream file = new FileInputStream("./res/baekjoon/ship.txt");
        BufferedReader in = new BufferedReader(new InputStreamReader(file));
//        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        // 크레인의 수
        int n = Integer.parseInt(st.nextToken());
        // 크레인의 무게 제한
        LinkedList<Integer> crain = new LinkedList<>();

        st = new StringTokenizer(in.readLine());
        for (int x = 0; x < n; x++) {
            crain.offer(Integer.parseInt(st.nextToken()));
        }

        Collections.sort(crain, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });

//        System.out.println(crain);
        // 컨테이너의 갯수
        st = new StringTokenizer(in.readLine());
        int k = Integer.parseInt(st.nextToken());

        LinkedList<Integer> container = new LinkedList<>();

        st = new StringTokenizer(in.readLine());
        for(int y = 0; y < k; y++) {
            container.offer(Integer.parseInt(st.nextToken()));
        }

        Collections.sort(container, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });

//        System.out.println(container);
        // 제일 큰 크레인으로도 옮길 수 없다면 모든 박스는 배로 옮기는 것은 불가능
        if( container.getFirst() > crain.getFirst() ) {
            System.out.println(-1);
        }
        // 그렇지 않으면 옯길 수 있음
        else {
            dfs(0, crain, container);
        }
        long end = System.nanoTime();
        System.out.println( ( end - start ) / 1_000_000_000 + "s");
    }

    private static void dfs(int cnt, LinkedList<Integer> crain,LinkedList<Integer> container) {
        // 컨테이너의 박스가 없다면 종료
        if( container.size() == 0 ) {
            System.out.println(cnt);
            return;
        }

        int c = 0;  // 크레인의 순서
        Iterator<Integer> iterator = container.iterator();
        while ( iterator.hasNext() ) {
            int tmp = iterator.next();
            if( crain.get(c) >=  tmp) {
//                System.out.println(c + " <-- 크레인 index | 컨테이너 index --> " + tmp);
                iterator.remove(); // 컨테치너에서 제거
                c++; // 다음 크레인 이동
            }
            // 모든 크레인을 사용했으면 종료
            if( c == crain.size()) {
                break;
            }
        }
//        System.out.println(container);
        dfs(cnt + 1, crain, container);
    }
}
