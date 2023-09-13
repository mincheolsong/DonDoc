package com.dondoc.backend.group.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="Category")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @Column(name="id")
    private Long id;

    @Column(name="name", nullable = false, length = 20)
    private String name;

}
