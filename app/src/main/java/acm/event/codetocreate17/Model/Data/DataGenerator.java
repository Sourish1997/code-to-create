package acm.event.codetocreate17.Model.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import acm.event.codetocreate17.R;
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
                return OrderStatus.INACTIVE;
            else if (sys.compareTo(date2) == 0 || sys.compareTo(date2) == 1)
                return OrderStatus.COMPLETED;
            else
                return OrderStatus.ACTIVE;
        } catch (ParseException e) {
            return OrderStatus.INACTIVE;
        }
    }

    public static String getSponsorTitle(int i)
    {
        String title[] = {"Title Sponsor","Title Sponsor","Cloud Patner","Sponsor","Sponsor","Sponsor","Collaborator",
                "Collaborator","Collaborator"};
        return title[i];

    }

    public static int pathToImage(int i)
    {
        int title[] = {R.drawable.transits,R.drawable.tricentis,R.drawable.digitalocean,R.drawable.oil_t,R.drawable.dakshinamurti
        ,R.drawable.winuall,R.drawable.cardea,R.drawable.vicara,R.drawable.paypal};
        return title[i];

    }

    public String[] getCouponTitles() {
        String[] couponTitles = new String[] {"C\nO\nF\nF\nE\nE", "B\nR\nE\nA\nK\nF\nA\nS\nT", "L\nU\nN\nC\nH", "S\nN\nA\nC\nK\nS", "D\nI\nN\nN\nE\nR"};
        return couponTitles;
    }

    public int[] getCouponPrimaryImages() {
        int[] couponsIds = new int[] {R.drawable.ic_coffee, R.drawable.ic_breakfast, R.drawable.ic_lunch, R.drawable.ic_snacks, R.drawable.ic_dinner};
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

    public static AboutGroupModel getFacultyOrganaiser(AboutFragment aboutFragment) {
        ArrayList<AboutModel> childList=new ArrayList<>();
        AboutModel child1=new AboutModel();
        child1.setName("Dr. Aswani Kumar Cherukuri");
        child1.setDesignation("Dean\n" +
                "School of Information Technology & Engineering");
        child1.setImageResource(R.drawable.dean_site);
        AboutModel child2 = new AboutModel();
        child2.setName("Prof. H.R. Vishwakarma");
        child2.setDesignation("Faculty Sponsor\n" +
                "ACM VIT Student Chapter");
        child2.setImageResource(R.drawable.hrv);
        childList.add(child1);
        childList.add(child2);
        AboutGroupModel group = new AboutGroupModel(childList);
        group.setName("Faculty Organisers");
        return group;
    }

    public static AboutGroupModel getStudentOrganiser(AboutFragment aboutFragment){
        ArrayList<AboutModel> childList=new ArrayList<>();
        AboutModel children[] = new AboutModel[10];
        for(int i = 0; i < 10; i++)
            children[i] = new AboutModel();
        children[0].setName("Abhinav Das");
        children[0].setDesignation("President");
        children[0].setContact(false);
        children[0].setImageResource(R.drawable.abhinav);
        children[1].setName("Pranay Gupta");
        children[1].setDesignation("Vice President");
        children[1].setContact(false);
        children[1].setImageResource(R.drawable.pranay);
        children[2].setName("Ashwini Purohit");
        children[2].setDesignation("Technical Head");
        children[2].setContact(false);
        children[2].setImageResource(R.drawable.ashwini);
        children[3].setName("Rahul Nigam");
        children[3].setDesignation("Design Head");
        children[3].setContact(false);
        children[3].setImageResource(R.drawable.rahul);
        children[4].setName("Rishi Raj");
        children[4].setDesignation("Deputy Technical Head");
        children[4].setContact(false);
        children[4].setImageResource(R.drawable.rishi);
        children[5].setName("Mugdha Pandaya");
        children[5].setDesignation("General Secretary");
        children[5].setContact(false);
        children[5].setImageResource(R.drawable.mugdha);
        children[6].setName("Tanish Noah");
        children[6].setDesignation("Treasurer");
        children[6].setContact(false);
        children[6].setImageResource(R.drawable.tanish);
        children[7].setName("Aarti Susan Kuruvilla");
        children[7].setDesignation("Communication Head");
        children[7].setContact(false);
        children[7].setImageResource(R.drawable.aarti);
        children[8].setName("Mallika Rai");
        children[8].setDesignation("University Relations");
        children[8].setContact(false);
        children[8].setImageResource(R.drawable.mallika);
        children[9].setName("Lekhani Ray");
        children[9].setDesignation("Membership Coordinator");
        children[9].setContact(false);
        children[9].setImageResource(R.drawable.lekhani);
        for(int i = 0; i < 10; i++) childList.add(children[i]);
        AboutGroupModel group= new AboutGroupModel(childList);
        group.setName("Student Organisers");
        return group;
    }

    public static AboutGroupModel getContacts(AboutFragment aboutFragment)
    {
        ArrayList<AboutModel> childList=new ArrayList<>();
        AboutModel child1=new AboutModel();
        child1.setName("Facebook");
        child1.setContact(true);
        child1.setImageResource(R.drawable.ic_facebook);
        child1.setDesignation("http://facebook.com/acm.vitu");

        AboutModel child2=new AboutModel();
        child2.setName("Website");
        child2.setContact(true);
        child2.setImageResource(R.drawable.ic_website);
        child2.setDesignation("http://c2c.acmvit.in");

        AboutModel child3=new AboutModel();
        child3.setName("Mail");
        child3.setContact(true);
        child3.setImageResource(R.drawable.ic_email);
        child3.setDesignation("mailto:outreach@acmvit.in");

        AboutModel child4=new AboutModel();
        child4.setName("CALL");
        child4.setContact(true);
        child4.setImageResource(R.drawable.ic_call);
        child4.setDesignation("tel:919791085772");


        childList.add(child1);
        childList.add(child2);
        childList.add(child3);
        childList.add(child4);

        AboutGroupModel group = new AboutGroupModel(childList);
        group.setName("Contact us");
        return group;
    }

    public String[] quizQuestions() {
        String[] quizQuestions = new String[] {
                "What is the jumper setting on a SCSI device to configure it to use the fourth SCSI id?",
                "What tool is used to test serial and parallel ports?",
                "What device prevents power interruptions, resulting in corrupted data?",
                "A sound card typically uses which IRQ?",
                "What form of transmissions do modems use?",
                "Which of the following is NOT one of the four major data processing functions of a computer?",
                "What tag, when placed on an animal, can be used to record and track all its movements?",
                "Surgeons can perform delicate operations by manipulating devices through computers instead of manually. This technology is known as:",
                "Technology no longer protected by copyright, available to everyone, is considered to be",
                "What is the study of molecules and structures whose size ranges from 1 to 100 nanometers?",
                "Science that attempts to produce machines that display the same type of intelligence as humans:",
                "Data that has been organized or presented in a meaningful fashion:",
                "The name for the way that computers manipulate data into information is called:",
                "Computers gather data, which means that they allow users to ____________ data.",
                "After a picture has been taken with a digital camera and processed appropriately, the actual print of the picture is considered:",
                "Computers use the ____________ language to process data.",
                "Computers process data into information by working exclusively with:",
                "In the binary language, each letter of the alphabet, each number and each special character is made up of a unique combination of:",
                "What does FDISK do?",
                "Which of the following conditions most increases the likelihood that ESD will occur?",
                "Which IRQ does the hard disk controller commonly use?",
                "IRQ 6 is commonly assigned to:",
                "Which of the following is NOT a type of RAM?",
                "Which is NOT typically a Field Replaceable Unit?",
                "How many pins are present on a VGA?",
                "What component would most likely cause a parity error?",
                "How many devices can be used on a single SCSI bus?",
                "What helps prevent power surges?",
                "How many pins do IDE cables have?",
                "The first mechanical computer designed by Charles Babbage was called?"
        };
        return quizQuestions;
    }

    public String[][] quizQuestionOptions() {
        String[][] quizOptions = new String[][]{
                {"010", "110", "011", "101", "001"},
                {"High volt probe", "Cable scanner", "Loop backs (wrap plugs)", "Sniffer"},
                {"Battery back-up unit", "Surge protector", "Multiple SIMMs strips", "Data guard system"},
                {"6", "5", "15", "1"},
                {"Synchronous", "Asynchronous", "Timed interval", "Bank"},
                {"Gathering data", "Processing data into information", "Analyzing the data", "Storing the data"},
                {"POS", "RFID", "PPS", "GPS"},
                {"Robotics", "Computer forensics", "Simulation", "Forecasting"},
                {"Proprietary", "Open", "Experimental", "In the public domain"},
                {"Nanoscience", "Microelectrodes", "Computer forensics", "Artificial intelligence"},
                {"Nanoscience", "Nanotechnology", "Simulation", "Artificial intelligence"},
                {"Process", "Software", "Storage", "Information"},
                {"Programming", "Processing", "Storing", "Organizing"},
                {"Present", "Input", "Output", "Store"},
                {"Data", "Output", "Input", "Process"},
                {"Processing", "Kilobyte", "Binary", "Representational"},
                {"Multimedia", "Words", "Characters", "Numbers"},
                {"8 bytes", "8 kilobytes", "8 characters", "8 bits"},
                {"Performs low-level formatinf of hard drive", "Fixes bad sectors on hard drive", "Recovers lost clusters on hard drive", "Creates partitions on hard drive"},
                {"Hot, dry conditions", "Cool, damp conditions", "Cool, dry conditions", "Hot, damp conditions"},
                {"14", "1", "2", "11"},
                {"Sound card", "COM1", "Floppy drive controller", "LPT1"},
                {"SIMM", "DIMM", "ROM", "SLIPP"},
                {"System ROM", "Power supply", "System chassis", "Video controller"},
                {"15", "9", "25", "32"},
                {"Bad hard disk", "Bad controller", "Bad RAM", "Bad software"},
                {"1", "8", "20", "10"},
                {"Surge processor", "Spike protector", "UPS system", "High-grade multimeter"},
                {"25", "50", "100", "40"},
                {"Abacus", "Analytical Engine", "ENIAC", "IAS Machine"}
        };
        return quizOptions;
    }

    public int[] correctOptions() {
        int[] correctOptions = new int[] {
                2, 2, 0, 1, 1, 2, 1, 0, 0, 0,
                3, 3, 1, 1, 1, 2, 3, 3, 3, 0,
                0, 2, 2, 2, 0, 2, 1, 0, 3, 1
        };
        return correctOptions;
    }

    public QuizQuestionModel[] qetQuizDatabase() {
        QuizQuestionModel[] quizDatabase = new QuizQuestionModel[30];
        String[] questions = quizQuestions();
        String[][] options = quizQuestionOptions();
        int[] correctOptions = correctOptions();
        for(int i = 0; i < quizDatabase.length; i++)
            quizDatabase[i] = new QuizQuestionModel(questions[i], options[i], correctOptions[i]);
        return quizDatabase;
    }
}
