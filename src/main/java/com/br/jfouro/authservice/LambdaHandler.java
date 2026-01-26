package com.br.jfouro.authservice;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class LambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    // Instanciação manual para evitar carregar o Spring Context no Lambda puro
    private final JwtService jwtService = new JwtService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        // Assume que o body é o token puro. Se for um JSON {"token": "xyz"}, precisaria de parse antes.
        String token = input.getBody();

        AuthResponse authResponse = jwtService.validateToken(token);

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        try {
            response.setStatusCode(200);
            response.setBody(objectMapper.writeValueAsString(authResponse));
            response.setHeaders(Map.of("Content-Type", "application/json"));
        } catch (JsonProcessingException e) {
            response.setStatusCode(500);
            response.setBody("{\"valido\": false, \"mensagem\": \"Erro interno de JSON\"}");
        }

        return response;
    }
}