package acm.event.codetocreate17.Model.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import acm.event.codetocreate17.R;
import acm.event.codetocreate17.Model.Holders.AbouUsHolder;
import acm.event.codetocreate17.View.Fragments.AboutFragment;

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
        String Ques[] = {"Who all can register?","What are the registration fees?","How many people can I have on my team?",
                "What tracks does this hack include?", "How do I choose my track?" , "How do I qualify for the pitching round?", "What is the quiz about ?"
                , "What happens if I rank last during the quiz?", "What all can I win?", "What if I get hungry or need internet ? "};
        return Ques;


    }
    public String[] getAnswers()
    {
        String Ans[] = {"Any and all students from all over the country can register. We welcome you all!",
                "Nothing, it’s absolutely free!",
                "You can have 2 to 4 people per team. You should either create your own team or accept invitations from other team admins.",
                "Code2Create incorporates four main areas of interest: \n\n" +
                        "-FinTech \n" +
                        "-AR/VR \n" +
                        "-Clean Energy \n" +
                        "-Health Care","When you register we will take your preferences for each track and allot your track on a first-come-first-served basis. You will also select a problem statement from a given set."
                ,"There will be two inspections conducted during the hack. After the first inspection, we will put the bottom 25 teams on the red list. If these teams do not show any improvement by the second inspection, they will be eliminated. Around 35 teams will pitch their ideas.",
                "The quiz is for determining the final pitching order. It is based on GK and your Googling skills.",
                "Don’t worry! We will simply ask you to pitch your idea first. We hope you are fast with powerpoint."
                ,"Cash prizes and internships for first three teams, T-Shirts, certificates for participants, Digital Ocean credits, and stickers.",
                "We’ll provide you with delicious food at regular intervals (breakfast, lunch, dinner and snacks) along with beverages. Also, you will be provided with free access to our beloved internet facility, VOLSBB.",};
        return Ans;
    }
    public static String getTimelineEvent(int i)
    {
        String Event[] = {"Registration","Introduction","Hack begins","Dinner","Beverage period",
                "Breakfast break","Fun activity","Lunch break","Technical inspection - 1","Red List Announcement",
                "Snacks break","Fun activity","Dinner break","Sponsor presentation","Quiz",
        "Technical inspection - 2","Final pitch list","Final pitch","Hack ends"};
        return Event[i];

    }
    public static String getTimelineDate(int i)
    {
        String Date[] = {"2017-04-13 19:00","2017-04-13 21:00","2017-04-13 22:00","2017-04-13 23:30","2017-04-14 00:00",
                "2017-04-14 9:00","2017-04-14 11:30","2017-04-14 13:00","2017-04-14 15:00",
                "2017-04-14 17:00","2017-04-14 18:00","2017-04-14 19:30","2017-04-14 21:00","2017-04-14 22:00","2017-04-14 23:30"
        ,"2017-04-15 2:00","2017-04-15 6:00","2017-04-15 8:30","2017-04-15 12:00","2017-04-15 18:00"};
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
    public static String getSponsorTitle(int i)
    {
        String title[] = {"Title Sponsor","Title Sponsor","Cloud Patner","Online Patner"};
        return title[i];

    }

    public static int pathToImage(int i)
    {
        int title[] = {R.drawable.aisec_tt,R.drawable.oil_t,R.drawable.digitalocean,R.drawable.hackerrank};
        return title[i];

    }

    public String[] getCouponTitles() {
        String[] couponTitles = new String[] {"C\nO\nF\nF\nE\nE", "B\nR\nE\nA\nK\nF\nA\nS\nT", "L\nU\nN\nC\nH", "S\nN\nA\nC\nK\nS", "D\nI\nN\nN\nE\nR"};
        return couponTitles;
    }

    public int[] getCouponPrimaryImages() {
        int[] couponsIds = new int[] {R.drawable.ic_coffee, R.drawable.ic_breakfast, R.drawable.ic_lunch, R.drawable.ic_snacks, R.drawable.ic_spaghetti};
        return couponsIds;
    }

    public enum OrderStatus {

        COMPLETED,
        ACTIVE,
        INACTIVE;

    }
    public enum Orientation {

        VERTICAL

    }

    public static AboutFragment.Group getFacultyOrganaiser(AboutFragment aboutFragment) {
        List<AbouUsHolder> childList=new ArrayList<>();
        AbouUsHolder child1=new AbouUsHolder();
        child1.setName("Dr. Aswani Kumar Cherukuri");
        child1.setDesignation("Dean\n" +
                "School of Information Technology & Engineering");
        child1.setImageResource(R.drawable.dean_site);
        AbouUsHolder child2=new AbouUsHolder();
        child2.setName("Prof. H.R. Vishwakarma");
        child2.setDesignation("Faculty Sponsor\n" +
                "ACM VIT Student Chapter");
        child2.setImageResource(R.drawable.hrv);
        childList.add(child1);
        childList.add(child2);
        AboutFragment.Group group= aboutFragment.new Group(childList);
        group.setName("Faculty Organisers");
        return group;
    }

    public static AboutFragment.Group getStudentOrganiser(AboutFragment aboutFragment){
        List<AbouUsHolder> childList=new ArrayList<>();
        AbouUsHolder child1=new AbouUsHolder();
        child1.setName("Abhinav Das");
        child1.setDesignation("President");
        child1.setImageResource(R.drawable.abhinav);
        AbouUsHolder child2=new AbouUsHolder();
        child2.setName("Pranay Gupta");
        child2.setDesignation("Vice President");
        child2.setImageResource(R.drawable.pranay);
        AbouUsHolder child3=new AbouUsHolder();
        child3.setName("Ashwini Purohit");
        child3.setDesignation("Technical Head");
        child3.setImageResource(R.drawable.ashwini);
        AbouUsHolder child4=new AbouUsHolder();
        child4.setName("Rahul Nigam");
        child4.setDesignation("Design Head");
        child4.setImageResource(R.drawable.rahul);
        AbouUsHolder child5=new AbouUsHolder();
        child5.setName("Rishi Raj");
        child5.setDesignation("Deputy Technical Head");
        child5.setImageResource(R.drawable.rishi);
        AbouUsHolder child6=new AbouUsHolder();
        child6.setName("Mugdha Pandaya");
        child6.setDesignation("General Secretary");
        child6.setImageResource(R.drawable.mugdha);
        AbouUsHolder child7=new AbouUsHolder();
        child7.setName("Tanish Noah");
        child7.setDesignation("Treasurer");
        child7.setImageResource(R.drawable.tanish);
        AbouUsHolder child8=new AbouUsHolder();
        child8.setName("Aarti Susan Kuruvilla");
        child8.setDesignation("Communication Head");
        child8.setImageResource(R.drawable.aarti);
        AbouUsHolder child9=new AbouUsHolder();
        child9.setName("Mallika Rai");
        child9.setDesignation("University Relations");
        child9.setImageResource(R.drawable.mallika);
        AbouUsHolder child10=new AbouUsHolder();
        child10.setName("Lekhani Ray");
        child10.setDesignation("Membership Coordinator");
        child10.setImageResource(R.drawable.lekhani);
        childList.add(child1);
        childList.add(child2);
        childList.add(child3);
        childList.add(child4);
        childList.add(child5);
        childList.add(child6);
        childList.add(child7);
        childList.add(child8);
        childList.add(child9);
        childList.add(child10);
        AboutFragment.Group group= aboutFragment.new Group(childList);
        group.setName("Student Organisers");
        return group;
    }

    public static AboutFragment.Group getContacts(AboutFragment aboutFragment)
    {

        List<AbouUsHolder> childList=new ArrayList<>();
        AbouUsHolder child1=new AbouUsHolder();
        child1.setName("Facebook");
        child1.setContact(true);
        child1.setImageResource(R.drawable.ic_facebook);
        child1.setDesignation("http://facebook.com/acm.vitu");

        AbouUsHolder child2=new AbouUsHolder();
        child2.setName("Website");
        child2.setContact(true);
        child2.setImageResource(R.drawable.ic_website);
        child2.setDesignation("http://c2c.acmvit.in");



        AbouUsHolder child3=new AbouUsHolder();
        child3.setName("Mail");
        child3.setContact(true);
        child3.setImageResource(R.drawable.ic_email);
        child3.setDesignation("mailto:outreach@acmvit.in");

        AbouUsHolder child4=new AbouUsHolder();
        child4.setName("CALL");
        child4.setContact(true);
        child4.setImageResource(R.drawable.ic_call);
        child4.setDesignation("tel:919585386705");


        childList.add(child1);
        childList.add(child2);
        childList.add(child3);
        childList.add(child4);



        AboutFragment.Group group= aboutFragment.new Group(childList);
        group.setName("Contact Us");
        return group;
    }




}
