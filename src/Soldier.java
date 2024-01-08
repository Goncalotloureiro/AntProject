

public class Soldier extends Ant {
    public Soldier(String name, int strength, int health, String color, Anthill anthill) {
        super(name, strength, health, color, anthill);
    }

    public void attackBlueAnt(Ant blueAnt) {
        // Logic for soldier to attack blue ants and go back to the anthill after giving a hit
        if (!this.getColor().equals(blueAnt.getColor())) {
            blueAnt.decreaseHealth();
            this.returnToAnthill(getAnthill().getCurrentLocation()); // Pass the anthill's current location
        }
    }
    // Other methods...
}
