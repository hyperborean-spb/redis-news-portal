package pro.amstepanov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.amstepanov.domain.Article;
import pro.amstepanov.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByArticle(Article article);
}