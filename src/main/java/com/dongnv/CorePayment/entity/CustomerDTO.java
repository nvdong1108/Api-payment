package com.dongnv.CorePayment.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerDTO {

    String name;
    String email;
    String comId;
    String id;

}
