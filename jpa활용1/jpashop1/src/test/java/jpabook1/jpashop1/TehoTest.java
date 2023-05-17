package jpabook1.jpashop1;

import jakarta.persistence.EntityManager;
import jpabook1.jpashop1.domain.Address;
import jpabook1.jpashop1.domain.Member;
import jpabook1.jpashop1.repository.OrderRepository;
import jpabook1.jpashop1.service.OrderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TehoTest {
    @Autowired
    EntityManager em;//오류나도 괜찮음 intellij 오류임

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void test1() throws Exception {

    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }
}
