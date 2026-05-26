package com.diego.projetos.springboot2_essentials.controller;

import com.diego.projetos.springboot2_essentials.domain.Anime;
import com.diego.projetos.springboot2_essentials.service.AnimeService;
import com.diego.projetos.springboot2_essentials.util.DateUtil;
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
        return ResponseEntity.ok(animeService.findById(id));
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED) -> another way
    public ResponseEntity<Anime> save(@RequestBody Anime anime) {
        return new ResponseEntity<>(animeService.save(anime), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody Anime anime) {
        animeService.replace(anime);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
