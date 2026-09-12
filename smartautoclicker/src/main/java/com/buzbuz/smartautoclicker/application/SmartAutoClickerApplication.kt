/*
 * Copyright (C) 2025 Kevin Buzeau
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.buzbuz.smartautoclicker.application

import android.app.Application
import android.content.ComponentName
import com.buzbuz.smartautoclicker.SmartAutoClickerService
import com.buzbuz.smartautoclicker.scenarios.ScenarioActivity
import com.buzbuz.smartautoclicker.core.base.data.AppComponentsManager
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class SmartAutoClickerApplication : Application() {

    @Inject lateinit var appComponentsManager: AppComponentsManager

    override fun onCreate() {
        super.onCreate()

        appComponentsManager.apply {
            registerOriginalAppId(packageName)
            registerSmartAutoClickerService(
                ComponentName(packageName, SmartAutoClickerService::class.java.name)
            )
            registerScenarioActivity(
                ComponentName(packageName, ScenarioActivity::class.java.name)
            )
        }

        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}