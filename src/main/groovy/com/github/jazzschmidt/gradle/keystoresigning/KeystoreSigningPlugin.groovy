package com.github.jazzschmidt.gradle.keystoresigning

import org.gradle.api.Plugin
import org.gradle.api.Project

class KeystoreSigningPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def task = project.tasks.create('signing', SigningTask)
        task.dependsOn 'assemble'

        project.configurations.create('signedArchives')
        def extension = project.extensions.create('signing', KeystoreSigningExtension)

        def keystore = project.findProperty('keystoresigning.keystore')
        extension.keystore = keystore ? project.file(keystore) : null
        extension.alias = project.findProperty('keystoresigning.alias')
        extension.password = project.findProperty('keystoresigning.password')
    }
}
