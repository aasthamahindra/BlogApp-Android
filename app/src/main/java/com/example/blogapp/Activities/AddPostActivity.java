package com.example.blogapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.blogapp.Model.Blog;
import com.example.blogapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPostActivity extends AppCompatActivity {

    private ImageButton mPostImage;
    private EditText mPostTitle;
    private EditText mPostDesc;
    private Button mSubmitButton;
    private DatabaseReference mPostDatabase;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        mProgress = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mPostDatabase = FirebaseDatabase.getInstance().getReference().child("mBlog");

        mPostImage = (ImageButton) findViewById(R.id.imageButton);
        mPostTitle = (EditText) findViewById(R.id.postTitleEt);
        mPostDesc = (EditText) findViewById(R.id.postTextEt);
        mSubmitButton = (Button) findViewById(R.id.submitButtonEt);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Post to database
                startPosting();
            }

            private void startPosting() {
                mProgress.setMessage("Posting to Blog...");
                mProgress.show();

                String titleVal = mPostTitle.getText().toString().trim();
                String descVal = mPostDesc.getText().toString().trim();

                if(!TextUtils.isEmpty(titleVal) && !TextUtils.isEmpty(descVal)){
                    //Start uploading to database
                    Blog blog = new Blog("title", "description", "imageurl",
                            "timestamp", "userid");
                    mPostDatabase.setValue(blog).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Item added",
                                    Toast.LENGTH_LONG).show();
                            mProgress.dismiss();
                        }
                    });

                }
            }
        });

    }
}