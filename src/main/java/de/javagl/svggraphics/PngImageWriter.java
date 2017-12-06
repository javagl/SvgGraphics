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

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.batik.ext.awt.image.spi.ImageWriter;
import org.apache.batik.ext.awt.image.spi.ImageWriterParams;

/**
 * Implementation of the ImageWriter interface that just delegates to
 * ImageIO to write PNG images.
 */
class PngImageWriter implements ImageWriter
{
    @Override
    public void writeImage(RenderedImage image, OutputStream outputStream, 
        ImageWriterParams params)
        throws IOException
    {
        ImageIO.write(image, "png", outputStream);
    }

    @Override
    public void writeImage(RenderedImage image, OutputStream outputStream) 
        throws IOException
    {
        ImageIO.write(image, "png", outputStream);
    }

    @Override
    public String getMIMEType()
    {
        return "image/png";
    }
}