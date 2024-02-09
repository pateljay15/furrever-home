package com.furreverhome.Furrever_Home.services;

import com.furreverhome.Furrever_Home.dto.GenericResponse;
import com.furreverhome.Furrever_Home.dto.JwtAuthenticationResponse;
import com.furreverhome.Furrever_Home.dto.RefreshTokenRequest;
import com.furreverhome.Furrever_Home.dto.SigninRequest;
import com.furreverhome.Furrever_Home.dto.user.PasswordDto;

public interface AuthenticationService {
    PetAdopterDto signup(PetAdopterSignupRequest signUpRequest);

    JwtAuthenticationResponse signin(SigninRequest signinRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    GenericResponse resetByEmail(final String contextPath, String email);

    GenericResponse resetPassword(final PasswordDto passwordDto);

    String validatePasswordResetToken(final String token);

    GenericResponse updateUserPassword(final PasswordDto passwordDto);

    public boolean verifyByEmail(String email);

}
