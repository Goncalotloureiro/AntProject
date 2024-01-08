public class Larvae {
    private int quantity;
    private Vertex currentVertex;

    public Larvae(int quantity) {
        this.quantity = quantity;
    }

    // Getters
    public int getQuantity() {
        return quantity;
    }

    public Vertex getCurrentVertex() {
        return currentVertex;
    }

    // Setters
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCurrentVertex(Vertex currentVertex) {
        this.currentVertex = currentVertex;
    }
}
