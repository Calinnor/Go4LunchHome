package com.cirederf.go4lunch.views.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.cirederf.go4lunch.R;

import java.text.MessageFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.cirederf.go4lunch.R.id.radioButton_search_bar;
import static com.cirederf.go4lunch.R.id.radioButton_search_food;
import static com.cirederf.go4lunch.R.id.radioButton_search_restaurants;

public class SettingsFragment extends Fragment {


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.seekBar_radius)
    SeekBar seekBarRadius ;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.seekBar_tilt)
    SeekBar seekBarTilt;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.radioGroup_searchType)
    RadioGroup radioGroupSearchTypes;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.radioButton_search_restaurants)
    RadioButton radioButtonRestaurants;
    @SuppressLint("NonConstantResourceId")
    @BindView(radioButton_search_bar)
    RadioButton radioButtonBars;
    @SuppressLint("NonConstantResourceId")
    @BindView(radioButton_search_food)
    RadioButton radioButtonBakers;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.button_save_settings)
    Button buttonSave;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.radius_view)
    TextView radiusView;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tilt_view)
    TextView tiltView;

    public static final String RADIUS_PREFS = "radius";
    public static final String TILT_PREFS = "tilt";
    public static final String TYPE_PREFS = "typeToSearch";
    public static final String APP_PREFS = "appSetting";
    private int radius;
    private int tilt;
    private String typeToSearch;

    public static SettingsFragment newInstance() {
        return (new SettingsFragment());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        setSharedPrefs();
        isChecked();
        setConfig();
        setSeekBarRadiusProgressValue();
        setSeekBarTiltProgressValue();
        onSaveButton();
        return view;
    }

    private void setConfig() {
        seekBarRadius.setMax(2500);
        seekBarTilt.setMax(45);
    }

    private void onSaveButton() {
        this.buttonSave.setOnClickListener(v -> SettingsFragment.this.doSave());
    }
   
    public void doSave()  {
        SharedPreferences sharedPreferences= this.requireContext().getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = getEditor(sharedPreferences);
        editor.apply();
        Toast.makeText(getContext(),"App Settings saved!",Toast.LENGTH_LONG).show();
    }

    private SharedPreferences.Editor getEditor(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TILT_PREFS, this.seekBarTilt.getProgress());
        editor.putInt(RADIUS_PREFS, this.seekBarRadius.getProgress());
        int checkedRadioButtonId = radioGroupSearchTypes.getCheckedRadioButtonId();
        editor.putString(TYPE_PREFS, setTypePref(checkedRadioButtonId));
        return editor;
    }

    private void setSeekBarRadiusProgressValue() {
        seekBarRadius.setProgress(radius);
        radiusView.setText(MessageFormat.format("{0}{1}/{2}", getString(R.string.radius_is), radius, seekBarRadius.getMax()));
        seekBarRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int seekBarRadiusValue;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                radiusView.setText(MessageFormat.format("{0}{1}", getString(R.string.radius_is), String.valueOf(progress)));
                seekBarRadiusValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                radiusView.setText(MessageFormat.format("{0}{1}/{2}", getString(R.string.radius_is), seekBarRadiusValue, seekBarRadius.getMax()));
            }
        });
    }

    private void setSeekBarTiltProgressValue() {
        seekBarTilt.setProgress(tilt);
        tiltView.setText(MessageFormat.format("{0}{1}/{2}", getString(R.string.tilt_is), tilt, seekBarTilt.getMax()));
        seekBarTilt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int seekBarTiltValue;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tiltView.setText(MessageFormat.format("{0}{1}", getString(R.string.tilt_is), String.valueOf(progress)));
                seekBarTiltValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tiltView.setText(MessageFormat.format("{0}{1}/{2}", getString(R.string.tilt_is), seekBarTiltValue, seekBarTilt.getMax()));
            }
        });
    }

    private void setSharedPrefs() {
        SharedPreferences appPrefs = requireContext().getSharedPreferences(SettingsFragment.APP_PREFS, Context.MODE_PRIVATE );
        radius = appPrefs.getInt(RADIUS_PREFS, 700);
        tilt = appPrefs.getInt(TILT_PREFS, 0);
        typeToSearch = appPrefs.getString(TYPE_PREFS, "restaurant");
    }

    @SuppressLint("NonConstantResourceId")
    private String setTypePref(int checkedRadioButtonId) {
        String type = typeToSearch;
        switch (checkedRadioButtonId) {
            case radioButton_search_restaurants:
                type = "restaurant";
                break;
            case radioButton_search_bar:
                type = "bar";
                break;
            case radioButton_search_food:
                type = "bakery";
                break;
            default:
                break;
        }
        return type;
    }

    private void isChecked() {
        switch (typeToSearch) {
            case "restaurant":
                radioButtonRestaurants.setChecked(true);
                radioButtonBakers.setChecked(false);
                radioButtonBars.setChecked(false);
                break;
            case "bar":
                radioButtonRestaurants.setChecked(false);
                radioButtonBakers.setChecked(false);
                radioButtonBars.setChecked(true);
                break;
            case "bakery":
                radioButtonRestaurants.setChecked(false);
                radioButtonBakers.setChecked(true);
                radioButtonBars.setChecked(false);
                break;
        }
    }
}