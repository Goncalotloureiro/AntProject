import java.util.Scanner;
import java.util.*;

public class AntSimulatorApp {
    private static boolean isPaused = false;

    public static void main(String[] args) {
        // Create the ant world, initialize ants, anthills, etc.
        // Initialize the world and ants
        Vertex[][] grid = new Vertex[5][5];


        World world = initializeWorld(grid); // Initialize the world
        identifyAdjacentVertices(grid);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (!isPaused) {
                // Run the simulation logic
                // Move ants, interact with the environment, etc.
                moveAnts(world);
                interactAnts(world);
                printGridState(grid);

            }

            // Check for user input to control the simulation
            processUserInput(scanner, world);

            try {
                Thread.sleep(2000); // 2000 milliseconds = 2 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static World initializeWorld(Vertex[][] grid) {
        World world = new World();

        // Populate the grid with vertices and their coordinates
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Coordinates coordinates = new Coordinates(i, j);
                Vertex vertex = new Vertex("Type", coordinates); // Type could be "Leaf", "Stone", etc.
                world.addVertex(vertex);
                grid[i][j] = vertex;
            }
        }

        Random random = new Random();
        int anthillCount = 0;
        int stoneCount = 0;
        int leafCount = 0;
        
        while (leafCount < 2){
            int x = random.nextInt(5); // Change this to the size of your grid
            int y = random.nextInt(5); // Change this to the size of your grid
        
            Vertex currentVertex = grid[x][y];
            // Check if the current vertex already has an element assigned to it
            // If not, assign an element based on the count and type
            if (currentVertex.getType().equals("Type")) {
                if (anthillCount < 2) {
                    currentVertex.setType("Anthill");
                    anthillCount++;
                    Anthill anthill = new Anthill("ColonyColor", currentVertex); // Create Anthill instance
                    currentVertex.setAnthill(anthill);
                    if (anthillCount == 1) {
                        anthill.setColonyColor("Blue");
                    }
                    else {
                        anthill.setColonyColor("Red");
                    }
                    continue;
                } else if (stoneCount < 2) {
                    currentVertex.setType("Stone");
                    stoneCount++;
                    continue;
                } else if (leafCount < 2) {
                    currentVertex.setType("Leaf");
                    leafCount++;
                    continue;
                }
            }
            //
        }
        return world;
    }

    public static void identifyAdjacentVertices(Vertex[][] grid) {
        int numRows = grid.length;
        int numCols = grid[0].length;
    
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                Vertex currentVertex = grid[i][j];
                List<Vertex> adjacentVertices = new ArrayList<>();
    
                // Define conditions to find adjacent vertices based on grid boundaries
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int newX = i + dx;
                        int newY = j + dy;
    
                        // Check if the new coordinates are within bounds and not the same as the current vertex
                        if (newX >= 0 && newX < numRows && newY >= 0 && newY < numCols &&
                                !(dx == 0 && dy == 0)) {
                            adjacentVertices.add(grid[newX][newY]);
                        }
                    }
                }
    
                // Store the adjacent vertices in the current vertex
                currentVertex.setAdjacentVertices(adjacentVertices);
            }
        }
    }

    private static void printGridState(Vertex[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                Vertex currentVertex = grid[i][j];
                String type = currentVertex.getType();
    
                // Print identifiers for different elements in the grid
                if (type.equals("Anthill")) {
                    System.out.print("AH "); // Anthill
                } else if (type.equals("Stone")) {
                    System.out.print("S "); // Stone
                } else if (type.equals("Leaf")) {
                    System.out.print("L "); // Leaf
                } else if (hasAnt(currentVertex)) {
                    System.out.print("A "); // Ant
                } else {
                    System.out.print("- "); // Empty space
                }
            }
            System.out.println(); // Move to the next line for the next row
        }
    }

    private static boolean hasAnt(Vertex vertex) {
        List<Ant> ants = vertex.getAnts();
        for (Ant ant : ants) {
            if (ant.getCurrentVertex().equals(vertex)) {
                return true;
            }
        }
        return false;
    }

    private static void moveAnts(World world) {
        List<Vertex> allVertices = world.getAllVertices();

        // Loop through all vertices in the world
        for (Vertex vertex : allVertices) {
            List<Ant> ants = vertex.getAnts();

            // Loop through ants in the current vertex and move them randomly
            for (Ant ant : ants) {
                if (!isPaused) {
                    // Get adjacent vertices for the current ant's position
                    List<Vertex> adjacentVertices = vertex.getAdjacentVertices();

                    // Move the ant randomly among adjacent vertices
                    ant.moveRandomly(adjacentVertices);
                }
            }
        }
    }

    private static void interactAnts(World world) {
        List<Vertex> allVertices = world.getAllVertices();

        // Loop through all vertices in the world
        // Loop through all vertices in the world
        for (Vertex vertex : allVertices) {
            String vertexType = vertex.getType();
            if (!vertexType.equals("Stone") && !vertexType.equals("Leaf")) {
                List<Ant> ants = vertex.getAnts();

                // Loop through ants in the current vertex and move them randomly
                for (Ant ant : ants) {
                    if (!isPaused) {
                        if (ant instanceof Soldier) {
                            List<Ant> otherAnts = vertex.getAnts();
                            for (Ant otherAnt : otherAnts) {
                                if (otherAnt.getColor().equals("Blue")) {
                                    ((Soldier) ant).attack(otherAnt); // Attack ants of the opposing color
                                    ant.returnToAnthill(ant.getAnthill().getCurrentLocation()); // Return to the anthill after attacking
                                    break; // Only attack one ant per iteration
                                }
                            }
                        
                        } else if (ant instanceof Worker) {
                            List<Ant> otherAnts = vertex.getAnts();
                            List<Larvae> larvaeInVertex = vertex.getLarvae();
                        
                            // Collect larvae if available
                            if (!larvaeInVertex.isEmpty()) {
                                ((Worker) ant).collectLarvae(vertex.getLarvae());
                                ant.returnToAnthill(ant.getAnthill().getCurrentLocation()); // Return to the anthill after attacking
                                return; // Worker should return after gathering larvae
                            }
                        
                            // Defend against red ants
                            for (Ant otherAnt : otherAnts) {
                                if (otherAnt.getColor().equals("Red")) {
                                    ((Worker) ant).attackRedAnt(otherAnt);
                                    ant.returnToAnthill(ant.getAnthill().getCurrentLocation()); // Return to the anthill after attacking
                                    break; // Only attack one red ant per iteration
                                }
                            }
                        } else if (ant instanceof Collector) {
                            List<Larvae> larvaeInVertex = vertex.getLarvae();

                            // Collect larvae if available
                            if (!larvaeInVertex.isEmpty()) {
                                ((Collector) ant).collectLarvae(vertex);
                                ant.returnToAnthill(ant.getAnthill().getCurrentLocation()); // Return to the anthill after attacking
                                return; // Collector should return after gathering larvae
                            }
                        } else if (ant instanceof Blunderer) {
                            List<Larvae> larvaeInVertex = vertex.getLarvae();

                            // Collect larvae and potentially drop them if attacked by red ants
                            if (!larvaeInVertex.isEmpty()) {
                                ((Blunderer) ant).collectLarvae(vertex);
                                ant.returnToAnthill(ant.getAnthill().getCurrentLocation()); // Return to the anthill after attacking
                                return; // Blunderer should return after gathering larvae
                            }
                        }
                    }
                }
            }
        }
    }
    
    private static void displayStatistics(World world) {
        // Get all vertices from the world
        List<Vertex> allVertices = world.getAllVertices();
    
        // Display information about each vertex
        for (Vertex vertex : allVertices) {
            System.out.println("Vertex: (" + vertex.getCoordinates().getX() + ", " + vertex.getCoordinates().getY() + ")");
                        
            // Display information about ants in the vertex
            List<Ant> ants = vertex.getAnts();
            for (Ant ant : ants) {
                System.out.println("Ant: " + ant.getName() + ", Type: " + ant.getClass().getSimpleName() +
                        ", Color: " + ant.getColor() + ", Strength: " + ant.getStrength() +
                        ", Health: " + ant.getHealth());
            }
    
            // Display information about anthill in the vertex
            Anthill anthill = vertex.getAnthill();
            if (anthill != null) {
                System.out.println("Anthill Color: " + anthill.getColonyColor() + ", Food: " + anthill.getFoodQuantity());
            }
    
            // Display other relevant information about the vertex
            String type = vertex.getType();
            System.out.println("Type: " + type);
            System.out.println("------------------");
        }
    }

    private static void processUserInput(Scanner scanner, World world) {
        System.out.println("Enter a command (P to pause, R to resume, Q to quit): ");
        String input = scanner.nextLine();
    
        switch (input.toUpperCase()) {
            case "P":
                pauseSimulation();
                performPausedActions(scanner, world);
                break;
            case "R":
                resumeSimulation();
                break;
            case "Q":
                quitSimulation();
                break;
            default:
                System.out.println("Invalid command.");
        }
    }
    
    private static void performPausedActions(Scanner scanner, World world) {
        boolean pausedActions = true;
        while (pausedActions) {
            System.out.println("Paused. Enter command (A to add ant, D to remove ant, S to display stats, R to resume): ");
            String input = scanner.nextLine();
    
            switch (input.toUpperCase()) {
                case "A":
                    addAnt(world);
                    break;
                case "D":
                    removeAnt(world);
                    break;
                case "S":
                    displayStatistics(world);
                    break;
                case "R":
                    pausedActions = false;
                    resumeSimulation();
                    break;
                default:
                    System.out.println("Invalid command.");
            }
        }
    }


    private static void addAnt(World world) {
        String[] antColors = {"Red", "Blue"};
        Random random = new Random();
        String randomColor = antColors[random.nextInt(antColors.length)];
    
        List<Vertex> allVertices = world.getAllVertices();
    
        if (!allVertices.isEmpty()) {
            Vertex randomVertex = allVertices.get(random.nextInt(allVertices.size()));
    
            // Create a specific type of ant and add it to the random vertex
            String randomType = ""; // Replace this with your logic to select a random type (Soldier, Worker, Collector, or Blunderer)
            Ant newAnt = createAnt(randomType, randomColor, randomVertex);
            newAnt.setCurrentVertex(randomVertex);
            randomVertex.addAnt(newAnt);
            System.out.println("Added a new " + randomType + " ant to vertex: " + randomVertex.getCoordinates().getX() + ", " + randomVertex.getCoordinates().getY() + ")");
        } else {
            System.out.println("No vertices to add an ant.");
        }
    }
    
    private static Ant createAnt(String antType, String color, Vertex vertex) {
        switch (antType) {
            case "Soldier":
                return new Soldier("SoldierAnt", 8, 10, color, vertex.getAnthill()); // Adjust parameters as needed
            case "Worker":
                return new Worker("WorkerAnt", 5, 5, color, vertex.getAnthill()); // Adjust parameters as needed
            case "Collector":
                return new Collector("CollectorAnt", 6, 6, color, vertex.getAnthill()); // Adjust parameters as needed
            case "Blunderer":
                return new Blunderer("BlundererAnt", 7, 7, color, vertex.getAnthill()); // Adjust parameters as needed
            default:
                return new Ant("RandomAnt", 5, 5, color, vertex.getAnthill()); // Default to a generic Ant
        }
    }
    
    private static void removeAnt(World world) {
        List<Vertex> allVertices = world.getAllVertices();
    
        if (!allVertices.isEmpty()) {
            Random random = new Random();
            Vertex randomVertex = allVertices.get(random.nextInt(allVertices.size()));
    
            // Remove a random ant from the random vertex
            List<Ant> antsInVertex = randomVertex.getAnts();
            if (!antsInVertex.isEmpty()) {
                Ant antToRemove = antsInVertex.get(random.nextInt(antsInVertex.size()));
                randomVertex.removeAnt(antToRemove);
                System.out.println("Removed ant: " + antToRemove.getName() + " from vertex: " + randomVertex.getCoordinates());
            } else {
                System.out.println("No ants in the selected vertex to remove.");
            }
        } else {
            System.out.println("No vertices available to remove an ant from.");
        }
    }

    private static void pauseSimulation() {
        isPaused = true;
        System.out.println("Simulation paused.");
    }

    private static void resumeSimulation() {
        isPaused = false;
        System.out.println("Simulation resumed.");
    }

    private static void quitSimulation() {
        System.out.println("Simulation ended.");
        System.exit(0);
    }
}
