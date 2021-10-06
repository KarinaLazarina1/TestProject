package com.avenga.testproject.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Advertisement {
    @Id
    @GeneratedValue
    private Long id;

    private String message;
    private String senderName;
//    private User user;


    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", senderName='" + senderName + '\'' +
                '}';
    }
}
