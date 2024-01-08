import java.util.List; // For using List interface

public class Ant {
    private String name;
    private int strength;
    private int health;
    private String color;
    private Vertex currentVertex;
    private Anthill anthill; // Association with Anthill

    public Ant(String name, int strength, int health, String color, Anthill anthill) {
        this.name = name;
        this.strength = strength;
        this.health = health;
        this.color = color;
        this.anthill = anthill;
    }

    public String getName() {
        return name;
    }

    public int getStrength() {
        return strength;
    }

    public int getHealth() {
        return health;
    }

    public String getColor() {
        return color;
    }

    public Vertex getCurrentVertex() {
        return currentVertex;
    }

    public Anthill getAnthill() {
        return anthill;
    }

    // Setters
    public void setCurrentVertex(Vertex currentVertex) {
        this.currentVertex = currentVertex;
    }

    public void setAnthill(Anthill anthill) {
        this.anthill = anthill;
    }

    // Additional methods for adding/removing associations
    public void removeFromCurrentVertex() {
        if (currentVertex != null) {
            currentVertex.removeAnt(this);
        }
        currentVertex = null;
    }

    public void moveRandomly(List<Vertex> adjacentVertices) {
        // Logic to move the ant randomly to adjacent vertices
        if (!adjacentVertices.isEmpty()) {
            int randomIndex = (int) (Math.random() * adjacentVertices.size());
            Vertex randomVertex = adjacentVertices.get(randomIndex);
            setCurrentVertex(randomVertex);
        }
    }

    public void returnToAnthill(Vertex anthillVertex) {
        // Logic for the ant to return to its anthill
        setCurrentVertex(anthillVertex);
    }

    public void attack(Ant targetAnt) {
        // Logic to handle attack between ants
        // Red ant attacks blue ant and vice versa based on their types and strength
        if (!this.color.equals(targetAnt.getColor())) {
            if (this.strength > targetAnt.getStrength()) {
                targetAnt.decreaseHealth();
            } else if (this.strength < targetAnt.getStrength()) {
                this.decreaseHealth();
            } // If strengths are equal, no change in health
        }
    }

    public void decreaseHealth() {
        // Decrease health when attacked
        this.health--;
    }
}
