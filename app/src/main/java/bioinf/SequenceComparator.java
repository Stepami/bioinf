package bioinf;

import java.util.Comparator;

public class SequenceComparator implements Comparator<Sequence> {
    private final int[] matchTable = new int[2];

    public SequenceComparator(int match, int mismatch) {
        matchTable[0] = mismatch;
        matchTable[1] = match;
    }

    @Override
    public int compare(Sequence o1, Sequence o2) {
        return matchTable[o1.compareTo(o2)];
    }
}
