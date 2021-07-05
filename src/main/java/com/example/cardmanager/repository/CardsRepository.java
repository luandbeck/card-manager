package com.example.cardmanager.repository;

import com.example.cardmanager.entities.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

//CRUD operations
@Service
public class CardsRepository {

    public static final String TABLE_NAME = "cards";

    public void save(final User user) {
        final Firestore dbFirestore = FirestoreClient.getFirestore();

        dbFirestore.collection(TABLE_NAME).document(user.getEmail()).set(user);
    }

    public User get(final String email) {
        final Firestore dbFirestore = FirestoreClient.getFirestore();
        final DocumentReference documentReference = dbFirestore.collection(TABLE_NAME).document(email);
        final ApiFuture<DocumentSnapshot> future = documentReference.get();

        final DocumentSnapshot document;
        try {
            User user = null;

            document = future.get();
            if (document.exists()) {
                user = document.toObject(User.class);
                return user;
            } else {
                return null;
            }

        } catch (final Exception e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    public void update(final User user) {
        final Firestore dbFirestore = FirestoreClient.getFirestore();

        dbFirestore.collection(TABLE_NAME).document(user.getEmail()).set(user);
    }

    public void delete(final String email) {
        final Firestore dbFirestore = FirestoreClient.getFirestore();
        dbFirestore.collection(TABLE_NAME).document(email).delete();
    }

}