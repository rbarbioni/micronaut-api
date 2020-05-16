.SILENT:
TAG:=build tag
ENV:=env
RELEASE_MESSAGE=$(shell git log `git describe --tags --abbrev=0`..master --oneline --no-decorate | sed 's/"/\\"/g' | awk '{printf "%s\\n", $$0}')

.PHONY: build
build: spotless
	./gradlew clean build test --tests "br.com.rbarbioni.unit*"

.PHONY: build/docker
build/docker: build
	docker build -t rbarbioni/micronaut-api .

.PHONY: test/all
test/all:
	./gradlew test

.PHONY: test/unit
test/unit:
	./gradlew test --tests "br.com.rbarbioni.unit*" --info

.PHONY: test/integration
test/integration: run/docker-compose-structure
	sleep 7
	./gradlew test --tests "br.com.rbarbioni.integration*" --info

.PHONY: spotless
spotless:
	./gradlew spotlessApply

.PHONY: run/docker-compose
run/docker-compose: down
	docker-compose up -d

.PHONY: run/docker-compose-structure
run/docker-compose-structure: down
	docker-compose -f docker-compose.yml up --scale micronaut-api=0 -d

.PHONY: down
down:
	docker-compose -f docker-compose.yml down --remove-orphans

.PHONY: run
run: build
	java -jar ${JAVA_OPTS} build/libs/micronaut-api-all.jar

.PHONY: run/gradle
run/gradle:
	./gradlew run --continuous

.PHONY: run/docker
run/docker: build/docker
	docker run -it -p 8080:8080 rbarbioni/micronaut-api:latest -d
