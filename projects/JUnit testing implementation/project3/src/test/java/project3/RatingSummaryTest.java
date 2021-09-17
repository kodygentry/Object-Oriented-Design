package project3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

class RatingSummaryTest {

	public RatingSummary rs;
	
	@BeforeEach
	public void setup() {
		rs = new RatingSummary("A1EE2E3N7PW666", 2);
	}

	@Test
	void testSortStatsNotSame() {
		float actualOutput = 3;
		rs.setList(3, 2);
		
		Assertions.assertEquals(rs.sortStats(), actualOutput);
	}

	@AfterEach
	public void cleanup() {
		rs = null;
	}

	// new test functions

	@Test
	void getNodeIDTest(){
		String this_id = "A1EE2E3N7PW666";
		String get_id = rs.getNodeID();
		assertEquals(this_id, get_id);
	}
	
	
	@Test
	void getDegreeTest() {
		long this_degree = 2;
		long get_degree = rs.getDegree();
		assertEquals(this_degree, get_degree);
	}
	
	@Test
	void testSetDegree() {
		long setDegree = 0;
		rs.setDegree(setDegree);
		long getDegree = rs.getDegree();
		assertEquals(setDegree, getDegree);
	}
	
	
	@Test
	void testSetDegree2(){
		Rating r1 = new Rating("ABC", "22222", 6.9f);
		Rating r2 = new Rating("11111", "CBD", 420f);
		
		List list = new ArrayList<>();
		list.add(r1); 
		list.add(r2);
		
		rs.setDegree(list);
		
		long degree = 0;
		long getDegree = rs.getDegree();
		
		assertEquals(degree, getDegree);
	}
	
	
	@Test
	void testCreateList() {
		List<Float> getList = rs.createList();
		int initSize = 0;
		
		assertEquals(initSize, getList.size());
	}
	
	@Test
	void testCreateList2() 
	{
		List<Float> getList = rs.createList(6.9f, 420f);
		float f1 = Float.valueOf(6.9f);
		
		int size = 2;
		
		assertEquals(size, getList.size());
		assertEquals(f1, getList.get(0));
	}
	
	
	@Test
	void testSetList(){
		rs.setList();
		List list = rs.getList();
		
		int size = 0;
		int get_size = list.size();
		
		assertEquals(size, get_size);
	}
	
	@Test
	void testSetList2(){
		rs.setList(420f, 6.9f);
		List list = rs.getList();
		
		int size = 2;
		int get_size = list.size();
		
		float f1 = Float.valueOf(420f);
		float f2 = Float.valueOf(6.9f);
		
		float get_f1 = (float) list.get(0);
		float get_f2 = (float) list.get(1);
		
		assertEquals(size, get_size);
		assertEquals(f1, get_f1);
		assertEquals(f2, get_f2);
	}

	
	@Test
	void testGetList() {
		rs.setList();
		List list = rs.getList();
		
		int initial_size = 0;
		assertEquals(initial_size, list.size());
	}
	
	@Test
	void testGetList2() {
		rs.setList(6.9f, 420f);
		List list = rs.getList();
		
		int initial_size = 2;
		float f1 = Float.valueOf(6.9f);
		float f2 = Float.valueOf(420f);
		
		assertEquals(initial_size, list.size());
		assertEquals(f1, list.get(0));
		assertEquals(f2, list.get(1));
	}
	
	
	@Test
	void testToString() {
		rs.setList(6.9f, 420f);
		
		String string = "A1EE2E3N7PW666,2,6.900,420.000\n";
		String getString = rs.toString();
		
		assertEquals(string, getString);
	}
	
	@Test
	void testCollectReviewerStats()
	{
		RatingSummary rating2 = new RatingSummary("B123", 3, 8.9f, 7.8f);
		
		Rating r1 = new Rating("B123", "222", 8.3f);
		Rating r2 = new Rating("333", "B123", 9.3f);
		
		List list = new ArrayList<>();
		list.add(r1); list.add(r2);
		
		rating2.collectStats(list);
		
		List<Float> get_list = rating2.getList();
		float f1 = Float.valueOf(9.3f);
		float get_f1 = get_list.get(0);
		
		int size_ = 2;
		
		assertEquals(size_, get_list.size());
		assertEquals(f1, get_f1);
	}
	
	
	@Test
	void testAvgScore(){
		RatingSummary rating = new RatingSummary("AAAA", 3, 8.9f, 7.8f);
		float score = 1.0f;
		float getScore = Math.round(rating.avgScore());
		
		assertEquals(score, getScore);
	}
	
	@Test
	void testSortStats(){
		RatingSummary rating = new RatingSummary("AAAA", 3, 8.9f, 7.8f);
		float score = 8.9f;
		float getScore = rating.sortStats();
		
		assertEquals(score, getScore);
	}

}
