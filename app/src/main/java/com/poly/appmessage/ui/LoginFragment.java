package com.poly.appmessage.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.poly.appmessage.R;
import com.poly.appmessage.data.UserRepository;


public class LoginFragment extends Fragment {

    TextView mCreateAccount;
    TextView mForgotPassWord;
    EditText mEmail;
    EditText mPassWord;
    Button mLogin;
    ProgressBar mProgressBar;
    FirebaseAuth mFirebaseAuth;

//    Button mButtonFaceBook;

    NavController mController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mCreateAccount = view.findViewById(R.id.tv_create_account);
        mCreateAccount.setOnClickListener(v -> {
            mController.navigate(R.id.signUpFragment);
        });

        mForgotPassWord = view.findViewById(R.id.tv_forgot_password);
        mForgotPassWord.setOnClickListener(v -> {
            mController.navigate(R.id.forgotFragment);
        });

        mEmail = view.findViewById(R.id.edt_email);
        mPassWord = view.findViewById(R.id.edt_pass_word);
        mLogin = view.findViewById(R.id.btn_login);
        mLogin.setOnClickListener(v -> {
            Login(mEmail.getText().toString(), mPassWord.getText().toString());
        });

//        mButtonFaceBook = view.findViewById(R.id.btn_face_book);
//        mButtonFaceBook.setOnClickListener(v -> {
//
//        });

        mProgressBar = view.findViewById(R.id.progress_bar);
    }

    public void Login (String email, String password){
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setProgress(0, true);
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgressBar.setVisibility(View.GONE);
                        mProgressBar.setProgress(0, false);
                        if (task.isSuccessful()){
                            mController.popBackStack();
                            mController.navigate(R.id.userListFragment);
                        }else {
                            Toast.makeText(requireContext(),
                                    task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}