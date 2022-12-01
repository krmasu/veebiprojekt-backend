package ee.taltech.iti0302.webproject.specification;

import ee.taltech.iti0302.webproject.entity.Task;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.text.MessageFormat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TaskSpecification {
    public static Specification<Task> byProject(Integer projectId) {
        return ((root, query, builder) -> builder.equal(root.get("project").get("id"), projectId));
    }
    public static Specification<Task> titleContains(String titleExpression) {
        return (root, query, builder) -> builder.like(root.get("title"), contains(titleExpression));
    }
    public static Specification<Task> assigneeContains(String assigneeExpression) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("assignee").get("username"), contains(assigneeExpression)));
    }
    public static Specification<Task> byStatus(Integer statusId) {
        return (root, query, builder) -> builder.equal(root.get("status").get("id"), statusId);
    }
    public static Specification<Task> byMilestone(Integer milestoneId) {
        return (root, query, builder) -> builder.equal(root.get("milestone").get("id"), milestoneId);
    }
    private static String contains(String expression) {
        return MessageFormat.format("%{0}%", expression);
    }
}
