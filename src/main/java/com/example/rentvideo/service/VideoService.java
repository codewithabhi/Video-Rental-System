package com.example.rentvideo.service;

import com.example.rentvideo.dto.VideoRequest;
import com.example.rentvideo.dto.VideoResponse;
import com.example.rentvideo.entity.Video;
import com.example.rentvideo.exception.ResourceNotFoundException;
import com.example.rentvideo.repository.VideoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {

    private final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public List<VideoResponse> getAvailableVideos() {
        return videoRepository.findByAvailable(true).stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<VideoResponse> getAllVideos() {
        return videoRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public VideoResponse getVideoById(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found with id: " + id));
        return mapToResponse(video);
    }

    public VideoResponse createVideo(VideoRequest request) {
        Video video = new Video(
                request.getTitle(),
                request.getDirector(),
                request.getGenre(),
                Boolean.TRUE.equals(request.getAvailable())
        );
        return mapToResponse(videoRepository.save(video));
    }

    public VideoResponse updateVideo(Long id, VideoRequest request) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found with id: " + id));

        video.setTitle(request.getTitle());
        video.setDirector(request.getDirector());
        video.setGenre(request.getGenre());
        video.setAvailable(Boolean.TRUE.equals(request.getAvailable()));

        return mapToResponse(videoRepository.save(video));
    }

    public void deleteVideo(Long id) {
        if (!videoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Video not found with id: " + id);
        }
        videoRepository.deleteById(id);
    }

    private VideoResponse mapToResponse(Video video) {
        return new VideoResponse(
                video.getId(),
                video.getTitle(),
                video.getDirector(),
                video.getGenre(),
                video.isAvailable()
        );
    }
}
