package com.example.finales.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.finales.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class GalleryFragment extends Fragment {


    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    LinearLayout linearLayout;
    Spinner spinner;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);


        linearLayout = root.findViewById(R.id.linearLayout);
        spinner = root.findViewById(R.id.spinner);


        return root;
    }
    public void getData(){
        firestore.collection("LabelModel").get().addOnCompleteListener(){
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        String label = document.getString("label");
                        String image = document.getString("image");
                        String name = document.getString("name");
                        String price = document.getString("price");
                        String description = document.getString("description");
                        String category = document.getString("category");
                        String id = document.getString("id");

                        LayoutInflater inflater = LayoutInflater.from(getContext());
                        View view = inflater.inflate(R.layout.item_layout, null);
                        TextView txtName = view.findViewById(R.id.txtName);
                        TextView txtPrice = view.findViewById(R.id.txtPrice);
                        TextView txtDescription = view.findViewById(R.id.txtDescription);
                        ImageView imageView = view.findViewById(R.id.imageView);

                        txtName.setText(name);
                        txtPrice.setText(price);
                        txtDescription.setText(description);
                        Picasso.get().load(image).into(imageView);

                        linearLayout.addView(view);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getPost(String labels){
        linearLayout.removeAllViews();
        CollectionReference postCollection = firestore.collection("PostModel");
        Query query = postCollection.whereEqualTo("label", labels);
        query.get().addOnCompleteListener(){
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        String label = document.getString("label");
                        String image = document.getString("image");
                        String name = document.getString("name");
                        String price = document.getString("price");
                        String description = document.getString("description");
                        String category = document.getString("category");
                        String id = document.getString("id");

                        LayoutInflater inflater = LayoutInflater.from(getContext());
                        View view = inflater.inflate(R.layout.item_layout, null);
                        TextView txtName = view.findViewById(R.id.txtName);
                        TextView txtPrice = view.findViewById(R.id.txtPrice);
                        TextView txtDescription = view.findViewById(R.id.txtDescription);
                        ImageView imageView = view.findViewById(R.id.imageView);

                        txtName.setText(name);
                        txtPrice.setText(price);
                        txtDescription.setText(description);
                        Picasso.get().load(image).into(imageView);

                        linearLayout.addView(view);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}