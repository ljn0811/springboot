package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional
public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em; //jpa는 entityManager를 통해 동작한다.

    public JpaMemberRepository(EntityManager em) {
        //파라미터 추가
        this.em = em;
    }
    @Override
    public Member save(Member member) {
        //회원가입
        em.persist(member);
        return member;
    }
    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }
    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }
    @Override
    public List<Member> findAll() {
        //객체를 대상으로 쿼리를 날림
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
    @Override
    public void clearStore() {

    }
}
