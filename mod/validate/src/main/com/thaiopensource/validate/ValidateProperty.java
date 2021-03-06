package com.thaiopensource.validate;

import com.thaiopensource.util.PropertyId;
import com.thaiopensource.util.PropertyMap;
import com.thaiopensource.util.PropertyMapBuilder;
import com.thaiopensource.xml.sax.Resolver;
import com.thaiopensource.xml.sax.XMLReaderCreator;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;

import javax.xml.transform.URIResolver;

/**
 * Provides common properties to control reading schemas and validation.
 *
 * @see Schema#createValidator
 * @see SchemaReader#createSchema
 * @see PropertyMap
 * @see PropertyId
 * @see com.thaiopensource.validate.prop.rng.RngProperty
 * @see com.thaiopensource.validate.prop.schematron.SchematronProperty
 * @see com.thaiopensource.validate.prop.wrap.WrapProperty
 */
public class ValidateProperty {
  /**
   * Property specifying ErrorHandler to be used for reporting errors.  The value
   * to which this PropertyId maps must be an instance of ErrorHandler.
   *
   * @see ErrorHandler
   */
  public static final ErrorHandlerPropertyId ERROR_HANDLER = new ErrorHandlerPropertyId("ERROR_HANDLER");

  /**
   * Property specifying EntityResolver to be used for resolving entities. The value
   * to which this PropertyId maps must be an instance of EntityResolver.
   *
   * @see EntityResolver
   */
  public static final EntityResolverPropertyId ENTITY_RESOLVER = new EntityResolverPropertyId("ENTITY_RESOLVER");

  /**
   * Property specifying URIResolver to be used for resolving URIs. The value
   * to which this PropertyId maps must be an instance of URIResolver.
   *
   * @see URIResolver
   */
  public static final URIResolverPropertyId URI_RESOLVER = new URIResolverPropertyId("URI_RESOLVER");

  /**
   * Property specifying Resolver to be used. The value
   * to which this PropertyId maps must be an instance of Resolver.
   *
   * @see Resolver
   */
  public static final ResolverPropertyId RESOLVER = new ResolverPropertyId("RESOLVER");

  /**
   * Property specifying SchemaResolver to be used for resolving schemas. The value
   * to which this PropertyId maps must be an instance of SchemaResolver.
   *
   * @see SchemaResolver
   */
  public static final SchemaResolverPropertyId SCHEMA_RESOLVER = new SchemaResolverPropertyId("SCHEMA_RESOLVER");

  
  /**
   * Property specifying XMLReaderCreator used to create XMLReader objects needed for
   * parsing XML documents.  The value to which this PropertyId maps must be an
   * instance of XMLReaderCreator.
   */
  public static final XMLReaderCreatorPropertyId XML_READER_CREATOR
          = new XMLReaderCreatorPropertyId("XML_READER_CREATOR");

  private ValidateProperty() { }

  /**
   * A PropertyId whose value is constrained to be an instance of
   * ErrorHandler.
   *
   * @see ErrorHandler
   */
  public static class ErrorHandlerPropertyId extends PropertyId {
    public ErrorHandlerPropertyId(String name) {
      super(name, ErrorHandler.class);
    }

    /**
     * Returns the value of the property.  This is a typesafe
     * version of <code>PropertyMap.get</code>.
     *
     * @param properties the PropertyMap to be used
     * @return the ErrorHandler to which the PropertyMap maps this PropertyId,
     * or <code>null</code> if this PropertyId is not in the PropertyMap
     * @see PropertyMap#get
     */
    public ErrorHandler get(PropertyMap properties) {
      return (ErrorHandler)properties.get(this);
    }

    /**
     * Sets the value of the property. Modifies the PropertyMapBuilder
     * so that this PropertyId is mapped to the specified value. This
     * is a typesafe version of PropertyMapBuilder.put.
     *
     * @param builder the PropertyMapBuilder to be modified
     * @param value the ErrorHandler to which this PropertyId is to be mapped
     * @return the ErrorHandler to which this PropertyId was mapped before,
     * or <code>null</code> if it was not mapped
     *
     * @see PropertyMapBuilder#put
     */
    public ErrorHandler put(PropertyMapBuilder builder, ErrorHandler value) {
      return (ErrorHandler)builder.put(this, value);
    }
  }

  /**
   * A PropertyId whose value is constrained to be an instance of
   * EntityResolver.
   *
   * @see EntityResolver
   */
  public static class EntityResolverPropertyId extends PropertyId {
    public EntityResolverPropertyId(String name) {
      super(name, EntityResolver.class);
    }

    /**
     * Returns the value of the property.  This is a typesafe
     * version of <code>PropertyMap.get</code>.
     *
     * @param properties the PropertyMap to be used
     * @return the EntityResolver to which the PropertyMap maps this PropertyId,
     * or <code>null</code> if this PropertyId is not in the PropertyMap
     * @see PropertyMap#get
     */
    public EntityResolver get(PropertyMap properties) {
      return (EntityResolver)properties.get(this);
    }

    /**
     * Sets the value of the property. Modifies the PropertyMapBuilder
     * so that this PropertyId is mapped to the specified value. This
     * is a typesafe version of PropertyMapBuilder.put.
     *
     * @param builder the PropertyMapBuilder to be modified
     * @param value the EntityResolver to which this PropertyId is to be mapped
     * @return the EntityResolver to which this PropertyId was mapped before,
     * or <code>null</code> if it was not mapped
     *
     * @see PropertyMapBuilder#put
     */
    public EntityResolver put(PropertyMapBuilder builder, EntityResolver value) {
      return (EntityResolver)builder.put(this, value);
    }
  }

