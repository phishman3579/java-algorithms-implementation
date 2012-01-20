package com.jwetherell.algorithms.data_structures;


/**
 * Matrx. This Matrix is designed to be more efficient in cache.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Matrix {

    private int rows = 0;
    private int cols = 0;
    private int[] matrix = null;
    
    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.matrix = new int[rows*cols];
    }

    private int getIndex(int row, int col) {
        if (row==0) return col;
        return ((row*cols)+col);
    }
    
    public int get(int row, int col) {
        return matrix[getIndex(row,col)];
    }

    public int[] getRow(int row) {
        int[] result = new int[cols];
        for (int c=0; c<cols; c++) {
            result[c] = this.get(row, c); 
        }
        return result;
    }
    
    public int[] getColumn(int col) {
        int[] result = new int[rows];
        for (int r=0; r<rows; r++) {
            result[r] = this.get(r, col); 
        }
        return result;
    }
    
    public void set(int row, int col, int value) {
        matrix[getIndex(row,col)] = value;
    }
    
    public Matrix add(Matrix input) {
        Matrix output = new Matrix(this.rows,this.cols);
        if ((this.cols!=input.cols)||(this.rows!=input.rows)) return output;
        
        for (int r=0; r<output.rows; r++) {
            for (int c=0; c<output.cols; c++) {
                for (int i=0; i<cols; i++) {
                    int m1 = this.get(r, c);
                    int m2 = input.get(r, c);
                    output.set(r, c, m1+m2); 
                }
            }
        }
        return output;
    }
    
    public Matrix subtract(Matrix input) {
        Matrix output = new Matrix(this.rows,this.cols);
        if ((this.cols!=input.cols)||(this.rows!=input.rows)) return output;
        
        for (int r=0; r<output.rows; r++) {
            for (int c=0; c<output.cols; c++) {
                for (int i=0; i<cols; i++) {
                    int m1 = this.get(r, c);
                    int m2 = input.get(r, c);
                    output.set(r, c, m1-m2); 
                }
            }
        }
        return output;
    }
    
    public Matrix multiply(Matrix input) {
        Matrix output = new Matrix(this.rows,input.cols);
        if (this.cols != input.rows) return output;
        
        for (int r=0; r<output.rows; r++) {
            for (int c=0; c<output.cols; c++) {
                int[] row = getRow(r);
                int[] column = input.getColumn(c);
                int result = 0;
                for (int i=0; i<cols; i++) {
                    result += row[i]*column[i]; 
                }
                output.set(r, c, result);
            }
        }
        return output;
    }

    public void copy(Matrix m) {
        for (int r=0; r<m.rows; r++) {
            for (int c=0; c<m.cols; c++) {
                set(r, c, m.get(r, c));
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Matrix:\n");
        for (int r=0; r<rows; r++) {
            builder.append("row=[").append(r).append("] ");
            for (int c=0; c<cols; c++) {
                builder.append(this.get(r,c)).append("\t");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
