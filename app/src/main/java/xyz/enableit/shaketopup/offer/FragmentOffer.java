package xyz.enableit.shaketopup.offer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.enableit.shaketopup.R;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by dinislam on 2/8/17.
 * email : milon@strativ.se
 */

public class FragmentOffer extends Fragment implements ViewOffer {

    private View rootView;

    private PresenterOfferImpl offerPresenter;
    private static int start = 0;
    private LinearLayoutManager mLayoutManager;
    private AdapterOffer adapterOffer;
    private int pageIndex = 0;
    private List<Offer> mData;

    @Bind(R.id.home_rv_offer)
    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        adapterOffer = new AdapterOffer(getContext());
        mRecyclerView.setAdapter(adapterOffer);

        adapterOffer.setOnItemClickListener(mOnItemClickListener);
        mRecyclerView.addOnScrollListener(mOnScrollListener);

        offerPresenter.loadOffer(start);

        return rootView;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        offerPresenter = new PresenterOfferImpl(this);

    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == adapterOffer.getItemCount()
                    && adapterOffer.isShowFooter()) {
                //加载更多
                //LogUtils.d(TAG, "loading more data");
                offerPresenter.loadOffer(pageIndex + 1);
            }
        }
    };

    private AdapterOffer.OnItemClickListener mOnItemClickListener = new AdapterOffer.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            if (mData.size() <= 0) {
                return;
            }
            Offer offer = adapterOffer.getItem(position);
            Toast.makeText(getContext(), offer.getId() + "", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), OfferDetailActivity.class);
            intent.putExtra("offer", offer);

            View transitionView = view.findViewById(R.id.ivNews);
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            transitionView, getString(R.string.transition_news_img));

            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        }
    };

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void offerAvailableHere(List<Offer> offerList) {

        adapterOffer.isShowFooter(true);
        if (mData == null) {
            mData = new ArrayList<Offer>();
        }
        mData.addAll(offerList);

        if (pageIndex == 0) {

            if (offerList.size() < 10) {
                adapterOffer.isShowFooter(false);
            }
            adapterOffer.setOfferData(mData);
        } else {
            //如果没有更多数据了,则隐藏footer布局
            if (offerList == null || offerList.size() == 0) {
                adapterOffer.isShowFooter(false);
            }
            adapterOffer.notifyDataSetChanged();
        }
        pageIndex += 10;
        // Log.d("Offer", offerList.get(0).getDetail());
    }

    @Override
    public void showErrorMsg() {

    }
}
