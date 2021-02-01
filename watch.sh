while true;
do
	./watchForChange.sh /home/z/projects/kotlin/kotlinOpenScad/src/main/kotlin/main.kt
	clear
	./gradlew --watch-fs compileKotlin run
done
