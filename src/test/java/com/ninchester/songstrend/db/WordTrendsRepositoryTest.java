package com.ninchester.songstrend.db;

import static org.assertj.core.api.Assertions.assertThat;

import com.ninchester.songstrend.db.entities.Word;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class WordTrendsRepositoryTest {
  @Autowired WordTrendsRepository repository;

  @Test
  public void given_trends_exist_when_query_then_correct_trends_returned() {
    prepareTestDataWithTwoWords();

    List<Word> trendsList = repository.findByWordAndDateRange("love", "20160101", "20160501");

    assertThat(trendsList.size() == 2);
  }

  private void prepareTestDataWithTwoWords() {
    Word trends1 = new Word();
    trends1.setId(1L);
    trends1.setWord("love");
    trends1.setWordCount(13);
    trends1.setTrendDate("20160101");

    Word trends2 = new Word();
    trends2.setId(2L);
    trends2.setWord("love");
    trends2.setWordCount(43);
    trends2.setTrendDate("20160501");

    repository.save(trends1);
    repository.save(trends2);
  }
}
