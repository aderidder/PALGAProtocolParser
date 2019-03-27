# The PALGA Protocol Parser
The PALGA Protocol Parser is software that creates codebooks from PALGA Protocols. 

## Creating an executable jar
You can use maven to create an executable jar file, using mvn package. The jar is placed in the target directory and can be run using java -jar <generated_jar_file>

## Generating codebooks
The program has three requirements: a protocol file, a workspace file and an output directory. Optionally, an overwrite file can be specified.
 
### The Protocol File
The protocol file contains identifiers for the protocols. Each line contains a prefix of the protocol, which is used to find the Nets for the protocol in the logicnet table, a tab, and the name of the protocol as it can be found in the project table

### The Workspace File
This is a database with the PALGA Protocols

### The Output Directory
Directory where the output will be written

### The Overwrite File
Contains the identifier of a concept, a tab, and a label for the concept. This basically allows you to overwrite the labels found in the PALGA Protocol.

## About
PALGA Protocol Parser is a collaboration between NKI / AvL, VUmc and PALGA.<br>
The program was designed and created by **Sander de Ridder** (NKI 2017; VUmc 2018/2019)<br>
Testers & Consultants: Rinus Voorham (PALGA), Rick Spaan (PALGA), Jeroen Belien (VUmc)<br>
This project was sponsored by MLDS project OPSLAG and KWF project TraIT2Health-RI (WP: Registry-in-a-Box)<br>

PALGAProtocolParser is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

PALGAProtocolParser is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
