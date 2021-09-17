package project3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;
  
class RatingSummaryHamcrestTest {

    public RatingSummary rs;
	
	@BeforeEach
	public void setup() {
		rs = new RatingSummary("A1EE2E3N7PW666", 2);
	}
 
	// From class RatingSummary
	@Test
	void testCreateList() {
		List<Float> list =  rs.createList();
		assertThat(ArrayList.class, equalTo(list.getClass()));
	}

	@AfterEach
	public void cleanup() {
		rs = null;
	}

    // new implemeneted tests

    @Test
    void testGetNodeID(){
        String thisID = "A1EE2E3N7PW666";
        String getID = rs.getNodeID(); 
        assertEquals(thisID, getID);
    }

    @Test
    void testGetDegree(){
        long thisDegree = 2;
        long getDegree = rs.getDegree();
        assertEquals(thisDegree, getDegree);
    }

    @Test
    void testSetDegree(){
        long setDegree = 100;
        rs.setDegree(setDegree);
        long getDegree = rs.getDegree();
        assertEquals(setDegree, getDegree);
    }

    @Test
    void testGetList(){
        rs.setList();
        List list = rs.getList();
        int initSize = 0;
        assertEquals(initSize, list.size());
    }

    @Test
    void testGetList2(){
        rs.setList(6.9f, 420f);
        List list = rs.getList();
        int initSize = 2;

        float f1 = Float.valueOf(6.9f);
        float f2 = Float.valueOf(420f);

        assertEquals(initSize, list.size());
        assertEquals(f1, list.get(0));
        assertEquals(f2, list.get(1));
    }

    @Test
    void testCreateList2(){
        List<Float> get_list = rs.createList();
        int initSize = 0;
        assertEquals(initSize, get_list.size());
    }

    @Test
    void testCreateList3(){
        List<Float> getList = rs.createList(6.9f, 420f);
        float f1 = Float.valueOf(6.9f);
        int size = 2;
        assertEquals(size, getList.size());
        assertEquals(f1, getList.get(0));
    }

    @Test
    void testPrint(){
        rs.setList(6.9f, 420f);
        String string = "A1EE2E3N7PW666,2,6.900,420.000\n";
        String getStr = rs.toString();
        assertEquals(string, getStr);
    }

    // @Test
    // void testGetNodeID2(){
	// rq = new RatingSummary("A4BB1Q6A9RQ111", 4);
    //     String thisID = "A4BB1Q6A9RQ111";
    //     String getID = rq.getNodeID(); 
    //     assertEquals(thisID, getID,"The GetNodeId Function works!");
    // }
    // @Test
    // void testPrintStats(){
	// rs.setList(6.9f, 420f);
    //     String string = "A1EE2E3N7PW666,2,6.900,420.000\n";
    //     String getStr = rs.printStats();
	//     assertEquals(string, getStr);
    // }
} 