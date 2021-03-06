/*
 * Copyright 2011,2012 Metamarkets Group Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.metamx.common.exception;

import com.google.common.collect.Maps;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Map;

/**
 */
public class FormattedException extends RuntimeException
{
  // High level error codes
  public static enum ErrorCode
  {
    S3BUCKET_NOT_FOUND,
    S3PATH_NOT_FOUND,
    FILE_NOT_FOUND,
    UNPARSABLE_HEADER,
    UNPARSABLE_ROW,
    MISSING_TIMESTAMP,
    UNPARSABLE_TIMESTAMP,
    MISSING_METRIC,
    UNPARSABLE_METRIC,
    SERVER_ERROR,
    AUTHENTICATION_FILE_NOT_FOUND,
    AUTHENTICATION_ERROR,
    IO_ERROR
  }

  // More specific error codes
  public static enum SubErrorCode
  {
    DUPLICATE_KEY,
    INVALID_CHARACTER,
    MISMATCH
  }

  public static class Builder
  {
    private ErrorCode errorCode;
    private Map<String, Object> details;
    private String message;

    public Builder()
    {
      details = Maps.newHashMap();
    }

    public Builder fromException(FormattedException e)
    {
      this.errorCode = e.errorCode;
      this.details = e.details;
      this.message = e.message;
      return this;
    }

    public Builder withErrorCode(ErrorCode errorCode)
    {
      this.errorCode = errorCode;
      return this;
    }

    public Builder withDetails(Map<String, Object> details)
    {
      this.details.clear();
      this.details.putAll(details);
      return this;
    }

    public Builder withAppendedDetails(Map<String, Object> details)
    {
      this.details.putAll(details);
      return this;
    }

    public Builder withMessage(String message)
    {
      this.message = message;
      return this;
    }

    public FormattedException build()
    {
      return new FormattedException(errorCode, details, message);
    }
  }

  private final ErrorCode errorCode;
  private final Map<String, Object> details;
  private final String message;

  private FormattedException(ErrorCode errorCode, Map<String, Object> details, String message)
  {
    this.errorCode = errorCode;
    this.details = details;
    this.message = message;
  }

  @JsonProperty
  public ErrorCode getErrorCode()
  {
    return errorCode;
  }

  @JsonProperty
  public Map<String, Object> getDetails()
  {
    return details;
  }

  @JsonProperty
  public String getMessage()
  {
    return message;
  }
}
