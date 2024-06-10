package pro.amstepanov.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.type.SerializationException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
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
	private final ModelMapper dtoMapper;
	private final ObjectMapper jsonMapper;;

	JedisPool pool = new JedisPool("localhost", 6379);


	@Override
	public ArticleDto getArticleById(Long id){
		return articleRepository.findById(id)
		.map(article -> new ArticleDto(
			article.getId(),
			article.getContent(),
			commentRepository.findByArticle(article).stream().mapToInt(Comment::getRating).average().orElse(0)
		))
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

	/*not appropriate as there is no  Source getter
	private void  convertToDto(){
		TypeMap<Article, ArticleDto> propertyMapper = this.mapper.createTypeMap(Article.class, ArticleDto.class);
		propertyMapper.addMapping(
			Article::get..., ArticleDto::setAverageScore)
		);
	}*/
}