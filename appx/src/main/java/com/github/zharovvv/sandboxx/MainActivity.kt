package com.github.zharovvv.sandboxx

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.github.zharovvv.common.di.featureApi
import com.github.zharovvv.sandboxx.di.mainscreen.entrypoints.api.MainScreenEntryPointsApi
import com.github.zharovvv.sandboxx.ui.mainscreen.entry.point.MainScreenEntryPointsAdapter

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recyclerView: RecyclerView = findViewById(R.id.main_screen_entry_points_recycler_view)
        val adapter = MainScreenEntryPointsAdapter(
            onItemClickBlock = { entryPoint ->
                val router = entryPoint.routerProvider.invoke()
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