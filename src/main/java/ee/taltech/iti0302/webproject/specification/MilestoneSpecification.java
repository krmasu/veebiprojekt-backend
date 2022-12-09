package ee.taltech.iti0302.webproject.specification;

import ee.taltech.iti0302.webproject.entity.Milestone;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.text.MessageFormat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MilestoneSpecification {

    public static Specification<Milestone> byProject(Integer projectId) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("project").get("id"), projectId));
    }

    public static Specification<Milestone> titleContains(String titleExpression) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), contains(titleExpression)));
    }

    private static String contains(String expression) {
        return MessageFormat.format("%{0}%", expression);
    }

}
