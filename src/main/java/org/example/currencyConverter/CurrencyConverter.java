package org.example.currencyConverter;


import org.example.currenyConverterConnection.ICurrencyConverterContract;
import org.example.currenyConverterConnection.ICurrencyConverterContract_Service;

public class CurrencyConverter {

    public double convertCurrency(double amount, String fromCurrency, String toCurrency) {
        try {
            ICurrencyConverterContract_Service service = new ICurrencyConverterContract_Service();
            ICurrencyConverterContract port = service.getBasicHttpBindingICurrencyConverterContract();
            double result = port.convertCurrency(amount, fromCurrency, toCurrency);
            return result;
        } catch (Exception e) {
            System.err.println("Currency change service is currently down");
            return -1;
        }
    }

}
