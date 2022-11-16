package com.poly.appmessage.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.poly.appmessage.R;

import java.util.HashMap;
import java.util.Map;

public class SignUpFragment extends Fragment {
    EditText mEmail;
    EditText mPassWord;
    Button mSignUp;
    ProgressBar mProgressBar;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirestore;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEmail = view.findViewById(R.id.edt_email);
        mPassWord = view.findViewById(R.id.edt_pass_word);
        mSignUp = view.findViewById(R.id.btn_signup);
        mProgressBar = view.findViewById(R.id.progress_bar);

        mSignUp.setOnClickListener(v -> {
            signUp(mEmail.getText().toString(), mPassWord.getText().toString());
        });
    }

    private void signUp(String email, String password){
        mProgressBar.setVisibility(View.VISIBLE); // biến mất nhưng vẫn còn tồn tại
        mProgressBar.setProgress(0, true);
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgressBar.setProgress(0, false);
                        mProgressBar.setVisibility(View.GONE); // biến mất
                        if (task.isSuccessful()){
                            addUserToDB(email);
                            Toast.makeText(requireContext(), "Register successful for account with email " +email, Toast.LENGTH_SHORT).show();
                            requireActivity().onBackPressed(); // người dùng muốn quay lại
                        }else {
                            Toast.makeText(requireContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void addUserToDB(String email){
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("email", email);
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        mFirestore.collection("users")
                .document(firebaseUser.getUid())
                .set(dataMap);
    }
}