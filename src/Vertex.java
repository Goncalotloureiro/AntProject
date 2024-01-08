import java.util.List; // For using List interface
import java.util.ArrayList;

public class Vertex {
    private String type; // Leaf/Stone/Anthill
    private Coordinates coordinates;
    private List<Ant> ants; // Association with Ants
    private List<Larvae> larvae; // Association with Larvae
    private List<Vertex> adjacentVertices;
    private Anthill anthill;

    public Vertex(String type, Coordinates coordinates) {
        this.type = type;
        this.coordinates = coordinates;
        this.ants = new ArrayList<>();
        this.larvae = new ArrayList<>();
    }

    public String getType() {
        return type;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Anthill getAnthill(){
        return anthill;
    }

    public List<Ant> getAnts() {
        return ants;
    }

    public List<Larvae> getLarvae() {
        return larvae;
    }

    public List<Vertex> getAdjacentVertices() {
        return this.adjacentVertices;
    }

    public void setAdjacentVertices(List<Vertex> adjacentVertices) {
        this.adjacentVertices = adjacentVertices;
    }
    
    public void setAnthill(Anthill anthill){
        this.anthill = anthill;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    // Additional methods for adding/removing associations
    public void addAnt(Ant ant) {
        ants.add(ant);
    }

    public void removeAnt(Ant ant) {
        ants.remove(ant);
    }

    public void addLarvae(Larvae larvae) {
        this.larvae.add(larvae);
    }

    public void removeLarvae(Larvae larvae) {
        this.larvae.remove(larvae);
    }

}
