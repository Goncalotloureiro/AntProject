import java.util.List; // For using List interface

public class Worker extends Ant {
    public Worker(String name, int strength, int health, String color, Anthill anthill) {
        super(name, strength, health, color, anthill);
    }

    public void work(Vertex currentVertex) {
        List<Ant> antsInVertex = currentVertex.getAnts();
        List<Larvae> larvaeInVertex = currentVertex.getLarvae();

        // Logic for gathering larvae
        if (!larvaeInVertex.isEmpty()) {
            collectLarvae(larvaeInVertex);
            returnToAnthill(getAnthill().getCurrentLocation());
            return; // Worker should return after gathering larvae
        }

        // Logic for defending against red ants
        for (Ant ant : antsInVertex) {
            if (ant instanceof Ant && ant.getColor().equals("red")) {
                attackRedAnt((Ant) ant);
                return;
            }
        }


    }

    public void collectLarvae(List<Larvae> larvaeInVertex) {
        int collectedLarvae = 0;
        int remainingStrength = getStrength();

        for (Larvae larvae : larvaeInVertex) {
            int larvaeQuantity = larvae.getQuantity();
            if (larvaeQuantity <= remainingStrength) {
                collectedLarvae += larvaeQuantity;
                remainingStrength -= larvaeQuantity;
            } else {
                collectedLarvae += remainingStrength;
                remainingStrength = 0;
                break; // Worker can't carry more larvae
            }
        }

        // Update the anthill's food quantity and remove the collected larvae from the vertex
        Anthill anthill = getAnthill();
        anthill.addFood(collectedLarvae);
        larvaeInVertex.clear();
    }

    public void attackRedAnt(Ant redAnt) {
        // Logic for worker to attack red ants and go back to the anthill after giving a hit
        if (!this.getColor().equals(redAnt.getColor())) {
            redAnt.decreaseHealth();
            this.returnToAnthill(getAnthill().getCurrentLocation()); // Pass the anthill's current location
        }
    }
}
