package com.Focus.Reddit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`customer`")
public class Customer {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotEmpty
    private String firstName;
    private String lastName;
    private Instant dateOfBirth;
    private Instant createdDate;
    @OneToOne
    private Country country;

    private String fileName;

//    @OneToOne
//    private ImageModel imageModel;



}
