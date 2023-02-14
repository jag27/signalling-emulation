## Getting Started

This is an attempt at an emulation of how british trains are signalled and regulated on the railway. Currently, there is
only one track layout, and it will likely stay that way for a while until all other features have been implemented, such 
as route setting, level crossings, stations, timetables and more.

## Folder Structure

The workspace contains two folders, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder.

Within the `src` folder there is also the `signalLogic` package. This contains all the main elements for the logic of
the program, defining important classes such as signals, trains and tracks.

## Dependency Management

This project requires JavaFX to run.