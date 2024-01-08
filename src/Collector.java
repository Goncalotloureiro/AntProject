import java.util.List; // For using List interface


public class Collector extends Ant {
    public Collector(String name, int strength, int health, String color, Anthill anthill) {
        super(name, strength, health, color, anthill);
    }

    public void collectLarvae(Vertex currentVertex) {
        List<Larvae> larvaeInVertex = currentVertex.getLarvae();
        int remainingStrength = getStrength();

        // Logic for collecting larvae with a chance to drop them on the way back
        if (!larvaeInVertex.isEmpty()) {
            int collectedLarvae = 0;

            for (Larvae larvae : larvaeInVertex) {
                int larvaeQuantity = larvae.getQuantity();
                if (larvaeQuantity <= remainingStrength) {
                    collectedLarvae += larvaeQuantity;
                    remainingStrength -= larvaeQuantity;
                } else {
                    collectedLarvae += remainingStrength;
                    remainingStrength = 0;
                    break; // Blunderer can't carry more larvae
                }
            }

            // Update the anthill's food quantity and remove the collected larvae from the vertex
            Anthill anthill = getAnthill();
            anthill.addFood(collectedLarvae);
        }
    }
}