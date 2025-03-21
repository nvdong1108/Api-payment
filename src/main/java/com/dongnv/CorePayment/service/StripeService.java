package com.dongnv.CorePayment.service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.scanner.Constant;

import com.dongnv.CorePayment.entity.CustomerDTO;
import com.dongnv.CorePayment.entity.OrdMstDto;
import com.dongnv.CorePayment.entity.StripeResponseDTO;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StripeService {

    public StripeResponseDTO createPayment(OrdMstDto ordMstDto) {
        StripeResponseDTO stripeResponseDTO = new StripeResponseDTO();
        String stripeCustomerId = null;
        setStripeApiKey();

        ordMstDto.setMoneyNo("");

        // SmWownetDTO smWownetDTO = wownetService.getSmWownetById(ordMstDto.getComId(),
        // ordMstDto.getLang());
        String successUrl = "/shopping-mall/checkout/payment-success";
        String cancelUrl = "/shopping-mall/checkout/payment-cancel";

        SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData
                .builder()
                .setName("Order product")
                .build();

        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("SGD")
                .setUnitAmount(new BigDecimal(ordMstDto.getAmt()).multiply(BigDecimal.valueOf(100)).longValue())
                .setProductData(productData)
                .build();

        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(1L)
                .setPriceData(priceData)
                .build();

        // create customer when empty
        if (null == ordMstDto.getStripeCustomerId()) {
            CustomerDTO customerDTO = getCustomer("dongnv","001vandong@gmail.com");
            ordMstDto.setStripeCustomerId(customerDTO.getId());
        }

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)
                .addLineItem(lineItem)
                .setCustomer(ordMstDto.getStripeCustomerId())
                .setPaymentIntentData(SessionCreateParams.PaymentIntentData.builder()
                        .setSetupFutureUsage(SessionCreateParams.PaymentIntentData.SetupFutureUsage.OFF_SESSION)
                        .build())
                .build();

        Session session;
        try {
            session = Session.create(params);

            stripeResponseDTO.setSessionId(session.getId());
            stripeResponseDTO.setSessionUrl(session.getUrl());
            stripeResponseDTO.setMessage("Payment session created successfully");
            stripeResponseDTO.setStatus("SUCCESS");
            stripeResponseDTO.setHttpStatus(200);
            stripeResponseDTO.setKeyValue(ordMstDto.getMoneyNo());
        } catch (StripeException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            stripeResponseDTO.setMessage(e.getMessage());
            stripeResponseDTO.setStatus("FAILURE");
            stripeResponseDTO.setHttpStatus(400);
        }

        return stripeResponseDTO;
    }

    public CustomerDTO getCustomer(String name, String email) {
        CustomerDTO customerDTO = new CustomerDTO();

        setStripeApiKey();

        CustomerCreateParams params = CustomerCreateParams.builder()
                .setName(name)
                .setEmail(email)
                .build();

        try {
            Customer customer = Customer.create(params);
            customerDTO.setId(customer.getId());
        } catch (StripeException e) {
            log.error(e.getMessage());
        }
        return customerDTO;
    }

    private void setStripeApiKey() {
        Stripe.apiKey = "sk_test_51QsFdSDs1SIogd7U7r9ojcBLR1IyDICMRFOjcWdGSqRXsmGtKd39KF5BosMPFrryQyIaU6WV4QddW8Cp0TFsBEG500T6xqRO9p";
    }
}