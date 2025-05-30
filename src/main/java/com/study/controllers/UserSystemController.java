package com.study.controllers;

import com.study.entities.ReviewLesson;
import com.study.entities.Reward;
import com.study.repos.IReviewLessonRepo;
import com.study.repos.IRewardRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequiredArgsConstructor
public class UserSystemController {
    @Autowired
    private IRewardRepo rewardRepo;

    @Autowired
    private IReviewLessonRepo reviewLessonRepo;

    @GetMapping("/api/reward/getPaged")
    public ResponseEntity<List<Reward>> getRewards() {
        List<Reward> all = rewardRepo.findAll();

        return ResponseEntity.ok(all);
    }

    @GetMapping("/api/reviews/getPaged")
    public ResponseEntity<List<ReviewLesson>> getReviewLessons() {
        List<ReviewLesson> all = reviewLessonRepo.findAll();

        return ResponseEntity.ok(all);
    }

    @PostMapping("/api/reviews/create")
    public ResponseEntity<ReviewLesson> createReviewLesson(@RequestBody ReviewLesson reviewLesson) {
        ReviewLesson save = reviewLessonRepo.save(reviewLesson);

        return ResponseEntity.ok(save);
    }

    @PostMapping("/api/reward/create")
    public ResponseEntity<Reward> createReviewLesson(@RequestBody Reward reward) {
        Reward save = rewardRepo.save(reward);

        return ResponseEntity.ok(save);
    }
}
