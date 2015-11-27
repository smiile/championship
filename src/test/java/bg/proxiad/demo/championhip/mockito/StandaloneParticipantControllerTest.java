package bg.proxiad.demo.championhip.mockito;

import bg.proxiad.demo.championship.front.EmailValidator;
import bg.proxiad.demo.championship.front.ParticipantFormValidator;
import bg.proxiad.demo.championship.front.ParticipantsController;
import bg.proxiad.demo.championship.front.WebAnnotationConfig;
import bg.proxiad.demo.championship.logic.ParticipantService;
import bg.proxiad.demo.championship.model.Participant;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebAnnotationConfig.class})
public class StandaloneParticipantControllerTest {

    private MockMvc mockMvc;
    private ParticipantService participantServiceMock;
    private ParticipantFormValidator participantFormValidator;
    
    @Before
    public void setUp() {
        participantServiceMock = Mockito.mock(ParticipantService.class);
        participantFormValidator = new ParticipantFormValidator(new EmailValidator());
        mockMvc = MockMvcBuilders.standaloneSetup(new ParticipantsController(participantServiceMock, participantFormValidator)).build();
    }
    
    @Test
    public void listParticipants_ShouldFetchParticipantsAndRenderView() throws Exception {
        Participant participant1 = new Participant();
        participant1.setEmail("email");
        participant1.setFirstName("First name");
        participant1.setLastName("Last name");
        
        Participant participant2 = new Participant();
        participant2.setEmail("email");
        participant2.setFirstName("First name");
        participant2.setLastName("Last name");
        
        when(participantServiceMock.listAllParticipants()).thenReturn(Arrays.asList(participant1, participant2));
        
        
        mockMvc.perform(get("/participants"))
                .andExpect(status().isOk())
                .andExpect(view().name("participant/list"))
                .andExpect(forwardedUrl("participant/list"))
                .andExpect(model().attribute("participants", hasSize(2)));
        
        verify(participantServiceMock).listAllParticipants();
        verifyNoMoreInteractions(participantServiceMock);
    }
    
    @Test
    public void createParticipant_shouldRenderView() throws Exception {
        
        mockMvc.perform(get("/participants/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("participant/create-edit"))
                .andExpect(forwardedUrl("participant/create-edit"))
                .andExpect(model().attribute("participantForm", hasProperty("id", nullValue())))
                .andExpect(model().attribute("participantForm", hasProperty("firstName", nullValue())))
                .andExpect(model().attribute("participantForm", hasProperty("lastName", nullValue())))
                .andExpect(model().attribute("participantForm", hasProperty("photoFileName", nullValue())))
                .andExpect(model().attribute("participantForm", hasProperty("email", nullValue())));
    }
    
    @Test
    public void showParticipant_shouldLoadParticipantAndRenderView() throws Exception {
        Participant participant = new Participant();
        participant.setId(1L);
        participant.setEmail("email");
        participant.setFirstName("first name");
        participant.setLastName("last name");
        participant.setPhotoFileName("participant.jpeg");
        
        when(participantServiceMock.loadParticipant(1L)).thenReturn(participant);
        
        mockMvc.perform(get("/participants/1/read"))
                .andExpect(status().isOk())
                .andExpect(view().name("participant/read"))
                .andExpect(forwardedUrl("participant/read"))
                .andExpect(model().attribute("participant", hasProperty("id", is(1L))))
                .andExpect(model().attribute("participant", hasProperty("firstName", is("first name"))))
                .andExpect(model().attribute("participant", hasProperty("lastName", is("last name"))))
                .andExpect(model().attribute("participant", hasProperty("email", is("email"))))
                .andExpect(model().attribute("participant", hasProperty("photoFileName", is("participant.jpeg"))));
        
        verify(participantServiceMock).loadParticipant(1L);
        verifyNoMoreInteractions(participantServiceMock);
    }
    
    @Test // Basically the same as showParticipant test
    public void updateParticipant_shouldLoadParticipantAndRenderView() throws Exception {
        Participant participant = new Participant();
        participant.setId(1L);
        participant.setEmail("email");
        participant.setFirstName("first name");
        participant.setLastName("last name");
        participant.setPhotoFileName("participant.jpeg");
        
        when(participantServiceMock.loadParticipant(1L)).thenReturn(participant);
        
        mockMvc.perform(get("/participants/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("participant/create-edit"))
                .andExpect(forwardedUrl("participant/create-edit"))
                .andExpect(model().attribute("participantForm", hasProperty("id", is(1L))))
                .andExpect(model().attribute("participantForm", hasProperty("firstName", is("first name"))))
                .andExpect(model().attribute("participantForm", hasProperty("lastName", is("last name"))))
                .andExpect(model().attribute("participantForm", hasProperty("email", is("email"))))
                .andExpect(model().attribute("participantForm", hasProperty("photoFileName", is("participant.jpeg"))));
        
        verify(participantServiceMock).loadParticipant(1L);
        verifyNoMoreInteractions(participantServiceMock);
    }
    
    @Test
    public void deleteParticipant_shouldRedirectWithFlashMessage() throws Exception {
        
        mockMvc.perform(get("/participants/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/app/participants"))
                .andExpect(flash().attribute("css", is("success")))
                .andExpect(flash().attribute("msg", is("Participant is deleted!")));
    }
    
    @Test
    public void saveOrUpdateParticipant_shouldShowErrorsOnAllFieldsEmtpy() throws Exception {
        mockMvc.perform(post("/participants")
                    .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(view().name("participant/create-edit"))
                .andExpect(model().attributeHasFieldErrors("participantForm", "firstName"))
                .andExpect(model().attributeHasFieldErrors("participantForm", "lastName"))
                .andExpect(model().attributeHasFieldErrors("participantForm", "email"))
                .andExpect(model().attributeHasFieldErrors("participantForm", "photoFileName")
        );
    }
    
    @Test
    public void saveOrUpdateParticipant_shouldShowErrorOnInvalidEmail() throws Exception {
        mockMvc.perform(post("/participants")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .param("email", "invalid@email"))
                .andExpect(status().isOk())
                .andExpect(view().name("participant/create-edit"))
                .andExpect(model().attributeHasFieldErrors("participantForm", "email"))
                .andExpect(model().attributeHasFieldErrorCode("participantForm", "email", "Pattern.participantForm.email")
        );
    }
    
    @Test
    public void saveOrUpdateParticipant_shouldCreateNewParticipantAndRedirectToList() throws Exception {
        
        mockMvc.perform(post("/participants")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .param("email", "valid@email.com")
                    .param("firstName", "first name")
                    .param("lastName", "last name")
                    .param("photoFileName", "participant.jpeg"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/app/participants"))
                .andExpect(flash().attribute("css", is("success")))
                .andExpect(flash().attribute("msg", is("Participant created successfully!"))
        );
    }
    
    @Test
    public void saveOrUpdateParticipant_shouldUpdateParticpantAndRedirectToList() throws Exception {
        Participant participant = new Participant();
        participant.setId(1L);
        
        // Mocking the loading of real entity in the transformer-helper
        when(participantServiceMock.loadParticipant(1L)).thenReturn(participant);
        
        mockMvc.perform(post("/participants")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .param("id", "1")
                    .param("email", "valid@email.com")
                    .param("firstName", "first name")
                    .param("lastName", "last name")
                    .param("photoFileName", "participant.jpeg"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/app/participants"))
                .andExpect(flash().attribute("css", is("success")))
                .andExpect(flash().attribute("msg", is("Participant saved successfully!"))
        );
        
    }
}
