// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: server-protobuf/izou.proto

package org.intellimate.server.proto;

public final class IzouOuterClass {
  private IzouOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_intellimate_Izou_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_intellimate_Izou_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\032server-protobuf/izou.proto\022\013intellimat" +
      "e\":\n\004Izou\022\n\n\002id\030\001 \001(\005\022\017\n\007version\030\002 \001(\t\022\025" +
      "\n\rdownload_link\030\003 \001(\tB \n\034org.intellimate" +
      ".server.protoP\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_intellimate_Izou_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_intellimate_Izou_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_intellimate_Izou_descriptor,
        new java.lang.String[] { "Id", "Version", "DownloadLink", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
