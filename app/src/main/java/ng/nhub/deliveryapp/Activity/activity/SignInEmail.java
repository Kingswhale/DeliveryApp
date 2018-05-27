package ng.nhub.deliveryapp.Activity.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ng.nhub.deliveryapp.Activity.util.JsonParser;
import ng.nhub.deliveryapp.Activity.util.Utils;
import ng.nhub.deliveryapp.R;

public class SignInEmail extends AppCompatActivity {

    private AppCompatActivity activity = SignInEmail.this;
    private final String TAG = SignInEmail.class.getSimpleName();
    private Utils util = new Utils();
    public static String PREFS_NAME = "mypref";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private SweetAlertDialog pDialog;
    private TextInputEditText etEmail, etPassword;
    private KProgressHUD hud;
    private String message, email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_email);

        etEmail = findViewById(R.id.login_email);
        etPassword = findViewById(R.id.login_password);
        Button signInBtn = findViewById(R.id.loginbtn);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = etEmail.getText().toString().trim();
                password = etPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email) || !util.isValidEmail(activity, email)) {
                    Log.d(TAG, email);
                    etEmail.setError("Enter a valid email address");
                    return;
                }
                if (TextUtils.isEmpty(password) || password.length() < 6) {
                    etPassword.setError("Enter your password");
                    return;
                }
                if (util.isNetworkAvailable(getApplicationContext())) {

                    try {
                        new SignInUserEmail().execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    message = "Check your Internet connection";
                    pDialog = new SweetAlertDialog(activity,
                            SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Oops...")
                            .setContentText(message)
                            .show();
                }
            }

        });

        findViewById(R.id.toolbar2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, SignIn.class));
                finish();
            }
        });
    }

    public class SignInUserEmail extends AsyncTask<String, Void, JSONObject> {

        JsonParser jsonParser = new JsonParser();
        private static final String REGISTER_URL =
                "https://niche-vendor.herokuapp.com/login";


        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        @Override
        protected void onPreExecute() {

            hud = KProgressHUD.create(activity)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait")
                    .setDetailsLabel("Validating User...")
                    .setCancellable(true)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .setBackgroundColor(Color.BLACK)
                    .setAutoDismiss(true)
                    .show();
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {

            try {

                HashMap<String, String> params = new HashMap<>();


                params.put("email", email);
                params.put("password", password);

                JSONObject json = jsonParser.makeHttpRequest(REGISTER_URL, "POST", params);

                if (json != null) {

                    return json;
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hud.dismiss();
                            pDialog = new SweetAlertDialog(activity,
                                    SweetAlertDialog.ERROR_TYPE);
                            pDialog.setTitleText("Oops...")
                                    .setContentText("Network error, please try again!")
                                    .show();
                        }
                    });

                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject json) {

            hud.dismiss();

            try {

                if (json != null) {
                    if (json.getInt("status") == 200) {

                        preferences = getApplicationContext().getSharedPreferences(
                                PREFS_NAME, Activity.MODE_PRIVATE);
                        editor = preferences.edit();

                        editor.putString("email", email);

                        Toast.makeText(activity, "Login successful",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, Home.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Error Message")
                                .setContentText("Please register first")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();

                                        Intent intent = new Intent(activity, SignUp.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .show();

                    }

                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
