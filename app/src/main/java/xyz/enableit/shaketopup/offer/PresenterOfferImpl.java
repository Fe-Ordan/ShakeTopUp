package xyz.enableit.shaketopup.offer;

import java.util.List;

/**
 * Created by dinislam on 2/8/17.
 * email : milon@strativ.se
 */

public class PresenterOfferImpl implements PresenterOffer, OnLoadOfferListListener {

    private ViewOffer mOfferView;
    private ModelOffer mOfferModel;

    public PresenterOfferImpl(ViewOffer offerView) {
        this.mOfferView = offerView;
        this.mOfferModel = new ModelOfferImpl();
    }

    @Override
    public void loadOffer(int lastOfferItem) {

        mOfferView.showProgress();
        mOfferModel.loadOfferFromDataSource(lastOfferItem, 1, this);
    }

    @Override
    public void onSuccess(List<Offer> offerList) {
        mOfferView.hideProgress();
        mOfferView.offerAvailableHere(offerList);
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mOfferView.hideProgress();

    }
}
