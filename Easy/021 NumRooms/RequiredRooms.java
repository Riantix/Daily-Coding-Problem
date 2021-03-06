import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class RequiredRooms {


    // Requires: room be size [x][2], schedule size [2]
    // Returns true if the time defined by schedule does not conflict with any other schedules in room
    // Returns false otherwise
    // Assumes the Intervals in room are in sorted order
    // Assumes schedule has a later start time than any other interval in room
    public static boolean canFit(ArrayList<Interval> room, Interval schedule) {
	Interval last = room.get(room.size()-1);
	return schedule.start > last.end;
    }

    public static int result(ArrayList<Interval> intervals) {
    // Sorts the intervals, then adds them one by one to rooms.
	// If an interval can fit, it goes in. If not, it goes to the next room/creates new one
	ArrayList<ArrayList<Interval>> many_rooms = new ArrayList<ArrayList<Interval>>();
	Collections.sort(intervals);
	for (int i = 0; i < intervals.size(); i++) {
	    Interval interval = intervals.get(i);
	    boolean fits = false;
	    for (int j = 0; j < many_rooms.size(); j++) {
		ArrayList<Interval> room = many_rooms.get(j);
	        if (canFit(room, interval)) { // Shove the interval to the first room it fits
		    room.add(interval);
		    fits = true;
		    break;
		}
	    }
	    if (!fits) { // Didn't fit anywhere so we create a new room
		ArrayList<Interval> temp = new ArrayList<Interval>();
		temp.add(interval);
		many_rooms.add(temp);
	    }
	}
	return many_rooms.size();
    }



    // min = minimum value
    // max = maximum value
    // num = number of [x, y] blocks
    // size = approximate size of [x, y] block (approximately y-x)
    public static ArrayList<Interval> randomTest(int min, int max, int num, int size) {
	Random randSeed = new Random();
	int seed = randSeed.nextInt();
	Random rand = new Random(seed);
	ArrayList<Interval> testCase = new ArrayList<Interval>();
        for (int i = 0; i < num; i++) {
	    Interval temp = new Interval();
	    temp.start = rand.nextInt((int)(max * .95) - min) + min; // gives room for end to be larger. This means max >> min
	    //int range = 100;
	    // Currently testing temp[1] = i + (size * (75%-175%))
	    //temp.end = temp.start + (int)(size * ((rand.nextInt(range) - range/4) * .01 + 1));
	    temp.end = temp.start - 1;
	    while (temp.end < temp.start) {
		temp.end = rand.nextInt(max - min) + min;
	    }
	    testCase.add(temp);
	}
	return testCase;
    }

    public static void print2DArray(ArrayList<Interval> ary, int x) {
	System.out.println("Printing array of intervals, size " + x);
	for (int i = 0; i < x; i++) {
	    System.out.println("[" + ary.get(i).start + ", " + ary.get(i).end + "]");
	}
    }

    public static void main(String[] args) {
	int num = 8;
	int testCases = 10;
	for (int i = 0; i < testCases; i++) {
	    ArrayList<Interval> test = randomTest(0, 1000, num, 1000 / 6);
	    Collections.sort(test);
	    print2DArray(test, num);
	    System.out.println("" + result(test) + " rooms required\n");
	}
    }
}
