package com.jwetherell.algorithms.data_structures;

/**
 * Matrx. This Matrix is designed to be more efficient in cache. A matrix is a
 * rectangular array of numbers, symbols, or expressions.
 * 
 * http://en.wikipedia.org/wiki/Matrix_(mathematics)
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Matrix<T extends Number> {

    private int rows = 0;
    private int cols = 0;
    private T[] matrix = null;

    @SuppressWarnings("unchecked")
    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.matrix = (T[]) new Number[rows * cols];
    }

    private int getIndex(int row, int col) {
        if (row == 0) return col;
        return ((row * cols) + col);
    }

    public T get(int row, int col) {
        return matrix[getIndex(row, col)];
    }

    @SuppressWarnings("unchecked")
    public T[] getRow(int row) {
        T[] result = (T[]) new Number[cols];
        for (int c = 0; c < cols; c++) {
            result[c] = this.get(row, c);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public T[] getColumn(int col) {
        T[] result = (T[]) new Number[rows];
        for (int r = 0; r < rows; r++) {
            result[r] = this.get(r, col);
        }
        return result;
    }

    public void set(int row, int col, T value) {
        matrix[getIndex(row, col)] = value;
    }

    @SuppressWarnings("unchecked")
    public Matrix<T> add(Matrix<T> input) {
        Matrix<T> output = new Matrix<T>(this.rows, this.cols);
        if ((this.cols != input.cols) || (this.rows != input.rows)) return output;

        for (int r = 0; r < output.rows; r++) {
            for (int c = 0; c < output.cols; c++) {
                for (int i = 0; i < cols; i++) {
                    T m1 = this.get(r, c);
                    T m2 = input.get(r, c);
                    Long l = m1.longValue() + m2.longValue();
                    output.set(r, c, (T) l);
                }
            }
        }
        return output;
    }

    @SuppressWarnings("unchecked")
    public Matrix<T> subtract(Matrix<T> input) {
        Matrix<T> output = new Matrix<T>(this.rows, this.cols);
        if ((this.cols != input.cols) || (this.rows != input.rows)) return output;

        for (int r = 0; r < output.rows; r++) {
            for (int c = 0; c < output.cols; c++) {
                for (int i = 0; i < cols; i++) {
                    T m1 = this.get(r, c);
                    T m2 = input.get(r, c);
                    Long l = m1.longValue() - m2.longValue();
                    output.set(r, c, (T) l);
                }
            }
        }
        return output;
    }

    @SuppressWarnings("unchecked")
    public Matrix<T> multiply(Matrix<T> input) {
        Matrix<T> output = new Matrix<T>(this.rows, input.cols);
        if (this.cols != input.rows) return output;

        for (int r = 0; r < output.rows; r++) {
            for (int c = 0; c < output.cols; c++) {
                T[] row = getRow(r);
                T[] column = input.getColumn(c);
                Long result = 0l;
                for (int i = 0; i < cols; i++) {
                    Long l = row[i].longValue() * column[i].longValue();
                    result += l;
                }
                output.set(r, c, (T) result);
            }
        }
        return output;
    }

    public void copy(Matrix<T> m) {
        for (int r = 0; r < m.rows; r++) {
            for (int c = 0; c < m.cols; c++) {
                set(r, c, m.get(r, c));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Matrix:\n");
        for (int r = 0; r < rows; r++) {
            builder.append("row=[").append(r).append("] ");
            for (int c = 0; c < cols; c++) {
                builder.append(this.get(r, c)).append("\t");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
