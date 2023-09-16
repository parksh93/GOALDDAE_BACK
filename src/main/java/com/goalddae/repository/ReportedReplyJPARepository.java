package com.goalddae.repository;

import com.goalddae.entity.ReportedReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportedReplyJPARepository extends JpaRepository <ReportedReply, Long> {
    Optional<ReportedReply> findByReplyIdAndReporterUserId(long replyId, long reporterUserId);
}
