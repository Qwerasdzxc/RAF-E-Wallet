package rs.raf.projekat1.luka_petrovic_rn3318.ui.list.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rs.raf.projekat1.luka_petrovic_rn3318.models.Outcome;

public class OutcomeViewModel extends ViewModel {
    private final MutableLiveData<List<Outcome>> outcomeData;

    public OutcomeViewModel() {
        outcomeData = new MutableLiveData<>();

        List<Outcome> seedData = new ArrayList<>(5);
        Random random = new Random();
        for (int i = 0; i < 5; i++)
            seedData.add(new Outcome(i, random.nextInt(100000), "Outcome " + i, "Description of outcome " + i));

        outcomeData.setValue(seedData);
    }

    public void addOutcome(Outcome outcome) {
        List<Outcome> currentData = new ArrayList<>(outcomeData.getValue());
        currentData.add(outcome);
        outcomeData.setValue(currentData);
    }

    public void editOutcome(Outcome outcome) {
        List<Outcome> currentData = new ArrayList<>(outcomeData.getValue());
        for (Outcome curr : currentData) {
            if (curr.getId().equals(outcome.getId())) {
                curr.setTitle(outcome.getTitle());
                curr.setDescription(outcome.getDescription());
                curr.setValue(outcome.getValue());
                curr.setAudioRecording(outcome.getAudioRecording());
            }
        }
        outcomeData.setValue(currentData);
    }

    public void deleteOutcome(Outcome outcome) {
        List<Outcome> currentData = new ArrayList<>(outcomeData.getValue());
        currentData.remove(outcome);
        outcomeData.setValue(currentData);
    }

    public LiveData<List<Outcome>> getOutcomeData() {
        return outcomeData;
    }
}