.PHONY: build

setup:
	gradle wrapper --gradle-version 7.3

clean:
	gradlew clean

build:
	gradlew clean build installDist

start:
	gradlew bootRun --args='--spring.profiles.active=dev'

start-prod:
	gradlew bootRun --args='--spring.profiles.active=prod'

install:
	gradlew installDist

lint:
	gradlew checkstyleMain checkstyleTest

test:
	gradlew test

report:
	gradlew jacocoTestReport

check-updates:
	gradlew dependencyUpdates

generate-migrations:
	gradle diffChangeLog

db-migrate:
	gradlew update

api-doc:
	gradlew clean generateOpenApiDocs