package com.github.zigcat.qiwi_assignment.requests;

import lombok.Data;

@Data
public class ChangeBalanceRequest {
    private String number;
    private Double value;
}
