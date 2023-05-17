package jpabook1.jpashop1.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // ORDi 쓰면 1,2,3d으로 되는데 이게 나중에 enum이 추가되면 망함 STRING으로 쓰자
    private DeliveryStatus status; //READY, COMP
}
