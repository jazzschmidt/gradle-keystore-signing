package com.github.jazzschmidt.gradle.keystoresigning

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import java.security.KeyStore

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
            sign file('no.file')
        }

        '''

        when: 'signing task is run'
        def result = gradleRunner
                .withArguments('signArchives')
                .buildAndFail()

        then:
        result.task(':signArchives').outcome == TaskOutcome.FAILED
    }

    def 'configures the task from the extension'() {
        given: 'a build script with the extension being configured explicitly'
        buildFile << '''
        plugins {
            id 'com.github.jazzschmidt.gradle.keystoresigning'
        }

        signing {
            keystore = file('keystore.jks')
            alias = 'alias'
            password = 'secret'
        }

        signArchives {
            sign file('no.file')
        }
        '''

        and: 'a keystore'
        def keystore = createKeystore('keystore.jks', 'secret')

        when: 'signing task is run'
        def result = gradleRunner
                .withArguments('signArchives')
                .build()

        then:
        result.task(':signArchives').properties.equals(alias: 'alias', password: 'secret')
    }

    KeyStore createKeystore(String filename, String password) {
        def keystore = KeyStore.getInstance(KeyStore.defaultType)

        keystore.load(null, password as char[])
        def outputStream = new FileOutputStream(projectDir.newFile(filename))
        keystore.store(outputStream, password as char[])

        return keystore
    }

    GradleRunner getGradleRunner() {
        GradleRunner.create()
                .withProjectDir(projectDir.root)
                .withPluginClasspath()
    }
}
