package com.github.jazzschmidt.gradle.keystoresigning

import org.gradle.api.Plugin
import org.gradle.api.Project

class KeystoreSigningPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.tasks.create('signing', SigningTask)
        project.configurations.create('signedArchives')
    }
}
