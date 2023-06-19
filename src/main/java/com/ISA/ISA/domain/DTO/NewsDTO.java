package com.ISA.ISA.domain.DTO;

import com.ISA.ISA.domain.News;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
public class NewsDTO {
    private int medicalCenterID;
    private LocalDate dateOfNews;
    private String description;

    public static News ConvertToNews(NewsDTO newsDTO){
        News news = new News();

        news.setDateOfNews(newsDTO.getDateOfNews());
        news.setDescription(newsDTO.getDescription());

        return news;
    }

}
