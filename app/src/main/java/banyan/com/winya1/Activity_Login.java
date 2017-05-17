package banyan.com.winya1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import banyan.com.winya1.global.AppConfig;
import banyan.com.winya1.global.SessionManager;
import dmax.dialog.SpotsDialog;

public class Activity_Login extends Activity {

    Button btn_login;
    EditText edt_email, edt_password;
    TextView txt_forgot_password;

    String user_email, user_password;

    // Session Manager Class
    SessionManager session;
    SpotsDialog dialog;
    public static RequestQueue queue;

    private static final String TAG_NAME = "name";
    private static final String TAG_ID = "reg_id";

    String TAG = "reg";

    String str_user_name, str_reg_id;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        isInternetOn();

        // Session Manager
        session = new SessionManager(getApplicationContext());

        btn_login = (Button) findViewById(R.id.login_btn_signin);

        edt_email = (EditText) findViewById(R.id.edt_login_email);
        edt_password = (EditText) findViewById(R.id.edt_login_password);

        txt_forgot_password = (TextView) findViewById(R.id.txt_forgot_password);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user_email = edt_email.getText().toString();
                user_password = edt_password.getText().toString();

                if (user_email.equals("")) {
                    TastyToast.makeText(getApplicationContext(), "Please Enter User ID", TastyToast.LENGTH_SHORT, TastyToast.WARNING);
                    edt_email.setError("Please Enter Email ID");
                } else if (user_password.equals("")) {
                    TastyToast.makeText(getApplicationContext(), "Please Enter Password", TastyToast.LENGTH_SHORT, TastyToast.WARNING);
                    edt_password.setError("Please Enter Password");
                } else {

                    dialog = new SpotsDialog(Activity_Login.this);
                    dialog.show();
                    queue = Volley.newRequestQueue(Activity_Login.this);
                    Function_Login();
                }

            }
        });


    }

    private void Function_Login() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_login_authentication, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                Log.d("USER_LOGIN", response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("success");

                    System.out.println("REG" + success);

                    if (success == 1) {

                        dialog.dismiss();

                        JSONArray arr;
                        arr = obj.getJSONArray("login_user");

                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj1 = arr.getJSONObject(i);

                            str_user_name = obj1.getString(TAG_NAME);
                            str_reg_id = obj1.getString(TAG_ID);

                        }

                        System.out.println("NAME" + str_user_name);
                        System.out.println("ID" + str_reg_id);

                        session.createLoginSession(str_user_name, str_reg_id);

                        Intent i = new Intent(getApplicationContext(), Activity_Timeline.class);
                        startActivity(i);
                        finish();

                    } else {
                        TastyToast.makeText(getApplicationContext(), "Oops...! Login Failed :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    }

                    dialog.dismiss();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("email", user_email);
                params.put("password", user_password);

                System.out.println("name" + user_email);
                System.out.println("reg_id" + user_password);

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }

    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

            // if connected with internet

            //Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {

            new AlertDialog.Builder(Activity_Login.this)
                    .setTitle("WINYA")
                    .setMessage("Oops no internet !")
                    .setIcon(R.mipmap.ic_launcher)
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub
                                    finishAffinity();
                                }
                            }).show();
            return false;
        }
        return false;
    }

    /***********************************
     *  Back Click Listener
     * ************************************/

    @Override
    public void onBackPressed() {

        try {
            String str_status = "Want to Exit?";
            FunctionAlert(str_status);
        } catch (Exception e) {

        }
    }

    private void FunctionAlert(String status) {

        new android.support.v7.app.AlertDialog.Builder(Activity_Login.this)
                .setTitle("Winya")
                .setMessage(status)
                .setIcon(R.mipmap.ic_launcher)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }
                })
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub
                                // finish();
                                finishAffinity();
                            }
                        }).show();
    }
}

