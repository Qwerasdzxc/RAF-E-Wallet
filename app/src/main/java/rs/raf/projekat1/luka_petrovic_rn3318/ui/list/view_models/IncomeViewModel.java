package rs.raf.projekat1.luka_petrovic_rn3318.ui.list.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rs.raf.projekat1.luka_petrovic_rn3318.models.Income;

public class IncomeViewModel extends ViewModel {

    private final MutableLiveData<List<Income>> incomeData;

    public IncomeViewModel() {
        incomeData = new MutableLiveData<>();

        List<Income> seedData = new ArrayList<>(5);
        Random random = new Random();
        for (int i = 0; i < 5; i++)
            seedData.add(new Income(i, random.nextInt(100000), "Income " + i, "Description of income " + i));

        incomeData.setValue(seedData);
    }

    public void addIncome(Income income) {
        List<Income> currentData = new ArrayList<>(incomeData.getValue());
        currentData.add(income);
        incomeData.setValue(currentData);
    }

    public void editIncome(Income income) {
        List<Income> currentData = new ArrayList<>(incomeData.getValue());
        for (Income curr : currentData) {
            if (curr.getId().equals(income.getId())) {
                curr.setTitle(income.getTitle());
                curr.setDescription(income.getDescription());
                curr.setValue(income.getValue());
                curr.setAudioRecording(income.getAudioRecording());
            }
        }
        incomeData.setValue(currentData);
    }

    public void deleteIncome(Income income) {
        List<Income> currentData = new ArrayList<>(incomeData.getValue());
        currentData.remove(income);
        incomeData.setValue(currentData);
    }

    public LiveData<List<Income>> getIncomeData() {
        return incomeData;
    }
}