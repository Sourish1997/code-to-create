package acm.event.codetocreate17.Model.Data;

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
    public String getQuestion(int i)
    {
        String Ques[] = {"When will the Registration start?","What is the Registration Fees?","What about food and beverages?",
                "Who all can participate?", "What are the skills required?" , "Is it an individual or team-challenge?"};
        return Ques[i];


    }
    public String getAnswer(int i)
    {
        String Ans[] = {"We will open the registrations very soon! Keep following us.","Its free. All are welcome.",
                "Its all on us! We would provide you with everything you need. We need you to concentrate on the code.",
                "Students irrespective of their college/branch can take part in this event."
                ,"Even if you don't know coding we will be having technical sessions before the event and mentors during the event."
                ,"Its a team Challenge. It Should have a minimum of 2 and a maximum of 4 members."};
        return Ans[i];
    }



}
