package com.jwetherell.algorithms.strings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringFunctionsTest {
    /*
    * Biểu diễn control folow của hàm dưới dạng tổng quát như sau:
    *
    * for (; A; F5()){
    *   F1()
    *   if (B || C)
    *       F2()
    *       if (D)
    *           F3()
    *       F4()
    *   F5()
    * }
    * F6()
    *
    * Để bao phủ điều kiện phức cho hàm trên, ta xét tổ hợp các giá trị True, False của các biểu thức điều kiện như sau:
    * | | A | B | C | D |
    * |1| F | _ | _ | _ |
    * |2| T | T | T | F |
    * |3| T | T | F | T |
    * |4| T | F | T | F |
    * |5| T | F | F | T |
    * |6| T | F | F | _ |
    * */

    @Test
    void reverseWordsUsingSplitWithAdditionalStorage1() {
        assertEquals("", StringFunctions.reverseWordsUsingStringTokenizerWithAdditionalStorage(""));
    }
    @Test
    void reverseWordsUsingSplitWithAdditionalStorage2() {
        assertEquals("hoale", StringFunctions.reverseWordsUsingStringTokenizerWithAdditionalStorage(" hoale"));
    }
    @Test
    void reverseWordsUsingSplitWithAdditionalStorage3() {
        assertEquals("le hoa", StringFunctions.reverseWordsUsingStringTokenizerWithAdditionalStorage("hoa le"));
    }
    @Test
    void reverseWordsUsingSplitWithAdditionalStorage4() {
        assertEquals("hoa thanh thi le", StringFunctions.reverseWordsUsingStringTokenizerWithAdditionalStorage("le thi thanh hoa"));
    }
    @Test
    void reverseWordsUsingSplitWithAdditionalStorage5() {
        assertEquals("cute hoa", StringFunctions.reverseWordsUsingStringTokenizerWithAdditionalStorage("hoa cute"));
    }
    @Test
    void reverseWordsUsingSplitWithAdditionalStorage6() {
        assertEquals("le", StringFunctions.reverseWordsUsingStringTokenizerWithAdditionalStorage("le"));
    }

}