package com.jwetherell.algorithms.mathematics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
*   Biểu diễn control folow của hàm dưới dạng tổng quát như sau:
*
*   if (A)
*       F1()
*   if (B)
*       F2()
*   if (C && D)
*       F3()
*   if (E)
*       F4()
*   while (F)
*       if (G)
*           F5()
*       F6()
*   F7()
*
*   Phương pháp bao phủ đường đi, ta có các đường đi như sau:
*
* 1. A -> F1
* 2. A -> B -> F2
* 3. A -> C,D -> F3
* 4. A -> B -> C,D -> E -> F4
* 5. A -> B -> C,D -> E -> F -> F7
* 6. A -> B -> C,D -> E -> F -> G -> F5 -> F6 -> F7
* 7. A -> B -> C,D -> E -> F -> G -> F6 -> F7
*
* Ở đây, em chỉ xét khả năng của vòng lặp là 0 lần và lớn hơn 0 lần theo pp kiểm thử đường đi biên trong
* */

class ModularTest {

    @Test
    void pow1() {
        assertThrows(IllegalArgumentException.class, () -> {
            Modular.pow(1, 1, 0);
        });
    }
    @Test
    void pow2() {
        assertThrows(IllegalArgumentException.class, () -> {
            Modular.pow(100, -19, 310);
        });
    }
    @Test
    void pow3() {
        assertThrows(IllegalArgumentException.class, () -> {
            Modular.pow(0, 0, 20);
        });
    }

    @Test
    void pow4() {

        assertEquals(0, Modular.pow(2, 1, 2));
    }
    @Test
    void pow5() {
        assertEquals(1, Modular.pow(1000, 0, 3));
    }
    @Test
    void pow6() {
        assertEquals(5 , Modular.pow(33, 7, 7));
    }
    @Test
    void pow7() {
        assertEquals(2 , Modular.pow(32, 8, 7));
    }
}