package com.dongnv.CorePayment.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrdMstDto {
    String moneyNo;
    String pdtName;
    String amt;
    String stripeCustomerId;
    String bName;
    String bMail;
}
