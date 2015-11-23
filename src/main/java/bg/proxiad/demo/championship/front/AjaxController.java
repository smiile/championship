package bg.proxiad.demo.championship.front;

import bg.proxiad.demo.championship.logic.GroupingService;
import bg.proxiad.demo.championship.logic.ParticipantService;
import bg.proxiad.demo.championship.jsonbeans.GroupReceiver;
import bg.proxiad.demo.championship.jsonbeans.StatusResponse;
import bg.proxiad.demo.championship.logic.MatchService;
import bg.proxiad.demo.championship.model.Grouping;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    ParticipantService participantService;

    @Autowired
    GroupingService groupingService;

    @Autowired
    MatchService matchService;
    
    @Autowired
    ServletContext context;

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
        
        // No more than 8 groups
        if(groups.size() > 8) {
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
        
        matchService.generateGroupMatches();
        
        return statusResponse;
    }
}
