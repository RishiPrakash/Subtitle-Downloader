# Subtitle-Downloader
Download subtitles for all your movies automatically

## Technologies used
* JDK 1.8
* Eclipse
* jsoup HTML parser for java

#### How to run
1. Import Java files locally
2. Get jsoup jar file from internet and keep it in your referenced libraries folder
3. Run project
4. It will ask for folder name, give the full path till folder in which your Movies are stored. for eg. if movies are in /Users/Desktop/Movies/chakDeIndia.mkv
pass /Users/Desktop/Movies 

#### Alternate Install option
* download jar file and run "java -jar jarFilename, follow 4th step from earlier option; replace jarFile with the name of jar,
in this case it would be "SubDownloader"

#### what's inside the code
* Uses subscene for downloading the subtitles
* for each movie title it will download max. 5 zip files containing srt files
* creates one folder along movie file with <moviename>_Subs and keeps all zip files in it.
* one hidden i.e commented option is to download from subDB also; but it is not very effective so it's not in use
* currently it downloades Eng subtitles automatically.



