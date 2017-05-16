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

        when: 'signing task is run'
        def result = gradleRunner
                .withArguments('signArchives')
                .build()

        then:
        result.task(':signArchives').outcome == TaskOutcome.NO_SOURCE
    }

    def 'fails when no keystore is configured'() {
        given: 'a build script with archives but no keystore'
        buildFile << '''
        plugins {
            id 'com.github.jazzschmidt.gradle.keystoresigning'
        }

        signArchives {
            archives = [file('no.file')]
        }

        '''

        when: 'signing task is run'
        def result = gradleRunner
                .withArguments('signArchives')
                .build()

        then:
        println(result.output)
        result.task(':signArchives').outcome == TaskOutcome.FAILED
    }

    GradleRunner getGradleRunner() {
        GradleRunner.create()
                .withProjectDir(projectDir.root)
                .withPluginClasspath()
    }
}
