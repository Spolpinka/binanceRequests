package com.muh.binancerequests.controller;

import com.muh.binancerequests.service.RequestService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.TreeMap;

//надо бы сделать эндпойнт для других бирж: ByBit, BitGet,
// соответственно распознание курсов и понять как перегонять на кошелек и с кошелька на биржу
//можно ли сделать автоматический перевод крипты через Trust кошелек?
//и телеграм бота запустить
@OpenAPIDefinition(
        info = @Info(
                title = "Программа для взаимодействия с Binance",
                description = "API серверной части программы по запросу и получению сведений от API Binance",
                version = "1.1.0.",
                contact = @Contact(
                        name = "Мухортов Алексей Юрьевич",
                        email = "a421243266@gmail.com",
                        url = "https://github.com/Spolpinka/binanceRequests"
                )
        )
)
@RestController
//@RequestMapping("/api")
public class RequestController {

    private final RequestService requestService;


    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @Operation(summary = "Hello", description = "проверка запуска программы")
    @GetMapping("/")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("The program is working");
    }

    @GetMapping("/RubUSDT")
    public ResponseEntity<String> getRubUSDT() {
        try {
            return ResponseEntity.ok(requestService.getRubUsdt("USDTRUB"));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("BTCUSDT")
    public String getBTCUSDT() {
        try {
            return requestService.getRubUsdt("BTCUSDT");
        } catch (IOException e) {
            return e.toString();
        }
    }

    @GetMapping("PHPUSDT")
    public String getPHPUSDT() {
        try {
            return requestService.getRubUsdt("USDT/PHP");
        } catch (IOException e) {
            return e.toString();
        }
    }

    @GetMapping("/avgUSDTRUB")
    public ResponseEntity<String> avgUSDTRUB() {
        try {
            return ResponseEntity.ok(requestService.getAvrCourse("USDTRUB"));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/avgBTCUSDT")
    public ResponseEntity<String> avgBTCUSDT() {
        try {
            return ResponseEntity.ok(requestService.getAvrCourse("BTCUSDT"));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/timeLapUSDTRUB")
    public ResponseEntity<Void> timeLapsUSDTRUB() {
        try {
            requestService.getTimeLapsRequests("USDTRUB");
            return ResponseEntity.ok().build();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/ratioRUBPHP")
    public ResponseEntity<String> ratioUSDTRUB() {
        try {
            return ResponseEntity.ok(requestService.getRatio("USDTRUB", "PHPUSDT"));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/exchangeInfo")
    public ResponseEntity<String> exchangeInfo() {
        try {
            return ResponseEntity.ok(requestService.exchangeInfo());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/getMonthCourses")
    public ResponseEntity<String> getMonthCourses(@RequestParam int month) {
        if (requestService.getMonthCourses(month).isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(requestService.getMonthCourses(month));
        }
    }

    @GetMapping("/getBestPricesNow")
    public ResponseEntity<String> getBestPrisesNow(){
        return ResponseEntity.ok(requestService.getBestPrisesNow());
    }

    @GetMapping(value = "/getDataForGraph", produces = MediaType.APPLICATION_JSON_VALUE)
    public TreeMap<LocalDateTime, Double> getDataForGraph(){
        return requestService.getDataForGraph();
    }

}
