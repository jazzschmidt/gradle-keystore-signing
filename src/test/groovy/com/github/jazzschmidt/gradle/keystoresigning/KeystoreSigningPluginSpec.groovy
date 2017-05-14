package com.github.jazzschmidt.gradle.keystoresigning

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class KeystoreSigningPluginSpec extends Specification {

    def 'adds a signing task to the project'() {
        when:
        Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply(KeystoreSigningPlugin)

        then:
        project.tasks.findByName('signing') != null
    }

    def 'adds a configuration for artifacts to the project'() {
        when:
        Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply(KeystoreSigningPlugin)

        then:
        project.configurations.findByName('signedArchives') != null
    }

}
