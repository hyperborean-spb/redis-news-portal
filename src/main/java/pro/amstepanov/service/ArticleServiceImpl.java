package pro.amstepanov.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pro.amstepanov.domain.Article;
import pro.amstepanov.exception.ArticleNotFoundException;
import pro.amstepanov.repository.ArticleRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService {

	private final ArticleRepository articleRepository;
	//private final ModelMapper modelMapper;

	@Transactional
	@Override
	public Article getArticleById(Long id){
		return articleRepository.findById(id).orElseThrow(() ->new ArticleNotFoundException("статья с указанным индексом не найдена"));
	}
}