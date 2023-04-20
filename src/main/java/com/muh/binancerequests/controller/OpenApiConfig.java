package com.muh.binancerequests.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Программа для взаимодействия с Binance",
                description = "API серверной части программы по запросу и получению сведений от API Binance",
                version = "1.1.0.",
                contact = @Contact(
                        name = "Сироткина Яна Евгеньевна",
                        email = "a421243266@gmail.com",
                        url = "https://github.com/Spolpinka/binanceRequests"
                )
        )
)
public class OpenApiConfig {
}
