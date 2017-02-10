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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
public class FragmentHome extends Fragment implements ListItemClickListener {


    public FragmentHome() {
        //Required empty public constructor
    }

    @Bind(R.id.home_rv_offer)
    RecyclerView recyclerView;
    List<UssdCode> array = new ArrayList<>();

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

        initializeViewsAdapter();

        setList();

        return rootView;
    }

    private void setList() {


        DatabaseReference codeListReference = FirebaseDatabase.getInstance().getReference().child("codes");
        ValueEventListener codeValueEvent = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleCodeObject : dataSnapshot.getChildren()) {
                    UssdCode code;
                    code = singleCodeObject.getValue(UssdCode.class);
                    array.add(code);
                }
                recyclerViewAdapter.setUssdCodeList(array);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        codeListReference.addListenerForSingleValueEvent(codeValueEvent);
    }

    private void initializeViewsAdapter() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }


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
