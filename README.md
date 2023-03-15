# Score Convert Java

This application was partially written in August 2021, before its first working version was finished in March 2023.

Code and ideas by Alfons Seelen, who developed the Ts numerical pitch/note representation system in 2014 (see [muditulib](http://muditulib.acls.eu), a multi-dimensional tuning library for [Pure Data](https://puredata.info/)).

Ts values are a lossless alternative to MIDI notes, which are lossy. For example, MIDI can't distinguish between a C sharp and a D flat. Ts can, since it separates whole tones from semitones in a list of two values, `T` and `s`.

## How it works

It's a Java command line application that takes a Lilypond file as an input and converts that to a file containing Ts values with note.

Currently, only note values can be interpreted, in the following style:

```
c16 d e c d e |
f,8 r bes~ |
bes8 a g |
c16 bes a g f e |
```

Bars will be handled as newlines, but currently not checked for validity. Validation is still a part left to be done. 

### Arguments

1. name of the input file
2. note relative to (in relative mode, e.g. `c'`)

### Build the project

Maven is configured to build one executable JAR file, that has all dependencies included. Use ...

`$ mvn package`

... to generate a `score-convert-java.jar` file, which will have version and description in its name too. To execute this file type ...

`$ java -jar score-convert-java.jar [FILENAME] [NOTE]`

... where the jar file should be the complete filename. FILENAME can be `path/to/myFile.ly` and NOTE perhaps `"c'"` or `c\'` for middle c, as long as you escape the apostrophe for bash (or whichever command line you use) to work correctly.

It should then output a file containing your note information, named `path/to/myFile-sc-out.txt`.

Good luck!

### Error handling

BTW, currently there's hardly any validation or logging, so it's extremely important to understand what the input should look like. Of course, this will be the first step in order to get an alpha release ready.

## Feature requests and sponsoring / donations

Check the list in `TODO.md` what features I intend implementing.
Feel free to send me feature requests, if you value this tool.
However, it was initially written completely voluntarily without being paid for, so if you enjoy this application, or even more when you're making money using it, consider a donation.