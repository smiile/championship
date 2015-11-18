package bg.proxiad.demo.championship.front;

import bg.proxiad.demo.championship.logic.GroupingService;
import bg.proxiad.demo.championship.logic.ParticipantService;
import bg.proxiad.demo.championship.model.Grouping;
import bg.proxiad.demo.championship.viewbeans.GroupingViewBean;
import bg.proxiad.demo.championship.model.Participant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/groups")
public class GroupingController {

    @Autowired
    GroupingFormValidator groupingFormValidator;

    @Autowired
    private GroupingService groupingService;

    @Autowired
    private GroupingTransformerHelper groupingTransformerHelper;

    @Autowired
    private ParticipantService participantService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(groupingFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String listGroups(ModelMap model) {

        model.addAttribute("groupings", groupingService.listAllGroupings());

        return "group/list";
    }

    @RequestMapping(path = "/create", method = RequestMethod.GET)
    public String createGroup(ModelMap model) {
        List<Participant> participants = new ArrayList(participantService.listAllParticipants());
        Map<Long, String> unassignedParticipants = new LinkedHashMap<>();

        for (Participant participant : participants) {
            if (Objects.equals(participant.getGrouping(), null)) {
                unassignedParticipants.put(participant.getId(), participant.toString());
            }
        }

        model.addAttribute("groupingForm", new GroupingViewBean());
        model.addAttribute("unassignedParticipants", unassignedParticipants);
        return "group/create";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveGroup(ModelMap model,
            @ModelAttribute("groupingForm") @Validated GroupingViewBean groupingViewBean,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {
        
        // List of participants assigned to current group
        List<Long> assignedParticipants = groupingViewBean.getParticipants();
        
        Map<Long, String> unassignedParticipants = new LinkedHashMap<>();
        List<Participant> participants = new ArrayList(participantService.listAllParticipants());

        for (Participant participant : participants) {
            if (Objects.equals(participant.getGrouping(), null) && !assignedParticipants.contains(participant.getId())) {
                unassignedParticipants.put(participant.getId(), participant.toString());
            }
        }
        
        // Prepare hashmap for the already set participants
        for (Long participantId : assignedParticipants) {
            Participant participant = participantService.loadParticipant(participantId);
            groupingViewBean.addToParticipantMap(participant.getId(), participant.toString());
        }

        model.addAttribute("unassignedParticipants", unassignedParticipants);

        if (result.hasErrors()) {
            return "group/create";
        } else {
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", "Group updated successfully!");

            Grouping grouping = groupingTransformerHelper.transformGroupingViewBeanToGrouping(groupingViewBean);
            groupingService.saveOrUpdateGrouping(grouping);

            // POST/REDIRECT/GET
            return "redirect:/app/groups";
        }
    }
    
    @RequestMapping(path = "/{id}/update", method = RequestMethod.GET)
    public String renameGroupScreen(ModelMap model,
            @PathVariable("id") Long id) {
        Grouping grouping = groupingService.loadGrouping(id);
        
        model.addAttribute("groupingForm", groupingTransformerHelper.transformGroupingToGroupingViewBean(grouping));
        return "group/rename";
    }
    
    @RequestMapping(path = "/{id}/update", method = RequestMethod.POST)
    public String renameGroup(ModelMap model,
            @ModelAttribute("groupingForm") @Validated GroupingViewBean groupingViewBean,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "group/rename";
        } else {
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", "Group renamed successfully!");

            Grouping grouping = groupingTransformerHelper.transformGroupingViewBeanToGrouping(groupingViewBean);
            groupingService.saveOrUpdateGrouping(grouping);

            // POST/REDIRECT/GET
            return "redirect:/app/groups";
        }
    }

    

    @RequestMapping(path = "/rearrange", method = RequestMethod.GET)
    public String rearrangeGroup(ModelMap model) {
        List<Grouping> groups = new ArrayList(groupingService.listAllGroupings());

        List<Participant> unassignedParticipants = new ArrayList();

        for (Participant participant : participantService.listAllParticipants()) {
            if (Objects.equals(participant.getGrouping(), null)) {
                unassignedParticipants.add(participant);
            }
        }

        model.addAttribute("participants", unassignedParticipants);
        model.addAttribute("groups", groups);
        return "group/rearrange";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String deleteParticipant(@PathVariable("id") Long id,
            final RedirectAttributes redirectAttributes) {

        Grouping group = groupingService.loadGrouping(id);
        group.removeParticipants();

        groupingService.deleteGrouping(group);

        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Group is deleted!");

        return "redirect:/app/groups";

    }

}
