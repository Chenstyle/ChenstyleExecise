package com.example.exercise.base;

import java.util.HashSet;
import java.util.Objects;

/**
 * hashCode主要用于提升查询效率提高哈希表性能，来确定在散列结构中对象的存储地址
 * 重写equals()必须重写hashCode()
 * 哈希存储结构中，添加元素重复性校验的标准就是先检查hashCode值，后判断equals()
 * 两个对象equals()相等，hashcode()必定相等
 * 两个对象hashcode()不等，equals()必定也不等
 * 两个对象hashcode()相等，对象不一定相等，需要通过equals()进一步判断。
 * ————————————————
 * 版权声明：本文为CSDN博主「LarsCheng」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/qq_33619378/article/details/92661494
 *
 * @auther to Chenstyle
 * @time 2020-1-21 10:05
 */
public class HashCodeAndEquals {

    public static void main(String[] args) {
        HashSet set1 = new HashSet();
        set1.add("1");
        set1.add("1");

        for (Object a : set1) {
            System.out.println(a);
        }

        HashSet set2 = new HashSet();
        Person p1 = new Person("wang");
        Person p2 = new Person("wang");
        set2.add(p1);
        set2.add(p2);

        for (Object a : set2) {
            System.out.println(a);
        }
    }

    static class Person {
        private String mName;

        Person(String name) {
            mName = name;
        }

        @Override
        public boolean equals(Object o) {
            System.out.println("calculate equals");
            if (!(o instanceof Person)) {
                return false;
            }

            if (o == this) {
                return true;
            }

            Person person = (Person) o;
            return person.mName.equals(mName);
        }

        @Override
        public int hashCode() {
            System.out.println("check person hash.");
            return Objects.hash(mName);
        }

        @Override
        public String toString() {
            return "Person { name = \"" + mName + "\"}";
        }
    }
}
