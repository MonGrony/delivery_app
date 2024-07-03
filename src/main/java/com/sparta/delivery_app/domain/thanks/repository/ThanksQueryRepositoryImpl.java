package com.sparta.delivery_app.domain.thanks.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.delivery_app.domain.thanks.entity.Thanks;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.sparta.delivery_app.domain.commen.page.util.PageUtil.PAGE_SIZE_FIVE;
import static com.sparta.delivery_app.domain.thanks.entity.QThanks.thanks;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class ThanksQueryRepositoryImpl implements ThanksQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Thanks> findPersonalThanksAll(User user, Pageable pageable) {

        List<Thanks> result = query(thanks, user)
                .orderBy(thanks.userReviews.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(PAGE_SIZE_FIVE)
                .fetch();

        long total = countQuery(user).fetch().get(0);

        return PageableExecutionUtils.getPage(result, pageable, () -> total);

    }

    private <T> JPAQuery<T> query(Expression<T> expression, User user) {
        return jpaQueryFactory
                .select(expression)
                .from(thanks)
                .leftJoin(thanks.user).fetchJoin()
                .leftJoin(thanks.userReviews).fetchJoin()
                .where(userIdEq(user.getId()));
    }

    private JPAQuery<Long> countQuery(User user) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(thanks)
                .where(userIdEq(user.getId()));
    }

    private BooleanExpression userIdEq(Long userId) {
        return nonNull(userId) ? thanks.user.id.eq(userId) : null;
    }
}
