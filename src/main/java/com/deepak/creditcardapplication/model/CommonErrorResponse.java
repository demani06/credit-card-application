package com.deepak.creditcardapplication.model;

import lombok.*;

import java.util.List;

/*
 * Standard Error response for the complete application
 * */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonErrorResponse {

    private String errorMessage;

    private List<String> errorMessageDetails;

}
