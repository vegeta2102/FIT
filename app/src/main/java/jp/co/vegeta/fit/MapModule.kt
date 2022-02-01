package jp.co.vegeta.fit

import android.content.Context
import app.mobilitytechnologies.lib.map.MapComponent
import app.mobilitytechnologies.lib.map.globalnavi.GNaviMapComponent
import app.mobilitytechnologies.lib.map.globalnavi.GNaviMapComponentFactory
import app.mobilitytechnologies.lib.map.globalnavi.model.GMapDataFactory
import app.mobilitytechnologies.lib.map.globalnavi.model.GNaviMapScaleFactory
import app.mobilitytechnologies.lib.map.model.MapDataFactory
import app.mobilitytechnologies.lib.map.model.MapScaleFactory
import app.mobilitytechnologies.lib.navi.globalnavi.GNaviNaviEngineFactory
import app.mobilitytechnologies.lib.navi.model.engine.NaviEngine
import app.mobilitytechnologies.lib.navi.model.engine.RouteEngine
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MapSingletonModule {
    @Provides
    fun provideGNaviMapComponent(): GNaviMapComponent =
        GNaviMapComponentFactory.create() as GNaviMapComponent

    @Provides
    fun provideMapComponent(impl: GNaviMapComponent): MapComponent =
        impl

    @Provides
    fun provideNaviEngine(): NaviEngine =
        GNaviNaviEngineFactory.getInstance()

    @Provides
    fun provideRouteEngine(naviEngine: NaviEngine): RouteEngine =
        naviEngine

    @Provides
    fun provideMapScaleFactory(): MapScaleFactory = GNaviMapScaleFactory()

    @Provides
    fun provideMapDataFactory(): MapDataFactory = GMapDataFactory()

    @Provides
    fun provideMapModuleManager(
        @ApplicationContext context: Context,
        mapComponent: MapComponent
    ): MapModuleManager {
        return MapModuleManager(context, mapComponent)
    }
}