  /**
   * A PropertyId whose value is constrained to be an instance of
   * URIResolver.
   *
   * @see URIResolver
   */
  public static class URIResolverPropertyId extends PropertyId {
    public URIResolverPropertyId(String name) {
      super(name, URIResolver.class);
    }

    /**
     * Returns the value of the property.  This is a typesafe
     * version of <code>PropertyMap.get</code>.
     *
     * @param properties the PropertyMap to be used
     * @return the URIResolver to which the PropertyMap maps this PropertyId,
     * or <code>null</code> if this PropertyId is not in the PropertyMap
     * @see PropertyMap#get
     */
    public URIResolver get(PropertyMap properties) {
      return (URIResolver)properties.get(this);
    }

    /**
     * Sets the value of the property. Modifies the PropertyMapBuilder
     * so that this PropertyId is mapped to the specified value. This
     * is a typesafe version of PropertyMapBuilder.put.
     *
     * @param builder the PropertyMapBuilder to be modified
     * @param value the URIResolver to which this PropertyId is to be mapped
     * @return the URIResolver to which this PropertyId was mapped before,
     * or <code>null</code> if it was not mapped
     *
     * @see PropertyMapBuilder#put
     */
    public URIResolver put(PropertyMapBuilder builder, URIResolver value) {
      return (URIResolver)builder.put(this, value);
    }
  }

  /**
   * A PropertyId whose value is constrained to be an instance of
   * Resolver.
   *
   * @see Resolver
   */
  public static class ResolverPropertyId extends PropertyId {
    public ResolverPropertyId(String name) {
      super(name, Resolver.class);
    }

    /**
     * Returns the value of the property. This is a typesafe version of
     * <code>PropertyMap.get</code>.
     * 
     * @param properties
     *          the PropertyMap to be used
     * @return the Resolver to which the PropertyMap maps this PropertyId, or
     *         <code>null</code> if this PropertyId is not in the PropertyMap
     * @see PropertyMap#get
     */
    public Resolver get(PropertyMap properties) {
      return (Resolver)properties.get(this);
    }

    /**
     * Sets the value of the property. Modifies the PropertyMapBuilder so that
     * this PropertyId is mapped to the specified value. This is a typesafe
     * version of PropertyMapBuilder.put.
     * 
     * @param builder
     *          the PropertyMapBuilder to be modified
     * @param value
     *          the Resolver to which this PropertyId is to be mapped
     * @return the Resolver to which this PropertyId was mapped before, or
     *         <code>null</code> if it was not mapped
     * 
     * @see PropertyMapBuilder#put
     */
    public Resolver put(PropertyMapBuilder builder, Resolver value) {
      return (Resolver)builder.put(this, value);
    }
  }

  /**
   * A PropertyId whose value is constrained to be an instance of
   * SchemaResolver.
   * 
   * @see SchemaResolver
   */
  public static class SchemaResolverPropertyId extends PropertyId {
    public SchemaResolverPropertyId(String name) {
      super(name, SchemaResolver.class);
    }

    /**
     * Returns the value of the property. This is a typesafe version of
     * <code>PropertyMap.get</code>.
     * 
     * @param properties
     *          the PropertyMap to be used
     * @return the SchemaResolver to which the PropertyMap maps this PropertyId,
     *         or <code>null</code> if this PropertyId is not in the
     *         PropertyMap
     * @see PropertyMap#get
     */
    public SchemaResolver get(PropertyMap properties) {
      return (SchemaResolver)properties.get(this);
    }

    /**
     * Sets the value of the property. Modifies the PropertyMapBuilder so that
     * this PropertyId is mapped to the specified value. This is a typesafe
     * version of PropertyMapBuilder.put.
     * 
     * @param builder
     *          the PropertyMapBuilder to be modified
     * @param value
     *          the SchemaResolver to which this PropertyId is to be mapped
     * @return the SchemaResolver to which this PropertyId was mapped before, or
     *         <code>null</code> if it was not mapped
     * 
     * @see PropertyMapBuilder#put
     */
    public SchemaResolver put(PropertyMapBuilder builder, SchemaResolver value) {
      return (SchemaResolver)builder.put(this, value);
    }
  }  
  /**
   * A PropertyId whose value is constrained to be an instance of
   * XMLReaderCreator.
   *
   * @see XMLReaderCreator
   */
  public static class XMLReaderCreatorPropertyId extends PropertyId {
    public XMLReaderCreatorPropertyId(String name) {
      super(name, XMLReaderCreator.class);
    }

    /**
     * Returns the value of the property.  This is a typesafe
     * version of <code>PropertyMap.get</code>.
     *
     * @param properties the PropertyMap to be used
     * @return the XMLReaderCreator to which the PropertyMap maps this PropertyId,
     * or <code>null</code> if this PropertyId is not in the PropertyMap
     * @see PropertyMap#get
     */
    public XMLReaderCreator get(PropertyMap properties) {
      return (XMLReaderCreator)properties.get(this);
    }

    /**
     * Sets the value of the property. Modifies the PropertyMapBuilder
     * so that this PropertyId is mapped to the specified value. This
     * is a typesafe version of PropertyMapBuilder.put.
     *
     * @param builder the PropertyMapBuilder to be modified
     * @param value the XMLReaderCreator to which this PropertyId is to be mapped
     * @return the XMLReaderCreator to which this PropertyId was mapped before,
     * or <code>null</code> if it was not mapped
     *
     * @see PropertyMapBuilder#put
     */
    public XMLReaderCreator put(PropertyMapBuilder builder, XMLReaderCreator value) {
      return (XMLReaderCreator)builder.put(this, value);
    }
  }
}
