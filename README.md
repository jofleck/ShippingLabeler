# Shipping Labeler

This is a simple Java App to convert PDF Files for a 62mm Labelprinter. Currently only a few German carriers are implemented. To change this, Pull-Requests are always welcome :) 


## Usage 

Java 8 is required.

Just download the latest release and execute the jar file with the terminal(macOS)/shell(Linux)/cmd(Windows) or whatever :) 

Command:
`java -jar shippinglabeler.jar`

(Don't worry. A GUI is planned :D ) 

Now you can insert the path of your PDF label and select the carrier of it. After this the converted document will be shown in your default PDF-Viewer.

Please print the document on a 62 x 184 (sometimes 62 x 209 ) label. Otherwise you risk that the barcodes on the labels aren't readable. 