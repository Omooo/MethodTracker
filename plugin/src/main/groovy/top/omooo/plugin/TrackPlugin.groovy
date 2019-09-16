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

        project.repositories {
            maven {
                url "https://omooo2333.bintray.com/MethodTrack"
            }
        }
        project.dependencies {
            implementation 'top.omooo:method-track-annotation:0.1.1'
        }
    }
}