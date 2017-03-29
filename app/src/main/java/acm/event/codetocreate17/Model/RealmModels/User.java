package acm.event.codetocreate17.Model.RealmModels;

/**
 * Created by Sourish on 29-03-2017.
 */

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject{
    @PrimaryKey
    public String email;
    public String name;
    public String gender;
    public boolean hasTeam;
    public String teamName;
    public boolean isLeader;
    public RealmList<TeamMember> teamMembers;
}
