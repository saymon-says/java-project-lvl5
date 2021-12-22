.PHONY: build

install:
	gradlew clean install

run-dist:
	build\install\app\bin\app
check-updates:
	gradlew dependencyUpdates

lint:
	gradlew checkstyleMain

build:
	gradlew clean build

start:
	APP_ENV=development ./gradlew run

test:
	gradlew test