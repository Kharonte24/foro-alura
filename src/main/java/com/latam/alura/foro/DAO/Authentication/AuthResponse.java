package com.latam.alura.foro.DAO.Authentication;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthResponse(@JsonProperty("access_token") String jwtToken) {
}
