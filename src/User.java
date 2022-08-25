import java.util.concurrent.atomic.AtomicInteger;

public class User implements Player{

    /*
    This class is the User player.
    It provides no function but is needed to insert into the game constructor.
    This is because the user places a counter using the GUI, the method for which is in the game class.
     */

    @Override

    public int[][] place(int[][] m, boolean isRed, AtomicInteger row, AtomicInteger col) {

        return m;

    }



}