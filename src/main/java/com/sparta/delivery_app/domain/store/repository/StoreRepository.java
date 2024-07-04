package com.sparta.delivery_app.domain.store.repository;

import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.store.entity.StoreStatus;
import com.sparta.delivery_app.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long>, storeQueryRepository {

    Optional<Store> findByUserOrStoreRegistrationNumber(User user, String storeRegistrationNumber);

    Optional<Store> findStoreByUser(User user);

    Page<Store> findAllByStatus(Pageable pageable, StoreStatus storeStatus);
}
