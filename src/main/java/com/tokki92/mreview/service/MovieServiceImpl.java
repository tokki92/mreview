package com.tokki92.mreview.service;

import com.tokki92.mreview.dto.MovieDTO;
import com.tokki92.mreview.dto.PageRequestDTO;
import com.tokki92.mreview.dto.PageResultDTO;
import com.tokki92.mreview.entity.Movie;
import com.tokki92.mreview.entity.MovieImage;
import com.tokki92.mreview.repository.MovieImageRepository;
import com.tokki92.mreview.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    private final MovieImageRepository imageRepository;

    @Override
    public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("mno").descending());

        Page<Object[]> result = movieRepository.getListPage(pageable);
        result.stream().forEach(arr -> {
            System.out.println(Arrays.toString(arr));
        });

        Function<Object[], MovieDTO> fn = (arr -> {
            return entitiesToDTO(
                    (Movie) arr[0],
                    Arrays.asList((MovieImage) arr[1]),
                    (Double) arr[2],
                    (Long) arr[3]);
        }
        );

        return new PageResultDTO<>(result, fn);
    }

    @Transactional
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
