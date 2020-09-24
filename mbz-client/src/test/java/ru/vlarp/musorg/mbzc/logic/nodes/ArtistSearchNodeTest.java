package ru.vlarp.musorg.mbzc.logic.nodes;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import ru.vlarp.musorg.mbzc.logic.ProcessorApi;
import ru.vlarp.musorg.mbzc.logic.ProcessorApiResponse;
import ru.vlarp.musorg.mbzc.pojo.mbz.ArtistSearch;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArtistSearchNodeTest {
    private static final String TEST_ARTIST_NAME = "tan";

    @Test
    public void processTest1() {
        //  Given
        ProcessorApi processorApi = mock(ProcessorApi.class);

        when(processorApi.readyForInteract()).thenReturn(true);

        ArtistSearch mbzArtistSearch = new ArtistSearch();
        mbzArtistSearch.setCount(1);
        mbzArtistSearch.setOffset(0);
        ArtistSearch.ArtistEntry mbzArtistEntry = new ArtistSearch.ArtistEntry();
        mbzArtistEntry.setId("1111-2222-3333-4444-5555");
        mbzArtistEntry.setName(TEST_ARTIST_NAME);
        mbzArtistEntry.setSortName(TEST_ARTIST_NAME);
        mbzArtistSearch.setArtists(ImmutableList.of(mbzArtistEntry));

        when(processorApi.artistSearch(eq(TEST_ARTIST_NAME), eq(0)))
                .thenReturn(new ProcessorApiResponse<>(mbzArtistSearch, ProcessorApiResponse.State.PASS));

        ArtistSearchNode artistSearchNode = new ArtistSearchNode(TEST_ARTIST_NAME);
        //  When
        artistSearchNode.process(processorApi);
        //  Then
        assertEquals(BaseNode.State.COMPLETE, artistSearchNode.getState());
        assertEquals(1, artistSearchNode.getFindingArtists().size());
        assertEquals(TEST_ARTIST_NAME, artistSearchNode.getFindingArtists().get("1111-2222-3333-4444-5555"));
    }

    @Test
    public void processTest2() {
        //  Given
        ProcessorApi processorApi = mock(ProcessorApi.class);

        when(processorApi.readyForInteract()).thenReturn(true);

        ArtistSearch mbzArtistSearch = new ArtistSearch();
        mbzArtistSearch.setCount(2);
        mbzArtistSearch.setOffset(0);
        ArtistSearch.ArtistEntry mbzArtistEntry1 = new ArtistSearch.ArtistEntry();
        mbzArtistEntry1.setId("1111-2222-3333-4444-5555");
        mbzArtistEntry1.setName(TEST_ARTIST_NAME);
        mbzArtistEntry1.setSortName(TEST_ARTIST_NAME);
        ArtistSearch.ArtistEntry mbzArtistEntry2 = new ArtistSearch.ArtistEntry();
        mbzArtistEntry2.setId("1111-2222-3333-4444-6666");
        mbzArtistEntry2.setName(TEST_ARTIST_NAME);
        mbzArtistEntry2.setSortName(TEST_ARTIST_NAME);
        mbzArtistSearch.setArtists(ImmutableList.of(mbzArtistEntry1, mbzArtistEntry2));

        when(processorApi.artistSearch(eq(TEST_ARTIST_NAME), eq(0)))
                .thenReturn(new ProcessorApiResponse<>(mbzArtistSearch, ProcessorApiResponse.State.PASS));

        ArtistSearchNode artistSearchNode = new ArtistSearchNode(TEST_ARTIST_NAME);
        //  When
        artistSearchNode.process(processorApi);
        //  Then
        assertEquals(BaseNode.State.COMPLETE, artistSearchNode.getState());
        assertEquals(2, artistSearchNode.getFindingArtists().size());
        assertEquals(TEST_ARTIST_NAME, artistSearchNode.getFindingArtists().get("1111-2222-3333-4444-5555"));
        assertEquals(TEST_ARTIST_NAME, artistSearchNode.getFindingArtists().get("1111-2222-3333-4444-6666"));
    }

    @Test
    public void processTest3() {
        //  Given
        ProcessorApi processorApi = mock(ProcessorApi.class);

        when(processorApi.readyForInteract()).thenReturn(true);

        ArtistSearch mbzArtistSearch1 = new ArtistSearch();
        mbzArtistSearch1.setCount(2);
        mbzArtistSearch1.setOffset(0);
        ArtistSearch.ArtistEntry mbzArtistEntry1 = new ArtistSearch.ArtistEntry();
        mbzArtistEntry1.setId("1111-2222-3333-4444-5555");
        mbzArtistEntry1.setName(TEST_ARTIST_NAME);
        mbzArtistEntry1.setSortName(TEST_ARTIST_NAME);
        mbzArtistSearch1.setArtists(ImmutableList.of(mbzArtistEntry1));
        when(processorApi.artistSearch(eq(TEST_ARTIST_NAME), eq(0)))
                .thenReturn(new ProcessorApiResponse<>(mbzArtistSearch1, ProcessorApiResponse.State.PASS));

        ArtistSearch mbzArtistSearch2 = new ArtistSearch();
        mbzArtistSearch2.setCount(2);
        mbzArtistSearch2.setOffset(1);
        ArtistSearch.ArtistEntry mbzArtistEntry2 = new ArtistSearch.ArtistEntry();
        mbzArtistEntry2.setId("1111-2222-3333-4444-6666");
        mbzArtistEntry2.setName(TEST_ARTIST_NAME);
        mbzArtistEntry2.setSortName(TEST_ARTIST_NAME);
        mbzArtistSearch2.setArtists(ImmutableList.of(mbzArtistEntry2));
        when(processorApi.artistSearch(eq(TEST_ARTIST_NAME), eq(1)))
                .thenReturn(new ProcessorApiResponse<>(mbzArtistSearch2, ProcessorApiResponse.State.PASS));

        ArtistSearchNode artistSearchNode = new ArtistSearchNode(TEST_ARTIST_NAME);
        //  When
        artistSearchNode.process(processorApi);
        //  Then
        assertEquals(BaseNode.State.COMPLETE, artistSearchNode.getState());
        assertEquals(2, artistSearchNode.getFindingArtists().size());
        assertEquals(TEST_ARTIST_NAME, artistSearchNode.getFindingArtists().get("1111-2222-3333-4444-5555"));
        assertEquals(TEST_ARTIST_NAME, artistSearchNode.getFindingArtists().get("1111-2222-3333-4444-6666"));
    }

    @Test
    public void processTest4() {
        //  Given
        ProcessorApi processorApi = mock(ProcessorApi.class);

        when(processorApi.readyForInteract())
                .thenReturn(true)
                .thenReturn(false)
                .thenReturn(true);

        ArtistSearch mbzArtistSearch1 = new ArtistSearch();
        mbzArtistSearch1.setCount(2);
        mbzArtistSearch1.setOffset(0);
        ArtistSearch.ArtistEntry mbzArtistEntry1 = new ArtistSearch.ArtistEntry();
        mbzArtistEntry1.setId("1111-2222-3333-4444-5555");
        mbzArtistEntry1.setName(TEST_ARTIST_NAME);
        mbzArtistEntry1.setSortName(TEST_ARTIST_NAME);
        mbzArtistSearch1.setArtists(ImmutableList.of(mbzArtistEntry1));
        when(processorApi.artistSearch(eq(TEST_ARTIST_NAME), eq(0)))
                .thenReturn(new ProcessorApiResponse<>(mbzArtistSearch1, ProcessorApiResponse.State.PASS));

        ArtistSearch mbzArtistSearch2 = new ArtistSearch();
        mbzArtistSearch2.setCount(2);
        mbzArtistSearch2.setOffset(1);
        ArtistSearch.ArtistEntry mbzArtistEntry2 = new ArtistSearch.ArtistEntry();
        mbzArtistEntry2.setId("1111-2222-3333-4444-6666");
        mbzArtistEntry2.setName(TEST_ARTIST_NAME);
        mbzArtistEntry2.setSortName(TEST_ARTIST_NAME);
        mbzArtistSearch2.setArtists(ImmutableList.of(mbzArtistEntry2));
        when(processorApi.artistSearch(eq(TEST_ARTIST_NAME), eq(1)))
                .thenReturn(new ProcessorApiResponse<>(mbzArtistSearch2, ProcessorApiResponse.State.PASS));

        ArtistSearchNode artistSearchNode = new ArtistSearchNode(TEST_ARTIST_NAME);

        //  When
        artistSearchNode.process(processorApi);
        //  Then
        assertEquals(BaseNode.State.FILL_RESULT, artistSearchNode.getState());
        assertEquals(1, artistSearchNode.getFindingArtists().size());
        assertEquals(TEST_ARTIST_NAME, artistSearchNode.getFindingArtists().get("1111-2222-3333-4444-5555"));

        //  When
        artistSearchNode.process(processorApi);
        //  Then
        assertEquals(BaseNode.State.COMPLETE, artistSearchNode.getState());
        assertEquals(2, artistSearchNode.getFindingArtists().size());
        assertEquals(TEST_ARTIST_NAME, artistSearchNode.getFindingArtists().get("1111-2222-3333-4444-5555"));
        assertEquals(TEST_ARTIST_NAME, artistSearchNode.getFindingArtists().get("1111-2222-3333-4444-6666"));
    }

    @Test
    public void processTest5() {
        //  Given
        ProcessorApi processorApi = mock(ProcessorApi.class);

        when(processorApi.readyForInteract())
                .thenReturn(true)
                .thenReturn(false)
                .thenReturn(true);

        ArtistSearch mbzArtistSearch1 = new ArtistSearch();
        mbzArtistSearch1.setCount(2);
        mbzArtistSearch1.setOffset(0);
        ArtistSearch.ArtistEntry mbzArtistEntry1 = new ArtistSearch.ArtistEntry();
        mbzArtistEntry1.setId("1111-2222-3333-4444-5555");
        mbzArtistEntry1.setName(TEST_ARTIST_NAME);
        mbzArtistEntry1.setSortName(TEST_ARTIST_NAME);
        mbzArtistSearch1.setArtists(ImmutableList.of(mbzArtistEntry1));
        when(processorApi.artistSearch(eq(TEST_ARTIST_NAME), eq(0)))
                .thenReturn(new ProcessorApiResponse<>(mbzArtistSearch1, ProcessorApiResponse.State.PASS));

        ArtistSearch mbzArtistSearch2 = new ArtistSearch();
        mbzArtistSearch2.setCount(2);
        mbzArtistSearch2.setOffset(1);
        ArtistSearch.ArtistEntry mbzArtistEntry2 = new ArtistSearch.ArtistEntry();
        mbzArtistEntry2.setId("1111-2222-3333-4444-6666");
        mbzArtistEntry2.setName(TEST_ARTIST_NAME);
        mbzArtistEntry2.setSortName(TEST_ARTIST_NAME);
        mbzArtistSearch2.setArtists(ImmutableList.of(mbzArtistEntry2));
        when(processorApi.artistSearch(eq(TEST_ARTIST_NAME), eq(1)))
                .thenReturn(new ProcessorApiResponse<>(null, ProcessorApiResponse.State.ERROR))
                .thenReturn(new ProcessorApiResponse<>(mbzArtistSearch2, ProcessorApiResponse.State.PASS));

        ArtistSearchNode artistSearchNode = new ArtistSearchNode(TEST_ARTIST_NAME);

        //  When
        artistSearchNode.process(processorApi);
        //  Then
        assertEquals(BaseNode.State.FILL_RESULT, artistSearchNode.getState());
        assertEquals(1, artistSearchNode.getFindingArtists().size());
        assertEquals(TEST_ARTIST_NAME, artistSearchNode.getFindingArtists().get("1111-2222-3333-4444-5555"));

        //  When
        artistSearchNode.process(processorApi);
        //  Then
        assertEquals(BaseNode.State.COMPLETE, artistSearchNode.getState());
        assertEquals(2, artistSearchNode.getFindingArtists().size());
        assertEquals(TEST_ARTIST_NAME, artistSearchNode.getFindingArtists().get("1111-2222-3333-4444-5555"));
        assertEquals(TEST_ARTIST_NAME, artistSearchNode.getFindingArtists().get("1111-2222-3333-4444-6666"));
    }
}