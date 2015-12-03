package bg.proxiad.demo.championhip.mockito;

import bg.proxiad.demo.championship.front.AjaxController;
import bg.proxiad.demo.championship.front.WebAnnotationConfig;
import bg.proxiad.demo.championship.jsonbeans.StatusResponse;
import bg.proxiad.demo.championship.logic.GroupingService;
import bg.proxiad.demo.championship.logic.MatchService;
import bg.proxiad.demo.championship.logic.ParticipantResultService;
import bg.proxiad.demo.championship.model.Grouping;
import bg.proxiad.demo.championship.model.Match;
import bg.proxiad.demo.championship.model.Participant;
import bg.proxiad.demo.championship.model.ParticipantResult;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.Assert.assertEquals;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebAnnotationConfig.class})
public class StandaloneAjaxControllerTest {

    private MockMvc mockMvc;
    private MatchService matchServiceMock;
    private ParticipantResultService participantResultServiceMock;
    private GroupingService groupingServiceMock;
    
    @Before
    public void setUp() {
        matchServiceMock = Mockito.mock(MatchService.class);
        participantResultServiceMock = Mockito.mock(ParticipantResultService.class);
        groupingServiceMock = Mockito.mock(GroupingService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new AjaxController(matchServiceMock, participantResultServiceMock, groupingServiceMock)).build();
    }
    
    @Test
    public void checkQuarterFinalMatchesConditions_shouldReturnFAILOnGroupMatchesWithoutResults() throws Exception {
        List<Match> matches = new ArrayList();
        
        Match match1 = new Match();
        match1.setP1GamesWon(1L);
        match1.setP2GamesWon(2L);
        matches.add(match1);
        
        Match match2 = new Match();
        match2.setP1GamesWon(2L);
        match2.setP2GamesWon(1L);
        matches.add(match2);
        
        Match match3 = new Match();
        match3.setP1GamesWon(2L);
        match3.setP2GamesWon(0L);
        matches.add(match3);
        
        // One bad apple spoils them all
        Match match4 = new Match();
        match4.setP1GamesWon(null);
        match4.setP2GamesWon(null);
        matches.add(match4);
        
        when(matchServiceMock.listGroupMatches()).thenReturn(matches);
        
        mockMvc.perform(get("/ajax/checkQuarterFinalMatchesConditions"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"status\":\"FAIL\",\"data\":null}"));
        
    }
    
    @Test
    public void checkQuarterFinalMatchesConditions_shouldReturnOKOnGroupMatchesWithResults() throws Exception {
        List<Match> matches = new ArrayList();
        
        Match match1 = new Match();
        match1.setP1GamesWon(1L);
        match1.setP2GamesWon(2L);
        matches.add(match1);
        
        Match match2 = new Match();
        match2.setP1GamesWon(2L);
        match2.setP2GamesWon(1L);
        matches.add(match2);
        
        Match match3 = new Match();
        match3.setP1GamesWon(2L);
        match3.setP2GamesWon(0L);
        matches.add(match3);
        
        Match match4 = new Match();
        match4.setP1GamesWon(1L);
        match4.setP2GamesWon(1L);
        matches.add(match4);
        
        when(matchServiceMock.listGroupMatches()).thenReturn(matches);
        
        mockMvc.perform(get("/ajax/checkQuarterFinalMatchesConditions"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"status\":\"OK\",\"data\":null}"));
    }
    
    @Test
    public void generateQuarterFinalMatches_shouldReturnFAILOnEmptyGroup() throws Exception {
        
        List<Grouping> groups = new ArrayList();
        List<Participant> participants = new ArrayList();
        
        Grouping group1 = new Grouping();
        group1.setName("A");
        group1.setParticipants(participants);
        groups.add(group1);
        
        when(groupingServiceMock.listAllGroupings()).thenReturn(groups);
        
        mockMvc.perform(get("/ajax/generateQuarterFinalMatches"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"status\":\"FAIL\",\"data\":\"Fatal error!! One of the groups(A) has zero participants.\"}"));
        
    }
    
