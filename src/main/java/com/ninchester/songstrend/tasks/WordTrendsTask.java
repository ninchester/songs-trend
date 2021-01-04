package com.ninchester.songstrend.tasks;

import com.ninchester.songstrend.clients.soundcloud.SoundCloudClient;
import com.ninchester.songstrend.clients.soundcloud.domain.Track;
import com.ninchester.songstrend.db.WordTrendsRepository;
import com.ninchester.songstrend.db.entities.Word;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WordTrendsTask {

  private SoundCloudClient soundCloudClient;
  private WordTrendsRepository repository;

  private static final String DATE_FORMAT = "yyyyMMdd";

  @Autowired
  public WordTrendsTask(SoundCloudClient soundCloudClient, WordTrendsRepository repository) {
    this.soundCloudClient = soundCloudClient;
    this.repository = repository;
  }

  @Scheduled(cron = "0 0 12 * * ?")
  //    @Scheduled(cron = "0 */1 * ? * *")
  public void fetchTracks() {
    Track[] tracks = soundCloudClient.getTracks();
    if (tracks != null && tracks.length != 0) {
      Map<String, Integer> wordsTrends = transformTrackTitlesToTrendingWords(tracks);
      storeToDb(wordsTrends);
    }
  }

  private void storeToDb(Map<String, Integer> wordsTrends) {
    String today = getTodaysDate();
    for (Map.Entry<String, Integer> trends : wordsTrends.entrySet()) {
      Word word = new Word();
      word.setWord(trends.getKey());
      word.setTrendDate(today);
      word.setWordCount(trends.getValue());

      repository.save(word);
    }
  }

  private String getTodaysDate() {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    return now.format(formatter);
  }

  private Map<String, Integer> transformTrackTitlesToTrendingWords(Track[] tracks) {
    List<String> words = new ArrayList<>();
    for (Track track : tracks) {
      String title = track.getTitle();
      List<String> wordsFromTitle = detectWords(title);
      words.addAll(wordsFromTitle);
    }

    return countWords(words);
  }

  private Map<String, Integer> countWords(List<String> words) {
    Map<String, Integer> wordsTrends = new HashMap<>();
    for (String word : words) {
      word = word.toLowerCase();
      if (wordsTrends.containsKey(word)) {
        Integer count = wordsTrends.get(word);
        wordsTrends.put(word, ++count);
      } else {
        wordsTrends.put(word, 1);
      }
    }

    return wordsTrends;
  }

  private List<String> detectWords(String sentence) {
    sentence = sentence.toLowerCase();
    String[] potentialWords = sentence.split("\\s+");
    List<String> words = new ArrayList<>();
    for (String potentialWord : potentialWords) {
      String word = potentialWord.replaceAll("\\p{Punct}", "");
      if (!word.equals("") && (word.length() != 1)) {
        words.add(word);
      }
    }
    return words;
  }
}
