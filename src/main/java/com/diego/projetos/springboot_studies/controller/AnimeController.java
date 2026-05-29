package com.diego.projetos.springboot_studies.controller;

import com.diego.projetos.springboot_studies.domain.Anime;
import com.diego.projetos.springboot_studies.requests.AnimePostRequestBody;
import com.diego.projetos.springboot_studies.requests.AnimePutRequestBody;
import com.diego.projetos.springboot_studies.service.AnimeService;
import com.diego.projetos.springboot_studies.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController // @Controller + @ResponseBody (response body HTTP -> contains data)
@RequestMapping("animes") // maps -> localhost:8080/animes
@Log4j2
@RequiredArgsConstructor // Lombok -> create constructor with all final fields
// @AllArgsConstructor: Lombok -> create constructor with all the fields
public class AnimeController {
    private final DateUtil dateUtil;
    private final AnimeService animeService;

    //localhost:8080/
    @GetMapping
    public ResponseEntity<List<Anime>> list() {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
//        return new ResponseEntity<>(animeService.listAll(), HttpStatus.OK); -> another way
        return ResponseEntity.ok(animeService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anime> findById(@PathVariable long id) {
        return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
    }

    @GetMapping("/find")
    public ResponseEntity<List<Anime>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(animeService.findByName(name));
        // @RequestParam args:
        // - (name = "name") -> not mandatory since Spring uses the method parameter
        // - (default = ...) -> modify the default value of the method parameter
        // - (required = true/false) -> "should the parameter be required to make this request?"
        // *** obs: you can use multiple @RequestParam in one method
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED) -> another way
    public ResponseEntity<Anime> save(@RequestBody AnimePostRequestBody animePostRequestBody) {
        log.info(animePostRequestBody.getName());
        return new ResponseEntity<>(animeService.save(animePostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody AnimePutRequestBody animePutRequestBody) {
        animeService.replace(animePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
