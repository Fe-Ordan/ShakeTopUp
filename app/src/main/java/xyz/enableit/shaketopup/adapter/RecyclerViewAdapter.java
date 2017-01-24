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
import xyz.enableit.shaketopup.model.Offer;

/**
 * Created by dinislam on 1/25/17.
 * email : milon@strativ.se
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RadioViewHolder> {

    private Context context;

    private List<Offer> offerList;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
        offerList = new ArrayList<>();
    }

    public void setOfferList(List<Offer> offerList) {
        this.offerList = offerList;
        notifyDataSetChanged();
    }

    @Override
    public RadioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_offer, parent, false);
        RadioViewHolder viewHolder = new RadioViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RadioViewHolder holder, final int position) {

        Offer offer = offerList.get(position);
        holder.offerTitle.setText(offer.getTitle());
        holder.offerDetail.setText(offer.getDetail());

    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    public class RadioViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.offer_title)
        TextView offerTitle;

        @Bind(R.id.offer_detail)
        TextView offerDetail;


        @Bind(R.id.imageview_radio_logo)
        ImageView offerOperator;

        public RadioViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}