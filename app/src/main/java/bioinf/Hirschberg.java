package bioinf;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.IntStream;

/**
 * Алгоритм Хиршберга
 */
public class Hirschberg extends AlignmentAlgorithm {

    public Hirschberg(int gap, Comparator<Sequence> sequenceComparator) {
        super(gap, sequenceComparator);
    }

    private int[] needlemanWunschScore(Sequence seq1, Sequence seq2) {
        var scores = new int[2][seq2.length() + 1];

        scores[0][0] = 0;
        for (int j = 1; j <= seq2.length(); j++) {
            scores[0][j] = scores[0][j - 1] + gap;
        }
        for (int i = 1; i <= seq1.length(); i++) {
            scores[1][0] = scores[0][0] + gap;
            for (int j = 1; j <= seq2.length(); j++) {
                var scoreSub = scores[0][j - 1] + sequenceComparator.compare(seq1.at(i - 1), seq2.at(j - 1));
                var scoreDel = scores[0][j] + gap;
                var scoreIns = scores[1][j - 1] + gap;
                scores[1][j] = Collections.max(Arrays.asList(scoreSub, scoreDel, scoreIns));
            }
            System.arraycopy(scores[1], 0, scores[0], 0, seq2.length() + 1);
        }
        var lastLine = new int[seq2.length() + 1];
        System.arraycopy(scores[1], 0, lastLine, 0, seq2.length() + 1);

        return lastLine;
    }

    @Override
    public AlgoResult compute(Sequence seq1, Sequence seq2) {
        Sequence align1 = new Sequence(), align2 = new Sequence();
        int score = 0;

        if (seq1.length() == 0) {
            for (int i = 0; i < seq2.length(); i++) {
                align1.insert('-');
                align2.insert(seq2.at(i));
            }
            align2 = align2.reversed();
        } else if (seq2.length() == 0) {
            for (int i = 0; i < seq1.length(); i++) {
                align1.insert(seq1.at(i));
                align2.insert('-');
            }
            align1 = align1.reversed();
        } else if (seq1.length() == 1 || seq2.length() == 1) {
            return new NeedlemanWunsch(gap, sequenceComparator).compute(seq1, seq2);
        } else {
            int l1 = seq1.length(), l2 = seq2.length();
            int m1 = l1 / 2;

            var scoreLeft = needlemanWunschScore(seq1.subsequence(0, m1), seq2);
            var scoreRight = needlemanWunschScore(seq1.subsequence(m1, l1).reversed(), seq2.reversed());

            var argMaxOptional = IntStream.range(0, Math.min(scoreLeft.length, scoreRight.length))
                    .mapToObj(i -> new Object() {
                        final int x = i;
                        final int y = scoreLeft[i] + scoreRight[scoreRight.length - 1 - i];
                    })
                    .max(Comparator.comparingInt(a -> a.y));
            var m2 = 0;
            if (argMaxOptional.isPresent()) {
                m2 = argMaxOptional.get().x;
            }

            var result1 = compute(seq1.subsequence(0, m1), seq2.subsequence(0, m2));
            var result2 = compute(seq1.subsequence(m1, l1), seq2.subsequence(m2, l2));

            align1 = result1.align1().add(result2.align1());
            align2 = result1.align2().add(result2.align2());
            score += (result1.score() + result2.score());
        }

        return new AlgoResult(score, align1, align2);
    }
}
