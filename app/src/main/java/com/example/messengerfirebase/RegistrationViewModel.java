package com.example.messengerfirebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationViewModel extends ViewModel {

    private FirebaseAuth auth;
    private String databaseUrl = "https://messanger-8a07b-default-rtdb.europe-west1.firebasedatabase.app/";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usersRef;

    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public LiveData<String> getError() {
        return error;
    }

    public RegistrationViewModel() {
        auth = FirebaseAuth.getInstance();

        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                   user.setValue(firebaseAuth.getCurrentUser());
                }
            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance(databaseUrl);
        usersRef = firebaseDatabase.getReference("Users");
    }

    public void signUp(String email,
                       String password,
                       String name,
                       String lastName,
                       int age) {

        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                    // todo save user to firebase database
                        String userUid = authResult.getUser().getUid();
                        if (authResult.getUser() == null) {
                            return;
                        }

                        User user = new User(userUid, name, lastName, age, false);
                        usersRef.child(userUid).setValue(user);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception errorMessage) {
                        error.setValue(errorMessage.getMessage());
                    }
                });

    }
}
