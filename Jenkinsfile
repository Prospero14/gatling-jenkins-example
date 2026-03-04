pipeline {
    agent any

    // Параметры для выбора симуляции при запуске в Jenkins
    parameters {
        string(name: 'SIMULATION', defaultValue: 'simulations.Debug', description: 'Class симуляции (например, simulations.Debug)')
    }

    // Переменные окружения, заменяет export MAVEN_OPTS
    environment {
        MAVEN_OPTS = '-Xms512M -Xmx2G'
    }

    tools {
        // Имена инструментов должны совпадать с теми, что настроены в Jenkins -> Global Tool Configuration
        maven 'Maven 3'
        jdk 'Java 17'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run Gatling Tests') {
            steps {
                echo "Запускаем симуляцию: ${params.SIMULATION}"
                // Запуск maven с переданным параметром
                sh "mvn gatling:test -Dgatling.simulationClass=${params.SIMULATION}"
            }
        }
    }

    post {
        always {
            // Сохраняем отчеты (HTML), чтобы просматривать в интерфейсе Jenkins
            archiveArtifacts artifacts: 'target/gatling/**/*.html', allowEmptyArchive: true
            
            // Если в Jenkins установлен плагин Gatling, можно активировать построение графиков
            // gatlingArchive()
        }
    }
}
