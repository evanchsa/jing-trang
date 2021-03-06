package com.thaiopensource.xml.sax;

import com.thaiopensource.util.Uri;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.sax.SAXSource;
import java.io.IOException;
import java.net.URL;

public class Resolver implements XMLReaderCreator {
  private final URIResolver uriResolver;
  private final URIResolver uriResolverWrapper;
  private final EntityResolver entityResolver;
  private final XMLReaderCreator xrc;

  private Resolver(XMLReaderCreator xrc, URIResolver uriResolver, EntityResolver entityResolver) {
    this.xrc = xrc == null ? new Jaxp11XMLReaderCreator() : xrc;
    this.entityResolver = entityResolver;
    this.uriResolver = uriResolver;
    this.uriResolverWrapper = new URIResolverImpl();
  }

  public XMLReader createXMLReader() throws SAXException {
    XMLReader xr = xrc.createXMLReader();
    if (entityResolver != null)
      xr.setEntityResolver(entityResolver);
    return xr;
  }

  /**
   * Note that the XMLReader in the returned SAXSource may be null; call createXMLReader if you need one.
   * The InputSource in the returned SAXSource must be non-null, and the systemId in that InputSource
   * must also be non-null.
   * @param href
   * @param base
   * @return
   * @throws IOException
   * @throws SAXException
   */
  public SAXSource resolve(String href, String base) throws IOException, SAXException {
    Source source = null;
    if (uriResolver != null) {
      try {
        source = uriResolver.resolve(href, base);
      }
      catch (TransformerException e) {
        Throwable t = e.getException();
        if (t.getMessage() == null || t.getMessage().equals(e.getMessage())) {
          // it's just a wrapper
          if (t instanceof SAXException)
            throw (SAXException)t;
          if (t instanceof IOException)
            throw (IOException)t;
          if (t instanceof Exception)
            throw new SAXException((Exception)t);
        }
        throw new SAXException(e);
      }
    }
    return resolveFinish(source, href, base);
  }

  /**
   * Finish off the resolution process using the Source from the URIResolver. This is split off
   * from resolve so that any exception returned from the uriResolver doesn't get wrapped when it is called
   * from the URIResolver wrapper returned by getURIResolver.
   * @param source the Source returned from URIResolver.resolve; may be null
   * @param href
   * @param base
   * @return
   * @throws IOException
   * @throws SAXException
   */
  private SAXSource resolveFinish(Source source, String href, String base) throws IOException, SAXException {
    SAXSource saxSource = null;
    if (source != null) {
      if (source instanceof SAXSource) {
        saxSource = (SAXSource)source;
      }
      else {
        InputSource in = SAXSource.sourceToInputSource(source);
        if (in == null) {
          String systemId = source.getSystemId();
          in = new InputSource(systemId);
        }
        saxSource = new SAXSource(in);
      }
    }
    if (saxSource == null) {
      InputSource in = null;
      String uri = Uri.resolve(base, href);
      if (uriResolver == null && entityResolver != null)
        in = entityResolver.resolveEntity(null, uri);
      if (in == null)
        in = new InputSource(uri);
      saxSource = new SAXSource(in);
    }
    return saxSource;
  }

  // Note that this is not static so we can access the methods and fields of the Resolver.
  private class URIResolverImpl implements URIResolver {
    public Source resolve(String href, String base) throws TransformerException {
      Source source = null;
      if (uriResolver != null)
        source = uriResolver.resolve(href, base);
      try {
        SAXSource saxSource = resolveFinish(source, href, base);
        if (saxSource.getXMLReader() == null)
          saxSource = new SAXSource(createXMLReader(), saxSource.getInputSource());
        return saxSource;
      }
      catch (SAXException e) {
        throw new TransformerException(e);
      }
      catch (IOException e) {
        throw new TransformerException(e);
      }
    }
  }

  /**
   * Return a wrapper around this Resolver that allows it to be used as a URIResolver.
   * (for use with Transformer).
   * @return a URIResolver, never null.
   * @see javax.xml.transform.Transformer
   */
  public URIResolver getURIResolver() {
    // Always return a wrapper, so that the right XMLReader and EntityResolver gets used.
    return uriResolverWrapper;
  }

  public InputSource open(InputSource in) throws IOException, SAXException {
    if (in.getCharacterStream() != null || in.getByteStream() != null)
      return in;
    String systemId = in.getSystemId();
    if (systemId == null)
      throw new IllegalArgumentException("byteStream, charStream and systemId of the InputSource are all null");
    String uri = Uri.escapeDisallowedChars(systemId);
    URL url = new URL(uri);
    InputSource opened = new InputSource(systemId);
    opened.setPublicId(in.getPublicId());
    opened.setEncoding(in.getEncoding());
    // XXX if encoding is null, should use charset parameter of content-type to set encoding in text/xml case
    opened.setByteStream(url.openStream());
    return opened;
  }

  public EntityResolver getEntityResolver() {
    return entityResolver;
  }

  public static Resolver newInstance() {
    return new Resolver(null, null, null);
  }

  public static Resolver newInstance(URIResolver uriResolver) {
    return new Resolver(null, uriResolver, null);
  }

  public static Resolver newInstance(EntityResolver entityResolver) {
    return new Resolver(null, null, entityResolver);
  }

  public static Resolver newInstance(URIResolver uriResolver, EntityResolver entityResolver) {
    return new Resolver(null, uriResolver, entityResolver);
  }

  public static Resolver newInstance(XMLReaderCreator xrc, URIResolver uriResolver, EntityResolver entityResolver) {
    return new Resolver(xrc, uriResolver, entityResolver);
  }

  /**
   *
   * @param className name of a class that implements URIResolver or EntityResolver or both
   * @param loader
   * @return
   */
  public static Resolver newInstance(String className, ClassLoader loader) throws ResolverInstantiationException {
    Object obj;
    try {
      if (loader == null) {
        loader = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
          loader = ClassLoader.getSystemClassLoader();
          if (loader == null)
            throw new ResolverInstantiationException("no class loader");
        }
      }
      obj = loader.loadClass(className).newInstance();
    }
    catch (Exception e) {
      throw new ResolverInstantiationException(e);
    }
    if (obj instanceof Resolver)
      return (Resolver)obj;
    EntityResolver entityResolver = null;
    URIResolver uriResolver = null;
    if (obj instanceof EntityResolver)
      entityResolver = (EntityResolver)obj;
    if (obj instanceof URIResolver)
      uriResolver = (URIResolver)obj;
    if (entityResolver == null && uriResolver == null)
      throw new ResolverInstantiationException(className + " not an instance of javax.xml.transform.URIResolver, org.xml.sax.EntityResolver or com.thaiopensource.xml.sax.Resolver");
    return new Resolver(null, uriResolver, entityResolver);
  }

}
