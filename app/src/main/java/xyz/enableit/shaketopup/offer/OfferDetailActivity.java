package xyz.enableit.shaketopup.offer;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import xyz.enableit.shaketopup.R;

/**
 * Created by dinislam on 2/9/17.
 * email : milon@strativ.se
 */
public class OfferDetailActivity extends SwipeBackActivity {

    private Offer mOffer;
    private SwipeBackLayout mSwipeBackLayout;

    @Bind(R.id.toolbar_detail)
    Toolbar toolbar;
    @Bind(R.id.htNewsContent)
    HtmlTextView mTVNewsContent;
    @Bind(R.id.ivImage)
    ImageView featureImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_offer_detail);
        ButterKnife.bind(this);

        mOffer = (Offer) getIntent().getSerializableExtra("offer");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mTVNewsContent.setHtmlFromString(mOffer.getDetail(), new HtmlTextView.RemoteImageGetter());

        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeSize(getResources().getDisplayMetrics().widthPixels);
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);



        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(mOffer.getTitle());

        Glide.with(this).load(mOffer.getImageUrl()).into(featureImage);
    }

}

