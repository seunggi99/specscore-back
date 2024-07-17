package imade.specscore.service;

import imade.specscore.domain.Lecture;
import imade.specscore.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;
}
