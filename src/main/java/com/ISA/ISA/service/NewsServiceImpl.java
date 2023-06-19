package com.ISA.ISA.service;


import com.ISA.ISA.domain.DTO.NewsDTO;
import com.ISA.ISA.domain.DTO.NewsResponseDTO;
import com.ISA.ISA.domain.News;
import com.ISA.ISA.repository.MedicalCenterRepository;
import com.ISA.ISA.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private MedicalCenterRepository medicalCenterRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public News Add(NewsDTO newsDTO){
        News news = NewsDTO.ConvertToNews(newsDTO);

        news.setMedicalCenter(medicalCenterRepository.findById(newsDTO.getMedicalCenterID()).get());

        news = newsRepository.save(news);

        return news;
    }

    @Override
    public List<NewsResponseDTO> GetAll(LocalDate date){
        List<News> news = newsRepository.findAll();
        List<NewsResponseDTO> latestNews = new ArrayList<>();

        for (News temp : news){
            if(temp.getDateOfNews().isAfter(date)){
                latestNews.add(NewsResponseDTO.ConvertToResponse(temp));
            }
        }

        return latestNews;
    }
}
