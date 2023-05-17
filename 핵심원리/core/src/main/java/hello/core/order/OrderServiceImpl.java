package hello.core.order;

import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.discount.DiscountPolicy;
import org.springframework.stereotype.Component;

@Component

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberID, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberID);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberID, itemName, itemPrice, discountPrice);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
