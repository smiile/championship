package bg.proxiad.demo.championhip.mockito;

import bg.proxiad.demo.championship.front.AjaxController;
import bg.proxiad.demo.championship.front.WebAnnotationConfig;
import bg.proxiad.demo.championship.jsonbeans.StatusResponse;
import bg.proxiad.demo.championship.logic.GroupingService;
import bg.proxiad.demo.championship.logic.MatchService;
import bg.proxiad.demo.championship.logic.ParticipantResultService;
import bg.proxiad.demo.championship.model.Match;
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
    public void checkQuarterFinalMatchesConditions_shouldReturnOKOnGroupMatchesWithoutResults() throws Exception {
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
    
    
    
}
