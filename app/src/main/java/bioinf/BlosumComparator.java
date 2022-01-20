package bioinf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

public class BlosumComparator implements Comparator<Sequence> {
    private final HashMap<Sequence, HashMap<Sequence, Integer>> blosum = new HashMap<>();

    public BlosumComparator() {
        var path = Paths.get("blosum62.txt");
        try {
            var lines = Files.readAllLines(path);
            var header = Arrays.stream(lines.get(0).split("\\s+"))
                    .filter(s -> s.matches("[0-9A-Z]"))
                    .map(Sequence::new)
                    .collect(Collectors.toList());

            header.forEach(h -> blosum.put(new Sequence(h.toString()), new HashMap<>()));
            for (int i = 0; i < header.size(); i++) {
                var h = header.get(i);
                var line = Arrays.stream(lines.get(i + 1).split("\\s+"))
                        .collect(Collectors.toList());
                for (var j = 1; j < line.size() - 1; j++) {
                    blosum.get(h).put(header.get(j - 1), Integer.parseInt(line.get(j)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int compare(Sequence o1, Sequence o2) {
        return blosum.get(o1).get(o2);
    }
}
