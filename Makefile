run-dist:
		./build/install/java-project-lvl2/bin/java-project-lvl2
build:
	./gradlew clean build
report:
	./gradlew jacocoTestReport
.PHONY: build