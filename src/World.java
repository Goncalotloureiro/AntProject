import java.util.List; // For using List interface
import java.util.ArrayList;

public class World {
    private List<Vertex> vertices;

    public World() {
        this.vertices = new ArrayList<>();
    }

    public void addVertex(Vertex vertex) {
        this.vertices.add(vertex);
    }

    public List<Vertex> getAllVertices(){
        return this.vertices;
    }

}