    @Test
    public void generateQuarterFinalMatches_shouldReturnFAILOnNoGroups() throws Exception {
        
        when(groupingServiceMock.listAllGroupings()).thenReturn(new ArrayList());
        
        mockMvc.perform(get("/ajax/generateQuarterFinalMatches"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"status\":\"FAIL\",\"data\":\"Fatal error!! No groups exist.\"}"));
    }
    
    @Test
    public void generateQuarterFinalMatches_shouldReturnOK() throws Exception {
        List<Grouping> groups = new ArrayList();
        List<Participant> participants = new ArrayList();
        
        List<ParticipantResult> participantResults1 = new ArrayList();
        List<ParticipantResult> participantResults2 = new ArrayList();
        List<ParticipantResult> participantResults3 = new ArrayList();
        
        Participant p1 = new Participant();
        p1.setId(1L);
        participants.add(p1);
        
        Participant p2 = new Participant();
        p2.setId(2L);
        participants.add(p2);
        
        Participant p3 = new Participant();
        p3.setId(3L);
        participants.add(p3);
        
        Participant p4 = new Participant();
        p4.setId(4L);
        participants.add(p4);
        
        Grouping group1 = new Grouping();
        group1.setId(1L);
        group1.setName("Group A");
        group1.setParticipants(participants);
        groups.add(group1);
        
        Grouping group2 = new Grouping();
        group2.setId(2L);
        group2.setName("Group B");
        group2.setParticipants(participants);
        groups.add(group2);
        
        Grouping group3 = new Grouping();
        group3.setId(3L);
        group3.setName("Group C");
        group3.setParticipants(participants);
        groups.add(group3);
        
        // Generate fake ranking
        ParticipantResult participantResult1 = new ParticipantResult();
        participantResult1.setParticipant(p1);
        participantResults1.add(participantResult1);
        
        ParticipantResult participantResult2 = new ParticipantResult();
        participantResult2.setParticipant(p2);
        participantResults1.add(participantResult2);
        
        ParticipantResult participantResult3 = new ParticipantResult();
        participantResult3.setParticipant(p3);
        participantResults1.add(participantResult3);
        
        ParticipantResult participantResult4 = new ParticipantResult();
        participantResult4.setParticipant(p4);
        participantResults1.add(participantResult4);
        
        ParticipantResult participantResult5 = new ParticipantResult();
        participantResult1.setParticipant(p1);
        participantResults2.add(participantResult5);
        
        ParticipantResult participantResult6 = new ParticipantResult();
        participantResult2.setParticipant(p2);
        participantResults2.add(participantResult6);
        
        ParticipantResult participantResult7 = new ParticipantResult();
        participantResult3.setParticipant(p3);
        participantResults2.add(participantResult7);
        
        ParticipantResult participantResult8 = new ParticipantResult();
        participantResult4.setParticipant(p4);
        participantResults2.add(participantResult8);
        
        ParticipantResult participantResult9 = new ParticipantResult();
        participantResult9.setParticipant(p1);
        participantResults3.add(participantResult9);
        
        ParticipantResult participantResult10 = new ParticipantResult();
        participantResult10.setParticipant(p2);
        participantResults3.add(participantResult10);
        
        ParticipantResult participantResult11 = new ParticipantResult();
        participantResult11.setParticipant(p3);
        participantResults3.add(participantResult11);
        
        ParticipantResult participantResult12 = new ParticipantResult();
        participantResult12.setParticipant(p4);
        participantResults3.add(participantResult12);
        
        
        when(groupingServiceMock.listAllGroupings()).thenReturn(groups);
        // Reuse fake ranking in each group
        when(participantResultServiceMock.calculateAndSetParticipantResultsPerGroup(group1)).thenReturn(participantResults1);
        when(participantResultServiceMock.calculateAndSetParticipantResultsPerGroup(group2)).thenReturn(participantResults2);
        when(participantResultServiceMock.calculateAndSetParticipantResultsPerGroup(group3)).thenReturn(participantResults3);
        
        mockMvc.perform(get("/ajax/generateQuarterFinalMatches"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"status\":\"OK\",\"data\":null}"));
    }
    
    
}
