package com.study.repos;

import com.study.entities.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface IRewardRepo extends JpaRepository<Reward, Long> {
}
