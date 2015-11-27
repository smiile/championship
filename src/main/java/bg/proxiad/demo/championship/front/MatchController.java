package bg.proxiad.demo.championship.front;

import bg.proxiad.demo.championship.logic.MatchService;
import bg.proxiad.demo.championship.model.Match;
import bg.proxiad.demo.championship.viewbeans.MatchViewBean;
import common.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/matches")
@Controller
public class MatchController {

    @Autowired
    MatchService matchService;
    
    @Autowired
    MatchFormValidator matchFormValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(matchFormValidator);
    }

    
    @RequestMapping(method = RequestMethod.GET)
    public String listMatches(ModelMap model) {

        model.addAttribute("matches", matchService.listAllMatches());
        return "match/list";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/results")
    public String setResults(@PathVariable("id") Long id, ModelMap model) {

        Match match = matchService.loadMatch(id);

        MatchViewBean matchViewBean = new MatchViewBean();
        matchViewBean.setId(match.getId());
        matchViewBean.setP1GamesWon(match.getP1GamesWon());
        matchViewBean.setP2GamesWon(match.getP2GamesWon());

        model.addAttribute("matchForm", matchViewBean);
        return "match/results";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveMatches(ModelMap model,
            @ModelAttribute("matchForm") @Validated MatchViewBean matchViewBean,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "match/results";
        } else {
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", "Match results were saved successfully!");

            Match match = matchService.loadMatch(matchViewBean.getId());
            match.setP1GamesWon(matchViewBean.getP1GamesWon());
            match.setP2GamesWon(matchViewBean.getP2GamesWon());
            
            matchService.saveOrUpdateMatch(match);

            // POST/REDIRECT/GET
            return "redirect:/app/matches";
        }
    }
    
    // Convenience method
    @RequestMapping(value = "/setRandomResults")
    public @ResponseBody String setRandomResults() { // Exercise: rewrite this using arrays
        List<Pair<Long, Long>> pairList = new ArrayList<>();
        
        pairList.add(new Pair(0L, 2L));
        pairList.add(new Pair(1L, 2L));
        pairList.add(new Pair(2L, 0L));
        pairList.add(new Pair(2L, 1L));
        
        Random rnd = new Random();
        
        for(Match match : matchService.listGroupMatches()) {
            Pair<Long, Long> randomResult = pairList.get(rnd.nextInt(3));
            
            match.setP1GamesWon(randomResult.getL());
            match.setP2GamesWon(randomResult.getR());
            
            matchService.saveOrUpdateMatch(match);
        }
        
        return "GREAT SUCCESS";
    }
}
