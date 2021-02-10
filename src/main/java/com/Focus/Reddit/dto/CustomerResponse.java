package com.Focus.Reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private Instant createdDate;
    private String  countryName;
    private String  userName;
    private String  postName;
    private boolean active;
    private Instant dateOfBirth;
    private String duration;
    private Integer customerCount;


}
