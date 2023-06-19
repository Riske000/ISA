package com.ISA.ISA.domain.DTO;

import com.ISA.ISA.domain.News;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class NewsResponseDTO {
    private String medicalCenterName;
    private LocalDate dateOfNews;
    private String description;

    public static NewsResponseDTO ConvertToResponse(News news){
        NewsResponseDTO newsResponseDTO = new NewsResponseDTO();

        newsResponseDTO.setMedicalCenterName(news.getMedicalCenter().getCenterName());
        newsResponseDTO.setDateOfNews(news.getDateOfNews());
        newsResponseDTO.setDescription(news.getDescription());

        return newsResponseDTO;
    }
}

