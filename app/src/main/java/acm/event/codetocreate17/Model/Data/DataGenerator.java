package acm.event.codetocreate17.Model.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sourish on 19-03-2017.
 */

public class DataGenerator {
    public DataGenerator()
    {}
    public DataGenerator(String text1, String text2){

    }
    public String splashData(int i) {
        String text[] = {"Innovate.Learn.", "Create."};
        return text[i];
    }
    public String[] getQuestions()
    {
        String Ques[] = {"When will the Registration start?","What is the Registration Fees?","What about food and beverages?",
                "Who all can participate?", "What are the skills required?" , "Is it an individual or team-challenge?"};
        return Ques;


    }
    public String[] getAnswers()
    {
        String Ans[] = {"We will open the registrations very soon! Keep following us.","Its free. All are welcome.",
                "Its all on us! We would provide you with everything you need. We need you to concentrate on the code.",
                "Students irrespective of their college/branch can take part in this event."
                ,"Even if you don't know coding we will be having technical sessions before the event and mentors during the event."
                ,"Its a team Challenge. It Should have a minimum of 2 and a maximum of 4 members."};
        return Ans;
    }
    public static String getTimelineEvent(int i)
    {
        String Event[] = {"Hackathon Event 1","Hackathon Event 2","Hackathon Event 3","Hackathon Event 4","Hackathon Event 5",
                "Hackathon Event 6","Hackathon Event 7","Hackathon Event 8","Hackathon Event 9"};
        return Event[i];

    }
    public static String getTimelineDate(int i)
    {
        String Date[] = {"2017-03-28 9:00","2017-03-28 18:00","2017-03-28 23:00","2017-03-29 00:46",
                "2017-04-14 9:00","2017-04-14 9:00","2017-04-14 9:00","2017-04-14 9:00",
                "2017-04-14 9:00","2017-04-14 9:00"};
        return Date[i];

    }
    public static OrderStatus checkOderStatus(String s1,String s2 ) {
        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date date1 = sdf.parse(s1);
            Date date2 = sdf.parse(s2);
            Calendar cal = Calendar.getInstance();
            Date sys = sdf.parse(sdf.format(cal.getTime()));
            if (sys.compareTo(date1) == -1)
                //incomplete or inactive
                return OrderStatus.INACTIVE;
            else if (sys.compareTo(date2) == 0 || sys.compareTo(date2) == 1)
                //completed
                return OrderStatus.COMPLETED;
            else
                //on going or active
                return OrderStatus.ACTIVE;
            //if((sys.compareTo(date1)==1||sys.compareTo(date1)==0)&&sys.compareTo(date2)==-1)
            //  return 0;

        } catch (ParseException e) {
            // Exception handling goes here
            return OrderStatus.INACTIVE;
        }
    }

    public enum OrderStatus {

        COMPLETED,
        ACTIVE,
        INACTIVE;

    }
    public enum Orientation {

        VERTICAL

    }



}
