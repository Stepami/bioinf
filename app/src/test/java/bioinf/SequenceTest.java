package bioinf;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

public class SequenceTest {
    private final Comparator<Sequence> comparator = Comparator.comparing(Sequence::toString);

    @Test
    public void insertionIsCorrect() {
        var seq1 = new Sequence("abc");
        seq1.at(1);
        var seq2 = new Sequence();
        seq2.insert(seq1);
        assertEquals("b", seq2.toString());
    }

    @Test
    public void sequenceAdditionTest() {
        var x = new Sequence("A").add(new Sequence("B"));
        var y = new Sequence("AB");

        assertEquals(0, comparator.compare(x, y));
    }

    @Test
    public void reverseSequenceTest() {
        var x = new Sequence("xyz");
        var y = new Sequence("zyx");

        assertEquals(0, comparator.compare(x.reversed(), y));
    }

    @Test
    public void subsequenceTest() {
        var seq = new Sequence("abcde");
        var l = seq.length();

        var subseq = seq.subsequence(l / 2, l);
        var res = comparator.compare(new Sequence("cde"), subseq);
        assertEquals(0, res);
    }
}
