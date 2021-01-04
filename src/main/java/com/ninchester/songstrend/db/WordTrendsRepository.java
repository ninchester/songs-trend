package com.ninchester.songstrend.db;

import com.ninchester.songstrend.db.entities.Word;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WordTrendsRepository extends JpaRepository<Word, Long> {
  @Query(
      "SELECT w FROM Word w WHERE w.word = (:word) and w.trendDate >= (:from) and w.trendDate <= (:to)")
  List<Word> findByWordAndDateRange(
      @Param("word") String word, @Param("from") String from, @Param("to") String to);
}
