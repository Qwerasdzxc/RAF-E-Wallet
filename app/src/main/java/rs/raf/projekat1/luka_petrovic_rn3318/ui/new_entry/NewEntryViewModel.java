package rs.raf.projekat1.luka_petrovic_rn3318.ui.new_entry;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewEntryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NewEntryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is new entry fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}