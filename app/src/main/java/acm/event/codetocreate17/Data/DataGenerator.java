package acm.event.codetocreate17.Data;

/**
 * Created by Sourish on 19-03-2017.
 */

public class DataGenerator {
    public String splashData(int i, int j) {
        String text1[] = {"36", "300+", "₹30k"};
        String text2[] = {"Hours Hack", "Participants", "Prize Money"};
        if(i == 0)
            return text1[j];
        else
            return  text2[j];
    }
}
