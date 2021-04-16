package rs.raf.projekat1.luka_petrovic_rn3318.ui.list.edit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import rs.raf.projekat1.luka_petrovic_rn3318.R;
import rs.raf.projekat1.luka_petrovic_rn3318.models.Income;

public class EditIncomeActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText valueEditText;
    private EditText descriptionEditText;
    private ImageView audioButton;

    private Income originalIncome;

    private MediaRecorder mediaRecorder;

    private boolean isRecording = false;
    private File recording;

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

        originalIncome = (Income) getIntent().getExtras().getSerializable("income");

        titleEditText.setText(originalIncome.getTitle());
        valueEditText.setText(originalIncome.getValue().toString());

        if (originalIncome.getAudioRecording() != null) {
            descriptionEditText.setVisibility(View.GONE);
            audioButton.setVisibility(View.VISIBLE);

            audioButton.setOnClickListener(__ -> {
                if (!isRecording) {
                    Toast.makeText(this, "Started recording", Toast.LENGTH_SHORT).show();
                    File folder = new File(this.getFilesDir(), "recordings");
                    if (!folder.exists()) folder.mkdir();
                    recording = new File(folder, "recording" + originalIncome.getId() + ".3gp");

                    mediaRecorder = new MediaRecorder();
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                    mediaRecorder.setOutputFile(recording);

                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                        isRecording = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "Finished recording", Toast.LENGTH_SHORT).show();
                    mediaRecorder.stop();
                    mediaRecorder.release();
                }
            });
        } else {
            descriptionEditText.setText(originalIncome.getDescription());
        }

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
            if (originalIncome.getAudioRecording() != null) {
                Income income = new Income(originalIncome.getId(), Integer.parseInt(valueEditText.getText().toString()), titleEditText.getText().toString(), recording);
                Intent intent = new Intent();

                intent.putExtra("current_income", income);
                setResult(Activity.RESULT_OK, intent);
            }
            // Description entry:
            else {
                if (descriptionEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, getResources().getString(R.string.new_entry_description_required), Toast.LENGTH_SHORT).show();
                    return;
                }
                Income income = new Income(originalIncome.getId(), Integer.parseInt(valueEditText.getText().toString()), titleEditText.getText().toString(), descriptionEditText.getText().toString());
                Intent intent = new Intent();

                intent.putExtra("current_income", income);
                setResult(Activity.RESULT_OK, intent);
            }
            finish();
        });
    }
}