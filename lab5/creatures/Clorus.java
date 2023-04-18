package creatures;


import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.LinkedList;
import java.util.Map;

import static huglife.HugLifeUtils.randomEntry;
import static huglife.HugLifeUtils.randomInt;

public class Clorus extends Creature {
    /* Clorus' color in rgb */
    private final int r = 34;
    private final int g = 0;
    private final int b = 231;

    /**
     * Creates a clorus with energy equal to n
     *
     * @param e
     */
    public Clorus(double e) {
        super("clorus");
        energy = e;
    }

    /**
     * Creates a clorus with default energy (equal to 1)
     */
    public Clorus() {
        super("clorus");
        energy = 1;
    }

    /**
     * Clorus loses 0.03 energy when moving
     */
    @Override
    public void move() {
        energy -= 0.03;
    }

    /**
     * Clorus gains all the energy of the attacked creature
     */
    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    /**
     * Clorus replicates itself and gives its offspring half of its energy
     */
    @Override
    public Creature replicate() {
        energy /= 2;
        return new Clorus(energy);
    }

    /**
     * Clorus loses 0.01 unit of energy if it stays still
     * */
    @Override
    public void stay() {
        energy -= 0.01;
    }

    /**
     * Choose an appropriate action based on the surroundings and self
     * */
    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Initialization
        LinkedList<Direction> emptyNeighbors = new LinkedList<>();
        LinkedList<Direction> plipsDirection = new LinkedList<>();
        for (Map.Entry<Direction, Occupant> neighbor : neighbors.entrySet()) {
            Direction neighborDirection = neighbor.getKey();
            Occupant occupant = neighbor.getValue();
            if (occupant.name().equals("empty")) {
                emptyNeighbors.addFirst(neighborDirection);
            }
            else if (occupant.name().equals("plip")) {
                plipsDirection.addFirst(neighborDirection);
            }
        }

        // Rule 1
        if (emptyNeighbors.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        }

        // Rule 2
        if (!plipsDirection.isEmpty()) {
            return new Action(Action.ActionType.ATTACK, randomEntry(plipsDirection));
        }

        // Rule 3
        if (energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, randomEntry(emptyNeighbors));
        }

        // Rule 4
        return new Action(Action.ActionType.MOVE, randomEntry(emptyNeighbors));
    }

    @Override
    public Color color() {
        return new Color(r, g, b);
    }
}
