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
		return ResponseEntity.ok().body(articleService.getArticleById(id));
	}
/*
	@GetMapping("/getbyname")
	@Operation(summary = "Поиск по имени")
	@SecurityRequirement(name = "JWT")
	public ResponseEntity<Page<Client>> getClientByName(@RequestParam int page, @RequestParam int size, @RequestParam  String name) {
		return ResponseEntity.ok().body(articleService.getClientsByName(page,  size,  name));
	}


	@PostMapping("/registerclient")
	@Operation(summary = "Регистрация нового клиента")
	@SecurityRequirement(name = "JWT")
	public ResponseEntity<Client> registerClient(@RequestBody @Valid ClientDto clientDto) {
		return new ResponseEntity<>(articleService.registerClient(clientDto), HttpStatus.CREATED);
	}

	@PutMapping("/moneytransfer")
	@Operation(summary = "Трансфер средств между клиентами")
	@SecurityRequirement(name = "JWT")
	public ResponseEntity<Boolean> moneyTransfer(@RequestParam long sourceId, @RequestParam long recipientId, @RequestParam  float amount) {
		return ResponseEntity.ok(articleService.moneyTransfer(sourceId, recipientId, amount));
	}*/
}