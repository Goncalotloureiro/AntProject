import java.util.List; // For using List interface
import java.util.ArrayList;

public class Anthill {
    private String colonyColor;
    private int foodQuantity;
    private List<Ant> ants; // Association with Ants
    private Vertex currentLocation; // Reference to the vertex representing the anthill's position

    public Anthill(String colonyColor, Vertex currentLocation) {
        this.colonyColor = colonyColor;
        this.foodQuantity = 0;
        this.ants = new ArrayList<>();
        this.currentLocation = currentLocation;
    }

    public String getColonyColor() {
        return colonyColor;
    }

    public int getFoodQuantity() {
        return foodQuantity;
    }

    public List<Ant> getAnts() {
        return ants;
    }

    public Vertex getCurrentLocation() {
        return currentLocation;
    }

    // Setters
    public void setColonyColor(String colonyColor) {
        this.colonyColor = colonyColor;
    }

    public void setFoodQuantity(int foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public void setCurrentLocation(Vertex currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void addAnt(Ant ant) {
        ants.add(ant);
    }

    public void removeAnt(Ant ant) {
        ants.remove(ant);
    }

    public void addFood(int quantity) {
        // Logic to add food to the anthill
        this.foodQuantity += quantity;
    }

    public void removeFood(int quantity) {
        // Logic to remove food from the anthill
        this.foodQuantity -= quantity;
        if (this.foodQuantity < 0) {
            this.foodQuantity = 0; // Ensure food quantity doesn't go below zero
        }
    }
}
