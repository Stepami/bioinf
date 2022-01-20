package bioinf;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Алгоритм Нидлмана — Вунша с афинным вычислением разрывов
 */
public class AffineNeedlemanWunsch extends AlignmentAlgorithm {
    private final int extensionGap;

    public AffineNeedlemanWunsch(int openGap, Comparator<Sequence> sequenceComparator, int extensionGap) {
        super(openGap, sequenceComparator);
        this.extensionGap = extensionGap;
    }

    private int gap(int length) {
        return gap + extensionGap * (length - 1);
    }

    @Override
    public AlgoResult compute(Sequence seq1, Sequence seq2) {
        int i = seq1.length(), j = seq2.length();

        int[][] X = new int[i + 1][j + 1], Y = new int[i + 1][j + 1], M = new int[i + 1][j + 1];
        for (int ii = 0; ii <= i; ii++) {
            for (int jj = 0; jj <= j; jj++) {
                if (ii > 0 && jj == 0) {
                    X[ii][jj] = Integer.MIN_VALUE / 2;
                } else {
                    if (jj > 0) {
                        X[ii][jj] = gap(jj);
                    }
                }
            }
        }
        for (int ii = 0; ii <= i; ii++) {
            for (int jj = 0; jj <= j; jj++) {
                if (jj > 0 && ii == 0) {
                    Y[ii][jj] = Integer.MIN_VALUE / 2;
                } else {
                    if (ii > 0) {
                        Y[ii][jj] = gap(ii);
                    }
                }
            }
        }
        for (int ii = 0; ii <= i; ii++) {
            M[ii][0] = Integer.MIN_VALUE / 2;
        }
        for (int jj = 0; jj <= j; jj++) {
            M[0][jj] = Integer.MIN_VALUE / 2;
        }
        M[0][0] = 0;
        for (int ii = 1; ii <= i; ii++) {
            for (int jj = 1; jj <= j; jj++) {
                X[ii][jj] = Collections.max(Arrays.asList(
                        gap(2) + M[ii][jj - 1],
                        extensionGap + X[ii][jj - 1],
                        gap(2) + Y[ii - 1][jj]
                ));
                Y[ii][jj] = Collections.max(Arrays.asList(
                        gap(2) + M[ii - 1][jj],
                        gap(2) + X[ii - 1][jj],
                        extensionGap + Y[ii - 1][jj]
                ));
                M[ii][jj] = Collections.max(Arrays.asList(
                        sequenceComparator.compare(seq1.at(ii - 1), seq2.at(jj - 1)) + M[ii - 1][jj - 1],
                        X[ii][jj],
                        Y[ii][jj]
                ));
            }
        }


        Sequence align1 = new Sequence(), align2 = new Sequence();
        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 && M[i][j] == M[i - 1][j - 1] + sequenceComparator.compare(seq1.at(i - 1), seq2.at(j - 1))) {
                align1.insert(seq1);
                align2.insert(seq2);
                i -= 1;
                j -= 1;
            } else if (i > 0 && M[i][j] == Y[i][j]) {
                align1.insert(seq1.at(i - 1));
                align2.insert('-');
                i -= 1;
            } else if (j > 0 && M[i][j] == X[i][j]) {
                align1.insert('-');
                align2.insert(seq2.at(j - 1));
                j -= 1;
            }
        }

        return new AlgoResult(M[seq1.length()][seq2.length()], align1, align2);
    }
}
