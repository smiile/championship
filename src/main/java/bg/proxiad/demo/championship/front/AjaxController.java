package bg.proxiad.demo.championship.front;

import bg.proxiad.demo.championship.logic.GroupingService;
import bg.proxiad.demo.championship.logic.ParticipantService;
import bg.proxiad.demo.championship.jsonbeans.GroupReceiver;
import bg.proxiad.demo.championship.jsonbeans.StatusResponse;
import bg.proxiad.demo.championship.logic.MatchService;
import bg.proxiad.demo.championship.logic.ParticipantResultService;
import bg.proxiad.demo.championship.model.ParticipantResult;
import bg.proxiad.demo.championship.model.Grouping;
import bg.proxiad.demo.championship.model.Match;
import bg.proxiad.demo.championship.model.Participant;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/ajax")
public class AjaxController {

    private final MatchService matchService;
    private final ParticipantResultService participantResultService;
    private final GroupingService groupingService;
    
    @Autowired
    public AjaxController(MatchService matchService, ParticipantResultService participantResultService, GroupingService groupingService) {
        this.matchService = matchService;
        this.participantResultService = participantResultService;
        this.groupingService = groupingService;
    }
    
    @Autowired
    ParticipantService participantService;
    
    @Autowired
    ServletContext context;
    
    // Must always be (MAX_FINALISTS % 4) == 0
    private final int MAX_FINALISTS = 8;

    @RequestMapping(value = "/rearrangePlayers", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    StatusResponse rearrangePlayers(@RequestBody List<GroupReceiver> playerDistributions) {
        StatusResponse statusResponse = new StatusResponse();
        statusResponse.setStatus("OK");

        try {
            groupingService.reassignPlayers(playerDistributions);
        } catch (Exception e) {
            statusResponse.setStatus("FAIL");
            statusResponse.setData(e.getMessage());
        }

        return statusResponse;
    }

    // AJAX method
    @RequestMapping(value = "/uploadPhoto", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    StatusResponse uploadPhoto(@RequestParam("file") MultipartFile file, ServletRequest request) throws IOException {
        StatusResponse statusResponse = new StatusResponse();
        Random rn = new Random();
        String name = rn.nextInt(99999) + "_" + file.getOriginalFilename();

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(
                        context.getRealPath(context.getContextPath() + File.separator + ".." + File.separator + "img")
                        + File.separator + name)));
                stream.write(bytes);
                stream.close();

                statusResponse.setStatus("OK");
                statusResponse.setData(name);
            } catch (Exception e) {
                statusResponse.setStatus("FAIL");
                statusResponse.setData("You failed to upload " + name + " => " + e.getMessage());
            }
        } else {
            statusResponse.setStatus("FAIL");
            statusResponse.setData("A non-empty file is  required");
        }

        return statusResponse;
    }
    
    @RequestMapping(value = "/checkGroupMatchesConditions")
    public @ResponseBody StatusResponse checkIfGroupMatchesCanBeGenerated() {
        StatusResponse statusResponse = new StatusResponse();
        statusResponse.setStatus("OK");
        
        List<Grouping> groups = new ArrayList(groupingService.listAllGroupings());
        
        // No more groups than the defined finalists
        if(groups.size() > MAX_FINALISTS) {
            statusResponse.setStatus("FAIL");
        }
        
        for(Grouping group : groups) {
            if (group.getParticipants().size() < 2) {
                statusResponse.setStatus("FAIL");
                break;
            }
        }
        
        return statusResponse;
    }
    
    @RequestMapping(value = "/generateGroupMatches")
    public @ResponseBody StatusResponse generateGroupMatches() {
        StatusResponse statusResponse = new StatusResponse();
        statusResponse.setStatus("OK");
        
        // Remove previously generated group matches
        matchService.deleteAllGroupMatches();
        
        // Remove previously generated finals (this can be a tournament reset)
        matchService.deleteAllFinalMatches();
        
        matchService.generateGroupMatches();
        
        return statusResponse;
    }
    
    @RequestMapping(value = "/checkQuarterFinalMatchesConditions")
    public @ResponseBody StatusResponse checkQuarterFinalMatchesConditions() {
        StatusResponse statusResponse = new StatusResponse();
        statusResponse.setStatus("OK");
        
        if(matchService.listGroupMatches().size() == 0) {
            statusResponse.setStatus("FAIL");
            return statusResponse;
        }
        
        // All matches should have results
        for(Match match : matchService.listGroupMatches()) {
            if(Objects.equals(match.getP1GamesWon(), null) || Objects.equals(match.getP2GamesWon(), null)) {
                statusResponse.setStatus("FAIL");
                return statusResponse;
            }
        }
        
        return statusResponse;
    }
    
    @RequestMapping(value = "/generateQuarterFinalMatches")
    public @ResponseBody StatusResponse generateQuarterFinalMatches() {
        StatusResponse statusResponse = new StatusResponse();
        statusResponse.setStatus("OK");
        
        List<Participant> quarterFinalists = new ArrayList();
        List<List<ParticipantResult>> allGroupResults = new ArrayList();
        
        // Calculate rankings for group phase
        try {
            if(groupingService.listAllGroupings().isEmpty()) {
                throw new Exception("Fatal error!! No groups exist.");
            }
            
            for(Grouping group : groupingService.listAllGroupings()) {
                
                if(Objects.equals(group.getParticipants(), null) || group.getParticipants().isEmpty()) {
                    throw new Exception("Fatal error!! One of the groups(" + group.getName() + ") " + "has zero participants.");
                }
                
                List<ParticipantResult> groupResult = participantResultService.calculateAndSetParticipantResultsPerGroup(group);
                allGroupResults.add(groupResult);
            }
        } catch (Exception e) {
            statusResponse.setStatus("FAIL");
            statusResponse.setData(e.getMessage());
            
            return statusResponse;
        }
        
        /* 
         *   Draw first player (lists are sorted by points) from each group 
         *   and remove him from the list
         *   until the needed number of finalists is reached. 
         *   More than one full loop will be needed
         *   if groups are less than required finalists.
         */ 
        while(quarterFinalists.size() < MAX_FINALISTS) {
            for(int i=0; i < allGroupResults.size(); i++) {
                
                quarterFinalists.add(allGroupResults.get(i).get(0).getParticipant());
                allGroupResults.get(i).remove(0);
                
                if(quarterFinalists.size() == MAX_FINALISTS) {
                    break;
                }
            }
        }
        
        // Shuffle the quarter-finalists "bowl"
        Collections.shuffle(quarterFinalists);
        
        // Delete previously generated quarter-final matches
        matchService.deleteAllFinalMatches();
        
        // Create final matches 
        for(int i = 0; i < MAX_FINALISTS; i = i + 2) {
            Participant p1 = quarterFinalists.get(i);
            Participant p2 = quarterFinalists.get(i+1);
            
            Match quarterFinalMatch = new Match();
            quarterFinalMatch.setIsGroupMatch(false);
            quarterFinalMatch.setParticipant1(p1);
            quarterFinalMatch.setParticipant2(p2);
            matchService.saveOrUpdateMatch(quarterFinalMatch);
        }
        
        return statusResponse;
    }
    
}