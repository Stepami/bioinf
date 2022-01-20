package bioinf;

import java.util.Objects;

/**
 * Последовательность
 */
public class Sequence implements Comparable<Sequence> {
    private String text;
    private int pos = 0;

    /**
     * Конструктор пустой последовательности
     */
    public Sequence() {
        this.text = "";
    }

    /**
     * Конструктор последовательности из некоторой строки
     *
     * @param text некоторая строка
     */
    public Sequence(String text) {
        this.text = text;
    }

    /**
     * Длина последовательности
     *
     * @return длина последовательности
     */
    public int length() {
        return text.length();
    }

    /**
     * Последовательность в заданной позиции
     *
     * @param pos индекс
     * @return последовательность с заданной позицией
     */
    public Sequence at(int pos) {
        this.pos = pos;
        return this;
    }

    /**
     * Подпоследовательность по заданным граничным позициям
     *
     * @param from от
     * @param to   до
     * @return подпоследовательность по заданным граничным позициям
     */
    public Sequence subsequence(int from, int to) {
        return new Sequence(text.substring(from, to));
    }

    /**
     * Перевёрнутая последовательность
     *
     * @return последовательность в обратном порядке
     */
    public Sequence reversed() {
        return new Sequence(new StringBuilder(text).reverse().toString());
    }

    /**
     * Вставка в последовательность
     *
     * @param element элемент для вставки
     */
    public void insert(char element) {
        text = element + text;
    }

    /**
     * Вставка в последовательность
     *
     * @param sequence последовательность с заданной позицией элемента для вставки
     */
    public void insert(Sequence sequence) {
        insert(sequence.text.charAt(sequence.pos));
    }

    /**
     * Конкатенация последовательностей
     *
     * @param sequence присоединямая последовательность
     * @return слияние двух послеждовательностей
     */
    public Sequence add(Sequence sequence) {
        return new Sequence(text + sequence.text);
    }

    /**
     * Сравнение двух последовательностей по элементу в заданной позиции
     *
     * @param sequence последовательность
     * @return 1 - совпадение, 0 - разница
     */
    @Override
    public int compareTo(Sequence sequence) {
        if (text.charAt(pos) == sequence.text.charAt(sequence.pos)) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sequence sequence = (Sequence) o;
        return text.charAt(pos) == sequence.text.charAt(sequence.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text.charAt(pos));
    }

    @Override
    public String toString() {
        return text;
    }
}
