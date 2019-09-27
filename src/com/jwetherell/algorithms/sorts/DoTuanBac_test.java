  
 package com.jwetherell.algorithms.search;
        import org.junit.Test;
        import static org.junit.Assert.*;
        
        public class SearchTest {
      //Bai viet goc : 
      
      static public int arr[]={1,2,4,9,13,15};
    @Test
    public void UpperBound1() {
        assertEquals(1, UpperBound.upperBound(arr, 6,1));
    }
       public void UpperBound2() {
        assertEquals(5, UpperBound.upperBound(arr, 6,1));
    }
      public void UpperBound5() {
        assertEquals(1, UpperBound.upperBound(arr, 7,1));
    }
      public void UpperBound9() {
        assertEquals(2, UpperBound.upperBound(arr, 6,2));
    }
     public void UpperBound6() {
        assertEquals(1, UpperBound.upperBound(arr, -1,1));
    }
     public void UpperBound7() {
        assertEquals(1, UpperBound.upperBound(arr, 0,1));
    }
   
}
