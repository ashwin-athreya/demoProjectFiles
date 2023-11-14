package com.cg.mts.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private int customerId;
    private String customerName;
    private String address;
    private String mobileNumber;
    private String email;
    private String password;
    
    
   
}