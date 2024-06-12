package pro.amstepanov;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class RedisInfoApplication{

	public static void main(String[] args) {
		SpringApplication.run(RedisInfoApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
		.setMatchingStrategy(MatchingStrategies.LOOSE)
		.setFieldMatchingEnabled(true)
		.setSkipNullEnabled(true)
		.setFieldAccessLevel(PRIVATE);
		return mapper;
	}
}