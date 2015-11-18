package bg.proxiad.demo.championship.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bg.proxiad.demo.championship.logic.ParticipantService;
import bg.proxiad.demo.championship.model.Participant;
import bg.proxiad.demo.championship.viewbeans.ParticipantViewBean;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/participants")
public class ParticipantsController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    ParticipantFormValidator participantFormValidator;

    @Autowired
    private ParticipantTransformerHelper participantTransformerHelper;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(participantFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String listParticipants(ModelMap model) {

        model.addAttribute("participants", participantService.listAllParticipants());
        return "participant/list";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveOrUpdateParticipant(ModelMap model,
            @ModelAttribute("participantForm") @Validated ParticipantViewBean participantViewBean,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "participant/create-edit";
        } else {
            redirectAttributes.addFlashAttribute("css", "success");

            if (participantViewBean.isNew()) {
                redirectAttributes.addFlashAttribute("msg", "Participant created successfully!");
            } else {
                redirectAttributes.addFlashAttribute("msg", "Participant saved successfully!");
            }

            Participant participant = participantTransformerHelper
                    .transformParticipantViewBeanToParticipant(participantViewBean);
            participantService.saveOrUpdateParticipant(participant);

            // POST/REDIRECT/GET
            return "redirect:/app/participants";
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createParticipant(ModelMap model) {

        model.addAttribute("participantForm", new ParticipantViewBean());
        return "participant/create-edit";
    }

    @RequestMapping(value = "/{id}/read", method = RequestMethod.GET)
    public String showParticipant(@PathVariable("id") Long id, ModelMap model) {
        Participant participant = participantService.loadParticipant(id);

        model.addAttribute("participant", participantTransformerHelper.transformParticipantToParticipantViewBean(participant));
        return "participant/read";
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public String updateParticipant(@PathVariable("id") Long id, ModelMap model) {

        Participant participant = participantService.loadParticipant(id);

        model.addAttribute("participantForm", participantTransformerHelper.transformParticipantToParticipantViewBean(participant));

        return "participant/create-edit";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String deleteParticipant(@PathVariable("id") Long id, final RedirectAttributes redirectAttributes) {

        Participant participant = participantService.loadParticipant(id);

        participantService.deleteParticipant(participant);

        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Participant is deleted!");

        return "redirect:/app/participants";

    }

    // Filler method
//    @RequestMapping(value = "/addSomeMore", method = RequestMethod.GET)
//    public void addSomeParticipants() {
//        for (int i = 0; i < 10; i++) {
//            Participant participant = new Participant();
//            participant.setEmail("test@test.it");
//            participant.setFirstName("Some");
//            participant.setLastName("Other Participant");
//            participant.setPhotoFileName("44257_knightstour.png");
//
//            participantService.saveOrUpdateParticipant(participant);
//        }
//    }
}
