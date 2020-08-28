package Entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BombListTest {
    BombList bl;

    @BeforeEach
    void setUp() {
        bl = new BombList();
        bl.add(new Bomb(0,0,3000,"4213s3"));
        bl.add(new Bomb(2,-1,3000,"n4X4Ts"));
        bl.add(new Bomb(5,3,3000,"vAOe1t"));
        bl.add(new Bomb(6,2,3000,"KAXlgC"));
        bl.add(new Bomb(3,3,3000,"BhV0tq"));
        bl.add(new Bomb(1,4,3000,"EbSxvH"));
    }

    @Test
    void findByBombId() {
        Bomb b = new Bomb(3,2,3000,"Nt312d");
        bl.add(b);

        assertEquals(bl.findByBombId(b.getBombId()),b);
    }

    @Test
    void findByBombId2() {
        assertNull(bl.findByBombId("testID"));
    }


    @Test
    void findByPlayerId() {
        Bomb b = new Bomb(3,2,3000,"Nt312d");
        bl.add(b);

        assertEquals(bl.findByPlayerId("Nt312d"),b);
    }

    @Test
    void findByPlayerId2() {
        assertNull(bl.findByPlayerId("testId"));
    }
}