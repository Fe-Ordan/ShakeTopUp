package xyz.enableit.shaketopup.offer;

/**
 * Created by dinislam on 2/8/17.
 * email : milon@strativ.se
 */

public interface ModelOffer {

    public void loadOfferFromDataSource(int startAt, int limitTo, OnLoadOfferListListener loadOfferListListener);

}
