import java.util.List; // For using List interface

public class Blunderer extends Collector {
    public Blunderer(String name, int strength, int health, String color, Anthill anthill) {
        super(name, strength, health, color, anthill);
    }

    @Override
    public void collectLarvae(Vertex currentVertex) {
        List<Larvae> larvaeInVertex = currentVertex.getLarvae();
        int remainingStrength = getStrength();

        // Logic for collecting larvae with a chance to drop them on the way back
        if (!larvaeInVertex.isEmpty()) {
            int collectedLarvae = 0;

            for (Larvae larvae : larvaeInVertex) {
                int larvaeQuantity = larvae.getQuantity();
                if (hasChanceToDrop()) {
                    currentVertex.removeLarvae(larvae);
                }
                else if (larvaeQuantity <= remainingStrength) {
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

    private boolean hasChanceToDrop() {
        // Logic to determine if the blunderer has a chance to drop larvae
        // Implement based on specific criteria or chance calculation
        return Math.random() < 0.2; // Example: 20% chance to drop
    }

}