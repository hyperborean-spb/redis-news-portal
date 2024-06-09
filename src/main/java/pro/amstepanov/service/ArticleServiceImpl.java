package pro.amstepanov.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pro.amstepanov.domain.Comment;
import pro.amstepanov.dto.ArticleDto;
import pro.amstepanov.exception.ArticleNotFoundException;
import pro.amstepanov.repository.ArticleRepository;
import pro.amstepanov.repository.CommentRepository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService {

	private final ArticleRepository articleRepository;
	private final CommentRepository commentRepository;
	private final ModelMapper mapper;

	JedisPool pool = new JedisPool("localhost", 6379);


	@Override
	public ArticleDto getArticleById(Long id){
		return articleRepository.findById(id)
		.map(article -> new ArticleDto(
			article.getId(),
			article.getContent(),
			commentRepository.findByArticle(article).stream().mapToInt(Comment::getRating).average().orElse(0)
		))
		.orElseThrow(() ->new ArticleNotFoundException("статья с указанным индексом не найдена"));
	}

	@Override
	public ArticleDto getCachedArticleById(Long id){

		try(Jedis jedis = pool.getResource()){
			// чтение данных по ключу.Формат ключа примем - article:id
			String key = String.format("article:%d",id);
			String record = jedis.get(key);
			if(record !=null)
				return mapper.map(record, ArticleDto.class);
			//TODO
			// jedis.setex(key, TTY, mapper.)
			return getArticleById(id);
		}

	}
}