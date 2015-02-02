/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 * 
 *    (C) 2002-2008, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package it.geosolutions.jaiext.colorconvert;

import it.geosolutions.jaiext.range.Range;
import it.geosolutions.jaiext.range.RangeFactory;
import it.geosolutions.jaiext.testclasses.TestBase;
import it.geosolutions.jaiext.testclasses.TestData;
import it.geosolutions.rendered.viewer.RenderedImageBrowser;

import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.media.jai.IHSColorSpace;
import javax.media.jai.JAI;
import javax.media.jai.ParameterBlockJAI;
import javax.media.jai.ROI;
import javax.media.jai.ROIShape;
import javax.media.jai.RasterFactory;
import javax.media.jai.RenderedOp;

import org.junit.Test;

/**
 * 
 * @author Nicola Lagomarsini, GeoSolutions
 * 
 * 
 * @source $URL$
 */
public class TestColorConvert extends TestBase {

    /**
     * Synthetic with Short Sample Model!
     * 
     * @throws IOException
     */
    @Test
    public void Synthetic_Short() throws IOException {

        // /////////////////////////////////////////////////////////////////////
        //
        // This test is interesting since it can be used to force the
        // creation of a sample model that uses a USHORT datatype since the
        // number of requested colors is pretty high. We are also using some
        // synthetic data where there is no NoData.
        //
        // /////////////////////////////////////////////////////////////////////

        // /////////////////////////////////////////////////////////////////////
        //
        // Set the pixel values. Because we use only one tile with one band,
        // the
        // code below is pretty similar to the code we would have if we were
        // just setting the values in a matrix.
        //
        // /////////////////////////////////////////////////////////////////////
        final BufferedImage image = getSynthetic_Short();

        ParameterBlockJAI pbj = new ParameterBlockJAI("ColorConvert");
        pbj.addSource(image);
        pbj.setParameter("colorModel", getJAICm());
        RenderedOp finalimage = JAI.create("ColorConvert", pbj);

        if (INTERACTIVE)
            RenderedImageBrowser.showChain(finalimage, false, false, null);
        else
            finalimage.getTiles();
        finalimage.dispose();

        // ROI creation
        ROI roi = new ROIShape(new Rectangle(image.getMinX() + 5, image.getMinY() + 5,
                image.getWidth() / 4, image.getHeight() / 4));

        Range nodata = RangeFactory.create((short) 5, (short) 5);

        // ROI
        pbj = new ParameterBlockJAI("ColorConvert");
        pbj.addSource(image);
        pbj.setParameter("colorModel", getJAICm());
        pbj.setParameter("roi", roi);
        finalimage = JAI.create("ColorConvert", pbj);

        if (INTERACTIVE)
            RenderedImageBrowser.showChain(finalimage, false, false, null);
        else
            finalimage.getTiles();
        finalimage.dispose();

        // NODATA
        pbj = new ParameterBlockJAI("ColorConvert");
        pbj.addSource(image);
        pbj.setParameter("colorModel", getJAICm());
        pbj.setParameter("nodata", nodata);
        finalimage = JAI.create("ColorConvert", pbj);

        if (INTERACTIVE)
            RenderedImageBrowser.showChain(finalimage, false, false, null);
        else
            finalimage.getTiles();
        finalimage.dispose();

        // NODATA AND ROI
        pbj = new ParameterBlockJAI("ColorConvert");
        pbj.addSource(image);
        pbj.setParameter("colorModel", getJAICm());
        pbj.setParameter("roi", roi);
        pbj.setParameter("nodata", nodata);
        finalimage = JAI.create("ColorConvert", pbj);

        if (INTERACTIVE)
            RenderedImageBrowser.showChain(finalimage, false, false, null);
        else
            finalimage.getTiles();
        finalimage.dispose();
    }

