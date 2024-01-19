package com.example.finales.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.finales.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class PhotoFragment extends Fragment {

    private static final int CAMERA_REQUEST = 1888;
    ImageView image;
    Button btnUpload;

    LinearLayout photolayout;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_photo, container, false);

        camera = root.findViewById(R.id.camera);
        upload = root.findViewById(R.id.upload);
        photolayout = root.findViewById(R.id.photolayout);
        getLabel();

    camera.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            takePhoto();
        }
    });
        return root;
    }
    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }
    public void getLabel() {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance().getOnDeviceImageLabeler();
        labeler.processImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
            @Override
            public void onSuccess(List<FirebaseVisionImageLabel> labels) {
                String text = "";
                for (FirebaseVisionImageLabel label : labels) {
                    text += label.getText() + " : " + label.getConfidence() + "\n";
                }
                textView.setText(text);
            }
        });
    }
    public void checkSelectedCheckbox() {
        for(int i = 0; i < photolayout.getChildCount(); i++) {
            View v = photolayout.getChildAt(i);
            if(v instanceof CheckBox) {
                CheckBox cb = (CheckBox) v;
                if(cb.isChecked()) {
                    String label = cb.getText().toString();
                    db.collection("photos").document().set(new PhotoModel(label, "https://firebasestorage.googleapis.com/v0/b/finales-1e9f9.appspot.com/o/IMG_20211013_123456.jpg?alt=media&token=8b9b9b9b-9b9b-9b9b-9b9b-9b9b9b9b9b9b"));
                }
            }
        }
    }
}