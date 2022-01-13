package com.tokki92.mreview.service;

import com.tokki92.mreview.dto.MovieDTO;
import com.tokki92.mreview.entity.Movie;
import com.tokki92.mreview.entity.MovieImage;
import com.tokki92.mreview.repository.MovieImageRepository;
import com.tokki92.mreview.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    private final MovieImageRepository imageRepository;

    @Override
    public Long register(MovieDTO movieDTO) {

        Map<String, Object> entityMap = dtoToEntity(movieDTO);
        Movie movie = (Movie) entityMap.get("movie");
        List<MovieImage> movieImageList = (List<MovieImage>) entityMap.get("imgList");

        movieRepository.save(movie);

        movieImageList.forEach(imageRepository::save);

        return movie.getMno();
    }
}
