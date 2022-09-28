import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final boolean[] percolationSystem;
    private int sitesOpen;
    private final int width;
    private final int top;
    private final int bottom;
    private final WeightedQuickUnionUF WUF;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        // create boolean array
        percolationSystem = new boolean[n * n + 2];
        bottom = n * n + 1;
        top = 0;
        //initialize sites open
        sitesOpen = 0;
        width = n;
        //Create weighted union find
        WUF = new WeightedQuickUnionUF(n * n + 2);

        //initialize array
        percolationSystem[top] = true;
        percolationSystem[bottom] = true;

        //Create initial connections for top and bottom
        for(int cell = 1; cell <= width; cell++){
            WUF.union(top, cell);
        }

        for(int cell = n * n + 1 - n; cell <= bottom; cell++){
            WUF.union(bottom, cell);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){

        //get index and check for valid cell
        int index = getIndex(row, col);
        if(!IsInRange(index)){
            throw new IllegalArgumentException();
        }

        //check if cell is already open
        if(!percolationSystem[index]) {

            //set cell to open
            percolationSystem[index] = true;

            //create array of adjacent cells
            int [] adjacentCell = new int[4];
            adjacentCell[0] = getIndex(row -1, col);
            adjacentCell[1] = getIndex(row + 1, col);
            adjacentCell[2] = getIndex(row, col -1);
            adjacentCell[3] = getIndex(row, col + 1);

            //join adjacent cells if they are open
            for(int cell : adjacentCell){
                if(IsInRange(cell)) {
                    if (percolationSystem[cell]) {
                        WUF.union(index, cell);
                    }
                }
            }

            sitesOpen++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        int index = getIndex(row, col);
        if(!IsInRange(index)){
            throw new IllegalArgumentException();
        }

        return percolationSystem[index];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        int index = getIndex(row, col);
        if(!IsInRange(index)){
            throw new IllegalArgumentException();
        }

        return percolationSystem[index] && WUF.find(top) == WUF.find(index);
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return sitesOpen;
    }

    // does the system percolate?
    public boolean percolates(){
        return WUF.find(top) == WUF.find(bottom);
    }

    private boolean IsInRange(int index) {
        return (index <= percolationSystem.length && index > 0);
    }

    private int getIndex(int row, int col){
        if(row <= 0 || col <= 0){
            return -1;
        }

        if(row >= width + 1 || col >= width + 1){
            return -1;
        }

        return (row - 1) * width + col;
    }

    // test client (optional)
    public static void main(String[] args){
    }
}
