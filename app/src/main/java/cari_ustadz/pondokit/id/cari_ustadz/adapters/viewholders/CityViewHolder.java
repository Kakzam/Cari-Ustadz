package cari_ustadz.pondokit.id.cari_ustadz.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import cari_ustadz.pondokit.id.cari_ustadz.R;
import cari_ustadz.pondokit.id.cari_ustadz.fragments.CitySelectionFragment;
import cari_ustadz.pondokit.id.cari_ustadz.models.City;

/**
 * Created by akif on 11/05/16.
 */
public class CityViewHolder extends RecyclerView.ViewHolder {
    private View cityItemLayout;
    private TextView textViewName;

    private CitySelectionFragment.OnCitySelectedListener onCitySelectedListener;

    public CityViewHolder(View cityItemLayout, CitySelectionFragment.OnCitySelectedListener onCitySelectedListener) {
        super(cityItemLayout);

        this.cityItemLayout = cityItemLayout;
        this.onCitySelectedListener = onCitySelectedListener;

        textViewName = (TextView) cityItemLayout.findViewById(R.id.textView_item_city_name);
    }

    public void setFrom(final City city) {
        textViewName.setText(city.name);

        cityItemLayout.setOnClickListener(v -> onCitySelectedListener.onCitySelected(city));
    }
}
