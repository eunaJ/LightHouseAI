package com.mju.lighthouseai.domain.like.service;

import com.mju.lighthouseai.domain.user.entity.User;

public interface LikeService {
    void addLike (Long boardId, User user);
    void deleteLike(Long boardId, User user);
    Long getAllLikes (Long boardId);
    Boolean isLike(Long boardId, User user);
}