package acm.event.codetocreate17.Utility.Adapters;


        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

        import java.util.List;

import acm.event.codetocreate17.Model.Data.DataGenerator;
import acm.event.codetocreate17.R;

public class SponsorAdapter extends RecyclerView.Adapter<SponsorAdapter.MyViewHolder> {

    private List<DataGenerator> sponsorsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView sponsor;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.sponsor_title);
            sponsor = (ImageView) view.findViewById(R.id.sponsor_image);
            ;
        }
    }


    public SponsorAdapter(List<DataGenerator> sponsorsList) {
        this.sponsorsList = sponsorsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_sponsor_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(sponsorsList.get(position).getSponsorTitle(position));
        holder.sponsor.setImageResource(sponsorsList.get(position).pathToImage(position));
    }

    @Override
    public int getItemCount() {
        return sponsorsList.size();
    }
}