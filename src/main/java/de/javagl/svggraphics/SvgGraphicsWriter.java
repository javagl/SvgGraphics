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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.batik.svggen.SVGGraphics2D;

/**
 * Utility methods to write Apache Batik <code>SVGGraphics2D</code> objects 
 * into SVG files.
 */
public class SvgGraphicsWriter
{
    /**
     * The file chooser that will be opened in {@link #save(SVGGraphics2D)}
     */
    private static JFileChooser fileChooser;
    
    /**
     * Save the given SVG graphics as a user-selected SVG file. This will
     * open a file chooser that allows selecting the target file. If 
     * the selected file does not have the extension <code>".svg"</code>,
     * it will be appended. If the file already exists, the user will be
     * asked for a confirmation to overwrite the file.
     * 
     * @param svgGraphics The SVG graphics to save
     */
    public static void save(SVGGraphics2D svgGraphics)
    {
        if (fileChooser == null)
        {
            fileChooser = new JFileChooser();
            FileFilter fileFilter =
                new FileNameExtensionFilter("SVG file", "svg");
            fileChooser.setFileFilter(fileFilter);
        }
        
        int saveOption = fileChooser.showSaveDialog(null);
        if (saveOption != JFileChooser.APPROVE_OPTION)
        {
            return;
        }
        
        File file = fileChooser.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(".svg"))
        {
            file = new File(file + ".svg");
        }
        if (file.exists())
        {
            int overwriteOption = JOptionPane.showConfirmDialog(null,
                "The selected file already exists. Should it be overwritten?", 
                "File already exists", JOptionPane.YES_NO_CANCEL_OPTION);
            if (overwriteOption == JOptionPane.NO_OPTION)
            {
                save(svgGraphics);
                return;
            }
            if (overwriteOption == JOptionPane.CANCEL_OPTION)
            {
                return;
            }
        }
        try
        {
            write(svgGraphics, file);
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, 
                "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Write the given SVG graphics to the given file. If the file already
     * exists, it will be overwritten.
     * 
     * @param svgGraphics The SVG graphics
     * @param file The target file
     * @throws IOException If an IO error occurs
     */
    public static void write(SVGGraphics2D svgGraphics, File file)
        throws IOException
    {
        try (OutputStream outputStream = new FileOutputStream(file))
        {
            write(svgGraphics, outputStream);
        }
    }

    /**
     * Write the given SVG graphics as SVG to the given output stream. The 
     * caller is responsible for closing the given stream.
     * 
     * @param svgGraphics The SVG graphics
     * @param outputStream The output stream
     * @throws IOException If an IO error occurs
     */
    public static void write(SVGGraphics2D svgGraphics, 
        OutputStream outputStream) throws IOException
    {
        Writer writer = new OutputStreamWriter(outputStream, "UTF-8");
        boolean useCss = true;
        svgGraphics.stream(writer, useCss);
    }

    /**
     * Private constructor to prevent instantiation
     */
    private SvgGraphicsWriter()
    {
        // Private constructor to prevent instantiation
    }
}
