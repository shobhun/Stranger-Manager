package com.example.admin.strangermanager;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static java.security.AccessController.getContext;

public class ForgetPasswordActivity extends Fragment
{
    private FragmentManager fragmentManager;
    private FirebaseAuth firebaseAuth;
    private EditText etxReserPassword;
    private String EmailReset;
    private Snackbar snackbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_forget_password,container,false);
        firebaseAuth = FirebaseAuth.getInstance();
        etxReserPassword = (EditText) view.findViewById(R.id.etxEmailReset);
        view.findViewById(R.id.btnResetPassword).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                EmailReset = etxReserPassword.getText().toString();
                if (TextUtils.isEmpty(EmailReset))
                {
                    snackbar= Snackbar.make(getView(),"Please Enter Email",Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("OK", new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            snackbar.dismiss();
                        }
                    });
                    View sview = snackbar.getView();
                    sview.setBackgroundColor(Color.BLUE);
                    snackbar.show();
                }
                else
                {
                    firebaseAuth.sendPasswordResetEmail(EmailReset).addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(getContext(), "Reset link has been sent.", Toast.LENGTH_SHORT).show();
                                fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.container,new fragment_login()).commit();
                            }
                            else
                            {
                                snackbar= Snackbar.make(getView(),"Incorrect Email",Snackbar.LENGTH_INDEFINITE);
                                snackbar.setAction("OK", new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View view)
                                    {
                                        snackbar.dismiss();
                                    }
                                });
                                View sview = snackbar.getView();
                                sview.setBackgroundColor(Color.BLUE);
                                snackbar.show();
                            }
                        }
                    });
                }
            }
        });
        return view;
    }

}
