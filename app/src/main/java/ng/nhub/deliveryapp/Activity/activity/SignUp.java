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

public class SignUp extends AppCompatActivity {

    private AppCompatActivity activity = SignUp.this;
    private final String TAG = SignUp.class.getSimpleName();
    public static String PREFS_NAME = "mypref";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private SweetAlertDialog pDialog;
    private TextInputEditText etEmail, etPassword;
    private KProgressHUD hud;
    private String message;

    final Utils util = new Utils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        etEmail = findViewById(R.id.signup_email);
        etPassword = findViewById(R.id.signup_password);
        etPassword.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        Button signUpBtn = findViewById(R.id.btn_signup);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email) || !util.isValidEmail(activity, email)) {
                    etEmail.setError("Enter a valid email address");
                    return;
                }
                if (TextUtils.isEmpty(password) || password.length() < 6) {
                    etPassword.setError("Password must be at least 6 characters long");
                    return;
                }
                if (util.isNetworkAvailable(activity)) {

                    try {
                        new SignUpUser().execute();
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

        findViewById(R.id.toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, SignUpFacebook.class));
                finish();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public class SignUpUser extends AsyncTask<String, Void, JSONObject> {

        JsonParser jsonParser = new JsonParser();
        private static final String REGISTER_URL =
                "https://niche-vendor.herokuapp.com/register";


        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        @Override
        protected void onPreExecute() {

            hud = KProgressHUD.create(activity)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait")
                    .setDetailsLabel("Registering...")
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

                        Toast.makeText(activity, "Account successfully created",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, SignInEmail.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Error Message")
                                .setContentText("User already registered")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();

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
