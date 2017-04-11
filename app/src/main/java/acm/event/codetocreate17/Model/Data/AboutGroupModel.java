package acm.event.codetocreate17.Model.Data;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sourish on 11-04-2017.
 */

public class AboutGroupModel implements ParentListItem {
    private ArrayList<AboutModel> mItems;
    private String name;

    public AboutGroupModel(ArrayList<AboutModel> items) {
        mItems = items;
    }

    @Override
    public List<?> getChildItemList() {
        return mItems;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
