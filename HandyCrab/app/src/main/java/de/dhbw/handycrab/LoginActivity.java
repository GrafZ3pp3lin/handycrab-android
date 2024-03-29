package de.dhbw.handycrab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;

import de.dhbw.handycrab.backend.BackendConnectionException;
import de.dhbw.handycrab.backend.BackendConnector;
import de.dhbw.handycrab.backend.IHandyCrabDataHandler;
import de.dhbw.handycrab.helper.IDataCache;
import de.dhbw.handycrab.model.User;

import javax.inject.Inject;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class LoginActivity extends AppCompatActivity {
    public static String LOGOUT = "LOGOUT";
    private static String COOKIE_TOKEN = "TOKEN";
    private static String COOKIE_DOMAIN = "DOMAIN";

    public static SharedPreferences preferences;

    public static String USER = "de.dhbw.handycrab.USER";
    private TextView username;
    private TextView email;
    private TextView password;
    private TextInputLayout maillayout;
    private TextInputLayout usernamelayout;
    private Button submit;
    private TabLayout tabLayout;

    @Inject
    IDataCache dataHolder;

    @Inject
    IHandyCrabDataHandler backendConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Program.getApplicationGraph().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.mail);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        submit = findViewById(R.id.submit);
        maillayout = findViewById(R.id.mail_layout);
        usernamelayout = findViewById(R.id.username_layout);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                submit.setText(tab.getText());
                //login
                if (tab.getPosition() == 0) {
                    maillayout.setVisibility(View.INVISIBLE);
                    usernamelayout.setHint(getString(R.string.usernameOrEmail));
                }
                //register
                else {
                    maillayout.setVisibility(View.VISIBLE);
                    usernamelayout.setHint(getString(R.string.username));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        preferences = getPreferences(MODE_PRIVATE);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getBoolean(LOGOUT)) {
                preferences.edit().remove(COOKIE_DOMAIN).remove(COOKIE_TOKEN).commit();
            }
        }

        String token = preferences.getString(COOKIE_TOKEN, "");
        String domain = preferences.getString(COOKIE_DOMAIN, "");
        if (token != null && !token.isEmpty() && domain != null && !domain.isEmpty()) {
            backendConnector.loadToken(token, domain);
            try {
                User user = backendConnector.currenUserAsync().get(BackendConnector.TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
                successLogin(user);
            }
            catch (InterruptedException | ExecutionException e) {
                if (e.getCause() instanceof BackendConnectionException) {
                    BackendConnectionException ex = (BackendConnectionException) e.getCause();
                    Toast.makeText(this, ex.getDetailedMessage(this), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, getString(R.string.unknownError), Toast.LENGTH_SHORT).show();
                }
            } catch (TimeoutException e) {
                Toast.makeText(this, getString(R.string.timeout), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void submit(View view) {
        try {
            User user;
            if (tabLayout.getSelectedTabPosition() == 0) {
                user = backendConnector.loginAsync(username.getText().toString(), password.getText().toString(), true).get(BackendConnector.TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
            }
            else {
                user = backendConnector.registerAsync(email.getText().toString(), username.getText().toString(), password.getText().toString(), true).get(BackendConnector.TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
            }
            backendConnector.saveToken((token, domain) -> preferences.edit().putString(COOKIE_TOKEN, token).putString(COOKIE_DOMAIN, domain).commit());
            successLogin(user);
        }
        catch (InterruptedException | ExecutionException e) {
            if (e.getCause() instanceof BackendConnectionException) {
                BackendConnectionException ex = (BackendConnectionException) e.getCause();
                Toast.makeText(this, ex.getDetailedMessage(this), Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, getString(R.string.unknownError), Toast.LENGTH_SHORT).show();
            }
        } catch (TimeoutException e) {
            Toast.makeText(this, getString(R.string.timeout), Toast.LENGTH_SHORT).show();
        }
    }

    private void successLogin(User user) {
        dataHolder.store(USER, user);
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}
