package com.example.teho_test.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @Column(name = "member_id")
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}
