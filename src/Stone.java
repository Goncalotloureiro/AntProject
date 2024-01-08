import java.util.List; // For using List interface
import java.util.ArrayList;

public class Stone {
    private List<Ant> antsWaiting;

    public Stone(int size) {
        this.antsWaiting = new ArrayList<>();
    }



    public List<Ant> getAntsWaiting() {
        return antsWaiting;
    }

    // Additional methods for adding/removing ants
    public void addWaitingAnt(Ant ant) {
        antsWaiting.add(ant);
    }

    public void removeWaitingAnt(Ant ant) {
        antsWaiting.remove(ant);
    }
}
