package ru.vlarp.musorg.mbzc.logic.procs;

import org.apache.commons.lang3.StringUtils;
import ru.vlarp.musorg.mbzc.logic.ProcessorApi;
import ru.vlarp.musorg.mbzc.logic.nodes.ArtistSearchNode;
import ru.vlarp.musorg.mbzc.logic.nodes.Node;

import java.util.List;
import java.util.stream.Collectors;

public class ArtistRecordProcessor extends BaseRecordProcessor {
    private enum LogicStep {
        PRE, ARTIST_SEARCH, COMPLETE
    }

    private final String artistName;
    private ArtistSearchNode artistSearchNode;
    private LogicStep logicStep;

    public String getArtistName() {
        return artistName;
    }

    public ArtistRecordProcessor(String artistName) {
        this.artistName = artistName;
        this.artistSearchNode = new ArtistSearchNode(artistName);
        this.logicStep = LogicStep.PRE;
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
            List<String> filteredArtists = artistSearchNode.getFindingArtists().values()
                    .stream()
                    .filter(tArtistName -> StringUtils.equalsIgnoreCase(tArtistName, this.artistName))
                    .collect(Collectors.toList());

            this.state = State.COMPLETE;
            this.logicStep = LogicStep.COMPLETE;
            this.result = (filteredArtists.size() == 1) ? Result.PASS : Result.FAILURE;
        }
    }

    @Override
    public void process(ProcessorApi processorApi) {
        while (true) {
            if (!processorApi.readyForInteract() || State.COMPLETE.equals(this.state)) {
                return;
            } else {
                if (LogicStep.PRE.equals(this.logicStep)) {
                    processPreStep();
                }

                if (LogicStep.ARTIST_SEARCH.equals(this.logicStep)) {
                    processArtistSearchStep(processorApi);
                }
            }
        }
    }
}
