package acm.event.codetocreate17.Model.RealmModels;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Sourish on 30-03-2017.
 */

public class TeamMember extends RealmObject {
    @PrimaryKey
    public String email;
    public String name;
    public boolean isLeader;
}
