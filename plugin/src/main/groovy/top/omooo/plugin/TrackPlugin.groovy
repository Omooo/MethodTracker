package top.omooo.plugin

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
//            compile project(':annotation')
        }
    }
}