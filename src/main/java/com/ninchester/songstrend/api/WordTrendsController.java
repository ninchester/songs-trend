package com.ninchester.songstrend.api;

import com.ninchester.songstrend.api.domain.TimeSeries;
import com.ninchester.songstrend.api.domain.WordTrend;
import com.ninchester.songstrend.db.entities.Word;
import com.ninchester.songstrend.db.WordTrendsRepository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordTrendsController {

  @Autowired private WordTrendsRepository repository;

  @GetMapping("/word")
  public WordTrend getWordTrends(
      @RequestParam(value = "word") String word,
      @RequestParam(value = "from") String from,
      @RequestParam(value = "to") String to) {
    List<Word> wordTrendsList = repository.findByWordAndDateRange(word, from, to);
    WordTrend wordTrend = convertFromWordToWordTrend(wordTrendsList);

    return wordTrend;
  }

  private WordTrend convertFromWordToWordTrend(List<Word> wordDbList) {
    String word = "";
    List<TimeSeries> timeSeriesList = new ArrayList<>();
    for (Word wordDb : wordDbList) {
      word = wordDb.getWord();
      TimeSeries timeSeries = new TimeSeries(wordDb.getWordCount(), wordDb.getTrendDate());
      timeSeriesList.add(timeSeries);
    }

    return new WordTrend(word, timeSeriesList);
  }
}
