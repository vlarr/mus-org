package ru.vlarp.musorg.mbzc.logic.nodes;

import ru.vlarp.musorg.mbzc.logic.ProcessorApi;
import ru.vlarp.musorg.mbzc.logic.ProcessorApiResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Поиск всех артистов по заданному имени. Метод processorApi.artistSearch возвращает результаты поиска по вхохдению,
 * классе осуществляется дополнительная фильтрация.
 */
public class ArtistSearchNode extends BaseNode {
    private final String artistName;
    private int offset = 0;
    private HashMap<String, String> artists = new HashMap<>();

    public String getArtistName() {
        return artistName;
    }

    /**
     * @return map artistId->artistName
     */
    public Map<String, String> getFindingArtists() {
        return artists;
    }

    public ArtistSearchNode(String artistName) {
        this.artistName = artistName;
    }

    @Override
    public void process(ProcessorApi processorApi) {
        while (true) {
            if (!processorApi.readyForInteract() || State.COMPLETE.equals(this.state)) {
                return;
            } else {
                var artistResponse = processorApi.artistSearch(artistName, offset);
                if (ProcessorApiResponse.State.PASS.equals(artistResponse.getState())) {
                    artistResponse.getResult().getArtists()
                            .forEach(artistEntry -> artists.put(artistEntry.getId(), artistEntry.getName()));
                    offset += artistResponse.getResult().getArtists().size();

                    if (offset >= artistResponse.getResult().getCount()) {
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
