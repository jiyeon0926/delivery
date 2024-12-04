package delivery.dto.review;

import delivery.entity.review.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponseDto {
    private final Long id;
    private final String Name;
    private final String comment;
    private final int rating;
    private final LocalDateTime updatedAt;

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.Name = review.getOrder().getUser().getName();
        this.comment = review.getComment();
        this.rating = review.getRating();
        this.updatedAt = review.getModifiedAt();
    }
}