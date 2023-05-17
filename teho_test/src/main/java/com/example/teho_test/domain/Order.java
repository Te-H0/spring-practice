package com.example.teho_test.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "order_id")
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @Override
    public String toString() {
        return super.toString();
    }
}
