package com.mju.lighthouseai.domain.user.entity;

import com.mju.lighthouseai.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_USER")
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column
    private String birth;

    @Column
    private String profile_img_url;

    @Column
    private String folderName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Builder
    public User(
        final String email,
        final String password,
        final String nickname,
        final UserRole role,
        final String birth,
        final String profile_img_url,
        final String folderName
    ) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = UserRole.USER;
        this.birth = birth;
        this.profile_img_url = profile_img_url;
        this.folderName = folderName;
    }

    public void changeRole(UserRole newRole) {
        this.role = newRole;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateBirth(String birth) {
        this.birth = birth;
    }

    public void updateProfile_img_url(String profile_img_url) {
        this.profile_img_url = profile_img_url;
    }


}
