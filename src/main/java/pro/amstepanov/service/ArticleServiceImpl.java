package pro.amstepanov.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;
import pro.amstepanov.domain.Article;
import pro.amstepanov.domain.Comment;
import pro.amstepanov.dto.ArticleDto;
import pro.amstepanov.exception.ArticleNotFoundException;
import pro.amstepanov.exception.DtoSerializationException;
import pro.amstepanov.repository.ArticleRepository;
import pro.amstepanov.repository.CommentRepository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService {

	private final ArticleRepository articleRepository;
	private final CommentRepository commentRepository;
	private  final ModelMapper modelMapper;
	private final ObjectMapper jsonMapper;;

	JedisPool pool = new JedisPool("localhost", 6379);


	public ArticleDto getArticleByIdSimpleWay(Long id){
		return articleRepository.findById(id)
		.map(article -> new ArticleDto(
			article.getId(),
			article.getContent(),
			commentRepository.findByArticle(article).stream().mapToInt(Comment::getRating).average().orElse(0)
		))
		.orElseThrow(() ->new ArticleNotFoundException("article not found"));
	}

	@Override
	public ArticleDto getArticleById(Long id){
		return articleRepository.findById(id)
		.map(article -> {
			modelMapper.createTypeMap(Article.class,  ArticleDto.class).setConverter(converter);
			return modelMapper.map(article, ArticleDto.class);
		})
		.orElseThrow(() ->new ArticleNotFoundException("article not found"));
	}

	@Override
	public ArticleDto getCachedArticleById(Long id){
		ArticleDto articleDto;
		try(Jedis jedis = pool.getResource()){
			// reading data from Redis cache. Key format - article:id
			String key = String.format("article:%d",id);
			String redisRecord = jedis.get(key);

			// if data found in cache, return it after deserialization to ArticleDto
			if(redisRecord != null)
				return jsonMapper.readValue(redisRecord, ArticleDto.class);

			// if data not found in cache, get data from Postgres while saving to cache
			articleDto = getArticleById(id);
			jedis.setex(key, 100, jsonMapper.writeValueAsString(articleDto));
			return articleDto;
		} catch (JsonProcessingException e) {
			throw new DtoSerializationException("conversion exception while writing DTO as String to  cache");
		}
	}

	private Double returnAverageScore(Article article){
		return commentRepository.findByArticle(article).stream().mapToInt(Comment::getRating).average().orElse(0);
	}

	Converter<Article, ArticleDto> converter = context -> {
		ArticleDto dto = new ArticleDto();
		Article article = context.getSource();
		dto.setAverageScore(returnAverageScore(article));
		dto.setId(article.getId());
		dto.setContent(article.getContent());
		return dto;
	};

	private PropertyMap<Article, ArticleDto>  dtoPropertyMap = new PropertyMap<Article, ArticleDto>() {
		@Override
		protected void configure() {
			/*you cannot execute complex custom logic directly within PropertyMap.configure(), but you can invoke methods that do.
			if you want to add complex custom logic, you could use a Converter, but never use a PropertyMap
			* https://stackoverflow.com/questions/44739738/modelmapper-ensure-that-method-has-zero-parameters-and-does-not-return-void
			map().setAverageScore(commentRepository.findByArticle(source).stream().mapToInt(Comment::getRating).average().orElse(0));
			*/
		}
	};
}