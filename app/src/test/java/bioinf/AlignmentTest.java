package bioinf;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AlignmentTest {
    @Test
    public void needlemanWunschTest() {
        var algo = new NeedlemanWunsch(-1, new SequenceComparator(1, -1));

        var seq1 = new Sequence("GCATGCU");
        var seq2 = new Sequence("GATTACA");

        var result = algo.compute(seq1, seq2);
        assertEquals(0, result.score());
    }

    @Test
    public void hirschbergTest() {
        var algo = new Hirschberg(-2, new SequenceComparator(2, -1));

        var seq1 = new Sequence("AGTACGCA");
        var seq2 = new Sequence("TATGC");

        var result = algo.compute(seq1, seq2);
        assertEquals("--TATGC-", result.align2().toString());
    }

    @Test
    public void affineNeedlemanWunschTest() {
        var algo = new AffineNeedlemanWunsch(-10, new BlosumComparator(), -1);

        var seq1 = new Sequence("AGTACGCA");
        var seq2 = new Sequence("AGGC");

        var result = algo.compute(seq1, seq2);
        assertEquals("AG---GC-", result.align2().toString());
    }
}