    /**
     * Synthetic with Float Sample Model!
     * 
     * @return {@linkplain BufferedImage}
     */
    private BufferedImage getSynthetic_Short() {
        final int width = 500;
        final int height = 500;
        final WritableRaster raster = RasterFactory.createBandedRaster(DataBuffer.TYPE_USHORT,
                width, height, 3, null);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                raster.setSample(x, y, 0, (x + y));
            }
        }
        final ColorModel cm = new ComponentColorModel(
                ColorSpace.getInstance(ColorSpace.CS_LINEAR_RGB), false, false,
                Transparency.OPAQUE, DataBuffer.TYPE_USHORT);
        final BufferedImage image = new BufferedImage(cm, raster, false, null);
        return image;
    }

    /**
     * Building a synthetic image upon a Byte sample-model.
     * 
     * @return {@linkplain BufferedImage}
     * @throws IOException
     */
    @Test
    public void Synthetic_Byte() throws IOException {

        // /////////////////////////////////////////////////////////////////////
        //
        // This test is interesting since it can be used to force the
        // creation of a sample model that uses a USHORT datatype since the
        // number of requested colors is pretty high. We are also using some
        // synthetic data where there is no NoData.
        //
        // /////////////////////////////////////////////////////////////////////

        // /////////////////////////////////////////////////////////////////////
        //
        // Set the pixel values. Because we use only one tile with one band,
        // the
        // code below is pretty similar to the code we would have if we were
        // just setting the values in a matrix.
        //
        // /////////////////////////////////////////////////////////////////////
        BufferedImage image = getSynthetic_Byte();

        ParameterBlockJAI pbj = new ParameterBlockJAI("ColorConvert");
        pbj.addSource(image);
        pbj.setParameter("colorModel", getJAIEXTCm());
        RenderedOp finalimage = JAI.create("ColorConvert", pbj);

        if (INTERACTIVE)
            RenderedImageBrowser.showChain(finalimage, false, false, null);
        else
            finalimage.getTiles();
        finalimage.dispose();

        // ROI creation
        ROI roi = new ROIShape(new Rectangle(image.getMinX() + 5, image.getMinY() + 5,
                image.getWidth() / 4, image.getHeight() / 4));

        Range nodata = RangeFactory.create((byte) 5, (byte) 5);

        // ROI
        pbj = new ParameterBlockJAI("ColorConvert");
        pbj.addSource(image);
        pbj.setParameter("colorModel", getJAIEXTCm());
        pbj.setParameter("roi", roi);
        finalimage = JAI.create("ColorConvert", pbj);

        if (INTERACTIVE)
            RenderedImageBrowser.showChain(finalimage, false, false, null);
        else
            finalimage.getTiles();
        finalimage.dispose();

        // NODATA
        pbj = new ParameterBlockJAI("ColorConvert");
        pbj.addSource(image);
        pbj.setParameter("colorModel", getJAIEXTCm());
        pbj.setParameter("nodata", nodata);
        finalimage = JAI.create("ColorConvert", pbj);

        if (INTERACTIVE)
            RenderedImageBrowser.showChain(finalimage, false, false, null);
        else
            finalimage.getTiles();
        finalimage.dispose();

        // NODATA AND ROI
        pbj = new ParameterBlockJAI("ColorConvert");
        pbj.addSource(image);
        pbj.setParameter("colorModel", getJAIEXTCm());
        pbj.setParameter("roi", roi);
        pbj.setParameter("nodata", nodata);
        finalimage = JAI.create("ColorConvert", pbj);

        if (INTERACTIVE)
            RenderedImageBrowser.showChain(finalimage, false, false, null);
        else
            finalimage.getTiles();
        finalimage.dispose();
    }

    /**
     * Building a synthetic image upon a byte sample-model.
     * 
     * @return {@linkplain BufferedImage}
     * @throws IOException
     */
    @Test
    public void Synthetic_Byte_IHS() throws IOException {

        // /////////////////////////////////////////////////////////////////////
        //
        // This test is interesting since it can be used to force the
        // creation of a sample model that uses a USHORT datatype since the
        // number of requested colors is pretty high. We are also using some
        // synthetic data where there is no NoData.
        //
        // /////////////////////////////////////////////////////////////////////

        // /////////////////////////////////////////////////////////////////////
        //
        // Set the pixel values. Because we use only one tile with one band,
        // the
        // code below is pretty similar to the code we would have if we were
        // just setting the values in a matrix.
        //
        // /////////////////////////////////////////////////////////////////////
        final BufferedImage image = getSynthetic_Byte_IHS();

        ParameterBlockJAI pbj = new ParameterBlockJAI("ColorConvert");
        pbj.addSource(image);
        pbj.setParameter("colorModel", getNotJAICm());
        RenderedOp finalimage = JAI.create("ColorConvert", pbj);

        if (INTERACTIVE)
            RenderedImageBrowser.showChain(finalimage, false, false, null);
        else
            finalimage.getTiles();
        finalimage.dispose();

        // ROI creation
        ROI roi = new ROIShape(new Rectangle(image.getMinX() + 5, image.getMinY() + 5,
                image.getWidth() / 4, image.getHeight() / 4));

        Range nodata = RangeFactory.create((byte) 5, (byte) 5);

        // ROI
        pbj = new ParameterBlockJAI("ColorConvert");
        pbj.addSource(image);
        pbj.setParameter("colorModel", getNotJAICm());
        pbj.setParameter("roi", roi);
        finalimage = JAI.create("ColorConvert", pbj);

        if (INTERACTIVE)
            RenderedImageBrowser.showChain(finalimage, false, false, null);
        else
            finalimage.getTiles();
        finalimage.dispose();

        // NODATA
        pbj = new ParameterBlockJAI("ColorConvert");
        pbj.addSource(image);
        pbj.setParameter("colorModel", getNotJAICm());
        pbj.setParameter("nodata", nodata);
        finalimage = JAI.create("ColorConvert", pbj);

        if (INTERACTIVE)
            RenderedImageBrowser.showChain(finalimage, false, false, null);
        else
            finalimage.getTiles();
        finalimage.dispose();

        // NODATA AND ROI
        pbj = new ParameterBlockJAI("ColorConvert");
        pbj.addSource(image);
        pbj.setParameter("colorModel", getNotJAICm());
        pbj.setParameter("roi", roi);
        pbj.setParameter("nodata", nodata);
        finalimage = JAI.create("ColorConvert", pbj);

        if (INTERACTIVE)
            RenderedImageBrowser.showChain(finalimage, false, false, null);
        else
            finalimage.getTiles();
        finalimage.dispose();
    }

    /**
     * Building a synthetic image upon a FLOAT sample-model.
     * 
     * @return {@linkplain BufferedImage}
     */
    private BufferedImage getSynthetic_Byte() {
        final int width = 500;
        final int height = 500;
        final WritableRaster raster = RasterFactory.createBandedRaster(DataBuffer.TYPE_BYTE, width,
                height, 3, null);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                raster.setSample(x, y, 0, (x + y));
            }
        }
        final ColorModel cm = new ComponentColorModel(
                ColorSpace.getInstance(ColorSpace.CS_LINEAR_RGB), false, false,
                Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
        final BufferedImage image = new BufferedImage(cm, raster, false, null);
        return image;
    }

    /**
     * Building a synthetic image upon a FLOAT sample-model.
     * 
     * @return {@linkplain BufferedImage}
     */
    private BufferedImage getSynthetic_Byte_IHS() {
        final int width = 500;
        final int height = 500;
        final WritableRaster raster = RasterFactory.createBandedRaster(DataBuffer.TYPE_BYTE, width,
                height, 3, null);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                raster.setSample(x, y, 0, (x + y));
            }
        }
        final ColorModel cm = new ComponentColorModel(new IHSColorSpaceJAIExt(), false, false,
                Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
        final BufferedImage image = new BufferedImage(cm, raster, false, null);
        return image;
    }

    /**
     * Spearfish test-case.
     * 
     * @throws IOException
     */
    @Test
    public void testTiff() throws IOException {

        final RenderedImage image = getTestTiff();
        ParameterBlockJAI pbj = new ParameterBlockJAI("ColorConvert");
        pbj.addSource(image);
        pbj.setParameter("colorModel", getNotJAICm());
        RenderedOp finalimage = JAI.create("ColorConvert", pbj);

        if (INTERACTIVE)
            RenderedImageBrowser.showChain(finalimage, false, false, null);
        else
            finalimage.getTiles();
        finalimage.dispose();

        // ROI creation
        ROI roi = new ROIShape(new Rectangle(image.getMinX() + 5, image.getMinY() + 5,
                image.getWidth() / 4, image.getHeight() / 4));

        Range nodata = RangeFactory.create((byte) 5, (byte) 5);

        // ROI
        pbj = new ParameterBlockJAI("ColorConvert");
        pbj.addSource(image);
        pbj.setParameter("colorModel", getNotJAICm());
        pbj.setParameter("roi", roi);
        finalimage = JAI.create("ColorConvert", pbj);

        if (INTERACTIVE)
            RenderedImageBrowser.showChain(finalimage, false, false, null);
        else
            finalimage.getTiles();
        finalimage.dispose();

        // NODATA
        pbj = new ParameterBlockJAI("ColorConvert");
        pbj.addSource(image);
        pbj.setParameter("colorModel", getNotJAICm());
        pbj.setParameter("nodata", nodata);
        finalimage = JAI.create("ColorConvert", pbj);

        if (INTERACTIVE)
            RenderedImageBrowser.showChain(finalimage, false, false, null);
        else
            finalimage.getTiles();
        finalimage.dispose();

        // NODATA AND ROI
        pbj = new ParameterBlockJAI("ColorConvert");
        pbj.addSource(image);
        pbj.setParameter("colorModel", getNotJAICm());
        pbj.setParameter("roi", roi);
        pbj.setParameter("nodata", nodata);
        finalimage = JAI.create("ColorConvert", pbj);

        if (INTERACTIVE)
            RenderedImageBrowser.showChain(finalimage, false, false, null);
        else
            finalimage.getTiles();
        finalimage.dispose();

    }

    /**
     * Building an image based on Spearfish data.
     * 
     * @return {@linkplain BufferedImage}
     * 
     * @throws IOException
     * @throws FileNotFoundException
     */
    private RenderedImage getTestTiff() throws IOException, FileNotFoundException {
        File spearfish = TestData.file(this, "test.tif");
        RenderedOp image = JAI.create("ImageRead", spearfish);
        return image;
    }

    private ColorModel getJAIEXTCm() {
        ColorSpace cs = new IHSColorSpaceJAIExt();

        final ColorModel cm = new ComponentColorModel(cs, false, false, Transparency.OPAQUE,
                DataBuffer.TYPE_BYTE);

        return cm;
    }

    private ColorModel getJAICm() {
        ColorSpace cs = IHSColorSpace.getInstance();

        final ColorModel cm = new ComponentColorModel(cs, false, false, Transparency.OPAQUE,
                DataBuffer.TYPE_USHORT);

        return cm;
    }

    private ColorModel getNotJAICm() {
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_LINEAR_RGB);

        final ColorModel cm = new ComponentColorModel(cs, false, false, Transparency.OPAQUE,
                DataBuffer.TYPE_BYTE);
        return cm;
    }
}