package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    public interface AddCityDialogListener {
        void addCity(City city);
    }

    private AddCityDialogListener listener;

    private City city; // holding the city when we edit

    // Empty constructor for adding new city
    public AddCityFragment() {}

    // Constructor for editing an existing city
    public AddCityFragment(City city) {
        this.city = city;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new ClassCastException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext())
                .inflate(R.layout.fragment_add_city, null, false);

        EditText cityEt = view.findViewById(R.id.edit_text_city_text);
        EditText provEt = view.findViewById(R.id.edit_text_province_text);

        // Pre-fill if editing
        if (city != null) {
            cityEt.setText(city.getName());
            provEt.setText(city.getProvince());
        }

        boolean isEdit = (city != null);

        return new AlertDialog.Builder(requireContext())
                .setTitle(isEdit ? "Edit City" : "Add City")
                .setView(view)
                .setPositiveButton(isEdit ? "Save" : "Add", (d, w) -> {
                    String cityName = cityEt.getText().toString().trim();
                    String provName = provEt.getText().toString().trim();
                    if (!cityName.isEmpty() && !provName.isEmpty()) {
                        if (isEdit) {
                            // Update existing object via setters
                            city.setName(cityName);
                            city.setProvince(provName);
                        } else {
                            // Add brand-new city back to Activity
                            listener.addCity(new City(cityName, provName));
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
    }
}
