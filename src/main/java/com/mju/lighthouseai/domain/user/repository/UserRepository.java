package com.mju.lighthouseai.domain.user.repository;

import com.mju.lighthouseai.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickName);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

}
