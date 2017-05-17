package com.github.jazzschmidt.gradle.keystoresigning

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class KeystoreSigningPluginSpec extends Specification {

    def 'adds a task, a configuration, and an extension to the project'() {
        given: 'a project'
        Project project = ProjectBuilder.builder().build()

        when: 'applying the plugin'
        project.pluginManager.apply(KeystoreSigningPlugin)

        then: 'the signing task is added'
        project.tasks.signArchives instanceof SigningTask

        and: 'a configuration for artifacts is added'
        project.configurations.getByName('signedArchives')

        and: 'a configuration extension is added'
        project.extensions.getByName('signing')
    }

    def 'configures the extension with project properties'() {
        given: 'a project'
        Project project = ProjectBuilder.builder().build()

        and: 'the signing properties'
        project.ext.'keystoresigning.keystore' = 'test'
        project.ext.'keystoresigning.alias' = 'alias'
        project.ext.'keystoresigning.password' = 'secret'

        when: 'applying the plugin'
        project.pluginManager.apply(KeystoreSigningPlugin)

        then: 'the properties are applied to the extension'
        def extension = project.extensions.getByName('signing')

        extension.keystore == project.file('test')
        extension.alias == 'alias'
        extension.password == 'secret'
    }

}
