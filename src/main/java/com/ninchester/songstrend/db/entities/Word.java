package com.ninchester.songstrend.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Table(
    uniqueConstraints = @UniqueConstraint(columnNames = {"word", "trend_date"}),
    indexes = {
      @Index(name = "word_index", columnList = "word"),
      @Index(name = "trend_date_index", columnList = "trend_date")
    })
@Entity
public class Word {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "word")
  private String word;

  @Column(name = "word_count")
  private long wordCount;

  @Column(name = "trend_date")
  private String trendDate;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public long getWordCount() {
    return wordCount;
  }

  public void setWordCount(long wordCount) {
    this.wordCount = wordCount;
  }

  public String getTrendDate() {
    return trendDate;
  }

  public void setTrendDate(String trendDate) {
    this.trendDate = trendDate;
  }
}
