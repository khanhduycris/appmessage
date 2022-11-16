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
import com.google.firebase.auth.FirebaseAuth;
import com.poly.appmessage.R;

public class ForgotFragment extends Fragment {

    EditText mEmail;
    Button mRisetPassWord;
    ProgressBar mProgressBar;
    FirebaseAuth mFirebaseAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mEmail = view.findViewById(R.id.edt_email);
        mRisetPassWord = view.findViewById(R.id.btn_riset_password);
        mRisetPassWord.setOnClickListener(v -> {
            risetPassWord(mEmail.getText().toString());
        });
        mProgressBar = view.findViewById(R.id.progress_bar);
    }

    public void risetPassWord(String email){
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setProgress(0, true);
        mFirebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mProgressBar.setVisibility(View.GONE);
                            mProgressBar.setProgress(0, false);
                            Toast.makeText(requireContext(), "Check your email" +" "+ email +" "+ "to change your Password", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(requireContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}