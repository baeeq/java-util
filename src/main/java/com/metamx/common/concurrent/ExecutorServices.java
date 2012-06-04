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

package com.metamx.common.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.metamx.common.lifecycle.Lifecycle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServices
{
  public static ExecutorService create(Lifecycle lifecycle, ExecutorServiceConfig config)
  {
    return manageLifecycle(
        lifecycle,
        Executors.newFixedThreadPool(
            config.getNumThreads(),
            new ThreadFactoryBuilder().setDaemon(true).setNameFormat(config.getFormatString()).build()
        )
    );
  }

  public static <T extends ExecutorService> T manageLifecycle(Lifecycle lifecycle, final T service)
  {
    lifecycle.addHandler(
        new Lifecycle.Handler()
        {
          @Override
          public void start() throws Exception
          {
          }

          @Override
          public void stop()
          {
            service.shutdownNow();
          }
        }
    );

    return service;
  }
}
