package rs.raf.projekat1.luka_petrovic_rn3318.ui.list.details;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import rs.raf.projekat1.luka_petrovic_rn3318.R;
import rs.raf.projekat1.luka_petrovic_rn3318.models.Income;
import rs.raf.projekat1.luka_petrovic_rn3318.models.Outcome;

public class OutcomeDetailsActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private AudioFocusRequest audioFocusRequest;
    private boolean playing = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_details);

        EditText titleEditText = findViewById(R.id.entry_details_title_edit_text);
        EditText valueEditText = findViewById(R.id.entry_details_value_edit_text);
        EditText descriptionEditText = findViewById(R.id.entry_details_description);
        ImageView audioButton = findViewById(R.id.entry_details_audio_button);

        Outcome outcome = (Outcome) getIntent().getExtras().getSerializable("outcome");

        titleEditText.setText(outcome.getTitle());
        valueEditText.setText(outcome.getValue().toString());


        if (outcome.getAudioRecording() != null) {
            descriptionEditText.setVisibility(View.GONE);
            audioButton.setVisibility(View.VISIBLE);

            mediaPlayer = MediaPlayer.create(this, Uri.fromFile(outcome.getAudioRecording()));
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

            mediaPlayer.setOnCompletionListener(mp -> {
                mediaPlayer.seekTo(0);
            });

            audioButton.setOnClickListener(view -> {
                if (!playing) {
                    int result = audioManager.requestAudioFocus(audioFocusRequest);
                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        audioButton.setImageResource(R.drawable.outline_pause_24);
                        mediaPlayer.start();
                        playing = true;
                    }
                } else {
                    audioButton.setImageResource(R.drawable.outline_play_arrow_24);
                    mediaPlayer.pause();
                    playing = false;
                }
            });

            // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a short amount of time.
            // The AUDIOFOCUS_LOSS case means we've lost audio focus
            // The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
            // our app is allowed to continue playing sound but at a lower volume.
            // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
            AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = focusChange -> {
                switch (focusChange) {
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    case AudioManager.AUDIOFOCUS_LOSS: {
                        // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a short amount of time.
                        // The AUDIOFOCUS_LOSS case means we've lost audio focus
                        audioButton.setImageResource(R.drawable.outline_play_arrow_24);
                        mediaPlayer.pause();
                        playing = false;
                    }
                    break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK: {
                        // The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                        // our app is allowed to continue playing sound but at a lower volume.
                        playerDuck(true);
                    }
                    break;
                    case AudioManager.AUDIOFOCUS_GAIN: {
                        // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                        playerDuck(false);
                        audioButton.setImageResource(R.drawable.outline_pause_24);
                        mediaPlayer.start();
                        playing = true;
                    }
                    break;
                }
            };

            audioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                    .setAudioAttributes(new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build())
                    .setAcceptsDelayedFocusGain(true)
                    .setWillPauseWhenDucked(true)
                    .setOnAudioFocusChangeListener(onAudioFocusChangeListener)
                    .build();
        } else {
            descriptionEditText.setText(outcome.getDescription());
        }
    }

    public synchronized void playerDuck(boolean duck) {
        if (mediaPlayer != null) {
            // Reduce the volume when ducking - otherwise play at full volume.
            mediaPlayer.setVolume(duck ? 0.2f : 1.0f, duck ? 0.2f : 1.0f);
        }
    }
}