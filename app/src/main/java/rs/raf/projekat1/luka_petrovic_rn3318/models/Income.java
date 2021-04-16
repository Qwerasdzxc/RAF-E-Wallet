package rs.raf.projekat1.luka_petrovic_rn3318.models;

import java.io.File;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Qwerasdzxc on 4/16/21.
 */
public class Income implements Serializable {

    private Integer id;
    private Integer value;

    private String title;
    private String description;

    private File audioRecording;

    public Income(Integer id, Integer value, String title, String description) {
        this.id = id;
        this.value = value;
        this.title = title;
        this.description = description;
    }

    public Income(Integer id, Integer value, String title, File audioRecording) {
        this.id = id;
        this.value = value;
        this.title = title;
        this.audioRecording = audioRecording;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public File getAudioRecording() {
        return audioRecording;
    }

    public void setAudioRecording(File audioRecording) {
        this.audioRecording = audioRecording;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Income income = (Income) o;
        return id.equals(income.id) &&
                value.equals(income.value) &&
                title.equals(income.title) &&
                Objects.equals(description, income.description) &&
                Objects.equals(audioRecording, income.audioRecording);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, title, description, audioRecording);
    }
}
