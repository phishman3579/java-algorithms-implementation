package com.jwetherell.algorithms.numbers;

/**
 * A complex number is a number that can be expressed in the form a + bi, where a and b are real numbers and i is the 
 * imaginary unit, satisfying the equation i2 = −1.[1] In this expression, a is the real part and b is the imaginary 
 * part of the complex number. If z=a+bi z=a+bi, then Rz=a, Iz=b.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Complex_number">Complex Number (Wikipedia)</a>
 * <br>
 * @author Mateusz Cianciara <e.cianciara@gmail.com>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Complex {

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
