package com.example.finales.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.finales.R;
import com.example.finales.models.LabelModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class LabelFragment extends Fragment {

    EditText label, description;
    LinearLayout layout;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_label, container, false);

        label = root.findViewById(R.id.label);
        description = root.findViewById(R.id.description);
        layout = root.findViewById(R.id.layout);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String labelString = label.getText().toString();
                String descriptionString = description.getText().toString();
                if (labelString.isEmpty() || descriptionString.isEmpty()) {
                    Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Label label = new Label(labelString, descriptionString);
                    label.save();
                    Toast.makeText(requireContext(), "Label added", Toast.LENGTH_SHORT).show();
                    label.setText("");
                    description.setText("");
                }
            }
        });


        return root;
    }
    public void Submit(View view) {
        String labelString = label.getText().toString();
        String descriptionString = description.getText().toString();
        if (labelString.isEmpty() || descriptionString.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            Label label = new Label(labelString, descriptionString);
            label.save();
            Toast.makeText(requireContext(), "Label added", Toast.LENGTH_SHORT).show();
            label.setText("");
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            LabelModel labelModel = new LabelModel(labelString, descriptionString);
            db.collection("labels").add(labelModel);
            description.setText("");
        }
    }
    public void getLabels(View view) {
        List<Label> labels = Label.listAll(Label.class);
        layout.removeAllViews();
        db.collection("labels").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<LabelModel> labels = queryDocumentSnapshots.toObjects(LabelModel.class);
                layout.removeAllViews();
                for (LabelModel label : labels) {
                    TextView textView = new TextView(requireContext());
                    textView.setText(label.getLabel());
                    textView.setTextSize(20);
                    textView.setPadding(0, 10, 0, 10);
                    layout.addView(textView);
                }
            }
        });
        for (Label label : labels) {
            TextView textView = new TextView(requireContext());
            textView.setText(label.getLabel());
            textView.setTextSize(20);
            textView.setPadding(0, 10, 0, 10);
            layout.addView(textView);
        }
    }

}