package com.jwetherell.algorithms.mathematics;
import com.jwetherell.algorithms.numbers.Complex;
import static com.jwetherell.algorithms.numbers.Complex.polar;

/**
 * Simple implementation of Cooley-Tukey FFT algorithm
 * Coefficients array size must be power of 2
 * @author Mateusz Cianciara <e.cianciara@gmail.com>
 */
public class FFT {
    //coefficients size must be power of 2
    public static void FFT(Complex[] coefficients) {
        int size = coefficients.length;
        if (size <= 1) return;
        Complex[] even = new Complex[size / 2];
        Complex[] odd = new Complex[size / 2];
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0) {
                even[i / 2] = coefficients[i];
            } else {
                odd[(i - 1) / 2] = coefficients[i];
            }
        }
        FFT(even);
        FFT(odd);
        for (int k = 0; k < size / 2; k++) {
            Complex t = polar(1.0, -2 * Math.PI * k / size).multiply(odd[k]);
            coefficients[k] = even[k].add(t);
            coefficients[k + size / 2] = even[k].sub(t);
        }
    }
}
