package com.ISA.ISA.controller;

import com.ISA.ISA.domain.DTO.NewsDTO;
import com.ISA.ISA.domain.DTO.NewsResponseDTO;
import com.ISA.ISA.domain.News;
import com.ISA.ISA.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping(path = "/post")
    public ResponseEntity<?> addNews(@RequestBody NewsDTO newsDTO){
        News news = newsService.Add(newsDTO);

        if(news == null){
            return ResponseEntity.ok("Bad Req");
        }

        return new ResponseEntity<>(news, HttpStatus.OK);
    }


    @GetMapping (path = "/getAll/{date}")
    public ResponseEntity<?> getAll(@PathVariable  String date) {
        LocalDate date1 = LocalDate.parse(date);
        List<NewsResponseDTO> news = newsService.GetAll(date1);

        if (news.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(news, HttpStatus.OK);
    }

}
