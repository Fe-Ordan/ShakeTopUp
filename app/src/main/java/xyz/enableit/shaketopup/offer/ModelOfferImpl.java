package xyz.enableit.shaketopup.offer;

import android.os.Handler;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dinislam on 2/8/17.
 * email : milon@strativ.se
 */

public class ModelOfferImpl implements ModelOffer, ValueEventListener, ChildEventListener {


    OnLoadOfferListListener loadOfferListListener;
    private DatabaseReference mDatabase;

    @Override
    public void loadOfferFromDataSource(int startAt, int limitTo, OnLoadOfferListListener loadOfferListListener) {


        this.loadOfferListListener = loadOfferListListener;

        mDatabase = FirebaseDatabase.getInstance().getReference().child("offer");

        //for single item change, add, move or delete
        mDatabase.addChildEventListener(this);
        Log.d("Start", "" + startAt);

        //fireBase query data
        Query pagination = mDatabase.orderByChild("id").startAt(startAt).endAt(startAt + 10);
        pagination.addListenerForSingleValueEvent(this);

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        Offer offer;
        List<Offer> offers = new ArrayList<>();

        Log.d("Database Snapshot", dataSnapshot.toString());
        for (DataSnapshot singleOffer : dataSnapshot.getChildren()) {
            offer = singleOffer.getValue(Offer.class);
            offers.add(offer);
        }
        loadOfferListListener.onSuccess(offers);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Log.d("Database Snapshot + s", dataSnapshot.toString() + " " + s);
        //loadOfferListListener.onSuccess(null);

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
