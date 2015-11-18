package bg.proxiad.demo.championship.jsonbeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "groupId",
    "participants"
})
public class GroupReceiver {

    @JsonProperty("groupId")
    private Long groupId;
    @JsonProperty("participants")
    private List<Long> participants = new ArrayList<Long>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return The groupId
     */
    @JsonProperty("groupId")
    public Long getGroupId() {
        return groupId;
    }

    /**
     *
     * @param groupId The groupId
     */
    @JsonProperty("groupId")
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    /**
     *
     * @return The participants
     */
    @JsonProperty("participants")
    public List<Long> getParticipants() {
        return participants;
    }

    /**
     *
     * @param participants The participants
     */
    @JsonProperty("participants")
    public void setParticipants(List<Long> participants) {
        this.participants = participants;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
