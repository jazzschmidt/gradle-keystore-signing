package com.github.jazzschmidt.gradle.keystoresigning

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class SigningTaskSpec extends Specification {

    @Rule
    TemporaryFolder projectDir
    File buildFile

    def setup() {
        buildFile = projectDir.newFile('build.gradle')
    }

    def 'skips execution when no archives are present'() {
        given: 'a build script without archives'
        buildFile << '''
        plugins {
            id 'com.github.jazzschmidt.gradle.keystoresigning'
        }
        '''

        when:
        def result = GradleRunner.create()
                .withProjectDir(projectDir.root)
                .withArguments('signing')
                .withPluginClasspath()
                .build()

        then:
        result.task(':signing').outcome == TaskOutcome.NO_SOURCE
    }

}
