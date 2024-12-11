package delivery.dto.review;

import delivery.entity.review.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponseDto {
    private final Long id;
    private final String menu;
    private final String name;
    private final String comment;
    private final int rating;
    private final LocalDateTime modifiedAt;

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.menu = review.getOrder().getMenu().getName();
        this.name = review.getOrder().getUser().getName();
        this.comment = review.getComment();
        this.rating = review.getRating();
        this.modifiedAt = review.getModifiedAt();
    }
}