package com.github.zharovvv.sandboxx

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.github.zharovvv.common.di.featureApi
import com.github.zharovvv.core.navigation.EntryPoint
import com.github.zharovvv.core.navigation.OnlyForMainScreen
import com.github.zharovvv.core.navigation.ui.DefaultEntryPointsAdapter
import com.github.zharovvv.core.ui.activity.LogLifecycleAppCompatActivity
import com.github.zharovvv.sandboxx.di.mainscreen.entrypoints.api.MainScreenEntryPointsApi

@OptIn(OnlyForMainScreen::class)
class MainActivity : LogLifecycleAppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recyclerView: RecyclerView = findViewById(R.id.main_screen_entry_points_recycler_view)
        val adapter = DefaultEntryPointsAdapter<EntryPoint.ActivityEntryPoint>(
            onItemClick = { entryPoint ->
                val router = entryPoint.activityLauncherProvider.invoke()
                router.launch(context = this)
            }
        )
        recyclerView.adapter = adapter
        val mainScreenEntryPoints = featureApi<MainScreenEntryPointsApi>()
            .mainScreenEntryPointsMap
            .values
            .toList()
        adapter.submitList(mainScreenEntryPoints)
    }
}