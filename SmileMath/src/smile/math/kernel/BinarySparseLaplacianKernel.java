/******************************************************************************
 *                   Confidential Proprietary                                 *
 *         (c) Copyright Haifeng Li 2011, All Rights Reserved                 *
 ******************************************************************************/

package smile.math.kernel;

import smile.math.Math;

/**
 * The Laplacian Kernel. k(u, v) = e<sup>-||u-v|| / &sigma;</sup>,
 * where &sigma; > 0 is the scale parameter of the kernel. The kernel
 * works sparse binary array as int[], which are the indices of nonzero elements.

 * @author Haifeng Li
 */
public class BinarySparseLaplacianKernel implements MercerKernel<int[]> {

    /**
     * The width of the kernel.
     */
    private double gamma;

    /**
     * Constructor.
     * @param sigma the smooth/width parameter of Laplacian kernel.
     */
    public BinarySparseLaplacianKernel(double sigma) {
        if (sigma <= 0)
            throw new IllegalArgumentException("sigma is not positive.");

        this.gamma = 1.0 / sigma;
    }

    @Override
    public String toString() {
        return String.format("Sparse Binary Laplacian Kernel (\u02E0 = %.4f)", 1.0/gamma);
    }

    @Override
    public double k(int[] x, int[] y) {
        if (x.length != y.length)
            throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", x.length, y.length));

        double d = 0.0;
        int p1 = 0, p2 = 0;
        while (p1 < x.length && p2 < y.length) {
            int i1 = x[p1];
            int i2 = y[p2];
            if (i1 == i2) {
                p1++;
                p2++;
            } else if (i1 > i2) {
                d++;
                p2++;
            } else {
                d++;
                p1++;
            }
        }

        d += x.length - p1;
        d += y.length - p2;

        return Math.exp(-gamma * Math.sqrt(d));
    }
}
