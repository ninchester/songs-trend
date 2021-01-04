package com.ninchester.songstrend.tasks;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ninchester.songstrend.clients.soundcloud.SoundCloudClient;
import com.ninchester.songstrend.clients.soundcloud.domain.Track;
import com.ninchester.songstrend.db.WordTrendsRepository;
import com.ninchester.songstrend.db.entities.Word;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class WordTrendsTaskTest {

  private WordTrendsTask task;

  private SoundCloudClient soundCloudClient = mock(SoundCloudClient.class);
  private WordTrendsRepository repository = mock(WordTrendsRepository.class);

  public WordTrendsTaskTest() {
    task = new WordTrendsTask(soundCloudClient, repository);
  }

  @Test
  public void when_task_is_run_data_successfully_inserted() {
    when(soundCloudClient.getTracks()).thenReturn(prepareTracksResponse());
    when(repository.save(any())).thenReturn(mock(Word.class));

    task.fetchTracks();

    verify(repository, times(4)).save(any());
  }

  private Track[] prepareTracksResponse() {
    Track track = new Track();
    track.setTitle("Test title");

    Track track2 = new Track();
    track2.setTitle("Test title two times");

    return new Track[] {track, track2};
  }
}
