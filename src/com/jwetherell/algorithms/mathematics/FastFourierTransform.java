package com.jwetherell.algorithms.mathematics;

import com.jwetherell.algorithms.numbers.Complex;

/**
 * A fast Fourier transform (FFT) algorithm computes the discrete Fourier transform (DFT) of a sequence, or its inverse. 
 * Fourier analysis converts a signal from its original domain (often time or space) to a representation in the frequency 
 * domain and vice versa. An FFT rapidly computes such transformations by factorizing the DFT matrix into a product of 
 * sparse (mostly zero) factors.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Fast_Fourier_transform">Fast Fourier Transform (Wikipedia)</a>
 * <br>
 * @author Mateusz Cianciara <e.cianciara@gmail.com>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class FastFourierTransform {

    private FastFourierTransform() { }

    /** 
     * The Cooley–Tukey algorithm, named after J.W. Cooley and John Tukey, is the most common fast Fourier transform 
     * (FFT) algorithm. It re-expresses the discrete Fourier transform (DFT) of an arbitrary composite size N = N1N2 
     * in terms of N1 smaller DFTs of sizes N2, recursively, to reduce the computation time to O(N log N) for highly 
     * composite N (smooth numbers).
     * <p>
     * @see <a href="https://en.wikipedia.org/wiki/Cooley%E2%80%93Tukey_FFT_algorithm">Cooley–Tukey Algorithm (Wikipedia)</a>
     * <br>
     * @param coefficients size must be power of 2
     */
    public static void cooleyTukeyFFT(Complex[] coefficients) {
        final int size = coefficients.length;
        if (size <= 1) 
            return;

        final Complex[] even = new Complex[size / 2];
        final Complex[] odd = new Complex[size / 2];
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0) {
                even[i / 2] = coefficients[i];
            } else {
                odd[(i - 1) / 2] = coefficients[i];
            }
        }
        cooleyTukeyFFT(even);
        cooleyTukeyFFT(odd);
        for (int k = 0; k < size / 2; k++) {
            Complex t = Complex.polar(1.0, -2 * Math.PI * k / size).multiply(odd[k]);
            coefficients[k] = even[k].add(t);
            coefficients[k + size / 2] = even[k].sub(t);
        }
    }
}
