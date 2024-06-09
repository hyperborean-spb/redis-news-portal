package pro.amstepanov.service;

import pro.amstepanov.dto.ArticleDto;

public interface ArticleService {

	ArticleDto getArticleById(Long  id);
	ArticleDto getCachedArticleById(Long id);
}