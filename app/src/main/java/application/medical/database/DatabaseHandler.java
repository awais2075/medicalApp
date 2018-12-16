package application.medical.database;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import application.medical.interface_.FirebaseInterface;
import application.medical.model.Doctor;

public class DatabaseHandler<Model> {

    private FirebaseInterface firebaseInterface;

    public DatabaseHandler(FirebaseInterface firebaseInterface) {
        this.firebaseInterface = firebaseInterface;
    }

    public FirebaseDatabase getInstance() {
        return FirebaseDatabase.getInstance();
    }

    public DatabaseReference getReference(FirebaseDatabase firebaseDatabase, String path, String child) {
        return firebaseDatabase.getReference(path).child(child);
    }

    public void insert(DatabaseReference databaseReference, String child, Model value) {
        databaseReference.child(child).setValue(value);
    }

    public void view(DatabaseReference databaseReference, String orderBy, final Object model) {
        final List<Object> list = new ArrayList<>();
        databaseReference.orderByChild(orderBy).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //doctorList.add(snapshot.getValue(Doctor.class));
                    //doctorAdapter.notifyDataSetChanged();
                    list.add(snapshot.getValue((Class<Object>) model));
                }
                firebaseInterface.getValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                firebaseInterface.getValue(null);
            }
        });

    }

}
