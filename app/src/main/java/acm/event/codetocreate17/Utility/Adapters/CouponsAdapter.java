package acm.event.codetocreate17.Utility.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import acm.event.codetocreate17.Model.Holders.CouponsHolder;
import acm.event.codetocreate17.R;

/**
 * Created by Sourish on 26-03-2017.
 */

public class CouponsAdapter extends RecyclerView.Adapter {
    ArrayList<String> couponTitles;
    ArrayList<Integer> primaryimages;
    /*ArrayList<Integer> secondaryImages;
    ArrayList<Integer> barcodes;*/

    public CouponsAdapter(ArrayList<String> couponTitles, ArrayList<Integer> primaryimages/*, ArrayList<Integer> secondaryImages, ArrayList<Integer> barcodes*/) {
        this.couponTitles = couponTitles;
        this.primaryimages = primaryimages;
        //this.secondaryImages = secondaryImages;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CouponsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_coupons_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CouponsHolder couponsHolder = (CouponsHolder) holder;
        couponsHolder.title.setText(couponTitles.get(position));
        couponsHolder.primaryImage.setImageResource(primaryimages.get(position));
    }

    @Override
    public int getItemCount() {
        return couponTitles.size();
    }
};
