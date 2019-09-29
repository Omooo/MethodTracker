package top.omooo.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class TrackPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.task("doAction") {
            println('/***************************/')
            println('/*** --- TrackPlugin --- ***/')
            println('/***************************/')
        }

        project.dependencies {
            api 'top.omooo:method-track-annotation:0.1.1'
        }

        def android = project.extensions.findByType(AppExtension)
        android.registerTransform(new MethodTraceTransform(project))

    }
}