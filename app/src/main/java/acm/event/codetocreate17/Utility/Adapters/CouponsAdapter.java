package acm.event.codetocreate17.Utility.Adapters;

import android.graphics.Bitmap;
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
    ArrayList<Bitmap> qrCodes;

    public CouponsAdapter(ArrayList<String> couponTitles, ArrayList<Integer> primaryimages, ArrayList<Bitmap> qrCodes) {
        this.couponTitles = couponTitles;
        this.primaryimages = primaryimages;
        this.qrCodes = qrCodes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CouponsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_coupons_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CouponsHolder couponsHolder = (CouponsHolder) holder;
        couponsHolder.foldingCell.fold(true);
        couponsHolder.title.setText(couponTitles.get(position));
        couponsHolder.primaryImage.setImageResource(primaryimages.get(position));
        couponsHolder.barcode.setImageBitmap(qrCodes.get(position));
    }

    @Override
    public int getItemCount() {
        return couponTitles.size();
    }
};
