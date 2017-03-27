package acm.event.codetocreate17.Utility.Adapters;

        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import java.util.ArrayList;

        import acm.event.codetocreate17.Model.Data.DataGenerator;
        import acm.event.codetocreate17.R;

public class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .DataGeneratorHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<DataGenerator> mDataset;
    private static MyClickListener myClickListener;

    public static class DataGeneratorHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView label;
        TextView dateTime;

        public DataGeneratorHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.textView);
            dateTime = (TextView) itemView.findViewById(R.id.textView2);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyRecyclerViewAdapter(ArrayList<DataGenerator> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataGeneratorHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);

        DataGeneratorHolder DataGeneratorHolder = new DataGeneratorHolder(view);
        return DataGeneratorHolder;
    }

    @Override
    public void onBindViewHolder(DataGeneratorHolder holder, int position) {

            holder.label.setText(mDataset.get(position).getQuestion(position));
            holder.dateTime.setText(mDataset.get(position).getAnswer(position));

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}