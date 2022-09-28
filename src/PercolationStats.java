import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
   private final double[] percStats;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        percStats = new double[trials];

        if(n <= 0 || trials <= 0){
            throw new IllegalArgumentException();
        }


        while(trials-- != 0)
        {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1 );
                int col = StdRandom.uniformInt(1, n + 1 );

                perc.open(row, col);
            }

            percStats[trials] = (double)perc.numberOfOpenSites()/(double)(n*n);
        }

    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(percStats);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(percStats);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return StdStats.mean(percStats) - (1.96 *stddev()) / Math.sqrt(percStats.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return StdStats.mean(percStats) + (1.96 * stddev()) / Math.sqrt(percStats.length);
    }

    // test client (see below)
    public static void main(String[] args){
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, trials);

        System.out.printf("mean                    = %f\n", ps.mean());
        System.out.printf("stddev                  = %f\n", ps.stddev());
        System.out.printf("95%% confidence interval = [%g, %g]\n", ps.confidenceLo(),ps.confidenceHi() );
    }
}
