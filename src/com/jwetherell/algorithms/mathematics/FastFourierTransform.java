package com.jwetherell.algorithms.mathematics;

/**
 * A fast Fourier transform (FFT) algorithm computes the discrete Fourier transform (DFT) of a sequence, or its inverse. 
 * Fourier analysis converts a signal from its original domain (often time or space) to a representation in the frequency 
 * domain and vice versa. An FFT rapidly computes such transformations by factorizing the DFT matrix into a product of 
 * sparse (mostly zero) factors.
 * <p>
 * http://en.wikipedia.org/wiki/Fast_Fourier_transform
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
     * http://en.wikipedia.org/wiki/Cooley%E2%80%93Tukey_FFT_algorithm
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

    public static final class Complex {

        public double real;
        public double imaginary;

        public Complex() {
            this.real = 0.0;
            this.imaginary = 0.0;
        }

        public Complex(double r, double i) {
            this.real = r;
            this.imaginary = i;
        }

        public Complex multiply(final Complex x) {
            final Complex copy = new Complex(this.real, this.imaginary);
            copy.real = this.real * x.real - this.imaginary * x.imaginary;
            copy.imaginary = this.imaginary * x.real + this.real * x.imaginary;
            return copy;
        }

        public Complex add(final Complex x) {
            final Complex copy = new Complex(this.real, this.imaginary);
            copy.real += x.real;
            copy.imaginary += x.imaginary;
            return copy;
        }

        public Complex sub(final Complex x) {
            final Complex copy = new Complex(this.real, this.imaginary);
            copy.real -= x.real;
            copy.imaginary -= x.imaginary;
            return copy;
        }

        public double abs() {
            return Math.sqrt(this.real * this.real + this.imaginary * this.imaginary);
        }

        public String toString() {
            return "(" + this.real + "," + this.imaginary + ")";
        }

        public static Complex polar(final double rho, final double theta) {
            return (new Complex(rho * Math.cos(theta), rho * Math.sin(theta)));
        }
    }
}
