package com.github.zigcat.qiwi_assignment.requests;

import lombok.Data;

@Data
public class TransactRequest {
    private String sender;
    private String receiver;
    private Double value;
}
