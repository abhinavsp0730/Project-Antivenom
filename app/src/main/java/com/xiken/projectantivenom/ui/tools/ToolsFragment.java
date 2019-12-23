package com.xiken.projectantivenom.ui.tools;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;

import com.xiken.projectantivenom.R;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    TextView userNameTextView;
    public static final String TAG  ="volley";
//    DatabaseReference firebaseDatabase ;
    ImageView imageView;
    TextView about_more;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_tools, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);
        userNameTextView = root.findViewById(R.id.nav_user_name);
//        imageView = root.findViewById(R.id.imView);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("hello4", "onClick: cliiiii");
//            }
//        });
        about_more = root.findViewById(R.id.about_more);
        about_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(),AboutMore.class);
                startActivity(intent);
            }
        });
        String uid = FirebaseAuth.getInstance().getUid();
        getActivity().setTitle("two");

        Log.d(TAG, "onCreateView: " +uid);

//       firebaseDatabase = FirebaseDatabase.getInstance().getReference("/userList/" +uid);
//       firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//           @Override
//           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//               Log.d(TAG, "onDataChange: " + dataSnapshot.toString());
//
//           }
//
//           @Override
//           public void onCancelled(@NonNull DatabaseError databaseError) {
//
//           }
//       });
//        toolsViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}