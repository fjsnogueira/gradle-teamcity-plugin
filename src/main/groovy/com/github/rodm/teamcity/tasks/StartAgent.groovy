/*
 * Copyright 2015 Rod MacKenzie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.rodm.teamcity.tasks

import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class StartAgent extends TeamCityTask {

    @Input
    final Property<String> agentOptions = project.objects.property(String)

    StartAgent() {
        description = 'Starts the TeamCity Agent'
    }

    @TaskAction
    void start() {
        validate()
        def name = isWindows() ? 'agent.bat' : 'agent.sh'
        ant.exec(executable: "${getHomeDir().get()}/buildAgent/bin/$name") {
            env key: 'JAVA_HOME', path: getJavaHome().get()
            env key: 'TEAMCITY_AGENT_OPTS', value: getAgentOptions().get()
            arg value: 'start'
        }
    }
}
