# SvgGraphics

Utilities for saving Graphics output as SVG.

[Apache Batik](https://xmlgraphics.apache.org/batik/) offers an
infrastructure for dealing with SVG graphics in Java. It 
contains a class `SVGGraphics2D`, which extends the Java
Swing `Graphics2D` class and allows obtaining the results
of drawing operations as SVG data structures. 

This library is a thin layer that is wrapped around the
`SVGGraphics2D` class: The `SvgGraphics` class allows 
creating an `SVGGraphics2D` instance that has some extensions 
for saving drawn images as embedded PNG files and translating
gradient paint operations into SVG gradient paint elements.

The `SvgGraphicsWriter` class offers convenience methods for 
saving `SVGGraphics2D` to output streams or user-selected files.

The following snippet allows saving the contents of a `JComponent`
to a user-selected SVG file:

    SVGGraphics2D g = SvgGraphics.create();
    someJComponent.printAll(g);
    SvgGraphicsWriter.save(g);
    

## Releases

This library is available in Maven Central, under the following 
coordinates:

```
<dependency>
  <groupId>de.javagl</groupId>
  <artifactId>svg-graphics</artifactId>
  <version>0.0.2</version>
</dependency>
```

The library has some furter dependencies - mainly to the
Apache Batik library. For convenience, a standalone version
of the library is offered for download in the
[releases](https://github.com/javagl/SvgGraphics/releases)
section. Note that this standalone library includes classes 
from Apache Batik, and is thus only available under the
Apache License, Version 2.0. 
See https://xmlgraphics.apache.org/batik/ for further information.






