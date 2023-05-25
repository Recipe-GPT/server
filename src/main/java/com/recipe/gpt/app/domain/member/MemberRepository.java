package com.recipe.gpt.app.domain.member;

import com.recipe.gpt.common.exception.MemberNotFoundException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(final String email);

    Optional<Member> findByEmail(final String email);

    default Member getByEmail(final String email) {
        return findByEmail(email)
            .orElseThrow(MemberNotFoundException::new);
    }

}
