package com.sparta.delivery_app.domain.thanks.repository;

import com.sparta.delivery_app.domain.thanks.entity.Thanks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TanksRepository extends JpaRepository<Thanks, Long>, ThanksQueryRepository {
}
