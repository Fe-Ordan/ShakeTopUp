package xyz.enableit.shaketopup.fragment;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.enableit.shaketopup.R;
import xyz.enableit.shaketopup.adapter.RecyclerViewAdapter;
import xyz.enableit.shaketopup.listener.ListItemClickListener;
import xyz.enableit.shaketopup.model.UssdCode;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentShortCode extends Fragment implements ListItemClickListener {


    private static final int TYPE = 1;
    private static final int OPERATOR_ID = 2;
    private int pageIndex = 0;

    public FragmentShortCode() {
        //Required empty public constructor
    }


    ListItemClickListener listItemClickListener;
    @Bind(R.id.home_rv_offer)
    RecyclerView recyclerView;
    List<UssdCode> array = new ArrayList<>();
    LinearLayoutManager layoutManager;
    @Bind(R.id.progress)
    ProgressBar progressBar;

    //private  recyclerView;
    private View rootView;
    private UssdCode code;
    private RecyclerViewAdapter recyclerViewAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);
        listItemClickListener = this;

        initializeViewsAdapter();

        recyclerView.addOnScrollListener(mOnScrollListener);
        setList(TYPE, OPERATOR_ID, pageIndex);

        return rootView;
    }

    /**
     * @param type       what kind of data
     * @param operatorID which operator
     * @param start      pagination purpose
     */
    private void setList(int type, int operatorID, int start) {

        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference codeListReference = FirebaseDatabase.getInstance().getReference().child("codes");

        Query query = codeListReference.orderByChild("type").startAt(start).endAt(start + 10);

        ValueEventListener codeValueEvent = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("SnapShot", dataSnapshot.toString());
                progressBar.setVisibility(View.GONE);
                for (DataSnapshot singleCodeObject : dataSnapshot.getChildren()) {
                    UssdCode code;
                    code = singleCodeObject.getValue(UssdCode.class);
                    array.add(code);
                }
                pageIndex += 10;
                recyclerViewAdapter.setUssdCodeList(array);
                Log.d("Size", array.size() + "");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        query.addListenerForSingleValueEvent(codeValueEvent);
    }


    private void initializeViewsAdapter() {

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }


    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = layoutManager.findLastVisibleItemPosition();

        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == recyclerViewAdapter.getItemCount()) {
                //加载更多
                //LogUtils.d(TAG, "loading more data");
                progressBar.setVisibility(View.VISIBLE);
                setList(TYPE, OPERATOR_ID, pageIndex + 1);
            }
        }
    };


    @Override
    public void clickPosition(int position, int id) {
        switch (id) {
            case R.id.card_view:
                Toast.makeText(getContext(), position + " Full View ID" + id, Toast.LENGTH_SHORT).show();
                break;
            case R.id.code_detail:
                Toast.makeText(getContext(), position + " Title Text ID" + id, Toast.LENGTH_SHORT).show();
                break;
            case R.id.short_code:
                Toast.makeText(getContext(), position + " Image View ID" + id, Toast.LENGTH_SHORT).show();
                break;
            case R.id.arrow:
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("ShortCode", array.get(position).getShortCode());
                clipboard.setPrimaryClip(clip);
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(ussdToCallableUri("*5000*500#"));
                startActivity(intent);
                Toast.makeText(getContext(), "ShortCode copied in clipboard!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private Uri ussdToCallableUri(String ussd) {

        String uriString = "";

        if (!ussd.startsWith("tel:"))
            uriString += "tel:";

        for (char c : ussd.toCharArray()) {

            if (c == '#')
                uriString += Uri.encode("#");
            else
                uriString += c;
        }
        return Uri.parse(uriString);
    }


}
