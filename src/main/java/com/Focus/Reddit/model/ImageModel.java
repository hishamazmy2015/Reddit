package com.Focus.Reddit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.http.ResponseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Blob;
import java.sql.Types;
import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.*;

@Data
@Entity
@Table(name = "image_table")
public class ImageModel  {

    public ImageModel() {

        super();
    }


    public ImageModel(String name, String type, byte[] picByte) {
        this.name = name;
        this.type = type;
        this.picByte = picByte;
    }


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "name")

    private String name;

    @Column(name = "type")

    private String type;


    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "picByte", length = 100000)
//    @Type(type = "Blob")
    private byte[] picByte;


    public String getName() {

        return name;

    }


    public void setName(String name) {

        this.name = name;

    }

    public String getType() {

        return type;

    }


    public void setType(String type) {

        this.type = type;

    }

    public byte[] getPicByte() {
        return picByte;
    }

    public void setPicByte(byte[] picByte) {
        this.picByte = picByte;
    }
}