package com.github.jazzschmidt.gradle.keystoresigning

import org.gradle.api.Plugin
import org.gradle.api.Project

class KeystoreSigningPlugin implements Plugin<Project> {

    final public static String ARTIFACT_CONFIGURATION = 'signedArchives'

    final private class Extension implements KeystoreSigningConfiguration {
        final public static String EXTENSION_NAME = 'signing'
    }

    @Override
    void apply(Project project) {
        def task = project.tasks.create('signArchives', SigningTask)

        project.configurations.create(ARTIFACT_CONFIGURATION)

        def extension = createExtensionFromProperties(project)
        project.extensions.add(Extension.EXTENSION_NAME, extension)

        project.afterEvaluate {
            project.tasks.withType(SigningTask) {
                keystore = extension.keystore
                alias = extension.alias
                password = extension.password
            }
        }
    }

    private Extension createExtensionFromProperties(Project project) {
        def extension = new Extension()

        def keystore = project.findProperty('keystoresigning.keystore')
        extension.keystore = keystore ? project.file(keystore) : null
        extension.alias = project.findProperty('keystoresigning.alias')
        extension.password = project.findProperty('keystoresigning.password')

        return extension
    }
}
