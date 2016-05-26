// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: server-protobuf/izou.proto

package org.intellimate.server.proto;

/**
 * Protobuf type {@code intellimate.Izou}
 */
public  final class Izou extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:intellimate.Izou)
    IzouOrBuilder {
  // Use Izou.newBuilder() to construct.
  private Izou(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private Izou() {
    id_ = 0;
    version_ = "";
    downloadLink_ = "";
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private Izou(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 8: {

            id_ = input.readInt32();
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            version_ = s;
            break;
          }
          case 26: {
            java.lang.String s = input.readStringRequireUtf8();

            downloadLink_ = s;
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return org.intellimate.server.proto.IzouOuterClass.internal_static_intellimate_Izou_descriptor;
  }

  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.intellimate.server.proto.IzouOuterClass.internal_static_intellimate_Izou_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.intellimate.server.proto.Izou.class, org.intellimate.server.proto.Izou.Builder.class);
  }

  public static final int ID_FIELD_NUMBER = 1;
  private int id_;
  /**
   * <code>optional int32 id = 1;</code>
   */
  public int getId() {
    return id_;
  }

  public static final int VERSION_FIELD_NUMBER = 2;
  private volatile java.lang.Object version_;
  /**
   * <code>optional string version = 2;</code>
   */
  public java.lang.String getVersion() {
    java.lang.Object ref = version_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      version_ = s;
      return s;
    }
  }
  /**
   * <code>optional string version = 2;</code>
   */
  public com.google.protobuf.ByteString
      getVersionBytes() {
    java.lang.Object ref = version_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      version_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int DOWNLOAD_LINK_FIELD_NUMBER = 3;
  private volatile java.lang.Object downloadLink_;
  /**
   * <code>optional string download_link = 3;</code>
   */
  public java.lang.String getDownloadLink() {
    java.lang.Object ref = downloadLink_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      downloadLink_ = s;
      return s;
    }
  }
  /**
   * <code>optional string download_link = 3;</code>
   */
  public com.google.protobuf.ByteString
      getDownloadLinkBytes() {
    java.lang.Object ref = downloadLink_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      downloadLink_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (id_ != 0) {
      output.writeInt32(1, id_);
    }
    if (!getVersionBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessage.writeString(output, 2, version_);
    }
    if (!getDownloadLinkBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessage.writeString(output, 3, downloadLink_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (id_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, id_);
    }
    if (!getVersionBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(2, version_);
    }
    if (!getDownloadLinkBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(3, downloadLink_);
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  public static org.intellimate.server.proto.Izou parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.intellimate.server.proto.Izou parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.intellimate.server.proto.Izou parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.intellimate.server.proto.Izou parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.intellimate.server.proto.Izou parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static org.intellimate.server.proto.Izou parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.intellimate.server.proto.Izou parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static org.intellimate.server.proto.Izou parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.intellimate.server.proto.Izou parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static org.intellimate.server.proto.Izou parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(org.intellimate.server.proto.Izou prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessage.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code intellimate.Izou}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:intellimate.Izou)
      org.intellimate.server.proto.IzouOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.intellimate.server.proto.IzouOuterClass.internal_static_intellimate_Izou_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.intellimate.server.proto.IzouOuterClass.internal_static_intellimate_Izou_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.intellimate.server.proto.Izou.class, org.intellimate.server.proto.Izou.Builder.class);
    }

    // Construct using org.intellimate.server.proto.Izou.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      id_ = 0;

      version_ = "";

      downloadLink_ = "";

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.intellimate.server.proto.IzouOuterClass.internal_static_intellimate_Izou_descriptor;
    }

    public org.intellimate.server.proto.Izou getDefaultInstanceForType() {
      return org.intellimate.server.proto.Izou.getDefaultInstance();
    }

    public org.intellimate.server.proto.Izou build() {
      org.intellimate.server.proto.Izou result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public org.intellimate.server.proto.Izou buildPartial() {
      org.intellimate.server.proto.Izou result = new org.intellimate.server.proto.Izou(this);
      result.id_ = id_;
      result.version_ = version_;
      result.downloadLink_ = downloadLink_;
      onBuilt();
      return result;
    }

    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof org.intellimate.server.proto.Izou) {
        return mergeFrom((org.intellimate.server.proto.Izou)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.intellimate.server.proto.Izou other) {
      if (other == org.intellimate.server.proto.Izou.getDefaultInstance()) return this;
      if (other.getId() != 0) {
        setId(other.getId());
      }
      if (!other.getVersion().isEmpty()) {
        version_ = other.version_;
        onChanged();
      }
      if (!other.getDownloadLink().isEmpty()) {
        downloadLink_ = other.downloadLink_;
        onChanged();
      }
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      org.intellimate.server.proto.Izou parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (org.intellimate.server.proto.Izou) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int id_ ;
    /**
     * <code>optional int32 id = 1;</code>
     */
    public int getId() {
      return id_;
    }
    /**
     * <code>optional int32 id = 1;</code>
     */
    public Builder setId(int value) {
      
      id_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional int32 id = 1;</code>
     */
    public Builder clearId() {
      
      id_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object version_ = "";
    /**
     * <code>optional string version = 2;</code>
     */
    public java.lang.String getVersion() {
      java.lang.Object ref = version_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        version_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>optional string version = 2;</code>
     */
    public com.google.protobuf.ByteString
        getVersionBytes() {
      java.lang.Object ref = version_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        version_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>optional string version = 2;</code>
     */
    public Builder setVersion(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      version_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional string version = 2;</code>
     */
    public Builder clearVersion() {
      
      version_ = getDefaultInstance().getVersion();
      onChanged();
      return this;
    }
    /**
     * <code>optional string version = 2;</code>
     */
    public Builder setVersionBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      version_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object downloadLink_ = "";
    /**
     * <code>optional string download_link = 3;</code>
     */
    public java.lang.String getDownloadLink() {
      java.lang.Object ref = downloadLink_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        downloadLink_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>optional string download_link = 3;</code>
     */
    public com.google.protobuf.ByteString
        getDownloadLinkBytes() {
      java.lang.Object ref = downloadLink_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        downloadLink_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>optional string download_link = 3;</code>
     */
    public Builder setDownloadLink(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      downloadLink_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional string download_link = 3;</code>
     */
    public Builder clearDownloadLink() {
      
      downloadLink_ = getDefaultInstance().getDownloadLink();
      onChanged();
      return this;
    }
    /**
     * <code>optional string download_link = 3;</code>
     */
    public Builder setDownloadLinkBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      downloadLink_ = value;
      onChanged();
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:intellimate.Izou)
  }

  // @@protoc_insertion_point(class_scope:intellimate.Izou)
  private static final org.intellimate.server.proto.Izou DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.intellimate.server.proto.Izou();
  }

  public static org.intellimate.server.proto.Izou getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Izou>
      PARSER = new com.google.protobuf.AbstractParser<Izou>() {
    public Izou parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new Izou(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Izou> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Izou> getParserForType() {
    return PARSER;
  }

  public org.intellimate.server.proto.Izou getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

