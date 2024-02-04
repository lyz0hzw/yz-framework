package org.learn.framework.util;

import java.util.*;

/**
 * 快速构建二元组、三元组、List、Map工具类
 */
public class CollectionUtil {

    private CollectionUtil() {
    }

    public static <X, Y> Binary<X, Y> build(X x, Y y) {
        Binary<X, Y> binary = new Binary<>();
        binary.setX(x);
        binary.setY(y);
        return binary;
    }

    public static <X, Y, Z> Triples<X, Y, Z> build(X x, Y y, Z z) {
        Triples<X, Y, Z> triples = new Triples<>();
        triples.setX(x);
        triples.setY(y);
        triples.setZ(z);
        return triples;
    }

    public static <T> List<T> buildList(Class<T> tClass, T... values) {
        return new ArrayList<>(Arrays.asList(values));
    }

    public static List<String> buildStringList(String... values){
        return new ArrayList<>(Arrays.asList(values));
    }

    public static <T, V> Map<T, V> buildMap(Class<T> tClass, Class<V> vClass, Object... values) {
        int size = values.length / 2;
        Map<T, V> map = new HashMap<>(size);
        Object[] t = new Object[size];
        Object[] v = new Object[size];
        for (int i = 0; i < values.length; i++) {
            if (i % 2 == 0) {
                t[i / 2] = values[i];
            } else {
                v[i / 2] = values[i];
            }
        }
        for (int i = 0; i < size; i++) {
            map.put((T) t[i], (V) v[i]);
        }
        return map;
    }

    public static class Binary<X, Y> {
        private X x;
        private Y y;

        public X getX() {
            return x;
        }

        public void setX(X x) {
            this.x = x;
        }

        public Y getY() {
            return y;
        }

        public void setY(Y y) {
            this.y = y;
        }

        @Override
        public String toString() {
            return "Binary{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Binary<?, ?> binary = (Binary<?, ?>) o;
            return Objects.equals(x, binary.x) && Objects.equals(y, binary.y);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public static class Triples<X, Y, Z> {
        private X x;
        private Y y;
        private Z z;

        public X getX() {
            return x;
        }

        public void setX(X x) {
            this.x = x;
        }

        public Y getY() {
            return y;
        }

        public void setY(Y y) {
            this.y = y;
        }

        public Z getZ() {
            return z;
        }

        public void setZ(Z z) {
            this.z = z;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Triples<?, ?, ?> triples = (Triples<?, ?, ?>) o;
            return Objects.equals(x, triples.x) && Objects.equals(y, triples.y) && Objects.equals(z, triples.z);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }

        @Override
        public String toString() {
            return "Triples{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    '}';
        }
    }
}
