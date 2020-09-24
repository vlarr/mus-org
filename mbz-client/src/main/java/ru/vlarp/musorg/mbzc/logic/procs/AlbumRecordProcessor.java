package ru.vlarp.musorg.mbzc.logic.procs;

import org.apache.commons.lang3.StringUtils;
import ru.vlarp.musorg.mbzc.logic.ProcessorApi;
import ru.vlarp.musorg.mbzc.logic.nodes.ArtistSearchNode;
import ru.vlarp.musorg.mbzc.logic.nodes.FindArtistReleasesNode;
import ru.vlarp.musorg.mbzc.logic.nodes.Node;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AlbumRecordProcessor extends BaseRecordProcessor {
    private enum LogicStep {
        PRE, ARTIST_SEARCH, FIND_RELEASES, COMPLETE
    }

    private final String artistName;
    private final String albumName;

    private ArtistSearchNode artistSearchNode;
    private List<FindArtistReleasesNode> findArtistReleasesNodes;
    private LogicStep logicStep;

    public String getArtistName() {
        return artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public AlbumRecordProcessor(String artistName, String albumName) {
        this.artistName = artistName;
        this.albumName = albumName;
        this.artistSearchNode = new ArtistSearchNode(artistName);
        this.logicStep = LogicStep.PRE;

        this.findArtistReleasesNodes = Collections.emptyList();
    }

    private void processPreStep() {
        this.state = State.IN_PROGRESS;
        this.logicStep = LogicStep.ARTIST_SEARCH;
    }

    private void processArtistSearchStep(ProcessorApi processorApi) {
        if (!Node.State.COMPLETE.equals(artistSearchNode.getState())) {
            artistSearchNode.process(processorApi);
        }

        if (Node.State.COMPLETE.equals(artistSearchNode.getState())) {
            this.findArtistReleasesNodes = artistSearchNode.getFindingArtists().entrySet()
                    .stream()
                    .filter(entry -> StringUtils.equalsIgnoreCase(entry.getValue(), this.artistName))  //  логика поиска по полному совпадению
                    .map(entry -> new FindArtistReleasesNode(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());

            this.logicStep = LogicStep.FIND_RELEASES;
        }
    }

    private void processFindReleasesStep(ProcessorApi processorApi) {
        this.findArtistReleasesNodes
                .stream()
                .filter(node -> !Node.State.COMPLETE.equals(node.getState()))
                .forEach(node -> node.process(processorApi));

        boolean allComplete = this.findArtistReleasesNodes
                .stream()
                .allMatch(node -> Node.State.COMPLETE.equals(node.getState()));

        if (allComplete) {
            Map<String, String> filteredReleases = this.findArtistReleasesNodes
                    .stream()
                    .flatMap(node -> node.getReleases().entrySet().stream())
                    .filter(releaseEntry -> StringUtils.equalsIgnoreCase(releaseEntry.getValue(), this.albumName))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            if (filteredReleases.size() == 1) {
                this.state = State.COMPLETE;
                this.result = Result.PASS;
                this.logicStep = LogicStep.COMPLETE;
            } else {
                this.state = State.COMPLETE;
                this.result = Result.FAILURE;
                this.logicStep = LogicStep.COMPLETE;
            }
        }
    }

    @Override
    public void process(ProcessorApi processorApi) {
        while (true) {
            if (!processorApi.readyForInteract() || RecordProcessor.State.COMPLETE.equals(this.state)) {
                return;
            } else {
                if (LogicStep.PRE.equals(this.logicStep)) {
                    processPreStep();
                }

                if (LogicStep.ARTIST_SEARCH.equals(this.logicStep)) {
                    processArtistSearchStep(processorApi);
                }

                if (LogicStep.FIND_RELEASES.equals(this.logicStep)) {
                    processFindReleasesStep(processorApi);
                }
            }
        }
    }
}
