package xyz.enableit.shaketopup.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.enableit.shaketopup.R;
import xyz.enableit.shaketopup.listener.ListItemClickListener;
import xyz.enableit.shaketopup.model.UssdCode;

/**
 * Created by dinislam on 1/25/17.
 * email : milon@strativ.se
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.OfferViewHolder> {

    private Context context;
    private List<UssdCode> ussdCodeList = new ArrayList<>();
    private ListItemClickListener listItemClickListener;

    public RecyclerViewAdapter(Context context, ListItemClickListener listItemClickListener) {
        this.context = context;
        this.listItemClickListener = listItemClickListener;

    }

    public void setUssdCodeList(List<UssdCode> ussdCodeList) {
        this.ussdCodeList = ussdCodeList;
        notifyDataSetChanged();
    }

    @Override
    public OfferViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_offer, parent, false);
        OfferViewHolder viewHolder = new OfferViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OfferViewHolder holder, final int position) {

        UssdCode ussdCode = ussdCodeList.get(position);

        //set data
        holder.codeDetail.setText(ussdCode.getDescription());
        holder.shortCode.setText(ussdCode.getShortCode());

    }

    @Override
    public int getItemCount() {
        return ussdCodeList.size();
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.code_detail)
        TextView codeDetail;

        @Bind(R.id.short_code)
        TextView shortCode;


        @Bind(R.id.arrow)
        ImageView arrow;


        public OfferViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            //set click listener
            //itemView.setOnClickListener(this);
            //codeDetail.setOnClickListener(this);
            arrow.setOnClickListener(this);
            //shortCode.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            listItemClickListener.clickPosition(getAdapterPosition(), view.getId());
        }
    }

}