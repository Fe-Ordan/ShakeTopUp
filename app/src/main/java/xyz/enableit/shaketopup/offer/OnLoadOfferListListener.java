package xyz.enableit.shaketopup.offer;


import java.util.List;

/**
 * Created by dinislam on 2/8/17.
 * email : milon@strativ.se
 */

public interface OnLoadOfferListListener {

    void onSuccess(List<Offer> offerList);

    void onFailure(String msg, Exception e);
}
