package com.ISA.ISA.service;

import com.ISA.ISA.domain.DTO.NewsDTO;
import com.ISA.ISA.domain.DTO.NewsResponseDTO;
import com.ISA.ISA.domain.News;

import java.time.LocalDate;
import java.util.List;

public interface NewsService {

    News Add(NewsDTO newsDTO);

    List<NewsResponseDTO> GetAll(LocalDate date);
}
