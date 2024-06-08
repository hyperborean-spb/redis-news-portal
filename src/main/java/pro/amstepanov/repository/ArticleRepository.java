package pro.amstepanov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.amstepanov.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}