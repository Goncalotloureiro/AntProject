import java.util.List; // For using List interface
import java.util.ArrayList;

public class Leaf {
    private List<Ant> hiddenAnts;

    public Leaf() {
        this.hiddenAnts = new ArrayList<>();
    }

    // Getters

    public List<Ant> getHiddenAnts() {
        return hiddenAnts;
    }

    // Additional methods for adding/removing ants
    public void addHiddenAnt(Ant ant) {
        hiddenAnts.add(ant);
    }

    public void removeHiddenAnt(Ant ant) {
        hiddenAnts.remove(ant);
    }
}
