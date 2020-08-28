package Entity;

import io.vertx.core.net.NetSocket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientsListTest {

    ClientsList cl;

    @BeforeEach
    void setUp() {
        cl = new ClientsList();
        cl.add(new ClientConnector(null,"h3AhO3"));
        cl.add(new ClientConnector(null,"iBuuyX"));
        cl.add(new ClientConnector(null,"tu3iGp"));
        cl.add(new ClientConnector(null,"Cdgilw"));
    }

    @Test
    void findById() {
        ClientConnector cc = new ClientConnector(null, "tester");
        cl.add(cc);

        assertEquals(cl.findById(cc.getId()),cc);
    }

    @Test
    void findById2() {
        assertNull(cl.findById("testId"));
    }

    @Test
    void getCilentsNicksAndPings() {
        assertEquals(cl.getCilentsNicksAndPings(),"h3AhO3|1000:iBuuyX|1000:tu3iGp|1000:Cdgilw|1000");
    }

    @Test
    void getCilentsNicksAndPings2() {
        ClientConnector cc = new ClientConnector(null, "test");
        cc.setLastPing(25l);
        cl.add(cc);

        assertEquals(cl.getCilentsNicksAndPings(),"h3AhO3|1000:iBuuyX|1000:tu3iGp|1000:Cdgilw|1000:test|25");
    }
}