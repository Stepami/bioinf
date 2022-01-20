package bioinf;

import java.util.Comparator;

/**
 * Алгоритм выравнивания
 */
public abstract class AlignmentAlgorithm {
    protected final int gap;
    protected final Comparator<Sequence> sequenceComparator;

    /**
     * Конструктор алгоритма с заданным штрафом
     *
     * @param gap                штраф
     * @param sequenceComparator сравниватель последовательностей
     */
    public AlignmentAlgorithm(int gap, Comparator<Sequence> sequenceComparator) {
        this.gap = gap;
        this.sequenceComparator = sequenceComparator;
    }

    /**
     * Вычисление выравниваний
     *
     * @param seq1 первая последовательность
     * @param seq2 вторая последовательность
     * @return результат алгоритма
     */
    public abstract AlgoResult compute(Sequence seq1, Sequence seq2);

    /**
     * Результат алгоритма: выровненные последовательности и скор
     */
    protected record AlgoResult(int score, Sequence align1, Sequence align2) {
        @Override
        public String toString() {
            return String.format("%s\n%s\nScore: %d", align1, align2, score);
        }
    }
}
