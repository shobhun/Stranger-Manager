package com.example.admin.strangermanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class fragment_login extends Fragment
{
    private EditText etxEmail,etxPassword;
    private String Email,Password;
    private Snackbar snackbar;
    private FirebaseAuth fbAuth;
    private FragmentManager fragmentManager;
    private SharedPreferences sharedPreferences;
    private ProgressBar progressbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_fragment_login,null,false);

        progressbar = (ProgressBar) view.findViewById(R.id.progressbar);
        etxEmail = (EditText) view.findViewById(R.id.etxEmail);
        etxPassword = (EditText) view.findViewById(R.id.etxPassword);

        fbAuth = FirebaseAuth.getInstance();
        view.findViewById(R.id.btnSigUp).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View view)
            {
                progressbar.setVisibility(View.VISIBLE);
                Email = etxEmail.getText().toString();
                Password = etxPassword.getText().toString();
                if (TextUtils.isEmpty(Email))
                {
                    snackbar= Snackbar.make(view,"Please Enter Email ID",Snackbar.LENGTH_LONG);
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
                    progressbar.setVisibility(View.INVISIBLE);
                }
                else if (TextUtils.isEmpty(Password))
                {
                    snackbar= Snackbar.make(view,"Please Enter Password",Snackbar.LENGTH_LONG);
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
                    progressbar.setVisibility(View.INVISIBLE);
                }
                else if (Password.length()<6)
                {
                    snackbar= Snackbar.make(view,"Minimum length of password is 6",Snackbar.LENGTH_LONG);
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
                    progressbar.setVisibility(View.INVISIBLE);
                }
                else
                {
                    fbAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(getContext(), "Registered & Logged In Successfully", Toast.LENGTH_SHORT).show();
                                Intent ContactListActivity = new Intent(getContext(), ContactListActivity.class);
                                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                                sharedPreferences.edit().putString("User",Email).apply();
                                startActivity(ContactListActivity);
                                progressbar.setVisibility(View.INVISIBLE);
                            }
                            else if ((task.getException().toString()).equals("com.google.firebase.auth.FirebaseAuthUserCollisionException: The email address is already in use by another account."))
                            {
                                snackbar= Snackbar.make(view,"Email Id Already Used.",Snackbar.LENGTH_LONG);
                                snackbar.setAction("Forgot Password ?", new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View view)
                                    {
                                        fragmentManager = getActivity().getSupportFragmentManager();
                                        fragmentManager.beginTransaction().replace(R.id.container, new ForgetPasswordActivity()).commit();

                                    }
                                });
                                View sview = snackbar.getView();
                                sview.setBackgroundColor(Color.BLUE);
                                snackbar.show();
//                                etxEmail.setText(""+etxEmail.getError());
                                progressbar.setVisibility(View.INVISIBLE);
                            }
                            else
                            {
                                Toast.makeText(getContext(), ""+task.getException().toString() , Toast.LENGTH_SHORT).show();
                                Log.d("TAg",task.getException().toString());
                                progressbar.setVisibility(View.INVISIBLE);
                            }
                        }

                    });

                }
            }
        });

        //Log In
        view.findViewById(R.id.btnLogIn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View view)
            {
                progressbar.setVisibility(View.VISIBLE);
                Email = etxEmail.getText().toString();
                Password = etxPassword.getText().toString();

//                ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//                Toast.makeText(getContext(), ""+networkInfo.isConnected(), Toast.LENGTH_SHORT).show();
//                boolean flag = networkInfo.isConnected();
//                Log.d("TAG",""+flag);
//                if (flag )
//                    return;
//                else
//                {
//                    Snackbar.make(view,"Network Failure.",Snackbar.LENGTH_LONG).setAction("OK", new View.OnClickListener()
//                    {
//                        @Override
//                        public void onClick(View view)
//                        {
//                            snackbar.dismiss();
//                        }
//                    }).show();
//                }



                if (TextUtils.isEmpty(Email))
                {
                    snackbar= Snackbar.make(view,"Please Enter Email ID",Snackbar.LENGTH_LONG);
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
                    progressbar.setVisibility(View.INVISIBLE);
                }
                else if (TextUtils.isEmpty(Password))
                {
                    snackbar= Snackbar.make(view,"Please Enter Password",Snackbar.LENGTH_LONG);
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
                    progressbar.setVisibility(View.INVISIBLE);
                }
                else if (Password.length()<6)
                {
                    snackbar= Snackbar.make(view,"Minimum length of password is 6",Snackbar.LENGTH_LONG);
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
                    progressbar.setVisibility(View.INVISIBLE);
                }
                else
                {
                    fbAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(getContext(), "Welcome: "+Email, Toast.LENGTH_SHORT).show();
                                Intent ContactListActivity = new Intent(getContext(), ContactListActivity.class);
                                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                                sharedPreferences.edit().putString("User",Email).apply();
                                startActivity(ContactListActivity);
                                progressbar.setVisibility(View.INVISIBLE);
                            }
                            else
                            {
                                snackbar= Snackbar.make(view,"Incorrect Password",Snackbar.LENGTH_LONG);
                                snackbar.setAction("Forgot Password ?", new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View view)
                                    {
                                        fragmentManager = getActivity().getSupportFragmentManager();
                                        fragmentManager.beginTransaction().replace(R.id.container,new ForgetPasswordActivity()).commit();
                                    }
                                });

                                View sview = snackbar.getView();
                                sview.setBackgroundColor(Color.BLUE);
                                snackbar.show();
                                progressbar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
            }
        });

        return view;
    }
}
