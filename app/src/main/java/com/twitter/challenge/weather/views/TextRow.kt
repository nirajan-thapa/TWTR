package com.twitter.challenge.weather.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.twitter.challenge.R
import kotlinx.android.synthetic.main.text_row.view.title

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class TextRow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.text_row, this)
    }

    @TextProp
    fun setTitle(value: CharSequence) {
        title.text = value
    }
}
