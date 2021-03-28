package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.example.myapplication.dto.BadResultDto;
import com.example.myapplication.dto.LoginResultDto;
import com.example.myapplication.dto.RegistrationDto;
import com.example.myapplication.network.services.AccountService;    
import com.example.myapplication.utils.CommonUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public void OnClickBtn(View view) {
        final TextInputEditText nick = findViewById(R.id.textInputNickname);
        final TextInputLayout nickLayout = findViewById(R.id.textFieldNickname);
        final TextInputEditText email = findViewById(R.id.textInputEmail);
        final TextInputLayout emailLayout = findViewById(R.id.textFieldEmail);
        final TextInputEditText password = findViewById(R.id.textInputPassword);
        final TextInputLayout passwordLayout = findViewById(R.id.textFieldPassword);

        RegistrationDto dto = new RegistrationDto(nick.getText().toString(), email.getText().toString(), password.getText().toString());

        CommonUtils.showLoading(this);
        AccountService.getInstance()
                .getJSONApi()
                .registration(dto)
                .enqueue(new Callback<LoginResultDto>() {
                    @Override
                    public void onResponse(Call<LoginResultDto> call, Response<LoginResultDto> response) {
                        CommonUtils.hideLoading();
                        if (response.isSuccessful()) {
                            Log.e("server", "Registration Success");
                        } else {
                            try {
                                String json = response.errorBody().string();
                                Gson gson = new Gson();
                                BadResultDto result = gson.fromJson(json, BadResultDto.class);
                                emailLayout.setError(result.getEmail());
                                passwordLayout.setError(result.getPassword());
                            } catch (Exception ex) {
                                Log.e("server", "Bad");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResultDto> call, Throwable t) {
                        CommonUtils.hideLoading();
                        Log.e("server", "Bad");
                    }
                });
    }

}