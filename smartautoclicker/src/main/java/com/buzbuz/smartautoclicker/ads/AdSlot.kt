package com.buzbuz.smartautoclicker.ads

import android.view.View
import android.widget.TextView

import com.buzbuz.smartautoclicker.R

enum class AdSlot(val labelRes: Int) {
    SCENARIO_LIST(R.string.ad_slot_scenario_list),
    SETTINGS(R.string.ad_slot_settings),
    TASK_COMPLETION(R.string.ad_slot_task_completion),
}

interface AdSlotProvider {
    fun bind(slot: AdSlot, view: TextView)
}

object NoOpAdProvider : AdSlotProvider {
    override fun bind(slot: AdSlot, view: TextView) {
        view.text = view.resources.getString(R.string.ad_slot_placeholder, view.resources.getString(slot.labelRes))
        view.isClickable = false
        view.isFocusable = false
        view.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
        view.visibility = View.VISIBLE
    }
}
