package xyz.enableit.shaketopup.offer;

import java.util.List;

/**
 * Created by dinislam on 2/8/17.
 * email : milon@strativ.se
 */

public interface ViewOffer {

    public void showProgress();

    public void hideProgress();

    public void offerAvailableHere(List<Offer> offerList);

    public void showErrorMsg();
}
