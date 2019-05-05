package test.openshift.app.bean;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ToString
public class ComicCharacter implements Comparable<ComicCharacter>{
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 64, message = "Name max length is 64")
    private String name;

    @Size(max = 64, message = "Nickname max length is 64")
    private String nickname;

    @Size(max = 64, message = "City max length is 64")
    private String city;

    @Override
    public int compareTo(ComicCharacter o) {
        if(o == null)
            return 1;
        if(this.id != null && o.id != null)
            return id.compareTo(o.id);
        else
            return this.name.compareTo(o.name);
    }
}
