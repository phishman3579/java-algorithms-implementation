package com.jwetherell.algorithms.numbers.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import com.jwetherell.algorithms.numbers.Integers;
import com.jwetherell.algorithms.numbers.Longs;

public class Numbers {

    private static final Random RANDOM = new Random();

    @Test
    public void testPowerOf2() {
        int is = 256;
        int isNot = 1673;

        boolean isPowOf2is = Integers.powerOfTwoUsingBits(is);
        boolean isPowOf2isNot = Integers.powerOfTwoUsingBits(isNot);
        assertTrue("Is power of 2 using bits error", isPowOf2is);
        assertFalse("Is power of 2 using bits error", isPowOf2isNot);

        isPowOf2is = Integers.powerOfTwoUsingLog(is);
        isPowOf2isNot = Integers.powerOfTwoUsingLog(isNot);
        assertTrue("Is power of 2 using logarithm error", isPowOf2is);
        assertFalse("Is power of 2 using logarithm error", isPowOf2isNot);

        isPowOf2is = Integers.powerOfTwoUsingLoop(is);
        isPowOf2isNot = Integers.powerOfTwoUsingLoop(isNot);
        assertTrue("Is power of 2 using loops error", isPowOf2is);
        assertFalse("Is power of 2 using loops error", isPowOf2isNot);

        isPowOf2is = Integers.powerOfTwoUsingRecursion(is);
        isPowOf2isNot = Integers.powerOfTwoUsingRecursion(isNot);
        assertTrue("Is power of 2 using recursion error", isPowOf2is);
        assertFalse("Is power of 2 using recursion error", isPowOf2isNot);
    }

    @Test
    public void testIntegerToBinaryString() {
        int a = Math.abs(RANDOM.nextInt());
        String check = Integer.toBinaryString(a);

        String result1 = Integers.toBinaryUsingBigDecimal(a);
        assertTrue("toBinary using BigDecimal error. random="+a+" expected=\n"+check+"\ngot=\n"+result1+"\n", (check.equals(result1)));

        String result2 = Integers.toBinaryUsingDivideAndDouble(a);
        assertTrue("toBinary using BigDecimal error. random="+a+" expected=\n"+check+"\ngot="+result2+"\n", (check.equals(result2)));

        String result3 = Integers.toBinaryUsingDivideAndModulus(a);
        assertTrue("toBinary using BigDecimal error. random="+a+" expected=\n"+check+"\ngot="+result3+"\n", (check.equals(result3)));

        String result4 = Integers.toBinaryUsingShiftsAndModulus(a);
        assertTrue("toBinary using BigDecimal error. random="+a+" expected=\n"+check+"\ngot="+result4+"\n", (check.equals(result4)));
    }

    @Test
    public void testLongToBinaryString() {
        long a = Math.abs(RANDOM.nextLong());
        String check = Long.toBinaryString(a);

        String result1 = Longs.toBinaryUsingBigDecimal(a);
        assertTrue("toBinary using BigDecimal error. random="+a+" expected=\n"+check+"\ngot=\n"+result1+"\n", (check.equals(result1)));

        String result2 = Longs.toBinaryUsingDivideAndModulus(a);
        assertTrue("toBinary using BigDecimal error. random="+a+" expected=\n"+check+"\ngot=\n"+result2+"\n", (check.equals(result2)));

        String result3 = Longs.toBinaryUsingShiftsAndModulus(a);
        assertTrue("toBinary using BigDecimal error. random="+a+" expected=\n"+check+"\ngot=\n"+result3+"\n", (check.equals(result3)));
    }

    @Test
    public void testToEnglish() {
        int a = -1001; 
        String check = "negative one-thousand one";
        String english = Integers.toEnglish(a);
        assertTrue("toEnglish error. a="+a+" expected="+check+" got="+english, (check.equals(english)));

        a = -1;
        check = "negative one";
        english = Integers.toEnglish(a);
        assertTrue("toEnglish error. a="+a+" expected="+check+" got="+english, (check.equals(english)));

        a = 0;
        check = "zero";
        english = Integers.toEnglish(a);
        assertTrue("toEnglish error. a="+a+" expected="+check+" got="+english, (check.equals(english)));

        a = 199;
        check = "one-hundred ninety-nine";
        english = Integers.toEnglish(a);
        assertTrue("toEnglish error. a="+a+" expected="+check+" got="+english, (check.equals(english)));

        a = Integer.MAX_VALUE; // 2,147,483,647
        check = "two-billion one-hundred forty-seven-million four-hundred eighty-three-thousand six-hundred forty-seven";
        english = Integers.toEnglish(a);
        assertTrue("toEnglish error. a="+a+" expected="+check+" got="+english, (check.equals(english)));

        a = Integer.MIN_VALUE+1; // -2,147,483,647
        check = "negative two-billion one-hundred forty-seven-million four-hundred eighty-three-thousand six-hundred forty-seven";
        english = Integers.toEnglish(a);
        assertTrue("toEnglish error. a="+a+" expected="+check+" got="+english, (check.equals(english)));
    }
}
