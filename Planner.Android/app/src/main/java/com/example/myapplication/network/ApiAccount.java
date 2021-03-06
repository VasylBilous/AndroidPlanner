package com.example.myapplication.network;

import com.example.myapplication.dto.LoginDto;
import com.example.myapplication.dto.LoginResultDto;
import com.example.myapplication.dto.RegistrationDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiAccount {
    @POST("/api/account/login")
    public Call<LoginResultDto> login(@Body LoginDto loginDto);

    @POST("/api/account/registration")
    public Call<LoginResultDto> registration(@Body RegistrationDto registrationDto);
}
