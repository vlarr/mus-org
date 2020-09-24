package ru.vlarp.musorg.mbzc.logic.nodes;

import ru.vlarp.musorg.mbzc.logic.ProcessorApi;
import ru.vlarp.musorg.mbzc.logic.ProcessorApiResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Чтение всех релизов одного артиста. Если processorApi вернул кэшированый результат, то логика сразу начинает
 * выполнять чтение следующей пачки (если она есть).
 */
public class FindArtistReleasesNode extends BaseNode {
    private final String artistId;
    private final String artistName;
    private HashMap<String, String> releases;
    private int offset = 0;

    public Map<String, String> getReleases() {
        return releases;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public FindArtistReleasesNode(String artistId, String artistName) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.releases = new HashMap<>();
    }

    @Override
    public void process(ProcessorApi processorApi) {
        while (true) {
            if (!processorApi.readyForInteract() || State.COMPLETE.equals(this.state)) {
                return;
            } else {
                var releaseResponse = processorApi.findArtistReleases(artistId, offset);
                if (ProcessorApiResponse.State.PASS.equals(releaseResponse.getState())) {
                    releaseResponse.getResult().getReleases()
                            .forEach(releaseEntry -> releases.put(releaseEntry.getId(), releaseEntry.getTitle()));

                    offset += releaseResponse.getResult().getReleases().size();

                    if (offset >= releaseResponse.getResult().getReleaseCount()) {
                        this.state = State.COMPLETE;
                        return;
                    } else {
                        this.state = State.FILL_RESULT;
                    }
                }
            }
        }
    }
}
