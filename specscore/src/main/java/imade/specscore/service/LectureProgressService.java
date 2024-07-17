package imade.specscore.service;

import imade.specscore.domain.LectureProgress;
import imade.specscore.repository.LectureProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class LectureProgressService {
    private final LectureProgressRepository lectureProgressRepository;

}
