package org.example;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HomeWork {

    @Getter
    @Setter
    @AllArgsConstructor
    static class Knight implements Comparable<Knight> {
        Integer num;
        Knight defeatedBy;
        int defeatedByInt = 0;

        public String toStr() {
            return String.valueOf(num);
        }

        @Override
        public int compareTo(Knight o) {
            return num.compareTo(o.num);
        }

        @Override
        public String toString() {
            return "Knight{" +
                    "num=" + num +
                    ", defeatedByInt=" + defeatedByInt +
                    '}';
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Knight) {
                var o2 = (Knight) obj;
                return this.num.equals(o2.getNum());
            }
            return false;
        }
    }

    /**
     * <h1>Задание 1.</h1>
     * Решить задачу https://codeforces.com/contest/356/problem/A
     */
    @SneakyThrows
    public void championship(InputStream in, OutputStream out) {
        var bi = new BufferedReader(new InputStreamReader(in));
        var pw = new PrintWriter(out);

        var nums = bi.readLine().split("\\s+");
        var opCount = Integer.parseInt(nums[1]);
        var tree = new RedBlackTree<Knight>();

        IntStream.rangeClosed(1, Integer.parseInt(nums[0])).boxed().map(x -> new Knight(x, null, 0)).forEach(tree::add);
        var p = Pattern.compile("(\\d+) (\\d+) (\\d+)");
        for (int i = 1; i <= opCount; i++) {
            var s = p.matcher(bi.readLine());
            s.find();
            var winner = tree.key(new Knight(Integer.parseInt(s.group(3)), null, Integer.parseInt(s.group(3)))).key;

            for (int j = Integer.parseInt(s.group(1)); j <= Integer.parseInt(s.group(2)); j++) {
                var knight = tree.key(new Knight(j, null, 0)).key;
                if (isAlive(knight) && !winner.equals(knight)) {
                    knight.defeatedBy = winner;
                    knight.defeatedByInt = winner.getNum();
                }
            }
        }
        pw.print(tree.leftCurRight()
                .stream()
                .map(x -> x.defeatedByInt)
                .map(String::valueOf)
                .collect(Collectors.joining(" ")));
        pw.flush();
    }

    private boolean isAlive(Knight knight) {
        return null == knight.defeatedBy;
    }


}
