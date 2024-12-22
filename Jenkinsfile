pipeline {
    agent any

    environment {
        MODULE_API = 'module-api'
        MODULE_DATABASE = 'module-database'
        MODULE_REDIS = 'module-redis'
        CURRENT_LOCATION = '/var/lib/jenkins/workspace/fittner-server';
    }
    stages {
        stage('database build') {
            steps {
            sh './gradlew ${MODULE_DATABASE}:build -x test'
            }
        }
        stage('build') {
            parallel {
                stage('module-api(build)') {
                    when {
                        anyOf{
                            changeset "module-database/**/*"
                            changeset "module-api/**/*"
                        }
                    }
                    steps {
                        echo '[build start] ${MODULE_API}'
                        sh './gradlew ${MODULE_API}:build -x test'
                        sh "sudo mv ${CURRENT_LOCATION}/module-api/build/libs/module-api-0.0.1-SNAPSHOT.jar /app/health"
                        echo '[build end] ${MODULE_API}'
                    }
                }
    }
}
        stage('deploy') {
            parallel {
                stage('module-api-01(deploy) && module-api-02(deploy)') {
                    when {
                        anyOf{
                            changeset "module-database/**/*"
                            changeset "module-api/**/*"
                        }
                    }
                    steps {
                    script{
                        def pid
                        def response
                        def status = true
                        try {
                        echo '[kill port ${MODULE_API}]'
                        pid = sh(script: "sudo lsof -t -i :8080 -s TCP:LISTEN",returnStdout: true).trim()
                        }
                        catch(Exception e){
                            echo "오류 내용 : ${e.message}"
                            pid = null
                        }
                       if(pid != "" && pid != null){
                        echo '현재 PID : ${pid}'
                        sh "sudo kill -9 ${pid}"
                        }
                        else{
                            echo "not exist port"
                        }
                        echo '[deploy start] ${MODULE_API}'
                        sh "JENKINS_NODE_COOKIE=dontKillMe && sudo nohup java -jar -Dserver.port=8080 -Duser.timezone=Asia/Seoul /app/health/module-api-0.0.1-SNAPSHOT.jar 1>/dev/null 2>&1 &"
                        while(status) {
                        echo "1번 서버 구동 중..."
                        response = sh(script: "curl -s -o /dev/null -w '%{http_code}' http://localhost:8080/swagger-ui/index.html", returnStatus: true)
                        if(response == 0){
                        echo "1번 서버 구동 완료"
                        sleep 5
                        break
                        }
                        echo "1번 서버 구동 대기중..."
                        sleep 5
                        }
                        try{
                        pid = sh(script: "sudo lsof -t -i :8081 -s TCP:LISTEN",returnStdout: true).trim()
                        }
                        catch(Exception e){
                            echo "오류 내용 : ${e.message}"
                            pid = null
                        }
                       if(pid != "" && pid != null){
                        echo '현재 PID : ${pid}'
                        sh "sudo kill -9 ${pid}"
                        }
                        else{
                            echo "not exist port"
                        }
                        echo '[deploy start] ${MODULE_API}'
                        sh "JENKINS_NODE_COOKIE=dontKillMe && sudo nohup java -jar -Dserver.port=8081 -Duser.timezone=Asia/Seoul /app/health/module-api-1.0-SNAPSHOT.jar 1>/dev/null 2>&1 &"
                        while(status) {
                        echo "2번 서버 구동 중..."
                        response = sh(script: "curl -s -o /dev/null -w '%{http_code}' http://localhost:8081/swagger-ui/index.html", returnStatus: true)
                        if(response == 0){
                        echo "2번 서버 구동 완료"
                        break
                        }
                        echo "2번 서버 구동 대기중..."
                        sleep 5
                        }

                    }
                    }
                }
                }
            }
        }
    }
}
