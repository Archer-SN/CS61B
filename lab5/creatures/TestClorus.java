package creatures;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import huglife.*;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestClorus {
    @Test
    public void testBasics() {
        Clorus c = new Clorus(2);
        assertEquals("clorus", c.name());
        assertEquals(new Color(34, 0 , 231), c.color());
        c.move();
        assertEquals(1.97, c.energy(), 0.01);
        c.stay();
        assertEquals(1.96, c.energy(), 0.01);
    }

    @Test
    public void testReplication() {
        Clorus c = new Clorus(2);
        Creature cChild = c.replicate();
        assertEquals(cChild.energy(), c.energy(), 0.01);
    }

    @Test
    public void testAttack() {
        Clorus c = new Clorus();
        Plip p = new Plip(2);
        c.attack(p);
        assertEquals(c.energy(), 3.0, 0.01);
    }

    @Test
    public void testChoose() {
        // Test rule 1: Stay if surrounded
        Clorus c = new Clorus();
        Map<Direction, Occupant> surrounded = new HashMap<>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());
        Action at = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);
        assertEquals(at, expected);

        // Test rule 2: If not rule 1, attack if it sees plip.
        c = new Clorus();
        Map<Direction, Occupant> emptyTop = new HashMap<>();
        emptyTop.put(Direction.TOP, new Empty());
        emptyTop.put(Direction.BOTTOM, new Plip(2));
        emptyTop.put(Direction.LEFT, new Impassible());
        emptyTop.put(Direction.RIGHT, new Impassible());
        at = c.chooseAction(emptyTop);
        expected = new Action(Action.ActionType.ATTACK, Direction.BOTTOM);
        assertEquals(at, expected);

        // Test rule 3: If not rule 1 & rule 2, replicate.
        c = new Clorus();
        Map<Direction, Occupant> emptyBottom = new HashMap<>();
        emptyBottom.put(Direction.TOP, new Impassible());
        emptyBottom.put(Direction.BOTTOM, new Empty());
        emptyBottom.put(Direction.LEFT, new Impassible());
        emptyBottom.put(Direction.RIGHT, new Impassible());
        at = c.chooseAction(emptyBottom);
        expected = new Action(Action.ActionType.REPLICATE, Direction.BOTTOM);
        assertEquals(at, expected);

        //Test rule 4: Else, move to a random square
        c = new Clorus(0.75);
        at = c.chooseAction(emptyBottom);
        expected = new Action(Action.ActionType.MOVE, Direction.BOTTOM);
        assertEquals(at, expected);
    }
}
