package rs.raf.projekat1.luka_petrovic_rn3318.ui.list.edit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import rs.raf.projekat1.luka_petrovic_rn3318.R;
import rs.raf.projekat1.luka_petrovic_rn3318.models.Outcome;

public class EditOutcomeActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText valueEditText;
    private EditText descriptionEditText;
    private ImageView audioButton;

    private Outcome originalOutcome;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_income);

        titleEditText = findViewById(R.id.edit_entry_title_edit_text);
        valueEditText = findViewById(R.id.edit_entry_value_edit_text);
        descriptionEditText = findViewById(R.id.edit_entry_description);
        audioButton = findViewById(R.id.edit_entry_audio_button);
        Button cancelButton = findViewById(R.id.edit_entry_cancel_button);
        Button editButton = findViewById(R.id.edit_entry_edit_button);

        originalOutcome = (Outcome) getIntent().getExtras().getSerializable("outcome");

        titleEditText.setText(originalOutcome.getTitle());
        valueEditText.setText(originalOutcome.getValue().toString());
        descriptionEditText.setText(originalOutcome.getDescription());

        cancelButton.setOnClickListener(view -> {
            setResult(Activity.RESULT_CANCELED, null);
            finish();
        });

        editButton.setOnClickListener(view -> {
            if (titleEditText.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, getResources().getString(R.string.new_entry_title_required), Toast.LENGTH_SHORT).show();
                return;
            }
            if (valueEditText.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, getResources().getString(R.string.new_entry_value_required), Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                int value = Integer.parseInt(valueEditText.getText().toString());
                if (value < 0)
                    throw new NumberFormatException();
            } catch (NumberFormatException e) {
                Toast.makeText(this, getResources().getString(R.string.new_entry_value_format), Toast.LENGTH_SHORT).show();
                return;
            }
            // Audio entry:
            if (originalOutcome.getAudioRecording() != null) {
                // TODO: Audio implementation
            }
            // Description entry:
            else {
                if (descriptionEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, getResources().getString(R.string.new_entry_description_required), Toast.LENGTH_SHORT).show();
                    return;
                }
                Outcome outcome = new Outcome(originalOutcome.getId(), Integer.parseInt(valueEditText.getText().toString()), titleEditText.getText().toString(), descriptionEditText.getText().toString());
                Intent intent = new Intent();

                intent.putExtra("current_outcome", outcome);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}