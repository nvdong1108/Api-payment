package com.dongnv.CorePayment.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StripeResponseDTO {
    String status;
    String message;
    Integer httpStatus;
    String brand;

    String expMonth;

    String expYear;

    String lastCardNo;

    String invoiceId;

    String sessionId;

    String sessionStatus;
    
    String paymentStatus;

    String paymentMethod;

    String paymentIntentId;

    String subscriptionId;

    String adoNo;

    String sessionUrl;

    String stripeCustomerId;

    String clientId;

    String keyValue;
}
