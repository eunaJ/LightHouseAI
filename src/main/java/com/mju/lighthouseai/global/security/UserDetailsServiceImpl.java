package com.mju.lighthouseai.global.security;

import com.mju.lighthouseai.sample.entity.User;
import com.mju.lighthouseai.sample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
            new UsernameNotFoundException("사용자를 찾을 수 없습니다.")
        );

        return new UserDetailsImpl(user);
    }
}
