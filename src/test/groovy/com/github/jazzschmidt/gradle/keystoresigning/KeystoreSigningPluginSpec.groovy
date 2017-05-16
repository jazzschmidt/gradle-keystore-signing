package com.github.jazzschmidt.gradle.keystoresigning

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class KeystoreSigningPluginSpec extends Specification {

    def 'adds a signing task to the project'() {
        given: 'a project'
        Project project = ProjectBuilder.builder().build()

        when: 'applying the plugin'
        project.pluginManager.apply(KeystoreSigningPlugin)

        then: 'the signing task is added'
        project.tasks.signing instanceof SigningTask
    }

    def 'adds a configuration for artifacts to the project'() {
        given: 'a project'
        Project project = ProjectBuilder.builder().build()

        when:
        project.pluginManager.apply(KeystoreSigningPlugin)

        then:
        project.configurations.findByName('signedArchives') != null
    }

    def 'adds a signing extension to the project'() {
        given: 'a project'
        Project project = ProjectBuilder.builder().build()

        when:
        project.pluginManager.apply(KeystoreSigningPlugin)

        then:
        project.extensions.findByName('signing') != null
    }

}
