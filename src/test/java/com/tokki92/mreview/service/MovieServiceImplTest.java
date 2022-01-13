package com.tokki92.mreview.service;

import com.tokki92.mreview.dto.MovieDTO;
import com.tokki92.mreview.dto.MovieImageDTO;
import com.tokki92.mreview.entity.Movie;
import com.tokki92.mreview.entity.MovieImage;
import com.tokki92.mreview.repository.MovieImageRepository;
import com.tokki92.mreview.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @InjectMocks
    MovieServiceImpl movieService;

    @Mock
    MovieRepository movieRepository;

    @Mock
    MovieImageRepository imageRepository;

    @Test
    void testRegister() {
        // given
        List<MovieImageDTO> imageDTOList = IntStream.rangeClosed(1, 3).asLongStream().mapToObj(i ->
            MovieImageDTO.builder()
                    .uuid(UUID.randomUUID().toString())
                    .imgName("imgName..." + i)
                    .path("/path")
                    .build()
        ).collect(Collectors.toList());
        MovieDTO movieDTO = MovieDTO.builder()
                .mno(92L)
                .title("tokki92")
                .imageDTOList(imageDTOList)
                .build();

        // mocking
        when(movieRepository.save(any(Movie.class))).then(returnsFirstArg());
        when(imageRepository.save(any(MovieImage.class))).then(returnsFirstArg());

        // when
        Long mno = movieService.register(movieDTO);

        // then
        assertEquals(92L, mno);
    }
}