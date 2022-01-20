package bioinf;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Алгоритм Нидлмана — Вунша
 */
public class NeedlemanWunsch extends AlignmentAlgorithm {

    public NeedlemanWunsch(int gap, Comparator<Sequence> sequenceComparator) {
        super(gap, sequenceComparator);
    }

    @Override
    public AlgoResult compute(Sequence seq1, Sequence seq2) {
        int i = seq1.length(), j = seq2.length();

        // создаём нулевую матрицу и заполняем её по алгоритму
        var scores = new int[i + 1][j + 1];
        for (int ii = 0; ii <= i; ii++) {
            for (int jj = 0; jj <= j; jj++) {
                if (ii == 0) {
                    scores[0][jj] = gap * jj;
                } else if (jj == 0) {
                    scores[ii][0] = gap * ii;
                } else {
                    var matchScore = scores[ii - 1][jj - 1] + sequenceComparator.compare(seq1.at(ii - 1), seq2.at(jj - 1));
                    var deleteScore = scores[ii - 1][jj] + gap;
                    var insertScore = scores[ii][jj - 1] + gap;
                    scores[ii][jj] = Collections.max(Arrays.asList(matchScore, deleteScore, insertScore));
                }
            }
        }

        // выполняем выравниеание, двигаясь по матрице от нижней правой ячейки
        Sequence align1 = new Sequence(), align2 = new Sequence();
        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 && scores[i][j] == scores[i - 1][j - 1] + sequenceComparator.compare(seq1.at(i - 1), seq2.at(j - 1))) {
                align1.insert(seq1);
                align2.insert(seq2);
                i -= 1;
                j -= 1;
            } else if (i > 0 && scores[i][j] == scores[i - 1][j] + gap) {
                align1.insert(seq1.at(i - 1));
                align2.insert('-');
                i -= 1;
            } else if (j > 0 && scores[i][j] == scores[i][j - 1] + gap) {
                align1.insert('-');
                align2.insert(seq2.at(j - 1));
                j -= 1;
            }
        }

        return new AlgoResult(scores[seq1.length()][seq2.length()], align1, align2);
    }
}
