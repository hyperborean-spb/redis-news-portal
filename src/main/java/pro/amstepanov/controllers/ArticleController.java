package pro.amstepanov.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pro.amstepanov.dto.ArticleDto;
import pro.amstepanov.service.ArticleService;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleController {

	private final ArticleService articleService;

	@GetMapping("/getbyid/{id}")
	public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id) {
		return ResponseEntity.ok().body(articleService.getCachedArticleById(id));
	}
}