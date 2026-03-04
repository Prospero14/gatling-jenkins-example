pipeline {
    // ВАЖНО: Вместо agent any (запускать где угодно), мы используем agent { label ... }
    // Это значит, что Jenkins будет запускать этот тест ТОЛЬКО на том сервере, который ты выбрал в выпадающем списке TARGET_SERVER
    agent {
        label "${params.TARGET_SERVER}"
    }

    // Это параметры, которые Jenkins спросит у тебя перед запуском теста
    parameters {
        // 1. ВЫБОР СЕРВЕРА
        // Здесь перечисли имена твоих серверов (генераторов нагрузки), как они называются в Jenkins (Manage Jenkins -> Nodes)
        // Например: 'generator-1', 'generator-2', 'linux-node-ru' и так далее.
        choice(name: 'TARGET_SERVER', choices: ['server-1', 'server-2', 'server-3'], description: 'Выберите сервер (генератор нагрузки) для запуска Gatling')

        // 2. ВЫБОР СИМУЛЯЦИИ
        // Это названия твоих симуляций (пакет.Класс)
        choice(name: 'SIMULATION', choices: ['simulations.Debug', 'simulations.FinmonWebSimulation', 'simulations.SberratingSimulation'], description: 'Выберите симуляцию для запуска')
    }

    // Эти настройки памяти применятся на том сервере, который ты выбрал (заменяет твой export MAVEN_OPTS)
    environment {
        MAVEN_OPTS = '-Xms512M -Xmx2G'
    }

    // Инструменты, которые должны быть установлены в Jenkins -> Global Tool Configuration
    tools {
        maven 'Maven 3'
        jdk 'Java 17'
    }

    stages {
        // Шаг 1: Скачиваем твой код симуляций из репозитория на выбранный сервер
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        // Шаг 2: Непосредственно сам запуск
        stage('Run Gatling Tests') {
            steps {
                echo "⏳ Запускаем симуляцию ${params.SIMULATION} на сервере ${params.TARGET_SERVER}..."
                
                // Команда запуска для Linux серверов (где лежит pom.xml)
                sh "mvn gatling:test -Dgatling.simulationClass=${params.SIMULATION}"
            }
        }
    }

    // Шаг 3: Что делать, когда тест закончился (успешно или с ошибкой)
    post {
        always {
            echo "✅ Тест завершен. Сохраняем отчеты..."
            
            // Эта команда берет HTML-отчеты с сервера и сохраняет их в самом Jenkins, чтобы ты мог посмотреть их через браузер
            archiveArtifacts artifacts: 'target/gatling/**/*.html', allowEmptyArchive: true
            
            // Если в Jenkins установлен плагин Gatling, можно активировать построение графиков (раскомментируй строку ниже)
            // gatlingArchive()
        }
    }
}
