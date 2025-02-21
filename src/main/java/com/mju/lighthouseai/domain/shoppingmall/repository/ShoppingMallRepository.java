package com.mju.lighthouseai.domain.shoppingmall.repository;

import com.mju.lighthouseai.domain.shoppingmall.entity.ShoppingMall;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingMallRepository extends JpaRepository<ShoppingMall,Long> {
    Optional<ShoppingMall> findShoppingMallByTitle(String shoppingMall_title);
    List<ShoppingMall> findByConstituencyId(long id);
}