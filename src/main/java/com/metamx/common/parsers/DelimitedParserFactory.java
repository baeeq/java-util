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

package com.metamx.common.parsers;

import com.metamx.common.exception.FormattedException;

import java.util.List;

/**
 */
public class DelimitedParserFactory implements ParserFactory
{
  @Override
  public Parser makeParser(String delimiter, String header, List<String> columns) throws FormattedException
  {
    final DelimitedParser parser = new DelimitedParser(delimiter);
    
    if (columns != null) {
      parser.setFieldNames(columns);
    } else if (header != null) {
      parser.setFieldNames(header);
    }

    return parser;
  }
}
