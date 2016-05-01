// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: server-protobuf/http_request.proto

package org.intellimate.server.proto;

public interface HttpRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:intellimate.HttpRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>optional string url = 1;</code>
   */
  java.lang.String getUrl();
  /**
   * <code>optional string url = 1;</code>
   */
  com.google.protobuf.ByteString
      getUrlBytes();

  /**
   * <code>repeated .intellimate.HttpRequest.Param params = 2;</code>
   */
  java.util.List<org.intellimate.server.proto.HttpRequest.Param> 
      getParamsList();
  /**
   * <code>repeated .intellimate.HttpRequest.Param params = 2;</code>
   */
  org.intellimate.server.proto.HttpRequest.Param getParams(int index);
  /**
   * <code>repeated .intellimate.HttpRequest.Param params = 2;</code>
   */
  int getParamsCount();
  /**
   * <code>repeated .intellimate.HttpRequest.Param params = 2;</code>
   */
  java.util.List<? extends org.intellimate.server.proto.HttpRequest.ParamOrBuilder> 
      getParamsOrBuilderList();
  /**
   * <code>repeated .intellimate.HttpRequest.Param params = 2;</code>
   */
  org.intellimate.server.proto.HttpRequest.ParamOrBuilder getParamsOrBuilder(
      int index);

  /**
   * <code>optional string method = 3;</code>
   */
  java.lang.String getMethod();
  /**
   * <code>optional string method = 3;</code>
   */
  com.google.protobuf.ByteString
      getMethodBytes();

  /**
   * <code>optional string content_type = 4;</code>
   */
  java.lang.String getContentType();
  /**
   * <code>optional string content_type = 4;</code>
   */
  com.google.protobuf.ByteString
      getContentTypeBytes();

  /**
   * <code>optional bytes body = 5;</code>
   */
  com.google.protobuf.ByteString getBody();
}