package com.bank.backend.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="owner")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ownerId")
    private Long ownerId;


    @Column(name = "ownerName", nullable = false, length = 20)
    private String ownerName;


    @Column(name = "identificationNumber", nullable = false, length = 255)
    private String identificationNumber;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Account> accountList = new ArrayList<>();


    @Override
    public String toString() {
        return "Owner{" +
                "ownerId=" + ownerId +
                ", ownerName='" + ownerName + '\'' +
                ", identificationNumber='" + identificationNumber + '\'' +
                ", accountList=" + accountList +
                '}';
    }
}