package com.github.jazzschmidt.gradle.keystoresigning

import org.gradle.api.Plugin
import org.gradle.api.Project

class KeystoreSigningPlugin implements Plugin<Project> {

    private class Configuration implements KeystoreSigningConfiguration { }

    @Override
    void apply(Project project) {
        def task = project.tasks.create('signing', SigningTask)

        project.configurations.create('signedArchives')
        project.extensions.add('signing', new Configuration())

        def extension = project.extensions.getByName('signing')

        def keystore = project.findProperty('keystoresigning.keystore')
        extension.keystore = keystore ? project.file(keystore) : null
        extension.alias = project.findProperty('keystoresigning.alias')
        extension.password = project.findProperty('keystoresigning.password')
    }
}
