package com.jwetherell.algorithms.strings;

public class StringFunctions {
    
    private static final char SPACE = ' ';
    
    public static final String reverseWithStringConcat(String string) {
        String output = new String();
        for (int i=(string.length()-1); i>=0; i--) {
            output += (string.charAt(i));
        }
        return output;
    }
    
    public static final String reverseWithStringBuilder(String string) {
        StringBuilder builder = new StringBuilder();
        for (int i=(string.length()-1); i>=0; i--) {
            builder.append(string.charAt(i));
        }
        return builder.toString();
    }
    
    public static final String reverseWithStringBuilderBuiltinMethod(String string) {
        StringBuilder builder = new StringBuilder(string);
        return builder.reverse().toString();
    }
    
    public static final String reverseWithSwaps(String string) {
        char[] array = string.toCharArray();
        int length = array.length-1;
        int half = Math.round(array.length/2);
        char c;
        for (int i=length; i>=half; i--) {
            c = array[length-i];
            array[length-i] = array[i];
            array[i] = c; 
        }
        return String.valueOf(array);
    }
    
    public static final String reverseWordsWithAdditionalStorage(String string) {
        StringBuilder builder = new StringBuilder();
        
        char c = 0;
        int index = 0;
        int last = string.length();
        int length = string.length()-1;
        StringBuilder temp = new StringBuilder();
        for (int i=length; i>=0; i--) {
            c = string.charAt(i);
            if (c == SPACE || i==0) {
                index = (i==0)?0:i+1;
                temp.append(string.substring(index, last));
                if (index!=0) temp.append(c);
                builder.append(temp);
                temp.delete(0, temp.length());
                last = i;
            }
        }
        
        return builder.toString();
    }
    
    public static final String reverseWordsInPlace(String string) {
        char[] chars = string.toCharArray();
        
        int lengthI = 0;
        int lastI = 0;
        int lengthJ = 0;
        int lastJ = chars.length-1;
        
        int i = 0;
        char iChar = 0;
        char jChar = 0;
        while (i<chars.length && i<=lastJ) {
            iChar = chars[i];
            if (iChar == SPACE) {
                lengthI = i-lastI;
                for (int j=lastJ; j>=i; j--) {
                    jChar = chars[j];
                    if (jChar == SPACE) {
                        lengthJ = lastJ-j;
                        swapWords(lastI, i-1, j+1, lastJ, chars);
                        lastJ = lastJ-lengthI-1;
                        break;
                    }
                }
                lastI = lastI+lengthJ+1;
                i = lastI;
            } else {
                i++;
            }
        }
        
        return String.valueOf(chars);
    }
    
    private static final void swapWords(int startA, int endA, int startB, int endB, char[] array) {
        int lengthA = endA-startA+1;
        int lengthB = endB-startB+1;
        
        int length = lengthA;
        if (lengthA>lengthB) length = lengthB;

        int indexA = 0;
        int indexB = 0;
        char c = 0;
        for (int i=0; i<length; i++) {
            indexA = startA+i;
            indexB = startB+i;
            
            c = array[indexB];
            array[indexB] = array[indexA];
            array[indexA] = c;
        }

        if (lengthB>lengthA) {
            length = lengthB-lengthA;
            int end = 0;
            for (int i=0; i<length; i++) {
                end = endB-((length-1)-i);
                c = array[end];
                shiftRight(endA+i,end,array);
                array[endA+1+i] = c;
            }
        } else if (lengthA>lengthB) {
            length = lengthA-lengthB;
            for (int i=0; i<length; i++) {
                c = array[endA];
                shiftLeft(endA,endB,array);
                array[endB+i] = c;
            }
        }
    }
    
    private static final void shiftRight(int start, int end, char[] array) {
        for (int i=end; i>start; i--) {
            array[i] = array[i-1];
        }
    }
    
    private static final void shiftLeft(int start, int end, char[] array) {
        for (int i=start; i<end; i++) {
            array[i] = array[i+1];
        }
    }
    
    public static final boolean isPalindromeWithAdditionalStorage(String string) {
        String reversed = new StringBuilder(string).reverse().toString();
        return string.equals(reversed);
    }
    
    public static final boolean isPalindromeInPlace(String string) {
        char[] array = string.toCharArray();
        int length = array.length-1;
        int half = Math.round(array.length/2);
        char a,b;
        for (int i=length; i>=half; i--) {
            a = array[length-i];
            b = array[i];
            if (a != b) return false;
        }
        return true;
    }

}
