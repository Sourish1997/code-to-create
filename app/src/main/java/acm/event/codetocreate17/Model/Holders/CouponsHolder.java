package acm.event.codetocreate17.Model.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;

import acm.event.codetocreate17.R;

/**
 * Created by Sourish on 24-03-2017.
 */

public class CouponsHolder extends RecyclerView.ViewHolder {
    public ImageView barcode, primaryImage;
    public TextView title;
    public FoldingCell foldingCell;

    public CouponsHolder(View itemView) {
        super(itemView);
        barcode = (ImageView) itemView.findViewById(R.id.coupon_barcode);
        primaryImage = (ImageView) itemView.findViewById(R.id.coupon_primary_image);
        title = (TextView) itemView.findViewById(R.id.coupon_title);
        foldingCell = (FoldingCell) itemView.findViewById(R.id.coupon_folding_cell);
        foldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foldingCell.toggle(false);
            }
        });
    }
}