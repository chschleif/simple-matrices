import java.util.Random;

public class Matrix {

    private Fraction[][] cells;
    private int width = 0;
    private int height = 0;

    public Fraction[][] getCells() {
        return cells;
    }

    public void setCells(Fraction[][] cells) {
        this.cells = cells;
        this.width = cells[0].length;
        this.height = cells.length;
    }

    public Matrix(Fraction[][] cells) {
        this.setCells(cells);
    }

    public static Matrix generateIdentity(int size) {
        Fraction[][] output = new Fraction[size][size];
        for(int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                output[y][x] = Fraction.fromInt((y == x) ? 1 : 0);
            }
        }
        return new Matrix(output);
    }

    public Matrix appendColumn(Fraction[] columnData) {
        if (columnData.length != this.height){
            throw new ArithmeticException("Column heights don't match!");
        }
        this.width++;
        Fraction[][] newData = new Fraction[this.height][this.width];
        for(int y = 0; y < this.height; y++){
            for(int x = 0; x < this.width; x++){
                Fraction cell = (x == this.width-1) ? columnData[y] : this.cells[y][x];
                newData[y][x] = cell;
            }
        }
        this.cells = newData;
        return this;
    }

    public Matrix appendRow(Fraction[] rowData) {
        if (rowData.length != this.width){
            throw new ArithmeticException("Column heights don't match!");
        }
        this.height++;
        Fraction[][] newData = new Fraction[this.height][this.width];
        for(int y = 0; y < this.height; y++){
            for(int x = 0; x < this.width; x++){
                Fraction cell = (y == this.height-1) ? rowData[x] : this.cells[y][x];
                newData[y][x] = cell;
            }
        }
        this.cells = newData;
        return this;
    }

    public void multiplyRow(int row, Fraction factor){
        for(int i = 0; i < this.width; i++){
            this.cells[row][i] = cells[row][i].multiply(factor);
        }
    }

    public void swapRows(int rowA, int rowB){
        for(int x = 0; x < this.width; x++){
            Fraction hold = this.cells[rowA][x];
            this.cells[rowA][x] = this.cells[rowB][x];
            this.cells[rowB][x] = hold;
        }
    }

    public void addRow(int source, int destination){
        for(int x = 0; x < this.width; x++){
            this.cells[destination][x] = this.cells[destination][x].add(this.cells[source][x]);
        }
    }

    public void addRow(int source, int destination, Fraction factor){
        for(int x = 0; x < this.width; x++){
            this.cells[destination][x] = this.cells[destination][x].add(this.cells[source][x].multiply(factor));
        }
    }

    /**
     * Reduces to Row Echelon Form, but the matrix currently must already be an invertible, augmented matrix...
     */
    public void makeReducedEchelonForm(){

        int pivotRow = 0;
        for(int col = 0; col < this.width; col++){
            Fraction pivotValue = this.cells[pivotRow][col];
            this.multiplyRow(pivotRow, pivotValue.inverse());
            for(int row = 0; row < this.height; row++){
                if (row == pivotRow){ continue;}
                this.addRow(pivotRow, row, this.cells[row][col].negative());
            }
            pivotRow++;

            if (pivotRow == this.height){
                break;
            }

            int finderRow = pivotRow;
            int finderCol = col+1;
            boolean foundPivot = false;
            while(!foundPivot) {
                while (this.cells[finderRow][finderCol].getNumerator() == 0 && finderRow < height) {
                    finderRow++;
                }
                if (finderRow == height) {
                    // nothing good in the next column. keep moving?
                    finderCol++;
                    finderRow = pivotRow;
                    foundPivot = false;
                    if (finderCol >= width){
                        return; // no more pivots.
                    }
                } else if (this.cells[finderRow][finderCol].getNumerator() != 0){
                    col = finderCol-1; // loop will bump it up
                    swapRows(pivotRow, finderRow);
                    foundPivot = true;
                }
            }

        }
    }

    public void obfuscateMatrix(){
        Random rand = new Random();

        for(int i = 0; i < 20; i++){
            int move = rand.nextInt(3);
            switch(move) {
                case 0:
                    this.addRow(rand.nextInt(this.height    ),
                            rand.nextInt(this.height),
                            Fraction.fromInt(rand.nextInt(4) + 1));
                    break;
                case 1:
                    this.swapRows(rand.nextInt(this.height),
                            rand.nextInt(this.height));
                    break;
                case 2:
                    this.multiplyRow(rand.nextInt(this.height),
                            new Fraction(rand.nextInt(3) + 1, rand.nextInt(3) + 1));
                    break;
                default:
                    System.out.println("what");
                    break;
            }
        }
    }


    @Override
    public String toString(){
        StringBuilder op = new StringBuilder();
        op.append("[");
        for(int y = 0; y < this.height; y++){
            op.append("[");
            for(int x = 0; x < this.width; x++){
                op.append(this.cells[y][x]);
                if (x != this.width-1){
                    op.append(",");
                }
            }
            op.append("]");
            if (y != this.height-1){
                op.append("\n");
            }
        }
        op.append("]");
        return op.toString();
    }

}
