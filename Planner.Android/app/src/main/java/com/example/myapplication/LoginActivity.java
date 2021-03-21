package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.toolbox.NetworkImageView;
import com.example.myapplication.constants.Urls;
import com.example.myapplication.dto.BadResultDto;
import com.example.myapplication.dto.LoginDto;
import com.example.myapplication.dto.LoginResultDto;
import com.example.myapplication.network.ImageRequester;
import com.example.myapplication.network.services.AccountService;
import com.example.myapplication.utils.CommonUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ImageRequester imageRequester;
    private NetworkImageView myImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String url = Urls.BASE + "/images/3.jpg";

        imageRequester = ImageRequester.getInstance();
        myImage = findViewById(R.id.myimg);
        imageRequester.setImageFromUrl(myImage, url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_registration) {
            startActivity(new Intent(this, RegistrationActivity.class));
            return true;
        } else if (id == R.id.action_exit) {
            finish();
            System.exit(0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void OnClickBtn(View view) {
        final TextInputEditText email = findViewById(R.id.textInputEmail);
        final TextInputLayout emailLayout = findViewById(R.id.textFieldEmail);
        final TextInputEditText password = findViewById(R.id.textInputPassword);
        final TextInputLayout passwordLayout = findViewById(R.id.textFieldPassword);

        LoginDto dto = new LoginDto(email.getText().toString(), password.getText().toString());

       /* if (dto.getEmail().isEmpty()) {
            emailLayout.setError("Enter email!");
            return;
        } else
            emailLayout.setError("");

        if (dto.getPassword().isEmpty()) {
            password.setError("Enter password!");
            return;
        } else
            passwordLayout.setError("");*/

        CommonUtils.showLoading(this);
        AccountService.getInstance()
                .getJSONApi()
                .login(dto)
                .enqueue(new Callback<LoginResultDto>() {
                    @Override
                    public void onResponse(Call<LoginResultDto> call, Response<LoginResultDto> response) {
                        CommonUtils.hideLoading();
                        if (response.isSuccessful()) {
                            Log.d("server", "Good");
                        } else {
                            try {
                                String json = response.errorBody().string();
                                Gson gson = new Gson();
                                BadResultDto result = gson.fromJson(json, BadResultDto.class);
                                emailLayout.setError(result.getEmail());
                                passwordLayout.setError(result.getPassword());
                            } catch (Exception ex) {
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResultDto> call, Throwable t) {
                        CommonUtils.hideLoading();
                        Log.e("server", "Bad");
                    }
                });

        Log.d("Click my", email.getText().toString());
    }
}