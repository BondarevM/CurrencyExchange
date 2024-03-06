package by.mikhail.currencyexchange;

import by.mikhail.currencyexchange.dto.CurrencyDto;
import by.mikhail.currencyexchange.service.CurrencyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class TestClass {
    public static void main(String[] args) throws JsonProcessingException {
        CurrencyService instance = CurrencyService.getInstance();
        List<CurrencyDto> all = instance.findAll();

        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(all);
        System.out.println(s);
    }
}
