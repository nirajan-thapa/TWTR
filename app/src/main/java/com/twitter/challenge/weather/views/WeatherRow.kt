package com.twitter.challenge.weather.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.twitter.challenge.R
import kotlinx.android.synthetic.main.weather_row.view.cloud
import kotlinx.android.synthetic.main.weather_row.view.future_temp
import kotlinx.android.synthetic.main.weather_row.view.temperature
import kotlinx.android.synthetic.main.weather_row.view.wind

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class WeatherRow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.weather_row, this)
    }

    @TextProp
    fun setTemperature(temp: CharSequence) {
        temperature.text = temp
    }

    @TextProp
    fun setWind(windValue: CharSequence) {
        wind.text = windValue
    }

    @ModelProp
    fun setCloudVisibility(showCloud: Boolean) {
        cloud.visibility = if (showCloud)
            View.VISIBLE
        else
            View.INVISIBLE
    }

    @CallbackProp
    fun setClickListener(clickListener: OnClickListener?) {
        future_temp.setOnClickListener(clickListener)
    }
}
