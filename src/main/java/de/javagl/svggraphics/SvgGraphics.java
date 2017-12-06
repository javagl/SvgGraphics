/*
 * www.javagl.de - SvgGraphics - Utilities for saving Graphics output as SVG
 *
 * Copyright (c) 2017-2017 Marco Hutter - http://www.javagl.de
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package de.javagl.svggraphics;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.ext.awt.image.spi.ImageWriter;
import org.apache.batik.ext.awt.image.spi.ImageWriterRegistry;
import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

/**
 * Methods to create instances of Apache Batik <code>SVGGraphics2D</code> 
 * objects that can be written into SVG files using a {@link SvgGraphicsWriter} 
 */
public class SvgGraphics
{
    // Initialization of the ImageWriter for PNG, if none exists yet
    static
    {
        ImageWriterRegistry imageWriterRegistry =
            ImageWriterRegistry.getInstance();
        ImageWriter writer = imageWriterRegistry.getWriterFor("image/png");
        if (writer == null)
        {
            imageWriterRegistry.register(new PngImageWriter());
        }
    }
    
    /**
     * Creates a new Apache Batik <code>SVGGraphics2D</code> instance. The
     * returned instance can handle gradient paints and PNG images, and can
     * be saved to an SVG file using the {@link SvgGraphicsWriter}
     * 
     * @return The SVGGraphics2D instance
     */
    public static SVGGraphics2D create()
    {
        DOMImplementation domImplementation =
            GenericDOMImplementation.getDOMImplementation();
        Document document = domImplementation.createDocument(
            "http://www.w3.org/2000/svg", "svg", null);

        SVGGeneratorContext svgGeneratorContext =
            SVGGeneratorContext.createDefault(document);
        svgGeneratorContext.setExtensionHandler(
            new GradientExtensionHandler());

        boolean textAsShapes = false;
        SVGGraphics2D svgGraphics = 
            new SVGGraphics2D(svgGeneratorContext, textAsShapes);
        return svgGraphics;
    }
    
    /**
     * Private constructor to prevent instantiation
     */
    private SvgGraphics()
    {
        // Private constructor to prevent instantiation
    }

}
