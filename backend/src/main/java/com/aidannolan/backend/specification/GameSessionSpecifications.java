package com.aidannolan.backend.specification;

import com.aidannolan.backend.entity.GameSession;
import com.aidannolan.backend.enums.Difficulty;
import org.springframework.data.jpa.domain.Specification;

public final class GameSessionSpecifications {
    private GameSessionSpecifications() {
    }

    public static Specification<GameSession> hasUserId(Long userId) {
        return (root, query, cb) ->
                cb.equal(root.get("user").get("id"), userId);
    }

    public static Specification<GameSession> hasDifficulty(Difficulty difficulty) {
        return (root, query, cb) ->
                difficulty == null
                    ? cb.conjunction()
                    : cb.equal(root.get("difficulty"), difficulty);
    }

    public static Specification<GameSession> hasCategory(Integer categoryId) {
        return (root, query, cb) ->
                categoryId == null
                    ? cb.conjunction()
                    : cb.equal(root.get("categoryId"), categoryId);
    }

    public static Specification<GameSession> isFinished(Boolean finished) {
        return (root, query, cb) -> {
            if (finished == null) {
                return cb.conjunction();
            }

            return finished
                    ? cb.isNotNull(root.get("finishedAt"))
                    : cb.isNull(root.get("finishedAt"));
        };
    }

    public static Specification<GameSession> isInProgress() {
        return (root, query, cb) ->
                cb.isNull(root.get("finishedAt"));
    }
}
