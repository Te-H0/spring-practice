package com.example.teho_test.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(value = false)
class mappingCascadeTest {

    @PersistenceContext
    EntityManager em;

    @Test()
    public void mappingTest1() throws Exception {
        Order order = new Order();
        Member member = new Member();
        order.setName("cofee");
        member.setName("teho");

        em.persist(order);
        em.persist(member);

        member.getOrders().add(order);

        System.out.println("===================================");
        System.out.println("member.getOrders().size() = " + member.getOrders().size());
        System.out.println("order.getMember().getName() = " + order.getMember().getName());
        //주인아닌 곳에 해서 Order에 member_id(FK) 매핑 안돼!

//        order.setMember(member); // 매핑 후 수정 해볼거
////        order.getMember().setName("seo");
//// ----------------------당연 성공
//        System.out.println(order.getMember().getName());
//        member.setName("seo");
//        System.out.println("===================================");
//        System.out.println(order.getMember().getName());
        //-----------------객체도 데베에도 값이 바뀌네


    }

    @Test
    public void mappingTest2() throws Exception {

        Member member = new Member();
        Order order = new Order();

        member.setName("teho");
        order.setName("cofee");
        em.persist(member);
        em.persist(order);
        order.setMember(member);

        em.flush();
        em.clear();
        Member member1 = em.find(Member.class, member.getId());

        System.out.println("============================================");
        System.out.println("order.getMember().getName() = " + order.getMember().getName());
        System.out.println("member.getOrders().size() = " + member.getOrders().size());
        System.out.println("member1.getOrders().size() = " + member1.getOrders().size());
        System.out.println("============================================");

        //연관관계 매핑 됨! 근데 객체 입장에서 볼때 member에 orders가 세팅안돼
//       //member.getOrders().add(order)까지 해주는게 정석.

//        Member member1 = em.find(Member.class, 1);
//
//
//        System.out.println("===================================");
////        System.out.println("member.getOrders().size() = " + member.getOrders().size());
////        System.out.println("order.getMember().getName() = " + order.getMember().getName());
//        System.out.println("member1.getOrders().size() = " + member1.getOrders().size());
//        System.out.println("member1.getName() = " + member1.getName());
//
//
//        Assertions.assertEquals(member1.getName(), "teho");
    }

    @Test
    public void mappingTest3() throws Exception {
        Order order = em.find(Order.class, 2);//1차 캐시 저장 order.name=coke로 가정 이때 값으로 스냅샷!!!
        System.out.println("order.getName() = " + order.getName());//
        order.setName("coffee");
        Order order1 = em.find(Order.class, 2);
        System.out.println("===================================");
        System.out.println("order1.getName() = " + order1.getName());//coffee라고 나올거
        order.setName("coke");
        System.out.println("order.getName() = " + order.getName());
        // db에서 꺼내간값이 a 일때  a->c->a하고 commit되면 update쿼리안나감 스냅샷이랑 비교했을때 같아서
        // dirty checking 해도 차이없어 대박대박

    }

    @Test
    public void mappingTest4() throws Exception {
        Member member = new Member();
        Order order = new Order();
        member.setName("teho4");
        order.setName("cofee4");
        em.persist(member);
        em.persist(order);

        member.setName("teho5");
        // 헐 update쿼리가 나가 스냅샷이랑 다르면 무조건 update쿼리가 나가는거구나.

    }

    @Test
    public void mappingTest5() throws Exception {
        Member member = new Member();
        Order order = new Order();
        member.setName("KKKseo");
        order.setName("Latte");
        em.persist(member);
        em.persist(order);
        em.flush();
        member.setName("tehoseo");
        Member member1 = em.find(Member.class, member.getId());
        System.out.println("member1.getName() = " + member1.getName()); //tehoseo

    }
}