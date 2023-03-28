package com.security.DataTransferObjects.Responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
    private String authToken;
    private String tokenType = "Bearer ";

    public AuthenticationResponse(String token) {
        this.authToken = token;
    }

//    private Instant expiresAt;
//    private String username;
}
